package brian.pinpin.scenes;

import brian.pinpin.layers.FlashLayer;
import org.cocos2d.layers.CCScene;

public class FlashScene extends CCScene {
    private FlashLayer mFlashLayer = new FlashLayer();

    public FlashScene() {
        addChild(mFlashLayer, -1, 0);
    }

    public static FlashScene create() {
        return new FlashScene();

    }

    public void finishSelf() {
        mFlashLayer = null;
        removeSelf();
    }
}
