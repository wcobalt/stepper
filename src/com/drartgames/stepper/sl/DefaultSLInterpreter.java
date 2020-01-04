package com.drartgames.stepper.sl;

import com.drartgames.stepper.DefaultVersion;
import com.drartgames.stepper.Version;
import com.drartgames.stepper.display.*;
import com.drartgames.stepper.exceptions.SLRuntimeException;
import com.drartgames.stepper.sl.lang.Action;
import com.drartgames.stepper.sl.lang.Operator;
import com.drartgames.stepper.sl.lang.memory.Dialog;
import com.drartgames.stepper.sl.processors.*;
import com.drartgames.stepper.sl.lang.Scene;
import com.drartgames.stepper.sl.lang.memory.DefaultManager;
import com.drartgames.stepper.sl.lang.memory.Manager;
import com.drartgames.stepper.sl.lang.memory.Tag;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultSLInterpreter implements SLInterpreter {
    private static final Logger logger = Logger.getLogger(DefaultSLInterpreter.class.getName());

    public static final Version SL_VERSION = new DefaultVersion("1.0.0:0");;
    private Display display;
    private DisplayToolkit toolkit;
    private ScenesManager scenesManager;
    private Manager<Tag> tagsManager;
    private Operator currentOperator;

    private Map<Integer, OperatorProcessor> processors;

    public static final String INIT_ACTION_NAME = "init";
    public static final String FIRST_COME_ACTION_NAME = "first_come";

    public static final int IMMEDIATE_RETURN_FLAG = 0x1;
    public static final float MATCH_THRESHOLD = 0.2f;

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
        return SL_VERSION;
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
        //@fixme maybe split into two methods: load/initScenes and init (just interpreter) i dunno
        Iterator<Scene> iterator = scenesManager.getScenes().iterator();

        while (iterator.hasNext()) {
            Scene scene = iterator.next();
            Action firstCome = scene.getActionByName(INIT_ACTION_NAME);

            scenesManager.setCurrentScene(scene);

            executeAction(firstCome);
        }

        scenesManager.setCurrentScene(null);
        toolkit.initialize(this::handleInput);
    }

    private void handleInput(String input) {
        List<Dialog> dialogs = scenesManager.getCurrentScene().getDialogsManager().getAll();

        Dialog mostMatched = null;
        float maxMatch = 0.0f;

        for (Dialog dialog : dialogs) {
            if (dialog.isEnabled()) {
                float match = dialog.matches(input);

                if (match > maxMatch) {
                    mostMatched = dialog;
                    maxMatch = match;
                }
            }
        }

        if (maxMatch > MATCH_THRESHOLD) {
            Action action = mostMatched.getDialogAction();

            try {
                executeAction(action);
            } catch (SLRuntimeException exc) {
                logger.log(Level.SEVERE, "Runtime exception when executing dialog action `" + action.getName() + "`:", exc);
            }
        }
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

            currentOperator = operator;

            OperatorProcessor processor = processors.get(operator.getOperatorId());

            processor.execute(this, operator);
        }

        currentOperator = null;
    }

    @Override
    public void gotoScene(Scene scene) throws SLRuntimeException {
        //check for saved states
            //load if there's one
            //create new state otherwise

        SceneCacheEntry cacheEntry = sceneCache.get(scene);

        if (cacheEntry != null) {
            DisplayState newDisplayState = cacheEntry.getDisplayState();
            DisplayToolkitState newDisplayToolkitState = cacheEntry.getToolkitState();

            display.provideDisplayState(newDisplayState, () -> toolkit.setState(newDisplayToolkitState));
        } else {
            DisplayState newDisplayState = new DefaultDisplayState();

            //add to cache
            display.provideDisplayState(newDisplayState, () -> {
                toolkit.setState(toolkit.makeNewState());

                statesSwapEnd(scene);

                Action firstCome = scene.getActionByName(FIRST_COME_ACTION_NAME);

                try {
                    executeAction(firstCome);
                } catch (SLRuntimeException exc) {
                    logger.log(Level.SEVERE, "Runtime exception, when executing `" + firstCome.getName() + "` action", exc);
                }
            });
        }
    }

    private void statesSwapEnd(Scene scene) {
        SceneCacheEntry newCacheEntry = new SceneCacheEntry(scene, display.getDisplayState(),
                toolkit.getState());

        sceneCache.put(scene, newCacheEntry);

        scenesManager.setCurrentScene(scene);
    }

    @Override
    public Operator getCurrentOperator() {
        return currentOperator;
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

    @Override
    public DisplayToolkit getToolkit() {
        return toolkit;
    }
}
