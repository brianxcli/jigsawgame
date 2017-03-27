package brian.pinpin.scenes;

import org.cocos2d.layers.CCScene;

import brian.pinpin.layers.BaseLayer;
import brian.pinpin.managers.SceneManager;

public abstract class BaseScene extends CCScene implements IBaseScene {
    protected BaseLayer mLayer;

    public BaseScene(int... params) {
        mLayer = createLayer(params);
        addChild(mLayer, SceneManager.Z_ORDER_LAYER, SceneManager.TAG_LAYER);
    }

    public float getBackgroundScaleX() {
        return mLayer.getBackgroundScaleX();
    }

    public float getBackgroundScaleY() {
        return mLayer.getBackgroundScaleY();
    }

    public void cleanupScene() {

    }
}
