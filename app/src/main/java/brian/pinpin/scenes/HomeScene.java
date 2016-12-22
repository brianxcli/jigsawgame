package brian.pinpin.scenes;

import brian.pinpin.layers.HomeLayer;
import org.cocos2d.layers.CCScene;

public class HomeScene extends CCScene {
    private HomeLayer mHomeLayer = new HomeLayer();

    public HomeScene() {
        addChild(mHomeLayer, -1, 0);
    }

    public static HomeScene create() {
        return new HomeScene();
    }

    public void finishSelf() {
        removeSelf();
        mHomeLayer = null;
    }
}