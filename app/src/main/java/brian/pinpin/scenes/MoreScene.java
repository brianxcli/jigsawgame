package brian.pinpin.scenes;

import brian.pinpin.layers.MoreLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class MoreScene extends CCScene implements IBaseScene {
    private MoreScene() {
        addChild(new MoreLayer(), SceneManager.Z_ORDER_LAYER, SceneManager.TAG_LAYER);
    }

    public static MoreScene create() {
        return new MoreScene();
    }

    public void cleanupScene() {
        // removeSelf();
    }
}
