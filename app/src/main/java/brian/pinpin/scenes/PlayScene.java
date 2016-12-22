package brian.pinpin.scenes;

import brian.pinpin.layers.PlayLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class PlayScene extends CCScene implements IBaseScene {
    private PlayLayer layer;

    private PlayScene(){}

    public static PlayScene create() {
        return new PlayScene();
    }

    public void setPlayLayer(int side, int number) {
        layer = new PlayLayer(side, number);
        addChild(layer, SceneManager.Z_ORDER_LAYER, SceneManager.TAG_LAYER);
    }

    public void saveFinished() {
        layer.saveFinished();
    }

    public void cleanupScene() {
        removeSelf();
    }
}
