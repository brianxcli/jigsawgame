package brian.pinpin.layers;

import android.view.MotionEvent;

import brian.pinpin.R;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.events.TouchCallbacks;
import brian.pinpin.nodes.ScaledButtonSprite;
import brian.pinpin.scenes.PlayScene;

import java.util.List;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.transitions.CCShrinkGrowTransition;

public class SelectLayer extends BaseLayer implements TouchCallbacks {
    private static final int ANIMAL_PER_SCENE = 7;

    private ScaledButtonSprite[] mTrials = new ScaledButtonSprite[ANIMAL_PER_SCENE];
    private int mType = -1;

    public SelectLayer(int type) {
        mType = type;
        initViews(type);
        playBgMusic();
    }

    private ScaledButtonSprite findSprite(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);

        List<CCNode> children = getChildren();
        for (CCNode node : children) {
            if(node != null && node instanceof ScaledButtonSprite && contains(node, point)) {
                return (ScaledButtonSprite)node;
            }
        }

        return null;
    }

    private void playBgMusic() {
        mSoundManager.playSound(mContext, R.raw.sound_bg_music_home, true);
    }

    private void initViews(int type) {
        String scene = "scene" + type;
        addBackground(scene + ".png");
        addChild(backBtn, 2, BACK_ID);

        for (int i = 0; i < ANIMAL_PER_SCENE; i++) {
            mTrials[i] = ScaledButtonSprite.sprite(scene + "Play" + i + ".png", scene + "PlayHighlight" + i + ".png");
            mTrials[i].setAnchorPoint(0.5f, 0.5f);
            addChild(mTrials[i], 1, i);
        }

        calculateAnimalPos();
    }

    private void calculateAnimalPos() {
        int totalH = SceneManager.SCREEN_HEIGHT;
        int totalW = SceneManager.SCREEN_WIDTH;

        int secRow = totalH / 4;
        int firstRow = totalH * 2 / 3;

        mTrials[0].setPosition(totalW / 5, firstRow);
        mTrials[1].setPosition(totalW * 2 / 5, firstRow);
        mTrials[2].setPosition(totalW * 3 / 5, firstRow);
        mTrials[3].setPosition(totalW * 4 / 5, firstRow);
        mTrials[4].setPosition(totalW / 4, secRow);
        mTrials[5].setPosition(totalW / 2, secRow);
        mTrials[6].setPosition(totalW * 3 / 4, secRow);
    }

    private void addAnimalCallbacks() {
        for (int i = 0; i < ANIMAL_PER_SCENE; i++) {
            mTrials[i].addCallback(this);
        }
    }

    private void removeAnimalCallBacks() {
        for (int i = 0; i < ANIMAL_PER_SCENE; i++) {
            mTrials[i].removeCallBack();
        }
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        ScaledButtonSprite button = findSprite(event);
        return button != null && button.ccTouchesBegan(event);
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        ScaledButtonSprite button = findSprite(event);
        return button != null && button.ccTouchesCancelled(event);
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        ScaledButtonSprite button = findSprite(event);
        return button != null && button.ccTouchesEnded(event);
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        ScaledButtonSprite button = findSprite(event);
        return button != null && button.ccTouchesMoved(event);
    }

    public void onEnter() {
        super.onEnter();
        backBtn.addCallback(this);
        addAnimalCallbacks();
        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
    }

    public void onExit() {
        super.onExit();
        backBtn.removeCallBack();
        removeAnimalCallBacks();
        CCTouchDispatcher.sharedDispatcher().removeDelegate(this);
    }

    public boolean onTouchesBegan(MotionEvent event, int tag) {
        if (BACK_ID == tag) {
            return true;
        } else if (mType == 0) {
            if (0 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_deer);
                return true;
            }

            if (1 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_butterfly);
                return true;
            }

            if (2 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_lion);
                return true;
            }

            if (3 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_panda);
                return true;
            }

            if (4 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_snake);
                return true;
            }

            if (5 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_frog);
                return true;
            }

            if (6 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_turtle);
                return true;
            }
        } else if (mType == 1) {
            if (0 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_goat);
                return true;
            }

            if (1 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_cattle);
                return true;
            }

            if (2 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_goose);
                return true;
            }

            if (3 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_horse);
                return true;
            }

            if (4 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_pig);
                return true;
            }

            if (5 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_duck);
                return true;
            }

            if (6 == tag) {
                mSoundManager.playEffect(mContext, R.raw.sound_role_rooster);
                return true;
            }
        }

        return false;
    }

    public boolean onTouchesEnded(MotionEvent event, int tag) {
        if (tag == BACK_ID) {
            mSoundManager.playEffect(mContext, R.raw.sound_back_to_prev);
            CCDirector.sharedDirector().popScene();
            return true;
        } else if (mTrials[0].getTag() <= tag && tag <= mTrials[ANIMAL_PER_SCENE - 1].getTag()) {
            PlayScene scene = (PlayScene)mSceneManager.getScene(SceneManager.SCENE_PLAY);
            scene.setPlayLayer(mType, tag);
            CCDirector.sharedDirector().pushScene(CCShrinkGrowTransition.transition(1.0F, scene));
            return true;
        } else {
            return false;
        }
    }

    public boolean onTouchesCancelled(MotionEvent event, int tag) {
        return false;
    }
}