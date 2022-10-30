package uet.oop.bomberman.entities.Monsters;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Ova extends Entity {

    private final int ovaSpeed = 2;

    public Ova(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        Random rd = new Random();
        int ok = rd.nextInt(4);
        if (ok == 2 && location_x % Sprite.SCALED_SIZE < 3 && location_y % Sprite.SCALED_SIZE < 3) {
            int numGo = rd.nextInt(4);
            switch (numGo % 4) {
                case 0:
                    go = 'A';
                    break;
                case 1:
                    go = 'D';
                    break;
                case 2:
                    go = 'W';
                    break;
                case 3:
                    go = 'S';
                    break;
            }
        }
        switch (go) {
            case 'A':
                location_x -= ovaSpeed;
                if (count_img % 30 == 0) {
                    this.img = Sprite.ovape_left1.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.ovape_left2.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.ovape_left3.getFxImage();
                }
                count_img += 4;
                break;
            case 'D':
                location_x += ovaSpeed;
                if (count_img % 30 == 0) {
                    this.img = Sprite.ovape_right1.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.ovape_right2.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.ovape_right3.getFxImage();
                }
                count_img += 4;
                break;
            case 'W':
                location_y -= ovaSpeed;
                if (count_img % 30 == 0) {
                    this.img = Sprite.ovape_right1.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.ovape_right2.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.ovape_right3.getFxImage();
                }
                count_img += 4;
                break;
            case 'S':
                location_y += ovaSpeed;
                if (count_img % 30 == 0) {
                    this.img = Sprite.ovape_left1.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.ovape_left2.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.ovape_left3.getFxImage();
                }
                count_img += 4;
                break;
        }
        this.entityCollide();
        y = getLocation_y();
    }

    public void entityCollide() {
        int x1 = (location_x + 1) / Sprite.SCALED_SIZE;
        int y1 = (location_y + 1) / Sprite.SCALED_SIZE - 2;
        int y2 = (location_y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE - 2;
        int x2 = (location_x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

        switch (go) {
            case 'A': {
                if ((BombermanGame.game.mapGame.getMap(y1, x1) != ' ' && BombermanGame.game.mapGame.getMap(y1, x1) != '*')
                        || (BombermanGame.game.mapGame.getMap(y2, x1) != ' ' && BombermanGame.game.mapGame.getMap(y2, x1) != '*')) {
                    location_x = (x1 + 1) * Sprite.SCALED_SIZE;
                }
                break;
            }
            case 'D': {
                if ((BombermanGame.game.mapGame.getMap(y1, x2) != ' ' && BombermanGame.game.mapGame.getMap(y1, x2) != '*')
                        || (BombermanGame.game.mapGame.getMap(y2, x2) != ' ' && BombermanGame.game.mapGame.getMap(y2, x2) != '*')) {
                    location_x = (x2 - 1) * Sprite.SCALED_SIZE;
                }
                break;
            }
            case 'W': {
                if ((BombermanGame.game.mapGame.getMap(y1, x1) != ' ' && BombermanGame.game.mapGame.getMap(y1, x1) != '*')
                        || (BombermanGame.game.mapGame.getMap(y1, x2) != ' ' && BombermanGame.game.mapGame.getMap(y1, x2) != '*')) {
                    location_y = (y1 + 3) * Sprite.SCALED_SIZE;
                }
                break;
            }
            case 'S': {
                if ((BombermanGame.game.mapGame.getMap(y2, x1) != ' ' && BombermanGame.game.mapGame.getMap(y2, x1) != '*')
                        || (BombermanGame.game.mapGame.getMap(y2, x2) != ' ' && BombermanGame.game.mapGame.getMap(y2, x2) != '*')) {
                    location_y = (y2 + 1) * Sprite.SCALED_SIZE;
                }
                break;
            }
        }
    }
}
