package brian.pinpin.scenes;

import brian.pinpin.layers.BaseLayer;

public interface IBaseScene {
    BaseLayer createLayer(int... params);
    float getBackgroundScaleX();
    float getBackgroundScaleY();
    void cleanupScene();
}
