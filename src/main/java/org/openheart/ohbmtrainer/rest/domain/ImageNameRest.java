package org.openheart.ohbmtrainer.rest.domain;

public class ImageNameRest {
    private String imageName;

    public ImageNameRest(String name) {
        this.imageName = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
