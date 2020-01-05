package com.drartgames.stepper.display;

public interface FigureDescriptor extends Descriptor {
    void setX(float x);

    void setY(float y);

    float getX();

    float getY();

    void setWidth(float width);

    void setHeight(float height);

    float getWidth();

    float getHeight();

    boolean isVisible();

    void setVisible(boolean isVisible);
}
