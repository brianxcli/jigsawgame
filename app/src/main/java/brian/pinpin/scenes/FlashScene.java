package brian.pinpin.scenes;

import brian.pinpin.layers.FlashLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class FlashScene extends CCScene implements IBaseScene {
    private FlashScene() {
        addChild(new FlashLayer(), SceneManager.Z_ORDER_LAYER, SceneManager.TAG_LAYER);
    }

    public static FlashScene create() {
        return new FlashScene();
    }

    public void cleanupScene() {
        removeSelf();
    }
}
