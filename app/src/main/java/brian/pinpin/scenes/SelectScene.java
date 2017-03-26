package brian.pinpin.scenes;

import brian.pinpin.layers.SelectLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class SelectScene extends CCScene implements IBaseScene {
    private SelectLayer layer;

    private SelectScene() {
    }

    public static SelectScene create() {
        return new SelectScene();
    }

    public void setSide(int side) {
        layer = new SelectLayer(side);
        addChild(layer, SceneManager.Z_ORDER_LAYER, SceneManager.TAG_LAYER);
    }

    public void cleanupScene() {
        // removeSelf();
    }
}
