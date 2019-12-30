package com.drartgames.stepper.initializer;

import com.drartgames.stepper.Launcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DefaultStepperInitializer implements Initializer {
    private static String QUESTS_DIR = "--quests-dir";
    private static String QUEST = "--quest";

    private String questName;
    private File questsDirectory;
    private List<ParameterHandler> parametersHandlers;

    private class HelpParameterHandler implements ParameterHandler {
        private String HELP = "--help";
        private String HELP_SHORT = "-h";

        @Override
        public boolean hasArgument() {
            return false;
        }

        @Override
        public void handle(Initializer initializer, String parameter) {
            initializer.logn(Launcher.getVersion().getFullStringVersion() + "\n");

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
    public void initialize(String... args) {
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
    }

    @Override
    public void run() {
        //load manifest
        //show splash
        //AnalyzerFacade
            //load all scenes scripts
        //start interpreter
            //load scenes
        //show to start press [ENTER]
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
}
