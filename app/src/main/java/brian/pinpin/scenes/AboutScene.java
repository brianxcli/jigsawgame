package brian.pinpin.scenes;

import brian.pinpin.layers.AboutLayer;
import brian.pinpin.layers.BaseLayer;

public class AboutScene extends BaseScene {
    private AboutScene(int... params) {
        super(params);
    }

    public BaseLayer createLayer(int... params) {
        return new AboutLayer();
    }

    public static AboutScene create(int... params) {
        return new AboutScene(params);
    }
}
