package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound;
import uet.oop.bomberman.graphics.Sprite;


public class Bomber extends Entity {
    public static int coordinatesX;
    public static int coordinatesY;
    private int bomberSpeed = 2;
    EventHandler<KeyEvent> keyDownBomber;
    EventHandler<KeyEvent> keyUpBomber;
    private char goTest = ' ';

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        keyDownBomber = event -> {
            switch (event.getCode()) {
                case A:
                    goTest = 'A';
                    break;
                case D:
                    goTest = 'D';
                    break;
                case S:
                    goTest = 'S';
                    break;
                case W:
                    goTest = 'W';
                    break;
            }
            if (!Sound.bomberGo.isPlaying()) {
                Sound.bomberGo.play();
            }
        };
        BombermanGame.game.scene.addEventFilter(KeyEvent.KEY_PRESSED, keyDownBomber);
        keyUpBomber = event -> {
            go = ' ';
            goTest = ' ';
        };
        BombermanGame.game.scene.addEventFilter(KeyEvent.KEY_RELEASED, keyUpBomber);
    }

    public void update() {
        updateGo();
        updateImg();
        entityCollide();
        y = location_y;
        coordinatesX = location_x;
        coordinatesY = location_y;
    }

    private void updateImg() {
        switch (goTest) {
            case 'A':
                if (count_img % 30 == 0) {
                    this.img = Sprite.player_left.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.player_left_1.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.player_left_2.getFxImage();
                }
                break;
            case 'D':
                if (count_img % 30 == 0) {
                    this.img = Sprite.player_right.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.player_right_1.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.player_right_2.getFxImage();
                }
                break;
            case 'S':
                if (count_img % 30 == 0) {
                    this.img = Sprite.player_down.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.player_down_1.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.player_down_2.getFxImage();
                }
                break;
            case 'W':
                if (count_img % 30 == 0) {
                    this.img = Sprite.player_up.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.player_up_1.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.player_up_2.getFxImage();
                }
                break;
        }
        count_img += 4;
    }

    private void updateGo() {
        int _x = location_x;
        int _y = location_y;
        switch (goTest) {
            case 'A':
                _x -= bomberSpeed;
                break;
            case 'D':
                _x += bomberSpeed;
                break;
            case 'S':
                _y += bomberSpeed;
                break;
            case 'W':
                _y -= bomberSpeed;
                break;
        }

        int x1 = (_x + 1) / Sprite.SCALED_SIZE;
        int y1 = (_y + 1) / Sprite.SCALED_SIZE - 2;
        int y2 = (_y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE - 2;
        int x2 = (_x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

        int _x1 = (_x + 10) / Sprite.SCALED_SIZE;
        int _y1 = (_y + 10) / Sprite.SCALED_SIZE - 2;
        int _y2 = (_y + Sprite.SCALED_SIZE - 10) / Sprite.SCALED_SIZE - 2;
        int _x2 = (_x + Sprite.SCALED_SIZE - 10) / Sprite.SCALED_SIZE;

        switch (goTest) {
            case 'A': {
                if (BombermanGame.game.mapGame.getMap(_y1, x1) == ' ' && BombermanGame.game.mapGame.getMap(y2, x1) != ' ') {
                    location_y -= bomberSpeed;
                    go = 'W';
                } else if (BombermanGame.game.mapGame.getMap(y1, x1) != ' ' && BombermanGame.game.mapGame.getMap(_y2, x1) == ' ') {
                    location_y += bomberSpeed;
                    go = 'S';
                } else {
                    location_x -= bomberSpeed;
                    go = goTest;
                }
                break;
            }
            case 'D': {
                if (BombermanGame.game.mapGame.getMap(_y1, x2) == ' ' && BombermanGame.game.mapGame.getMap(y2, x2) != ' ') {
                    location_y -= bomberSpeed;
                    go = 'W';
                } else if (BombermanGame.game.mapGame.getMap(y1, x2) != ' ' && BombermanGame.game.mapGame.getMap(_y2, x2) == ' ') {
                    location_y += bomberSpeed;
                    go = 'S';
                } else {
                    location_x += bomberSpeed;
                    go = goTest;
                }
                break;
            }
            case 'W': {
                if (BombermanGame.game.mapGame.getMap(y1, _x1) == ' ' && BombermanGame.game.mapGame.getMap(y1, x2) != ' ') {
                    location_x -= bomberSpeed;
                    go = 'A';
                } else if (BombermanGame.game.mapGame.getMap(y1, x1) != ' ' && BombermanGame.game.mapGame.getMap(y1, _x2) == ' ') {
                    location_x += bomberSpeed;
                    go = 'D';
                } else {
                    location_y -= bomberSpeed;
                    go = goTest;
                }
                break;
            }
            case 'S': {
                if (BombermanGame.game.mapGame.getMap(y2, _x1) == ' ' && BombermanGame.game.mapGame.getMap(y2, x2) != ' ') {
                    location_x -= bomberSpeed;
                    go = 'A';
                } else if (BombermanGame.game.mapGame.getMap(y2, x1) != ' ' && BombermanGame.game.mapGame.getMap(y2, _x2) == ' ') {
                    location_x += bomberSpeed;
                    go = 'D';
                } else {
                    location_y += bomberSpeed;
                    go = goTest;
                }
                break;
            }
        }
    }

    public void setBomberSpeed() {
        this.bomberSpeed++;
    }
}
