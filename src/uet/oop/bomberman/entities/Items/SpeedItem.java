package uet.oop.bomberman.entities.Items;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {

    public SpeedItem(int x, int y) {
        super(x, y, Sprite.powerup_speed.getFxImage());
    }

    @Override
    public void update() {
        BombermanGame.game.getBomber().setBomberSpeed();
        remove();
    }
}
