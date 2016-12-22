package brian.pinpin.scenes;

import brian.pinpin.layers.AboutLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class AboutScene extends CCScene implements IBaseScene {
    private AboutScene() {
        addChild(new AboutLayer(), SceneManager.Z_ORDER_LAYER, SceneManager.TAG_LAYER);
    }

    public static AboutScene create() {
        return new AboutScene();
    }

    public void cleanupScene() {
        removeSelf();
    }
}
