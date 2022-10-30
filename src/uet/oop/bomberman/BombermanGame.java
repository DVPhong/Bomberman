package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import uet.oop.bomberman.Stage.Menu;
import uet.oop.bomberman.Stage.StageSetting;
import uet.oop.bomberman.Stage.Victory;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.graphics.Map;

public class BombermanGame extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    public static AnimationTimer timer;

    public static Menu menuGame = new Menu();
    public static Game game = new Game();
    public static Victory victory = new Victory();
    public static StageSetting stageSetting = new StageSetting();
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
            stageSetting.settingStage.close();
            stage.close();
        });

        stage.show();

        game.setting.setOnAction(event -> {
            stageSetting.settingStage.setX(stage.getX() + 704);
            stageSetting.settingStage.setY(stage.getY() + 64);
            stageSetting.settingStage.show();
        });
        game.exit.setOnAction(event -> {
            stageSetting.settingStage.close();
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
                    Sound.music.stop();
                    Sound.music = Sound.lobby;
                    Sound.music.play();
                    stage.setScene(menuGame.menu);
                    game.resetGame();
                }
                if (game.checkVictory()) {
                    if (Map.maxLevel < game.mapGame.mapLevel + 1) {
                        stage.setScene(victory.victory);
                        Sound.music.stop();
                        Sound.victory.play();
                        timer.stop();
                        victory.exit.setOnAction(event -> {
                            stage.close();
                        });
                    } else {
                        timer.stop();
                        Sound.music.stop();
                        Sound.music = Sound.lobby;
                        Sound.music.play();
                        Sound.uplevel.play();
                        game.mapGame.mapLevel++;
                        game.resetGame();
                        stageSetting.settingStage.close();
                        stage.setScene(menuGame.menu);
                    }
                }
            }
        };
    }
}