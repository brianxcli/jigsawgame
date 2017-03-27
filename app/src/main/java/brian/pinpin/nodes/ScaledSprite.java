package brian.pinpin.nodes;

import org.cocos2d.nodes.CCSprite;

import brian.pinpin.managers.ManagerService;
import brian.pinpin.managers.SceneManager;

public class ScaledSprite extends CCSprite {
    public static ScaledSprite sprite(String filePath, float scale, boolean autoAdjusted) {
        return new ScaledSprite(filePath, scale, autoAdjusted);
    }

    public static ScaledSprite sprite(String filePath) {
        return sprite(filePath, 0f, true);
    }

    protected ScaledSprite(String filePath, float scale, boolean autoAdjusted) {
        super(filePath);

        float scaleFactor;
        if (autoAdjusted) {
            SceneManager manager = (SceneManager) ManagerService.getInstance().getService(ManagerService.SCENE_MANAGER);
            scaleFactor = manager.getScalingFactor();
        } else {
            scaleFactor = scale;
        }

        setScale(scaleFactor);
    }
}
