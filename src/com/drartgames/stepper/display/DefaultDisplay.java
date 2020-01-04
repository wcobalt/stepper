package com.drartgames.stepper.display;

import com.drartgames.stepper.Manifest;
import com.drartgames.stepper.initializer.Initializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultDisplay implements JFrameDisplay {
    private static final Logger logger = Logger.getLogger(DefaultDisplay.class.getName());

    private static final int SCROLL_DOWN_KEYCODE = KeyEvent.VK_DOWN;
    private static final int SCROLL_UP_KEYCODE = KeyEvent.VK_UP;
    private static final int SCROLL_WEIGHT = 1;
    private static final float FPS = 30;
    private static final Color BACKGROUND_COLOR = Color.BLACK;

    private boolean isRunning;

    private ImageRenderer imageRenderer;
    private TextRenderer textRenderer;
    private InputRenderer inputRenderer;

    private Initializer initializer;

    private JFrame frame;
    private JPanel renderPanel;

    private Dimension renderResolution;
    private BufferedImage renderBuffer;

    private Font renderFont;

    private DisplayState state, providedDisplayState = null;
    private Work stateSwapWork;

    private List<KeyEventEntry> keyEvents;

    private class KeyEventEntry {
        public final static int KEY_TYPE = 1, KEY_PRESS = 2, KEY_RELEASE = 3;

        private KeyEvent keyEvent;
        private int type;

        public KeyEventEntry(int type, KeyEvent keyEvent) {
            this.keyEvent = keyEvent;
            this.type = type;
        }

        public KeyEvent getKeyEvent() {
            return keyEvent;
        }

        public int getType() {
            return type;
        }
    }

    public DefaultDisplay(String title, Initializer initializer) {
        state = new DefaultDisplayState();

        imageRenderer = new DefaultImageRenderer();
        textRenderer = new DefaultTextRenderer();
        inputRenderer = new DefaultInputRenderer();
        keyEvents = Collections.synchronizedList(new ArrayList<>());

        frame = new JFrame(title);

        this.initializer = initializer;
    }

    @Override
    public ImageDescriptor addPicture(Picture picture, float width, float x, float y) {
        ImageDescriptor imageDescriptor = new DefaultImageDescriptor(this, picture, width, x, y);

        state.getImageDescriptors().add(imageDescriptor);

        return imageDescriptor;
    }

    @Override
    public AudioDescriptor addAudio(Audio audio, boolean isLooped) {
        AudioDescriptor audioDescriptor = new DefaultAudioDescriptor(this, audio, isLooped);

        state.getAudioDescriptors().add(audioDescriptor);

        return audioDescriptor;
    }

    @Override
    public AnimationDescriptor addAnimation(ImageDescriptor imageDescriptor, Animation animation, boolean isLooped,
                                            boolean doReturnBack) {
        AnimationDescriptor animationDescriptor = new DefaultAnimationDescriptor(this, imageDescriptor, animation,
                isLooped, doReturnBack);

        state.getAnimationDescriptors().add(animationDescriptor);
        animationDescriptor.getAnimation().setInitPos(imageDescriptor);

        return animationDescriptor;
    }

    @Override
    public TextDescriptor addText(String message, float width, float height, float x, float y) {
        TextDescriptor textDescriptor = new DefaultTextDescriptor(this, message, width, height, x, y);

        state.getTextDescriptors().add(textDescriptor);

        return textDescriptor;
    }

    @Override
    public InputDescriptor addInput(float width, float height, float x, float y) {
        InputDescriptor inputDescriptor = new DefaultInputDescriptor(this, width, height, x, y);

        state.getInputDescriptors().add(inputDescriptor);

        return inputDescriptor;
    }

    @Override
    public KeyAwaitDescriptor awaitForKey(int key, KeyAwaitWork keyAwaitWork) {
        KeyAwaitDescriptor keyAwaiterDescriptor = new DefaultKeyAwaitDescriptor(this, key, keyAwaitWork);

        state.getKeyAwaitDescriptors().add(keyAwaiterDescriptor);

        return keyAwaiterDescriptor;
    }

    @Override
    public PostWorkDescriptor addWork(PostWork work) {
        PostWorkDescriptor postWorkDescriptor = new DefaultPostWorkDescriptor(this, work);

        state.getWorkDescriptors().add(postWorkDescriptor);

        return postWorkDescriptor;
    }

    @Override
    public void initialize() {
        frame.setUndecorated(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        frame.setResizable(false);

        Dimension screenSize = toolkit.getScreenSize();
        Insets insets = toolkit.getScreenInsets(frame.getGraphicsConfiguration());

        Dimension windowSize = new Dimension(screenSize.width - insets.right - insets.left,
                screenSize.height - insets.top - insets.bottom);

        frame.setSize(windowSize);

        renderPanel = new JPanel();

        Dimension resolution = initializer.getManifest().getResolution();

        //set size and pos of panel
        float screenAspectRatio = windowSize.width / (float)windowSize.height;
        float originalAspectRatio = resolution.width / (float)resolution.height;

        float scaleRatio = 0;

        int endWidth = resolution.width, endHeight = resolution.height, x = 0, y = 0;

        if (screenAspectRatio > originalAspectRatio) {
            //res_h = screen_h
            //adjust w

            endHeight = windowSize.height;

            scaleRatio = (windowSize.height / (float)resolution.height);

            endWidth *= scaleRatio;

            x = (windowSize.width - endWidth) / 2;
        } else {
            //res_w = screen_w
            //adjust h

            endWidth = windowSize.width;

            scaleRatio = (windowSize.width / (float)resolution.width);

            endHeight *= scaleRatio;

            y = (windowSize.height - endHeight) / 2;
        }

        renderResolution = new Dimension(endWidth, endHeight);

        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        renderPanel.setBounds(x, y, endWidth, endHeight);

        renderBuffer = new BufferedImage(endWidth, endHeight, BufferedImage.TYPE_INT_ARGB);

        imageRenderer.setDisplay(this);
        textRenderer.setDisplay(this);
        inputRenderer.setDisplay(this);

        frame.setLayout(null);
        frame.add(renderPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Manifest manifest = initializer.getManifest();

        int fontSize = (int)(manifest.getFontSize() * scaleRatio);

        renderFont = new Font(manifest.getFontName(), Font.PLAIN, fontSize);

        //cursor
        BufferedImage cursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor cursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "stepper_blank_cursor");

        frame.setCursor(cursor);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                keyEvents.add(new KeyEventEntry(KeyEventEntry.KEY_TYPE, e));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyEvents.add(new KeyEventEntry(KeyEventEntry.KEY_RELEASE, e));
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyEvents.add(new KeyEventEntry(KeyEventEntry.KEY_PRESS, e));
            }
        });
    }

    @Override
    public void run() {
        frame.setVisible(true);
        isRunning = true;

        Thread thread = new Thread(() -> {
            long lastTimestamp = System.currentTimeMillis();

            while (isRunning) {
                long begin = System.currentTimeMillis();

                handleAudios();
                handleKeys();

                long currentTimestamp = System.currentTimeMillis();

                handleAnimations(currentTimestamp - lastTimestamp);

                lastTimestamp = currentTimestamp;

                fillBackground();

                renderImages();
                renderTexts();
                renderInputs();

                swapBuffers();

                doWork();

                if (providedDisplayState != null) {
                    state = providedDisplayState;

                    if (stateSwapWork != null)
                        stateSwapWork.execute();

                    providedDisplayState = null;
                    stateSwapWork = null;
                }

                long delay = System.currentTimeMillis() - begin;
                long sleepFor = (int) (1000 / FPS) - delay;

                sleepFor = sleepFor > 0 ? sleepFor : 0;

                try {
                    Thread.sleep(sleepFor);
                } catch (InterruptedException exc) {
                    logger.log(Level.SEVERE, "Render thread was interrupted", exc);
                }
            }
        }, "Stepper Render Thread");

        thread.start();
    }

    private void swapBuffers() {
        Graphics g = renderPanel.getGraphics();

        g.drawImage(renderBuffer, 0, 0, renderResolution.width, renderResolution.height, null);

        g.dispose();
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    private void handleKeys() {
        synchronized (keyEvents) {
            for (KeyEventEntry entry : keyEvents) {
                switch (entry.getType()) {
                    case KeyEventEntry.KEY_TYPE:
                        handleKeyType(entry.getKeyEvent());

                        break;

                    case KeyEventEntry.KEY_PRESS:
                        handleKeyPress(entry.getKeyEvent());

                        break;

                    case KeyEventEntry.KEY_RELEASE:
                        handleKeyRelease(entry.getKeyEvent());

                        break;
                }
            }

            keyEvents.clear();
        }
    }

    private void handleKeyType(KeyEvent keyEvent) {
        InputDescriptor activeInput = state.getActiveInput();
        char typed = keyEvent.getKeyChar();

        if (typed != KeyEvent.CHAR_UNDEFINED && typed != KeyEvent.VK_BACK_SPACE && activeInput != null) {
            activeInput.addChar(typed);
        }
    }

    private void handleKeyPress(KeyEvent keyEvent) {
        InputDescriptor activeInput = state.getActiveInput();

        if (keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE && activeInput != null) {
            String text = activeInput.getCurrentText();

            if (text.length() > 0)
                activeInput.setCurrentText(text.substring(0, text.length() - 1));
        }
    }

    private void handleKeyRelease(KeyEvent keyEvent) {
        TextDescriptor activeScrollableText = state.getActiveScrollableText();

        int keyCode = keyEvent.getKeyCode();

        if (keyEvent.isActionKey()) {
            int scroll = 0;

            switch (keyCode) {
                case SCROLL_DOWN_KEYCODE:
                    scroll = SCROLL_WEIGHT;

                    break;

                case SCROLL_UP_KEYCODE:
                    scroll = -1 * SCROLL_WEIGHT;

                    break;
            }

            if (scroll != 0 && activeScrollableText != null)
                activeScrollableText.setScrollPosition(activeScrollableText.getScrollPosition() + scroll);
        }

        Iterator<KeyAwaitDescriptor> iterator = state.getKeyAwaitDescriptors().iterator();

        while (iterator.hasNext()) {
            KeyAwaitDescriptor descriptor = iterator.next();

            if (descriptor.getKey() == keyCode) {
                descriptor.getWork().execute(descriptor);

                if (descriptor.doFree())
                    iterator.remove();
            }
        }
    }

    private void doWork() {
        //@fixme it's no good that code like this is repeated everywhere in Display code
        Iterator<PostWorkDescriptor> iterator = state.getWorkDescriptors().iterator();

        while (iterator.hasNext()) {
            PostWorkDescriptor descriptor = iterator.next();

            descriptor.getWork().execute(descriptor);

            if (descriptor.doFree())
                iterator.remove();
        }
    }

    private void handleAudios() {
        Iterator<AudioDescriptor> iterator = state.getAudioDescriptors().iterator();

        while (iterator.hasNext()) {
            AudioDescriptor descriptor = iterator.next();

            if (descriptor.doFree())
                iterator.remove();
        }
    }

    private void handleAnimations(long deltaTime) {
        Iterator<AnimationDescriptor> iterator = state.getAnimationDescriptors().iterator();

        while (iterator.hasNext()) {
            AnimationDescriptor descriptor = iterator.next();

            boolean isEnded = descriptor.getAnimation().update(descriptor.getImageDescriptor(), deltaTime);

            if (isEnded) {
                if (descriptor.isLooped())
                    descriptor.getAnimation().loopEnded();
                else {
                    if (descriptor.doReturnBack())
                        descriptor.getAnimation().backToInitPos(descriptor.getImageDescriptor());

                    iterator.remove();
                }
            }
        }
    }

    private void renderImages() {
        Iterator<ImageDescriptor> iterator = state.getImageDescriptors().iterator();

        while (iterator.hasNext()) {
            ImageDescriptor descriptor = iterator.next();

            imageRenderer.render(descriptor);

            if (descriptor.doFree())
                iterator.remove();
        }
    }

    private void renderTexts() {
        Iterator<TextDescriptor> iterator = state.getTextDescriptors().iterator();

        while (iterator.hasNext()) {
            TextDescriptor descriptor = iterator.next();

            textRenderer.render(descriptor);

            if (descriptor.doFree())
                iterator.remove();
        }
    }

    private void renderInputs() {
        Iterator<InputDescriptor> iterator = state.getInputDescriptors().iterator();

        while (iterator.hasNext()) {
            InputDescriptor descriptor = iterator.next();

            inputRenderer.render(descriptor);

            if (descriptor.doFree())
                iterator.remove();
        }
    }

    private void fillBackground() {
        Graphics g = renderBuffer.getGraphics();
        Dimension renderResolution = getRenderResolution();

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0,
                renderResolution.width, renderResolution.height);

        g.dispose();
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }

    @Override
    public JPanel getRenderPanel() {
        return renderPanel;
    }

    @Override
    public Initializer getInitializer() {
        return initializer;
    }

    @Override
    public Dimension getRenderResolution() {
        return renderResolution;
    }

    @Override
    public BufferedImage getRenderBuffer() {
        return renderBuffer;
    }

    @Override
    public Font getRenderFont() {
        return renderFont;
    }

    @Override
    public DisplayState getDisplayState() {
        return state;
    }

    @Override
    public void provideDisplayState(DisplayState displayState, Work changeWork) {
        providedDisplayState = displayState;
        stateSwapWork = changeWork;
    }

    @Override
    public TextRenderer getTextRenderer() {
        return textRenderer;
    }
}
