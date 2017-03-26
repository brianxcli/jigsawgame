package brian.pinpin.scenes;

import brian.pinpin.layers.HomeLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class HomeScene extends CCScene implements IBaseScene {
    private HomeScene() {
        addChild(new HomeLayer(), SceneManager.Z_ORDER_LAYER, SceneManager.TAG_LAYER);
    }

    public static HomeScene create() {
        return new HomeScene();
    }

    public void cleanupScene() {
        // removeSelf();
    }
}