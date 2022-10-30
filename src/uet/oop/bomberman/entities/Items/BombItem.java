package uet.oop.bomberman.entities.Items;

import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {
    public BombItem(int x, int y) {
        super(x, y, Sprite.powerup_bombs.getFxImage());
    }

    @Override
    public void update() {
        Bomb.number_bomb++;
        remove();
    }
}
