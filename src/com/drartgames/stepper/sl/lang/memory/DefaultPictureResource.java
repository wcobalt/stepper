package com.drartgames.stepper.sl.lang.memory;

import com.drartgames.stepper.display.Picture;

public class DefaultPictureResource extends BaseEntity implements PictureResource {
    private Picture picture;

    public DefaultPictureResource(String name, Picture picture) {
        super(name);
        this.picture = picture;
    }

    @Override
    public Picture getPicture() {
        return picture;
    }
}
