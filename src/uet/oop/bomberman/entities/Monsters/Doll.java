package uet.oop.bomberman.entities.Monsters;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Entity {
    private int dollSpeed = 1;

    public Doll(int x, int y, Image img) {
        super(x, y, img);
    }

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
            char sea = search();
            if (sea != ' ') {
                go = sea;
            }
        }

        switch (go) {
            case 'A':
                location_x -= dollSpeed;
                if (count_img % 30 == 0) {
                    this.img = Sprite.doll_left1.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.doll_left2.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.doll_left3.getFxImage();
                }
                count_img += 4;
                break;
            case 'D':
                location_x += dollSpeed;
                if (count_img % 30 == 0) {
                    this.img = Sprite.doll_left1.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.doll_left2.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.doll_left3.getFxImage();
                }
                count_img += 4;
                break;
            case 'W':
                location_y -= dollSpeed;
                if (count_img % 30 == 0) {
                    this.img = Sprite.doll_left1.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.doll_left2.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.doll_left3.getFxImage();
                }
                count_img += 4;
                break;
            case 'S':
                location_y += dollSpeed;
                if (count_img % 30 == 0) {
                    this.img = Sprite.doll_left1.getFxImage();
                } else if (count_img % 30 == 10) {
                    this.img = Sprite.doll_left2.getFxImage();
                } else if (count_img % 30 == 20) {
                    this.img = Sprite.doll_left3.getFxImage();
                }
                count_img += 4;
                break;
        }
        this.entityCollide2();
        y = getLocation_y();
    }
}
