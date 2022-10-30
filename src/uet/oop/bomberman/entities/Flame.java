package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity{

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.x = xUnit * Sprite.SCALED_SIZE;
        if (yUnit>=4) this.y = yUnit * Sprite.SCALED_SIZE; else this.y = 3 * 32;
        this.img = img;
        this.location_x = this.x;
        this.location_y = this.y;
    }

    @Override
    public void update() {

    }
}