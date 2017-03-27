package brian.pinpin.nodes;

import android.graphics.Bitmap;

import org.cocos2d.nodes.CCSprite;

import brian.pinpin.managers.ManagerService;
import brian.pinpin.managers.SceneManager;

public class ScaledSprite extends CCSprite {
    public static ScaledSprite sprite(String filePath, float scale, boolean autoAdjusted) {
        return new ScaledSprite(filePath, scale, autoAdjusted);
    }

    public static ScaledSprite sprite(Bitmap bitmap, String key) {
        return new ScaledSprite(bitmap, key, 0.0f, true);
    }

    public static ScaledSprite sprite(String filePath) {
        return sprite(filePath, 0f, true);
    }

    protected ScaledSprite(String filePath, float scale, boolean autoAdjusted) {
        super(filePath);
        setScale(scale, autoAdjusted);
    }

    protected ScaledSprite(Bitmap bitmap, String key,float scale, boolean autoAdjusted) {
        super(bitmap, key);
        setScale(scale, autoAdjusted);
    }

    protected void setScale(float scale, boolean autoAdjusted) {
        float scaleFactor;
        if (autoAdjusted) {
            SceneManager manager = (SceneManager) ManagerService.getInstance().getService(ManagerService.SCENE_MANAGER);
            scaleFactor = manager.getScalingFactor();
        } else {
            scaleFactor = scale;
        }
        super.setScale(scaleFactor);
    }
}
