package brian.pinpin.scenes;

import brian.pinpin.layers.BaseLayer;
import brian.pinpin.layers.SelectLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class SelectScene extends BaseScene {
    private SelectScene(int... params) {
        super(params);
    }

    public static SelectScene create(int... params) {
        return new SelectScene(params);
    }

    public BaseLayer createLayer(int... params) {
        return new SelectLayer(params[0]);
    }
}
