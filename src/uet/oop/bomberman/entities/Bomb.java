package uet.oop.bomberman.entities;

import static uet.oop.bomberman.Game.bombs;
import static uet.oop.bomberman.Game.entities;
import static uet.oop.bomberman.Game.stillObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound;
import uet.oop.bomberman.entities.Monsters.Ball;
import uet.oop.bomberman.entities.Monsters.Min;
import uet.oop.bomberman.entities.Monsters.Oneal;
import uet.oop.bomberman.entities.Monsters.Ova;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {

    private static long time_bomb;
    private static long time_tmp;
    private static Bomb bomb;

    public static int power_bomb = 1;
    private static int swap_active = 0;
    public static int number_bomb = 1;
    public static int number_bomb_placed = -1;

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public static List<Flame> enn = new ArrayList<>();
    public static void keyBomb() {
        EventHandler<KeyEvent> keyEvent;
        keyEvent = event -> {
            if (event.getCode() == KeyCode.P) {
                placeBomb();
            }
        };
        BombermanGame.game.scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent);
    }

    public static void placeBomb() {
        if (number_bomb > 0) {
            Sound.setBomb.play();
            number_bomb--;
            bomb = new Bomb(
                    (Bomber.coordinatesX + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE,
                    (Bomber.coordinatesY + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE,
                    Sprite.bomb.getFxImage());
            bombs.add(bomb);
            BombermanGame.game.mapGame.setMap(
                (bomb.location_y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE - 2,
                (bomb.location_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE,'m');
            time_bomb = System.currentTimeMillis();
            time_tmp = time_bomb;
            number_bomb_placed++;
        }
    }

    public void countDownToExplosion() {
        if (System.currentTimeMillis() - time_bomb < 3050L) {
            if (System.currentTimeMillis() - time_tmp >= 800L) {
                time_tmp += 800L;
                if (swap_active == 0) {
                    bomb.img = Sprite.bomb.getFxImage();
                    swap_active = 1;
                } else if (swap_active == 1) {
                    bomb.img = Sprite.bomb_1.getFxImage();
                    swap_active = 2;
                } else if (swap_active == 2) {
                    bomb.img = Sprite.bomb_exploded1.getFxImage();
                    Sound.bomExplode.play();
                    Explosion();
                }
            }
        }
        if (System.currentTimeMillis() - time_bomb > 3050L) {
            afterExplosion();
        }
    }

    public void Explosion() {
        Flame[] flames = new Flame[2 + 4 * power_bomb];
        int x = (bomb.location_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
        int y = (bomb.location_y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;

        flames[0] = new Flame(x, y, Sprite.explosion_horizontal1.getFxImage());
        flames[1 + 4 * power_bomb] = new Flame(x, y, Sprite.explosion_vertical1.getFxImage());

        for (int i = 1; i < 1 + 4 * power_bomb; ++i) {
            if (i % 4 == 1 && i != 4 * power_bomb - 3) {
                flames[i] = new Flame(x + 1 + i / 4, y, Sprite.explosion_horizontal1.getFxImage());
            } else if (i % 4 == 2 && i != 4 * power_bomb - 2) {
                flames[i] = new Flame(x - 1 - i / 4, y, Sprite.explosion_horizontal1.getFxImage());
            } else if (i % 4 == 3 && i != 4 * power_bomb - 1) {
                flames[i] = new Flame(x, y + 1 + i / 4, Sprite.explosion_vertical1.getFxImage());
            } else if (i % 4 == 0 && i != 4 * power_bomb) {
                flames[i] = new Flame(x, y - i / 4, Sprite.explosion_vertical1.getFxImage());
            } else if (i == 4 * power_bomb - 3) {
                flames[i] = new Flame(x + power_bomb, y,
                        Sprite.explosion_horizontal_right_last1.getFxImage());
            } else if (i == 4 * power_bomb - 2) {
                flames[i] = new Flame(x - power_bomb, y,
                        Sprite.explosion_horizontal_left_last1.getFxImage());
            } else if (i == 4 * power_bomb - 1) {
                flames[i] = new Flame(x, y + power_bomb,
                        Sprite.explosion_vertical_down_last1.getFxImage());//
            } else if (i == 4 * power_bomb) {
                flames[i] = new Flame(x, y - power_bomb, Sprite.explosion_vertical_top_last1.getFxImage());
            }
        }

        for (int i = 0; i < flames.length; i++) {
            char position = BombermanGame.game.mapGame.getMap(
                    (flames[i].location_y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE - 2,
                    (flames[i].location_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
            if (position == '#') {
                flames[i].img = Sprite.wall.getFxImage();
                if (i + 4 < flames.length) {
                    char pos = BombermanGame.game.mapGame.getMap(
                            (flames[i + 4].location_y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE - 2,
                            (flames[i + 4].location_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
                    if (pos == '#')
                        flames[i + 4].img = Sprite.wall.getFxImage();
                    else if (pos == '*') {
                        flames[i + 4].img = Sprite.brick.getFxImage();
                        enn.add(flames[i+4]);}
                    else if (pos == ' ')
                        flames[i + 4].img = Sprite.grass.getFxImage();
                }
            }
        }

        try {
            Game.entities.addAll(Arrays.asList(flames).subList(0, 2 + 4 * power_bomb));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void afterExplosion() {
        for (Entity a : entities) {
            if (a instanceof Flame && !enn.contains(a))  {
                char position = BombermanGame.game.mapGame.getMap(
                        (a.location_y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE - 2,
                        (a.location_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);

                if (position != '#') {
                    BombermanGame.game.mapGame.setMap((a.location_y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE - 2,
                            (a.location_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE, ' ');
                    for (Entity b : stillObjects) {
                        if (b.getLocation_x() == a.getLocation_x() && b.getLocation_y() == a.getLocation_y()) {
                            if (position == '*' || position == ' ') {
                                b.img = Sprite.grass.getFxImage();
                            } else {
                                b.img = null;
                            }
                        }
                    }
                }

                for (int i = 1; i < entities.size(); i++) {
                    if (entities.get(i) instanceof Ball || entities.get(i) instanceof Min
                        || entities.get(i) instanceof Oneal || entities.get(i) instanceof Ova) {
                        if (Math.abs(entities.get(i).getLocation_x() - a.getLocation_x()) < Sprite.SCALED_SIZE
                                && Math.abs(entities.get(i).getLocation_y() - a.getLocation_y()) < Sprite.SCALED_SIZE) {
                            entities.set(i, new Flame(a.getLocation_x(), a.getLocation_y(),
                                    Sprite.balloom_dead.getFxImage()));
                        }
                    }
                }
            }
        }
        Game.entities.removeIf(i -> i instanceof Flame);
        time_bomb = System.currentTimeMillis();
        time_tmp = time_bomb;
        swap_active = 0;

        bomb.setX(50 * Sprite.SCALED_SIZE);
        bomb.setY(50 * Sprite.SCALED_SIZE);

        BombermanGame.game.mapGame.setMap(
            (bomb.location_y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE - 2,
            (bomb.location_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE,' ');
        number_bomb++;
        number_bomb_placed--;
    }

    @Override
    public void update() {
        if (number_bomb_placed >= 0) {
            countDownToExplosion();
        }
    }
}