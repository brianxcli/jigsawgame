package brian.pinpin.scenes;

import brian.pinpin.layers.BaseLayer;
import brian.pinpin.layers.PlayLayer;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.layers.CCScene;

public class PlayScene extends BaseScene {
    public static PlayScene create(int... params) {
        return new PlayScene(params);
    }

    private PlayScene(int... params){
        super(params);
    }

    @Override
    public BaseLayer createLayer(int... params) {
        return new PlayLayer(params[0], params[1]);
    }

    public void saveFinished() {
        ((PlayLayer)mLayer).saveFinished();
    }
}
