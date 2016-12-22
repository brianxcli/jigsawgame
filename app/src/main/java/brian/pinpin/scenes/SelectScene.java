package brian.pinpin.scenes;

import brian.pinpin.layers.SelectLayer;
import org.cocos2d.layers.CCScene;

public class SelectScene extends CCScene {
    private SelectLayer layer;

    public SelectScene() {
    }

    public static SelectScene create() {
        return new SelectScene();
    }

    public void a(int var1) {
        layer = new SelectLayer(var1);
        addChild(layer, -1, 0);
    }
}
