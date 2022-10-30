package uet.oop.bomberman.entities.Items;

import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {
    public FlameItem(int x, int y) {
        super(x, y, Sprite.powerup_flames.getFxImage());
    }
    @Override
    public void update() {
        Bomb.power_bomb++;
        remove();
    }
}
