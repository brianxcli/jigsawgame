package brian.pinpin.scenes;

import brian.pinpin.layers.AboutLayer;
import org.cocos2d.layers.CCScene;

public class AboutScene extends CCScene {
    private AboutLayer a = new AboutLayer();

    public AboutScene() {
        this.addChild(this.a, -1, 0);
    }

    public static AboutScene create() {
        return new AboutScene();
    }

    public void finishSelf() {
        this.removeSelf();
        this.a = null;
    }
}
