package com.drartgames.stepper.sl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DefaultScriptLoaderFacade implements ScriptLoaderFacade {
    private Logger logger = Logger.getLogger(DefaultScriptLoaderFacade.class.getName());
    private ScriptLoader scriptLoader;
    private static final String SSF_EXTENSION = ".ssf";

    public DefaultScriptLoaderFacade() {
        scriptLoader = new DefaultScriptLoader();
    }

    @Override
    public void load(SLInterpreter slInterpreter, File scenesDirectory) {
        List<File> list = new ArrayList<>();

        if (scenesDirectory.exists() && scenesDirectory.isDirectory()) {
            listFilesRecursive(list, scenesDirectory);

            List<Scene> allScenes = new ArrayList<>();

            for (File file : list) {
                String fileName = file.getName();
                int extensionIndex = fileName.lastIndexOf('.');

                if (extensionIndex != -1) {
                    String extension = fileName.substring(extensionIndex).toLowerCase();

                    if (extension.equals(SSF_EXTENSION)) {
                        String fileNameRelativeToScenes =
                                file.getAbsolutePath().substring(scenesDirectory.getAbsolutePath().length() + 1);

                        logger.info("Loading SSF file: " + fileNameRelativeToScenes);
                        List<Scene> scenes = scriptLoader.load(file);

                        allScenes.addAll(scenes);
                    }
                }
            }

            slInterpreter.loadScenes(allScenes);
        } else
            throw new IllegalArgumentException("Scenes directory either is not a directory or does not exist");
    }

    public void listFilesRecursive(List<File> list, File directory) {
        File[] currentDirectoryList = directory.listFiles();

        for (File file : currentDirectoryList) {
            if (file.isFile())
                list.add(file);
            else if (file.isDirectory())
                listFilesRecursive(list, file);
        }
    }
}
