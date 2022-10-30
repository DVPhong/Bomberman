package uet.oop.bomberman;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.entities.*;

import uet.oop.bomberman.entities.Items.BombItem;
import uet.oop.bomberman.entities.Items.FlameItem;
import uet.oop.bomberman.entities.Items.Item;
import uet.oop.bomberman.entities.Items.SpeedItem;
import uet.oop.bomberman.entities.Monsters.Ball;
import uet.oop.bomberman.entities.Monsters.Oneal;
import uet.oop.bomberman.graphics.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    Text lever = new Text(8, 43, "LEVER: ");
    Text item = new Text(160, 43, "ITEM: ");
    public Button setting = new Button("SETTING");
    public Button exit = new Button("EXIT");
    private static GraphicsContext gc;
    private static final Canvas canvas = new Canvas(Sprite.SCALED_SIZE * BombermanGame.WIDTH,
            Sprite.SCALED_SIZE * BombermanGame.HEIGHT);

    public Scene scene;

    public Map mapGame = new Map();
    private final List<Portal> portals = new ArrayList<>();

    public static final List<Entity> entities = new ArrayList<>();

    public static final List<Bomb> bombs = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static final List<Entity> stillObjects = new ArrayList<>();

    private void textSet(Text text) {
        text.setFont(Font.font(20));
        text.setFill(Color.rgb(51, 51, 0));
        text.setFont(Font.font("Harrington", 32));
    }

    private void buttonSet(Button button) {
        button.setLayoutX(544);
        button.setPrefWidth(96);
        button.setPrefHeight(32);
        button.setTextFill(Color.rgb(0, 153, 153));
        button.setFont(Font.font(12));
        button.setOnMouseMoved(event -> {
            button.setTextFill(Color.rgb(255, 0, 0));
            button.setFont(Font.font(14));
        });
        button.setOnMouseExited(event -> {
            button.setTextFill(Color.rgb(0, 153, 153));
            button.setFont(Font.font(12));
        });
    }

    private void gameBottom() {
    }

    public Game() {
        setting.setLayoutY(0);
        exit.setLayoutY(32);
        buttonSet(setting);
        buttonSet(exit);
        textSet(item);
        textSet(lever);
    }
    public void setGame() {
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().addAll(canvas);
        scene = new Scene(root);
        scene.setFill(Color.rgb(194, 214, 214));
        root.getChildren().addAll(lever, item, exit, setting);

        mapGame.upMap();
        createMap();
    }

    public Bomber getBomber() {
        for (Entity entity : entities) {
            if (entity instanceof Bomber) {
                return (Bomber) entity;
            }
        }
        return (Bomber) entities.get(0);
    }

    public void createMap() {
        for (int i = 2; i < Map.r + 2; ++i) {
            for (int j = 0; j < Map.c; ++j) {
                Entity object;
                switch (mapGame.getMap(i - 2, j)) {
                    case '#': {
                        object = new Tiles(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case '*': {
                        object = new Tiles(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case 'x': {
                        Portal portal = new Portal(j, i);
                        portals.add(portal);
                        object = new Tiles(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case 'p':
                        object = new Bomber(j, i, Sprite.player_right.getFxImage());
                        entities.add(object);
                        object = new Tiles(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        Bomber.coordinatesX = j * Sprite.SCALED_SIZE;
                        Bomber.coordinatesY = i * Sprite.SCALED_SIZE;
                        mapGame.setMap(i - 2, j, ' ');
                        break;
                    case '1': {
                        object = new Ball(j, i, Sprite.balloom_right1.getFxImage());
                        entities.add(object);
                        object = new Tiles(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        mapGame.setMap(i - 2, j, ' ');
                        break;
                    }
                    case '2': {
                        object = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                        entities.add(object);
                        object = new Tiles(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        mapGame.setMap(i - 2, j, ' ');
                        break;
                    }
                    case 'b': {
                        BombItem bombItem = new BombItem(j, i);
                        items.add(bombItem);
                        object = new Tiles(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case 'f': {
                        FlameItem flameItem = new FlameItem(j, i);
                        items.add(flameItem);
                        object = new Tiles(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case 's': {
                        SpeedItem speedItem = new SpeedItem(j, i);
                        items.add(speedItem);
                        object = new Tiles(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    default: {
                        object = new Tiles(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        mapGame.setMap(i - 2, j, ' ');
                        break;
                    }
                }
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        bombs.forEach(Bomb::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (Bomber.coordinatesX <= Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1) / 2) {
            entities.forEach(g -> g.setX(g.getLocation_x()));
        } else if (Bomber.coordinatesX < Sprite.SCALED_SIZE * (Map.c  - 0.5 - 0.5 * BombermanGame.WIDTH)) {
            stillObjects.forEach(g -> g.setX(g.getLocation_x() - (Bomber.coordinatesX - Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1) / 2)));
            bombs.forEach(g -> g.setX(Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1) / 2 - Bomber.coordinatesX + g.getLocation_x()));
            entities.forEach(g -> g.setX(g.getLocation_x() - (Bomber.coordinatesX - Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1) / 2)));
            items.forEach(g -> g.setX(Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1) / 2 - Bomber.coordinatesX + g.getLocation_x()));
            portals.forEach(g -> g.setX(Sprite.SCALED_SIZE * (BombermanGame.WIDTH - 1) / 2 - Bomber.coordinatesX + g.getLocation_x()));
        } else {
            stillObjects.forEach(g -> g.setX(g.getLocation_x() - Sprite.SCALED_SIZE * (Map.c - BombermanGame.WIDTH)));
            bombs.forEach(g -> g.setX(g.getLocation_x() - Sprite.SCALED_SIZE * (Map.c - BombermanGame.WIDTH)));
            entities.forEach(g -> g.setX(g.getLocation_x() - Sprite.SCALED_SIZE * (Map.c - BombermanGame.WIDTH)));
            items.forEach(g -> g.setX(g.getLocation_x() - Sprite.SCALED_SIZE * (Map.c - BombermanGame.WIDTH)));
            portals.forEach(g -> g.setX(g.getLocation_x() - Sprite.SCALED_SIZE * (Map.c - BombermanGame.WIDTH)));
        }
        items.forEach(g -> g.render(gc));
        portals.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public void downDataGame() {
        try {
            FileWriter file = new FileWriter("res/data_game.txt");
            StringBuilder data = new StringBuilder("0\n" + Map.r + " " + Map.c + " " + Bomber.coordinatesX + "\n");
            for (Entity s : stillObjects) {
                data.append(s.getLocation_x()).append(" ").append(s.getLocation_y()).append(" ").append(s.getX()).append(" ").append(s.getY()).append("\n");
            }
            for (Entity e : entities) {
                data.append(e.getLocation_x()).append(" ").append(e.getLocation_y()).append(" ").append(e.getX()).append(" ").append(e.getY()).append("\n");
            }
            file.write(data.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean bomberCollide() {
        int bomberX1 = Bomber.coordinatesX + 1;
        int bomberX2 = bomberX1 + Sprite.SCALED_SIZE - 1;
        int bomberY1 = Bomber.coordinatesY + 1;
        int bomberY2 = bomberY1 + Sprite.SCALED_SIZE - 1;

        for (int i = 1; i < entities.size(); ++i) {

            int monsterX1 = entities.get(i).getLocation_x() + 4;
            int monsterX2 = monsterX1 + Sprite.SCALED_SIZE - 4;
            int monsterY1 = entities.get(i).getLocation_y() + 4;
            int monsterY2 = monsterY1 + Sprite.SCALED_SIZE - 4;

            if (((bomberX1 < monsterX1 && monsterX1 < bomberX2)
                    || (bomberX1 < monsterX2 && monsterX2 < bomberX2))
                    && ((bomberY1 < monsterY1 && monsterY1 < bomberY2)
                    || (bomberY1 < monsterY2 && monsterY2 < bomberY2))) {
                return false;
            }
        }

        int j = 0;
        boolean ok = false;
        for (int i = 0; i < items.size(); ++i) {

            int monsterX1 = items.get(i).getLocation_x() + 4;
            int monsterX2 = monsterX1 + Sprite.SCALED_SIZE - 4;
            int monsterY1 = items.get(i).getLocation_y() + 4;
            int monsterY2 = monsterY1 + Sprite.SCALED_SIZE - 4;

            if (((bomberX1 < monsterX1 && monsterX1 < bomberX2)
                    || (bomberX1 < monsterX2 && monsterX2 < bomberX2))
                    && ((bomberY1 < monsterY1 && monsterY1 < bomberY2)
                    || (bomberY1 < monsterY2 && monsterY2 < bomberY2))) {
                if (items.get(i) instanceof SpeedItem
                        || items.get(i) instanceof BombItem
                        || items.get(i) instanceof FlameItem) {
                    j = i;
                    ok = true;
                    stillObjects.get(((monsterY1 / Sprite.SCALED_SIZE) - 2) * Map.c
                            + monsterX1 / Sprite.SCALED_SIZE).img = Sprite.grass.getFxImage();
                    Sound.itemWav.play();
                    break;
                }
            }
        }
        if (ok) {
            items.get(j).update();
        }
        return true;
    }

    public boolean checkVictory() {
        if (entities.size() > 1) {
            return false;
        }

        int bomberX1 = Bomber.coordinatesX + 1;
        int bomberX2 = bomberX1 + Sprite.SCALED_SIZE - 1;
        int bomberY1 = Bomber.coordinatesY + 1;
        int bomberY2 = bomberY1 + Sprite.SCALED_SIZE - 1;

        for (Portal portal : portals) {
            int monsterX1 = portal.getLocation_x() + 4;
            int monsterX2 = monsterX1 + Sprite.SCALED_SIZE - 4;
            int monsterY1 = portal.getLocation_y() + 4;
            int monsterY2 = monsterY1 + Sprite.SCALED_SIZE - 4;

            if (((bomberX1 < monsterX1 && monsterX1 < bomberX2)
                    || (bomberX1 < monsterX2 && monsterX2 < bomberX2))
                    && ((bomberY1 < monsterY1 && monsterY1 < bomberY2)
                    || (bomberY1 < monsterY2 && monsterY2 < bomberY2))) {
                return true;
            }
        }
        return false;
    }

    public void resetGame() {
        for (int i = 0; i < entities.size(); ++i) {
            entities.remove(i);
            --i;
        }
        for (int i = 0; i < stillObjects.size(); ++i) {
            stillObjects.remove(i);
            --i;
        }
        for (int i = 0; i < bombs.size(); ++i) {
            bombs.remove(i);
            --i;
        }
        //setGame();
    }
}