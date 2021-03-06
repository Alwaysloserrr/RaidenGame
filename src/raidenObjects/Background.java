package raidenObjects;

import utils.Faction;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

public class Background extends BaseRaidenObject {
    private float y2;
    private float maxSpeed = 1;

    public Background() {
        super("Background4", 0, 0, 640, 1260, Faction.NEUTRAL);
        y2 = -imgSizeY;
    }

    private float getY2() {
        return y2;
    }

    private void setY2(float y2) {
        this.y2 = y2;
    }

    public void step() {
        setY(getY() + maxSpeed);
        setY2(getY2() + maxSpeed);
        if (getY() >= getImgSizeY())
            setY(getY() - 2 * getImgSizeY());
        if (getY2() >= getImgSizeY())
            setY2(getY2() - 2 * getImgSizeY());
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bufferedImage = loadImage(getImageFile());
        g.drawImage(bufferedImage, 0, (int) getY(), null);
        g.drawImage(bufferedImage, 0, (int) getY2(), null);
    }

    public File getImageFile() {
        return Paths.get("data", "images", name + ".png").toFile();
    }
}
