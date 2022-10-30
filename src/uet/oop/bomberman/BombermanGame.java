package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    public static AnimationTimer timer;

    public static Menu menuGame = new Menu();
    public static Game game = new Game();
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bomberman Game");
        Image icon = new Image("file:res/game.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);




        StageSetting stageSetting = new StageSetting();
        Sound.upSound();
        Sound.music.play();
        stage.setScene(menuGame.menu);
        menuGame.setting.setOnAction(event -> {
            stageSetting.settingStage.setX(stage.getX() + 704);
            stageSetting.settingStage.setY(stage.getY() + 64);
            stageSetting.settingStage.show();
        });
        menuGame.play.setOnAction(event -> {
            game.setGame();
            Bomb.keyBomb();
            stageSetting.settingStage.close();
            Sound.music.stop();
            Sound.music = Sound.bgGame;
            Sound.music.play();
            stage.setScene(game.scene);
            timer.start();
        });
        menuGame.exit.setOnAction(event -> {
//            try {
//                FileWriter file = new FileWriter("res/data_game.txt");
//                file.write("" + Map.mapLever);
//                file.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            stageSetting.settingStage.close();
            stage.close();
        });


        stage.show();

//        Game game = new Game();
//        game.setGame();
        game.setting.setOnAction(event -> {
            stageSetting.settingStage.setX(stage.getX() + 704);
            stageSetting.settingStage.setY(stage.getY() + 64);
            stageSetting.settingStage.show();
        });
        game.exit.setOnAction(event -> {
            stageSetting.settingStage.close();
            //game.downDataGame();
            stage.close();
        });
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                game.update();
                game.render();
                if (!game.bomberCollide()) {
                    Sound.dead.play();
                    timer.stop();
                    stage.setScene(menuGame.menu);
                    game.resetGame();
                }
            }
        };
    }
}