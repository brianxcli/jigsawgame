package brian.pinpin.layers;

import android.content.Context;

import brian.pinpin.managers.ManagerService;
import brian.pinpin.managers.SoundManager;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.managers.SaveManager;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.nodes.ScaledButtonSprite;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

class BaseLayer extends CCLayer {
    protected static float SCALING;
    static final int BACK_ID = -2;

    CCSprite mBackground;
    ScaledButtonSprite backBtn;
    CGPoint mLeftFuncBtnPos;
    CGPoint mRightFuncBtnPos;
    CGPoint mMiddleFuncBtnPos;
    Context mContext = CCDirector.sharedDirector().getActivity().getApplicationContext();
    SoundManager mSoundManager = (SoundManager) ManagerService.getInstance().getService(ManagerService.SOUND_MANAGER);
    SceneManager mSceneManager = (SceneManager) ManagerService.getInstance().getService(ManagerService.SCENE_MANAGER);
    SaveManager mSaveManager = (SaveManager) ManagerService.getInstance().getService(ManagerService.SAVE_MANAGER);

    int mScreenWidth;
    int mScreenHeight;
    float centerHorizontal;

    BaseLayer() {
        mSaveManager.setContext(mContext);
        mScreenWidth = SceneManager.SCREEN_WIDTH;
        mScreenHeight = SceneManager.SCREEN_HEIGHT;
        centerHorizontal = (float) mScreenWidth / 2.0F;
        SCALING = mSceneManager.getScalingFactor();
        mLeftFuncBtnPos = CGPoint.make(30.0F * SCALING, 30.0F * SCALING);
        mMiddleFuncBtnPos = CGPoint.make(centerHorizontal, 30.0F * SCALING);
        mRightFuncBtnPos = CGPoint.make(mScreenWidth - 30.0F * SCALING, 30.0F * SCALING);
        initBackBtn();
    }

    private void initBackBtn() {
        backBtn = ScaledButtonSprite.sprite("back.png", "back_sel.png");
        backBtn.setAnchorPoint(0.0f, 0.0f);
        backBtn.setPosition(mLeftFuncBtnPos);
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