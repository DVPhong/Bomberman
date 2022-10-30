package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    protected int count_img = 0;
    protected int location_x;
    protected int location_y;
    protected int x;
    protected int y;
    public Image img;

    protected char go = ' ';

    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.location_x = xUnit * Sprite.SCALED_SIZE;
        this.location_y = yUnit * Sprite.SCALED_SIZE;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public int getLocation_x() {
        return location_x;
    }

    public int getLocation_y() {
        return location_y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void entityCollide() {
        int x1 = (location_x + 1) / Sprite.SCALED_SIZE;
        int y1 = (location_y + 1) / Sprite.SCALED_SIZE - 2;
        int y2 = (location_y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE - 2;
        int x2 = (location_x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

        switch (go) {
            case 'A': {
                if (BombermanGame.game.mapGame.getMap(y1, x1) != ' ' || BombermanGame.game.mapGame.getMap(y2, x1) != ' ') {
                    location_x = (x1 + 1) * Sprite.SCALED_SIZE;
                }
                break;
            }
            case 'D': {
                if (BombermanGame.game.mapGame.getMap(y1, x2) != ' ' || BombermanGame.game.mapGame.getMap(y2, x2) != ' ') {
                    location_x = (x2 - 1) * Sprite.SCALED_SIZE;
                }
                break;
            }
            case 'W': {
                if (BombermanGame.game.mapGame.getMap(y1, x1) != ' ' || BombermanGame.game.mapGame.getMap(y1, x2) != ' ') {
                    location_y = (y1 + 3) * Sprite.SCALED_SIZE;
                }
                break;
            }
            case 'S': {
                if (BombermanGame.game.mapGame.getMap(y2, x1) != ' ' || BombermanGame.game.mapGame.getMap(y2, x2) != ' ') {
                    location_y = (y2 + 1) * Sprite.SCALED_SIZE;
                }
                break;
            }
        }
    }

    public char aStar() {
        return  ' ';
    }
}
