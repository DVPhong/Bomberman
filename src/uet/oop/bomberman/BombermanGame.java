package uet.oop.bomberman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uet.oop.bomberman.graphics.Sprite;


public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    public static GraphicsContext gc;
    public static Canvas canvas;

    public static Scene scene;
    public static AnimationTimer timer;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bomberman Game");
        Image icon = new Image("file:res/game.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Menu menuGame = new Menu();
        StageSetting stageSetting = new StageSetting();
          Sound.upSound();
//        Sound.lobby.play();
        stage.setScene(menuGame.menu);
        menuGame.setting.setOnAction(event -> {
            stageSetting.settingStage.setX(stage.getX() + 704);
            stageSetting.settingStage.setY(stage.getY() + 64);
            stageSetting.settingStage.show();
        });
        menuGame.play.setOnAction(event -> {
            stageSetting.settingStage.close();
            Sound.music.stop();
            Sound.music = Sound.bgGame;
            Sound.music.setCycleCount(AudioClip.INDEFINITE);
            //Sound.music.play();
            stage.setScene(scene);
            timer.start();
        });
        menuGame.exit.setOnAction(event -> {
            stageSetting.settingStage.close();
            stage.close();
        });

        Group root = new Group();
        root.getChildren().addAll(canvas);
        scene = new Scene(root);
        scene.setFill(Color.rgb(194, 214, 214));
        stage.show();

        Game game = new Game();
        game.setGame();
        root.getChildren().addAll(game.lever, game.items, game.exit, game.setting);
        game.setting.setOnAction(event -> {
            stageSetting.settingStage.setX(stage.getX() + 704);
            stageSetting.settingStage.setY(stage.getY() + 64);
            stageSetting.settingStage.show();
        });
        game.exit.setOnAction(event -> {
            try {
                File file = new File("res/data_game.txt");
                FileReader input = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(input);
                String one = bufferedReader.readLine();
                String[] oneOne = one.split(" ");
                //System.out.println(oneOne[0]);
            } catch (IOException i) {
                i.printStackTrace();
            }
            stageSetting.settingStage.close();
            game.downDataGame();
            stage.close();
        });
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                game.update();
                game.render();
                if (!game.live()) {
                    Sound.dead.play();
                    game.getBomber().setImg(Sprite.player_dead1.getFxImage());
                    game.render();
                    delay();
                    game.getBomber().setImg(Sprite.player_dead2.getFxImage());
                    game.render();
                    delay();
                    game.getBomber().setImg(Sprite.player_dead3.getFxImage());
                    game.render();
                    delay();
                    timer.stop();
                    stage.setScene(menuGame.menu);
                    game.resetGame();
                }
            }
        };
    }

    public void delay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}