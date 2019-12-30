package com.drartgames.stepper;

import com.drartgames.stepper.utils.DefaultFileService;
import com.drartgames.stepper.utils.FileService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultManifestLoader implements ManifestLoader {
    private Logger logger = Logger.getLogger(DefaultManifestLoader.class.getName());

    private interface FieldLoader {
        void load(String value, ManifestBuilder manifestBuilder);

        String getFieldName();
    }

    private class RequiredSLVersionLoader implements FieldLoader {
        private static final String SL_VERSION_REQ_FIELD = "sl_version_req";

        @Override
        public void load(String value, ManifestBuilder manifestBuilder) {
            manifestBuilder.setRequiredSLVersion(new DefaultVersion(value));
        }

        @Override
        public String getFieldName() {
            return SL_VERSION_REQ_FIELD;
        }
    }

    private class InitSceneLoader implements FieldLoader {
        private static final String INIT_SCENE_FIELD = "init_scene";

        @Override
        public void load(String value, ManifestBuilder manifestBuilder) {
            manifestBuilder.setInitSceneName(value);
        }

        @Override
        public String getFieldName() {
            return INIT_SCENE_FIELD;
        }
    }

    private class QuestNameLoader implements FieldLoader {
        private static final String QUEST_NAME_FIELD = "quest_name";

        @Override
        public void load(String value, ManifestBuilder manifestBuilder) {
            manifestBuilder.setQuestName(value);
        }

        @Override
        public String getFieldName() {
            return QUEST_NAME_FIELD;
        }
    }

    private class ResolutionLoader implements FieldLoader {
        private static final String QUEST_NAME_FIELD = "resolution";

        @Override
        public void load(String value, ManifestBuilder manifestBuilder) {
            Pattern pattern = Pattern.compile("([0-9]+)x([0-9]+)");
            Matcher matcher = pattern.matcher(value);

            if (matcher.find() && matcher.groupCount() == 2) {
                int width = Integer.valueOf(matcher.group(1));
                int height = Integer.valueOf(matcher.group(2));

                manifestBuilder.setResolution(new Dimension(width, height));
            } else
                throw new IllegalArgumentException("Invalid format of resolution in manifest: " + value);
        }

        @Override
        public String getFieldName() {
            return QUEST_NAME_FIELD;
        }
    }

    private List<FieldLoader> fieldLoaders;

    public DefaultManifestLoader() {
        fieldLoaders = new ArrayList<>();

        fieldLoaders.add(new RequiredSLVersionLoader());
        fieldLoaders.add(new InitSceneLoader());
        fieldLoaders.add(new QuestNameLoader());
        fieldLoaders.add(new ResolutionLoader());
    }

    @Override
    public Manifest loadManifest(File file) throws IOException {
        FileService fileService = new DefaultFileService();

        String content = fileService.readAllContent(file);

        String[] lines = content.split(";");

        Pattern pattern = Pattern.compile("^\\s*([a-zA-Z_0-9]+)\\s*=\\s*(.+?)\\s*$");

        ManifestBuilder builder = new DefaultManifestBuilder();

        //@todo what if not all required fields were provided in the manifest?
        for (int i = 0; i < lines.length; i++) {
            Matcher matcher = pattern.matcher(lines[i]);

            if (matcher.find() && matcher.groupCount() == 2) {
                String fieldName = matcher.group(1);

                boolean wasFound = false;

                for (FieldLoader loader : fieldLoaders) {
                    if (loader.getFieldName().equals(fieldName)) {
                        loader.load(matcher.group(2), builder);
                        wasFound = true;

                        break;
                    }
                }

                if (!wasFound)
                    throw new IllegalArgumentException("Manifest field: " + fieldName + " is unsupported");
            } else
                throw new IllegalArgumentException("Manifest line:\n" + lines[i] + "\n does not follow required format");
        }

        return builder.build();
    }
}
