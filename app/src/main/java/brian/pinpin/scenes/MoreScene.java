package brian.pinpin.scenes;

import brian.pinpin.layers.BaseLayer;
import brian.pinpin.layers.MoreLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class MoreScene extends BaseScene {
    private MoreScene(int... params) {
        super(params);
    }

    public static MoreScene create(int... params) {
        return new MoreScene(params);
    }

    public BaseLayer createLayer(int... params) {
        return new MoreLayer();
    }
}
