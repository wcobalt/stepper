package com.drartgames.stepper.sl;

import com.drartgames.stepper.DefaultVersion;
import com.drartgames.stepper.Version;
import com.drartgames.stepper.display.*;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.processors.*;
import com.drartgames.stepper.sl.lang.Scene;
import com.drartgames.stepper.sl.lang.memory.DefaultManager;
import com.drartgames.stepper.sl.lang.memory.Manager;
import com.drartgames.stepper.sl.lang.memory.Tag;

import java.util.*;

public class DefaultSLInterpreter implements SLInterpreter {
    private Version version;
    private Display display;
    private DisplayToolkit toolkit;
    private ScenesManager scenesManager;
    private Manager<Tag> tagsManager;

    private Map<Integer, OperatorProcessor> processors;

    public static final String INIT_ACTION_NAME = "init";
    public static final String FIRST_COME_ACTION_NAME = "first_come";

    public static final int IMMEDIATE_RETURN_FLAG = 0x1;

    private Set<Integer> flags;

    private class SceneCacheEntry {
        private Scene scene;
        private DisplayState displayState;
        private DisplayToolkitState toolkitState;

        public SceneCacheEntry(Scene scene, DisplayState displayState, DisplayToolkitState toolkitState) {
            this.scene = scene;
            this.displayState = displayState;
            this.toolkitState = toolkitState;
        }

        public Scene getScene() {
            return scene;
        }

        public DisplayState getDisplayState() {
            return displayState;
        }

        public DisplayToolkitState getToolkitState() {
            return toolkitState;
        }
    }

    private Map<Scene, SceneCacheEntry> sceneCache;

    public DefaultSLInterpreter(Display display) {
        version = new DefaultVersion("1.0.0:0");
        scenesManager = new DefaultScenesManager();
        tagsManager = new DefaultManager<>();

        sceneCache = new HashMap<>();

        this.display = display;
        toolkit = new DefaultDisplayToolkit(display);

        flags = new HashSet<>();
        processors = new HashMap<>();

        initOperatorProcessors();
    }

    private void initOperatorProcessors() {
        addOperatorProcessor(new LoadAudioOperatorProcessor());
        addOperatorProcessor(new LoadImageOperatorProcessor());
        addOperatorProcessor(new SetTextOperatorProcessor());
        addOperatorProcessor(new SetBackgroundOperatorProcessor());
        addOperatorProcessor(new PlayOperatorProcessor());
        addOperatorProcessor(new IfTagOperatorProcessor(false));
        addOperatorProcessor(new IfTagOperatorProcessor(true));
        addOperatorProcessor(new ShowOperatorProcessor());
        addOperatorProcessor(new TagOperatorProcessor());
        addOperatorProcessor(new DisableDialogOperatorProcessor());
        addOperatorProcessor(new EnableDialogOperatorProcessor());
        addOperatorProcessor(new AddDialogOperatorProcessor());
        addOperatorProcessor(new CallOperatorProcessor());
    }

    private void addOperatorProcessor(OperatorProcessor operatorProcessor) {
        processors.put(operatorProcessor.getOperatorId(), operatorProcessor);
    }

    @Override
    public Version getSLVersion() {
        return version;
    }

    @Override
    public Display getDisplay() {
        return display;
    }

    @Override
    public void run() throws SLRuntimeException {
        Scene initScene = scenesManager.getInitScene();

        gotoScene(initScene);
    }

    @Override
    public void initialize() throws SLRuntimeException {
        Iterator<Scene> iterator = scenesManager.getScenes().iterator();

        while (iterator.hasNext()) {
            Scene scene = iterator.next();
            Action firstCome = scene.getActionByName(INIT_ACTION_NAME);

            scenesManager.setCurrentScene(scene);

            executeAction(firstCome);
        }

        scenesManager.setCurrentScene(null);
    }

    @Override
    public void executeAction(Action action) throws SLRuntimeException {
        executeOperators(action.getOperators());

        if (isFlagSet(IMMEDIATE_RETURN_FLAG))
            setFlag(IMMEDIATE_RETURN_FLAG, false);
    }

    @Override
    public void executeOperators(List<Operator> operatorList) throws SLRuntimeException {
        Iterator<Operator> iterator = operatorList.iterator();

        while (iterator.hasNext() && !isFlagSet(IMMEDIATE_RETURN_FLAG)) {
            Operator operator = iterator.next();

            OperatorProcessor processor = processors.get(operator.getOperatorId());

            processor.execute(this, operator);
        }
    }

    @Override
    public void gotoScene(Scene scene) throws SLRuntimeException {
        //check for saved states
            //load if there's one
            //create new state otherwise

        SceneCacheEntry cacheEntry = sceneCache.get(scene);

        DisplayState newDisplayState;
        DisplayToolkitState newDisplayToolkitState;

        if (cacheEntry != null) {
            newDisplayState = cacheEntry.getDisplayState();
            newDisplayToolkitState = cacheEntry.getToolkitState();
        } else {
            newDisplayState = new DefaultDisplayState();
            newDisplayToolkitState = toolkit.makeNewState();

            //add to cache
            SceneCacheEntry newCacheEntry = new SceneCacheEntry(scene, display.getDisplayState(),
                    toolkit.getState());

            sceneCache.put(scene, newCacheEntry);
        }

        display.provideDisplayState(newDisplayState, () -> toolkit.setState(newDisplayToolkitState));

        scenesManager.setCurrentScene(scene);

        Action firstCome = scene.getActionByName(FIRST_COME_ACTION_NAME);

        executeAction(firstCome);
    }

    @Override
    public ScenesManager getScenesManager() {
        return scenesManager;
    }

    @Override
    public Manager<Tag> getTagsManager() {
        return tagsManager;
    }

    @Override
    public boolean isFlagSet(int flag) {
        return flags.contains(flag);
    }

    @Override
    public void setFlag(int flag, boolean value) {
        if (value)
            flags.add(flag);
        else
            flags.remove(flag);
    }
}
