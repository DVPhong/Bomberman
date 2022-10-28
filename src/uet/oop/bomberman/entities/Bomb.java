package uet.oop.bomberman.entities;

import static uet.oop.bomberman.Game.bombs;
import static uet.oop.bomberman.Game.mapGame;

import java.util.Arrays;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {

  private static long time_bomb;
  private static long time_tmp;
  private static Bomb bomb;
  private static int number_bomb = 1;
  private static int power_bomb = 1; // Số ô bom phá theo 1 hướng
  private static int swap_active = 0;
  private static int isPlaceBomb = 0;

  public Bomb(int x, int y, Image img) {
    super(x, y, img);
  }

  public static void setNumber_bomb(int number_bomb) {
    Bomb.number_bomb = number_bomb;
  }

  public static void keyBomb() {
    EventHandler<KeyEvent> keyEvent;
    keyEvent = event -> {
      if (event.getCode() == KeyCode.P) {
        placeBomb();
      }
    };
    BombermanGame.scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent);
  }

  public static void placeBomb() {
    if (number_bomb > 0) {
      number_bomb--;
      bomb = new Bomb(
          (Bomber.coordinatesX + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE,
          (Bomber.coordinatesY + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE,
          Sprite.bomb.getFxImage());
      bombs.add(bomb);
      time_bomb = System.currentTimeMillis();
      time_tmp = time_bomb;
      //isPlaceBomb++;
    }
  }

  // Đếm ngược thời gian bomb nổ
  public void countDownToExplosion() {
    if (System.currentTimeMillis() - time_bomb < 3050L) {
      if (System.currentTimeMillis() - time_tmp >= 800L) {
        time_tmp += 800L;
        if (swap_active == 0) {
          bomb.setImg(Sprite.bomb.getFxImage());
          swap_active = 1;
        } else if (swap_active == 1) {
          bomb.setImg(Sprite.bomb_1.getFxImage());
          swap_active = 2;
        } else if (swap_active == 2) {
          bomb.setImg(Sprite.bomb_exploded1.getFxImage());
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

    for (int i = 1; i < 1 + 4 * power_bomb; i++) {
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

    for (Flame a : flames) {
      char position = mapGame.getMap(
          (a.location_y + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE - 2,
          (a.location_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
      if (position == '#') {
        a.setImg(Sprite.wall.getFxImage());
      } else if (position == '*') {
        // Xoa brick tai vi tri flame a
        Game.stillObjects.removeIf(brick -> brick.getLocation_x()
//            == (a.location_x + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE
//            && brick.getLocation_y() == (a.location_y+ Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE);
            == a.getLocation_x() && brick.getLocation_y() == a.getLocation_y());
      }
    }
    try {
      Game.entities.addAll(Arrays.asList(flames).subList(0, 2 + 4 * power_bomb));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public void afterExplosion() {
    Game.entities.removeIf(i -> i instanceof Flame);
    time_bomb = System.currentTimeMillis();
    time_tmp = time_bomb;
    swap_active = 0;
    for (int i = 0; i < bombs.size(); ++i) {
      bombs.remove(i);
      --i;
    }
    number_bomb++;
    //isPlaceBomb--;
  }

  @Override
  public void update() {
    //if (isPlaceBomb > 0)
      countDownToExplosion();
  }
}
