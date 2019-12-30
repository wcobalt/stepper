package com.drartgames.stepper.display;

import com.drartgames.stepper.initializer.Initializer;
import com.drartgames.stepper.utils.Work;
import com.drartgames.stepper.initializer.DefaultStepperInitializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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

    private List<ImageDescriptor> images;
    private List<AudioDescriptor> audios;
    private List<AnimationDescriptor> animations;
    private List<TextDescriptor> texts;
    private List<InputDescriptor> inputs;

    private class KeyAwaiterDescriptor {
        private Work work;
        private int key;

        public KeyAwaiterDescriptor(int key, Work work) {
            this.key = key;
            this.work = work;
        }

        public Work getWork() {
            return work;
        }

        public int getKey() {
            return key;
        }
    }

    private List<KeyAwaiterDescriptor> keyAwaiters;

    private InputDescriptor activeInput;
    private TextDescriptor activeScrollableText;

    private boolean isRunning;

    private ImageRenderer imageRenderer;
    private TextRenderer textRenderer;
    private InputRenderer inputRenderer;

    private Initializer initializer;

    private JFrame frame;
    private JPanel renderPanel;

    private Dimension renderResolution;

    public DefaultDisplay(String title, Initializer initializer) {
        images = new ArrayList<>();
        audios = new ArrayList<>();
        animations = new ArrayList<>();
        texts = new ArrayList<>();
        inputs = new ArrayList<>();
        keyAwaiters = new ArrayList<>();

        imageRenderer = new DefaultImageRenderer();
        textRenderer = new DefaultTextRenderer();
        inputRenderer = new DefaultInputRenderer();

        activeInput = null;
        activeScrollableText = null;

        frame = new JFrame(title);
        this.initializer = initializer;
    }

    @Override
    public ImageDescriptor addPicture(Picture picture, float width, float x, float y) {
        ImageDescriptor imageDescriptor = new DefaultImageDescriptor(this, picture, width, x, y);

        images.add(imageDescriptor);

        return imageDescriptor;
    }

    @Override
    public AudioDescriptor addAudio(Audio audio, boolean isLooped) {
        AudioDescriptor audioDescriptor = new DefaultAudioDescriptor(this, audio, isLooped);

        audios.add(audioDescriptor);

        return audioDescriptor;
    }

    @Override
    public AnimationDescriptor addAnimation(ImageDescriptor imageDescriptor, Animation animation, boolean isLooped) {
        AnimationDescriptor animationDescriptor = new DefaultAnimationDescriptor(this, imageDescriptor, animation, isLooped);

        animations.add(animationDescriptor);

        return animationDescriptor;
    }

    @Override
    public TextDescriptor addText(String message, float width, float height, float x, float y) {
        TextDescriptor textDescriptor = new DefaultTextDescriptor(this, message, width, height, x, y);

        texts.add(textDescriptor);

        return textDescriptor;
    }

    @Override
    public InputDescriptor addInput(float width, float height, float x, float y) {
        InputDescriptor inputDescriptor = new DefaultInputDescriptor(this, width, height, x, y);

        inputs.add(inputDescriptor);

        return inputDescriptor;
    }

    @Override
    public void setScrollableText(TextDescriptor textDescriptor) {
        activeScrollableText = textDescriptor;
    }

    @Override
    public void setActiveInput(InputDescriptor inputDescriptor) {
        activeInput = inputDescriptor;
    }

    @Override
    public void awaitForKey(int key, Work work) {
        KeyAwaiterDescriptor keyAwaiterDescriptor = new KeyAwaiterDescriptor(key, work);

        keyAwaiters.add(keyAwaiterDescriptor);
    }

    @Override
    public TextDescriptor getScrollableText() {
        return activeScrollableText;
    }

    @Override
    public InputDescriptor getActiveInput() {
        return activeInput;
    }

    @Override
    public void initialize() {
        frame.setUndecorated(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        frame.setSize(screenSize);

        renderPanel = new JPanel();

        Dimension resolution = initializer.getManifest().getResolution();

        //set size and pos of panel
        float screenAspectRatio = screenSize.width / (float)screenSize.height;
        float originalAspectRatio = resolution.width / (float)resolution.height;

        int endWidth = resolution.width, endHeight = resolution.height, x = 0, y = 0;

        if (screenAspectRatio > originalAspectRatio) {
            //res_h = screen_h
            //adjust w

            endHeight = screenSize.height;
            endWidth *= (screenSize.height / (float)resolution.height);

            x = (screenSize.width - endWidth) / 2;
        } else {
            //res_w = screen_w
            //adjust h

            endWidth = screenSize.width;
            endHeight *= (screenSize.width / (float)resolution.width);

            y = (screenSize.height - endHeight) / 2;
        }

        renderResolution = new Dimension(endWidth, endHeight);

        frame.getContentPane().setBackground(BACKGROUND_COLOR);
        renderPanel.setBounds(x, y, endWidth, endHeight);

        imageRenderer.setDisplay(this);
        textRenderer.setDisplay(this);
        inputRenderer.setDisplay(this);

        frame.setLayout(null);
        frame.add(renderPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //cursor
        BufferedImage cursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor cursor = toolkit.createCustomCursor(cursorImage, new Point(0, 0), "stepper_blank_cursor");

        frame.setCursor(cursor);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyType(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyRelease(e);
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

                long currentTimestamp = System.currentTimeMillis();

                handleAnimations(currentTimestamp - lastTimestamp);

                lastTimestamp = currentTimestamp;

                fillBackground();
                renderImages();
                renderTexts();
                renderInputs();

                long delay = System.currentTimeMillis() - begin;
                long sleepFor = (int) (1000 / FPS) - delay;

                sleepFor = sleepFor > 0 ? sleepFor : 0;

                try {
                    Thread.sleep(sleepFor);
                } catch (InterruptedException exc) {
                    logger.log(Level.SEVERE, "Render thread was interrupted", exc);
                }
            }
        });

        thread.start();
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    private void handleKeyType(KeyEvent keyEvent) {
        char typed = keyEvent.getKeyChar();

        if (typed != KeyEvent.CHAR_UNDEFINED && activeInput != null) {
            activeInput.setCurrentText(activeInput.getCurrentText() + typed);
        }
    }

    private void handleKeyRelease(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        if (keyEvent.isActionKey()) {
            int scroll = 0;

            switch (keyCode) {
                case SCROLL_DOWN_KEYCODE:
                    scroll = -1 * SCROLL_WEIGHT;

                    break;

                case SCROLL_UP_KEYCODE:
                    scroll = SCROLL_WEIGHT;

                    break;
            }

            if (scroll != 0 && activeScrollableText != null)
                activeScrollableText.setScrollPosition(activeScrollableText.getScrollPosition() + scroll);
        }

        for (KeyAwaiterDescriptor descriptor : keyAwaiters) {
            if (descriptor.key == keyCode)
                descriptor.work.execute();
        }
    }

    private void handleAudios() {
        for (AudioDescriptor descriptor : audios) {
            //@todo
        }
    }

    private void handleAnimations(long deltaTime) {
        for (AnimationDescriptor descriptor : animations) {
            descriptor.getAnimation().update(descriptor.getImageDescriptor(), deltaTime);
        }
    }

    private void renderImages() {
        for (ImageDescriptor descriptor : images) {
            imageRenderer.render(descriptor);
        }
    }

    private void renderTexts() {
        for (TextDescriptor descriptor : texts) {
            textRenderer.render(descriptor);
        }
    }

    private void renderInputs() {
        for (InputDescriptor descriptor : inputs) {
            inputRenderer.render(descriptor);
        }
    }

    private void fillBackground() {
        Graphics g = renderPanel.getGraphics();
        Dimension renderResolution = getRenderResolution();

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0,
                renderResolution.width, renderResolution.height);
    }

    @Override
    public JFrame getFrame() {
        return frame;
    }

    @Override
    public JPanel getPanel() {
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
}
