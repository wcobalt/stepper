package com.drartgames.stepper.initializer;

import com.drartgames.stepper.*;
import com.drartgames.stepper.display.*;
import com.drartgames.stepper.exceptions.*;
import com.drartgames.stepper.sl.DefaultSLInterpreter;
import com.drartgames.stepper.sl.analyzer.DefaultSLScriptLoader;
import com.drartgames.stepper.sl.analyzer.DefaultScriptLoaderFacade;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.analyzer.SLScriptLoader;
import com.drartgames.stepper.sl.analyzer.ScriptLoaderFacade;
import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.Scene;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultStepperInitializer implements Initializer {
    private Logger logger = Logger.getLogger(DefaultStepperInitializer.class.getName());

    private static final String MANIFEST_FILE_NAME = "manifest.smf";
    private static final String SPLASH_SCREEN_PATH = "data/splash.png";
    private static final String LOADING_PICTURE_PATH = "data/loading.png";
    private static final String SCENES_PATH = "scenes";

    private String questName;
    private File questsDirectory;
    private List<ParameterHandler> parametersHandlers;

    private Manifest manifest;
    private SLInterpreter interpreter;
    private Display display;
    private ScriptLoaderFacade scriptLoaderFacade;
    private Picture splashScreen, loadingPicture;

    private File questDirectory;

    private class HelpParameterHandler implements ParameterHandler {
        private String HELP = "--help";
        private String HELP_SHORT = "-h";

        @Override
        public boolean hasArgument() {
            return false;
        }

        @Override
        public void handle(Initializer initializer, String parameter) {
            initializer.logn("Stepper " + Launcher.getVersion().getFullStringVersion() + " (SL "
                    + DefaultSLInterpreter.SL_VERSION.getFullStringVersion() + ")\n" +//@fixme it's not good to use DefaultSlInt directly instead of initializer.getInterpreter()
                    "by Wert Cobalt\n");

            for (ParameterHandler handler : initializer.getParametersHandlers()) {
                initializer.logn(handler.getParameter() +
                        (handler.hasAlias() ? " " + handler.getParameterAlias() : "") +
                        (handler.hasArgument() ? " " + handler.getArgumentHelp() : "") + " --- " + handler.getHelp());
            }

            initializer.logn("");
        }

        @Override
        public String getParameter() {
            return HELP_SHORT;
        }

        @Override
        public String getParameterAlias() {
            return HELP;
        }

        @Override
        public boolean hasAlias() {
            return true;
        }

        @Override
        public String getHelp() {
            return "shows help";
        }

        @Override
        public String getArgumentHelp() {
            return "";
        }
    }

    private class VersionParameterHandler implements ParameterHandler {
        private String VERSION = "--SL_VERSION";
        private String VERSION_SHORT = "-v";

        @Override
        public boolean hasArgument() {
            return false;
        }

        @Override
        public void handle(Initializer initializer, String parameter) {
            initializer.logn(Launcher.getVersion().getFullStringVersion());
        }

        @Override
        public String getParameter() {
            return VERSION_SHORT;
        }

        @Override
        public String getParameterAlias() {
            return VERSION;
        }

        @Override
        public boolean hasAlias() {
            return true;
        }

        @Override
        public String getHelp() {
            return "shows version of Stepper";
        }

        @Override
        public String getArgumentHelp() {
            return "";
        }
    }

    private class QuestParameterHandler implements ParameterHandler {
        private String QUEST = "--quest";
        private String QUEST_SHORT = "-q";

        @Override
        public boolean hasArgument() {
            return true;
        }

        @Override
        public void handle(Initializer initializer, String parameter) {
            String processedParameter = parameter.replaceAll("\\s", "_").toLowerCase();

            initializer.setCurrentQuestName(processedParameter);
        }

        @Override
        public String getParameter() {
            return QUEST_SHORT;
        }

        @Override
        public String getParameterAlias() {
            return QUEST;
        }

        @Override
        public boolean hasAlias() {
            return true;
        }

        @Override
        public String getHelp() {
            return "sets name of quest to be loaded";
        }

        @Override
        public String getArgumentHelp() {
            return "\"<quest_name>\"";
        }
    }

    private class QuestsDirectoryParameterHandler implements ParameterHandler {
        private String QUESTS_DIRECTORY = "--quests-dir";
        private String QUESTS_DIRECTORY_SHORT = "-d";

        @Override
        public boolean hasArgument() {
            return true;
        }

        @Override
        public void handle(Initializer initializer, String parameter) {
            initializer.setQuestsDirectory(new File(parameter));
        }

        @Override
        public String getParameter() {
            return QUESTS_DIRECTORY_SHORT;
        }

        @Override
        public String getParameterAlias() {
            return QUESTS_DIRECTORY;
        }

        @Override
        public boolean hasAlias() {
            return true;
        }

        @Override
        public String getHelp() {
            return "sets directory (relative to work. dir.) where the data of the quests lies";
        }

        @Override
        public String getArgumentHelp() {
            return "\"<quests_dir>\"";
        }
    }

    public DefaultStepperInitializer() {
        parametersHandlers = new ArrayList<>();

        parametersHandlers.add(new HelpParameterHandler());
        parametersHandlers.add(new VersionParameterHandler());
        parametersHandlers.add(new QuestParameterHandler());
        parametersHandlers.add(new QuestsDirectoryParameterHandler());
    }

    @Override
    public void initialize(String... args) throws SLVersionMismatchException {
        for (int i = 0; i < args.length; i++) {
            String parameter = args[i];

            boolean wasFound = false;

            for (ParameterHandler handler : parametersHandlers) {
                if (parameter.equals(handler.getParameter()) ||
                        (handler.hasAlias() && parameter.equals(handler.getParameterAlias()))) {

                    String arg = "";

                    if (handler.hasArgument()) {
                        if (i + 1 == args.length)
                            throw new IllegalArgumentException(handler.getParameter() + " parameter demands an argument");

                        arg = args[++i];
                    }

                    handler.handle(this, arg);

                    wasFound = true;

                    break;
                }
            }

            if (!wasFound)
                throw new IllegalArgumentException("Undefined parameter " + parameter);
        }

        ManifestLoader manifestLoader = new DefaultManifestLoader();

        questDirectory = new File(questsDirectory.getAbsolutePath() + "/" + questName);

        try {
            String manifestFile = questDirectory.getAbsolutePath() + "/" + MANIFEST_FILE_NAME;
            manifest = manifestLoader.loadManifest(new File(manifestFile));
        } catch (IOException exc) {
            logger.log(Level.SEVERE, "Unable to read content from " + MANIFEST_FILE_NAME, exc);

            return;
        }

        //@todo should display need initializer???
        display = new DefaultDisplay(manifest.getQuestName(), this);
        display.initialize();

        interpreter = new DefaultSLInterpreter(display);

        if (!interpreter.getSLVersion().isHigherOrEqualThan(manifest.getRequiredSLVersion())) {
            throw new SLVersionMismatchException("Required by " + questName + " SL version (" +
                    manifest.getRequiredSLVersion().getStringVersion() + ") is higher than the interpreter can support (" +
                    interpreter.getSLVersion().getStringVersion() + ")");
        }

        scriptLoaderFacade = new DefaultScriptLoaderFacade();

        try {
            splashScreen = new DefaultPicture(SPLASH_SCREEN_PATH);
            loadingPicture = new DefaultPicture(LOADING_PICTURE_PATH);
        } catch (IOException exc) {
            logger.log(Level.SEVERE, "Unable to load splash screen image", exc);
        }

        initDevConsole();
    }

    @Override
    public void run() {
        display.run();

        ImageDescriptor splashScreenDescriptor = display.addPicture(splashScreen, 1.0f, 0.0f, 0.0f);
        ImageDescriptor loadingIconDescriptor = display.addPicture(loadingPicture, 0.04f, 0.94f, 0.9f);
        TextDescriptor textDescriptor = display.addText("Загрузка скриптов", 0.1f, 1.0f, 0.83f, 0.92f);

        String pathToScenes = questsDirectory.getAbsolutePath() + "/" + questName + "/" + SCENES_PATH;
        scriptLoaderFacade.load(interpreter, new File(pathToScenes), (event) -> {
            switch (event) {
                case SCRIPTS_LOADED:
                    textDescriptor.setMessage("Загрузка сцен");

                    break;

                case SCENES_LOADED:
                    textDescriptor.setMessage("Загрузка ресурсов");

                    break;
            }
        });

        //init all the scenes
        try {
            interpreter.initialize();
        } catch (SLRuntimeException exc) {
            logger.log(Level.SEVERE, "Runtime error when scenes initialization", exc);
        }

        display.awaitForKey(KeyEvent.VK_ENTER, (descriptor) -> {
            runInterpreter(interpreter);

            splashScreenDescriptor.setDoFree(true);
            loadingIconDescriptor.setDoFree(true);
            textDescriptor.setDoFree(true);
            descriptor.setDoFree(true);
        });

        //@todo make a fucking ability to localize the captions
        textDescriptor.setMessage("Нажмите ENTER");
    }

    private void runInterpreter(SLInterpreter slInterpreter) {
        Scene scene = slInterpreter.getScenesManager().getSceneByName(manifest.getInitSceneName());
        try {
            slInterpreter.getScenesManager().setInitScene(scene);
        } catch (NoInitSceneException exc) {
            logger.log(Level.SEVERE, "Unable to set init scene. Check its name in manifest.", exc);
        }

        try {
            slInterpreter.run();
        } catch (SLRuntimeException exc) {
            logger.log(Level.SEVERE, "Runtime SL exception: ", exc);
        }
    }

    private void initDevConsole() {
        Thread inputHandler = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                Pattern commandPattern = Pattern.compile("([a-zA-Z_0-9]+)\\s*(.*)");

                while (true) {
                    String commandString = reader.readLine();

                    System.out.println("Dev console's executing: " + commandString + "\n");
                    //show_desc text/image/audio/animation/keyawait/input
                    //rerun - may fuck up everything
                    //exec

                    Matcher matcher = commandPattern.matcher(commandString);

                    if (matcher.find() && matcher.groupCount() >= 1) {
                        String command = matcher.group(1);
                        String argument = matcher.group(2);

                        switch (command) {
                            case "show_desc":
                                List<? extends Descriptor> descriptors = null;
                                DisplayState state = display.getDisplayState();

                                switch (argument) {
                                    case "text":
                                        descriptors = state.getTextDescriptors();

                                        break;
                                    case "audio":
                                        descriptors = state.getAudioDescriptors();

                                        break;
                                    case "image":
                                        descriptors = state.getImageDescriptors();

                                        break;
                                    case "animation":
                                        descriptors = state.getAnimationDescriptors();

                                        break;
                                    case "keyawait":
                                        descriptors = state.getKeyAwaitDescriptors();

                                        break;
                                    case "input":
                                        descriptors = state.getInputDescriptors();

                                        break;
                                    default:
                                        System.out.println("Undefined descs to show: " + argument);
                                }

                                for (Descriptor descriptor : descriptors) {
                                    System.out.println(descriptor.getClass().getName() + ": " + descriptor.toString());
                                }

                                break;
                            case "rerun":
                                display.stop();
                                display.run();

                                break;
                            case "exec":
                                String fullScene = "scene __auto_scene {\n\taction __auto_act {\n\t\t" + argument + "\n\t}\n}";

                                SLScriptLoader scriptLoader = new DefaultSLScriptLoader();

                                try {
                                    Scene scene = scriptLoader.load(fullScene).get(0);
                                    Action action = scene.getActionByName("__auto_act");

                                    display.addWork((descriptor) -> {
                                        try {
                                            descriptor.setDoFree(true);

                                            interpreter.executeAction(scene.getActionByName("__auto_act"));
                                        } catch (SLRuntimeException exc) {
                                            logger.log(Level.SEVERE, "Unable to execute statement", exc);
                                        }
                                    });
                                } catch (ParseException exc) {
                                    logger.log(Level.SEVERE, "Unable to parse statement", exc);
                                } catch (AnalysisException exc) {
                                    logger.log(Level.SEVERE, "Unable to analyze statement", exc);
                                }

                                break;
                            case "threads":
                                for (Thread thread : Thread.getAllStackTraces().keySet()) {
                                    System.out.println(thread.getName());
                                }

                                break;
                            default:
                                System.out.println("Unsupported command: " + command);
                        }

                        System.out.println();
                    } else
                        System.out.println("Unsupported format. Check your syntax");
                }
            } catch (IOException exc) {
                logger.log(Level.SEVERE, "Unable to get input from System.in in dev console", exc);
            }
        }, "Stepper Dev Console Thread");

        inputHandler.start();
    }

    @Override
    public void log(String message) {
        System.out.print(message);
    }

    @Override
    public void logn(String message) {
        log(message + System.lineSeparator());
    }

    public List<ParameterHandler> getParametersHandlers() {
        return parametersHandlers;
    }

    @Override
    public void setCurrentQuestName(String questName) {
        this.questName = questName;
    }

    @Override
    public void setQuestsDirectory(File directory) {
        questsDirectory = directory;
    }

    @Override
    public String getCurrentQuestName() {
        return questName;
    }

    @Override
    public File getQuestsDirectory() {
        return questsDirectory;
    }

    @Override
    public File getQuestDirectory() {
        return questDirectory;
    }

    @Override
    public SLInterpreter getInterpreter() {
        return interpreter;
    }

    @Override
    public Manifest getManifest() {
        return manifest;
    }

    @Override
    public ScriptLoaderFacade getScriptLoaderFacade() {
        return scriptLoaderFacade;
    }

    @Override
    public Display getDisplay() {
        return display;
    }
}
