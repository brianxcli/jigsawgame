package brian.pinpin.scenes;

import brian.pinpin.layers.MoreLayer;
import org.cocos2d.layers.CCScene;

public class MoreScene extends CCScene {
    private MoreLayer a = new MoreLayer();

    public MoreScene() {
        this.addChild(this.a, -1, 0);
    }

    public static MoreScene create() {
        return new MoreScene();
    }

    public void finishSelf() {
        this.removeSelf();
        this.a = null;
    }
}
