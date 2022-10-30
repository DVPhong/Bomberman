package uet.oop.bomberman.Stage;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Victory {
    public Button exit = new Button("EXIT");
    public Scene victory;
    public Group victoryGroup = new Group();

    public Victory() {
        Image bgVictory = new Image("file:res/victory.png");
        ImageView viewBgVictory = new ImageView(bgVictory);

        Text thank = new Text(128, 384, "Thank you for playing the game");
        thank.setFill(Color.rgb(255, 0, 0));
        thank.setFont(Font.font(30));

        victoryGroup.getChildren().addAll(viewBgVictory, exit, thank);
        victory = new Scene(victoryGroup, Sprite.SCALED_SIZE * BombermanGame.WIDTH,
                Sprite.SCALED_SIZE * BombermanGame.HEIGHT);
    }
}
