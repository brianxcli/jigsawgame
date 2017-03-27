package brian.pinpin.scenes;

import brian.pinpin.layers.BaseLayer;
import brian.pinpin.layers.HomeLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class HomeScene extends BaseScene {
    private HomeScene(int... params) {
        super(params);
    }

    public BaseLayer createLayer(int... params) {
        return new HomeLayer();
    }

    public static HomeScene create(int... params) {
        return new HomeScene(params);
    }
}