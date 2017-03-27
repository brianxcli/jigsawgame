package brian.pinpin.scenes;

import brian.pinpin.layers.BaseLayer;
import brian.pinpin.layers.FlashLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class FlashScene extends BaseScene {
    private FlashScene(int... params) {
        super(params);
    }

    public BaseLayer createLayer(int... params) {
        return new FlashLayer();
    }

    public static FlashScene create(int... params) {
        return new FlashScene(params);
    }
}
