package brian.pinpin.scenes;

import brian.pinpin.layers.PlayLayer;
import org.cocos2d.layers.CCScene;

public class PlayScene extends CCScene {
    private PlayLayer layer;

    private PlayScene(){}

    public static PlayScene create() {
        return new PlayScene();
    }

    public void setPlayLayer(int side, int number) {
        layer = new PlayLayer(side, number);
        addChild(layer, -1, 0);
    }

    public void saveFinished() {
        layer.saveFinished();
    }
}
