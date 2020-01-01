package com.drartgames.stepper.initializer;

import com.drartgames.stepper.*;
import com.drartgames.stepper.display.*;
import com.drartgames.stepper.exceptions.NoInitSceneException;
import com.drartgames.stepper.exceptions.SLVersionMismatchException;
import com.drartgames.stepper.sl.DefaultSLInterpreter;
import com.drartgames.stepper.sl.DefaultScriptLoaderFacade;
import com.drartgames.stepper.sl.SLInterpreter;
import com.drartgames.stepper.sl.ScriptLoaderFacade;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    + initializer.getInterpreter().getSLVersion().getFullStringVersion() + ")\n");

            for (ParameterHandler handler : initializer.getParametersHandlers()) {
                initializer.logn(handler.getParameter() +
                        (handler.hasAlias() ? " " + handler.getParameterAlias() : "") +
                        (handler.hasArgument() ? handler.getArgumentHelp() : "") + " --- " + handler.getHelp());
            }
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
        private String VERSION = "--version";
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
            return "sets directory (relative to working one) where data of the quests lies";
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

        try {
            String manifestFile = questsDirectory.getAbsolutePath() + "/" + questName + "/" + MANIFEST_FILE_NAME;
            manifest = manifestLoader.loadManifest(new File(manifestFile));
        } catch (IOException exc) {
            logger.log(Level.SEVERE, "Unable to read content from " + MANIFEST_FILE_NAME, exc);

            return;
        }

        interpreter = new DefaultSLInterpreter();

        if (!interpreter.getSLVersion().isHigherOrEqualThan(manifest.getRequiredSLVersion())) {
            throw new SLVersionMismatchException("Required by " + questName + " SL version (" +
                    manifest.getRequiredSLVersion().getStringVersion() + ") is higher than the interpreter can support (" +
                    interpreter.getSLVersion().getStringVersion() + ")");
        }

        scriptLoaderFacade = new DefaultScriptLoaderFacade();
        display = new DefaultDisplay(manifest.getQuestName(), this);
        display.initialize();

        try {
            splashScreen = new DefaultPicture(SPLASH_SCREEN_PATH);
            loadingPicture = new DefaultPicture(LOADING_PICTURE_PATH);
        } catch (IOException exc) {
            logger.log(Level.SEVERE, "Unable to load splash screen image", exc);
        }
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
                    textDescriptor.setMessage("Запуск скриптов");

                    break;
            }
        });

        display.awaitForKey(KeyEvent.VK_ENTER, (KeyAwaitDescriptor keyAwaitDescriptor) -> {
            Thread thread = new Thread(() -> {
                try {
                    interpreter.run();
                } catch (NoInitSceneException exc) {
                    logger.log(Level.SEVERE, "There's no loaded init scene", exc);
                }
            });

            thread.start();

            splashScreenDescriptor.setDoFree(true);
            loadingIconDescriptor.setDoFree(true);
            textDescriptor.setDoFree(true);
            keyAwaitDescriptor.setDoFree(true);
        });

        //@todo make a fucking ability to localize the captions
        textDescriptor.setMessage("Нажмите ENTER");
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
