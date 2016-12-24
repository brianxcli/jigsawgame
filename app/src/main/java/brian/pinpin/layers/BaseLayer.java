package brian.pinpin.layers;

import android.content.Context;

import brian.pinpin.managers.ManagerService;
import brian.pinpin.managers.SoundManager;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.managers.SaveManager;
import brian.pinpin.nodes.ButtonSprite;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

class BaseLayer extends CCLayer {
    static final int BACK_ID = -2;

    CCSprite mBackground;
    ButtonSprite backBtn;
    CGPoint mBackPos = CGPoint.make(90.0F, 100.0F);
    Context mContext = CCDirector.sharedDirector().getActivity().getApplicationContext();
    SoundManager mSoundManager = (SoundManager) ManagerService.getInstance().getService(ManagerService.SOUND_MANAGER);
    SceneManager mSceneManager = (SceneManager) ManagerService.getInstance().getService(ManagerService.SCENE_MANAGER);
    SaveManager mSaveManager = (SaveManager) ManagerService.getInstance().getService(ManagerService.SAVE_MANAGER);
    int mScreenWidth;
    int mScreenHeight;
    float centerHorizontal;

    BaseLayer() {
        mSaveManager.setContext(mContext);
        mScreenWidth = SceneManager.sceneWidth;
        mScreenHeight = SceneManager.sceneHeight;
        centerHorizontal = (float) mScreenWidth / 2.0F;
    }

    void addBackground(String res) {
        mBackground = new CCSprite(res);
        mBackground.setAnchorPoint(0.0F, 0.0F);
        mBackground.setPosition(0.0F, 0.0F);
        CGSize size = mBackground.getContentSize();
        mBackground.setScaleX(mScreenWidth / size.getWidth());
        mBackground.setScaleY(mScreenHeight / size.getHeight());
        addChild(mBackground);
    }

    boolean contains(CCNode node, CGPoint point) {
        return CGRect.containsPoint(node.getBoundingBox(), point);
    }
}