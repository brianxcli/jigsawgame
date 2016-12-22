package brian.pinpin.managers;

import brian.pinpin.scenes.AboutScene;
import brian.pinpin.scenes.FlashScene;
import brian.pinpin.scenes.HomeScene;
import brian.pinpin.scenes.MoreScene;
import brian.pinpin.scenes.PlayScene;
import brian.pinpin.scenes.SelectScene;
import org.cocos2d.layers.CCScene;

public class SceneManager implements IService {
    public static final int TAG_LAYER = 0;
    public static final int Z_ORDER_LAYER = -1;

    public static final int SCENE_FLASH = 1;
    public static final int SCENE_HOME = 2;
    public static final int SCENE_SELECT = 3;
    public static final int SCENE_PLAY = 4;
    public static final int SCENE_ABOUT = 5;
    public static final int SCENE_MORE = 6;

    public static int sceneWidth;
    public static int sceneHeight;

    SceneManager() {

    }

    public CCScene getScene(int scene) {
        switch (scene) {
            case SCENE_FLASH:
                return FlashScene.create();
            case SCENE_HOME:
                return HomeScene.create();
            case SCENE_SELECT:
                return SelectScene.create();
            case SCENE_PLAY:
                return PlayScene.create();
            case SCENE_ABOUT:
                return AboutScene.create();
            case SCENE_MORE:
                return MoreScene.create();
            default:
                return null;
        }
    }

    public void onCreate() {

    }

    public void onDestroy() {

    }
}
