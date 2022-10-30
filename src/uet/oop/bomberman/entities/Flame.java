package uet.oop.bomberman.entities;

import static uet.oop.bomberman.graphics.Map.r;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity{

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.x = xUnit * Sprite.SCALED_SIZE;
        if (yUnit >= 3 && yUnit <= r) this.y = yUnit * Sprite.SCALED_SIZE;
        else if (yUnit < 3) this.y = 3 * Sprite.SCALED_SIZE;
        else this.y = (r) * Sprite.SCALED_SIZE;
        this.img = img;
        this.location_x = this.x;
        this.location_y = this.y;
    }

    @Override
    public void update() {

    }
}