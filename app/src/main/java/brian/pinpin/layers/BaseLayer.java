package brian.pinpin.layers;

import android.content.Context;
import android.util.Log;

import brian.pinpin.managers.ManagerService;
import brian.pinpin.managers.SoundManager;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.managers.SaveManager;
import brian.pinpin.nodes.ButtonSprite;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class BaseLayer extends CCLayer {
    protected static int BACK_ID = -2;

    protected CCSprite mBackground;
    protected ButtonSprite backBtn;
    protected CGPoint d = CGPoint.make(90.0F, 100.0F);
    protected Context mContext = CCDirector.sharedDirector().getActivity().getApplicationContext();
    protected SoundManager mSoundManager = (SoundManager) ManagerService.getInstance().getService(ManagerService.SOUND_MANAGER);
    protected SceneManager mSceneManager = (SceneManager) ManagerService.getInstance().getService(ManagerService.SCENE_MANAGER);
    protected SaveManager mSaveManager = (SaveManager) ManagerService.getInstance().getService(ManagerService.SAVE_MANAGER);
    protected int width;
    protected int height;
    protected float centerHorizontal;

    protected BaseLayer() {
        mSaveManager.setContext(mContext);
        width = SceneManager.sceneWidth;
        height = SceneManager.sceneHeight;
        centerHorizontal = (float)width / 2.0F;
    }

    protected void addBackground(String res) {
        mBackground = new CCSprite(res);
        if(mBackground != null) {
            mBackground.setAnchorPoint(0.0F, 0.0F);
            mBackground.setPosition(0.0F, 0.0F);
            CGSize size = mBackground.getContentSize();
            mBackground.setScaleX(width / size.getWidth());
            mBackground.setScaleY(height / size.getHeight());
            addChild(mBackground);
        }

    }
}