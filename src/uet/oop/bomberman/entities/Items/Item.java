package uet.oop.bomberman.entities.Items;

import javafx.scene.image.Image;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;

public abstract class Item extends Entity {

    public Item(int x, int y, Image image) {
        super(x, y, image);
    }

    public boolean compareItem(Item item) {
        return location_x == item.getLocation_x()
                && location_y == item.getLocation_y();
    }
    public abstract void update();

    protected void remove() {
        for (int i = 0; i < Game.items.size(); ++i) {
            if (Game.items.get(i).compareItem(this)) {
                Game.items.remove(i);
                break;
            }
        }
    }
}
