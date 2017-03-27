package brian.pinpin.layers;

import android.view.MotionEvent;
import brian.pinpin.R;
import brian.pinpin.events.TouchCallbacks;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.nodes.ScaledButtonSprite;
import brian.pinpin.nodes.ScaledSprite;
import brian.pinpin.scenes.SelectScene;
import java.util.List;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRepeat;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.transitions.CCShrinkGrowTransition;

public class HomeLayer extends BaseLayer implements TouchCallbacks {
    private CCAction titleAction;
    private CCAction cloudAction;
    private CCAction sceneBtn0Action;
    private CCAction sceneBtn1Action;

    private int lastTouched;
    private int index = 0;

    private ScaledSprite titleSprite;
    private ScaledSprite cloudSprite;
    private ScaledButtonSprite moreBtn;
    private ScaledButtonSprite soundBtn;
    private ScaledButtonSprite aboutBtn;
    private ScaledButtonSprite sceneBtn0;
    private ScaledButtonSprite sceneBtn1;

    public HomeLayer() {
        addBackground("root_bg.png");
        initButtons();
        initSoundState();
    }

    private void initButtons() {
        titleSprite = ScaledSprite.sprite("title.png");
        titleSprite.setAnchorPoint(0.5F, 0.5F);
        titleSprite.setPosition(centerHorizontal, (float)(mScreenHeight * 5 / 6));
        titleAction = CCRepeat.action(CCSequence.actions(
            CCMoveTo.action(2.0F, CGPoint.ccp(titleSprite.getPositionRef().x, titleSprite.getPositionRef().y - 5.0F)),
            CCMoveTo.action(2.0F, CGPoint.ccp(titleSprite.getPositionRef().x, titleSprite.getPositionRef().y + 5.0F))),
            Integer.MAX_VALUE);
        addChild(titleSprite, 1, index++);

        cloudSprite = ScaledSprite.sprite("cloud.png");
        cloudSprite.setPosition(centerHorizontal, (float)(mScreenHeight / 6));
        cloudAction = CCRepeat.action(CCSequence.actions(
            CCMoveTo.action(2.0F, CGPoint.ccp(cloudSprite.getPositionRef().x - 5.0F * SCALING, cloudSprite.getPositionRef().y)),
            CCMoveTo.action(2.0F, CGPoint.ccp(cloudSprite.getPositionRef().x + 5.0F * SCALING, cloudSprite.getPositionRef().y))),
            Integer.MAX_VALUE);
        addChild(cloudSprite, 1, index++);

        float x = centerHorizontal - 20.0F * SCALING;
        float y = titleSprite.getPosition().y - titleSprite.getContentSize().height + 40.0F * SCALING;

        sceneBtn0 = ScaledButtonSprite.sprite("sceneButton0.png", "sceneButtonHighlight0.png");
        sceneBtn0.setAnchorPoint(1.0F, 1.0F);
        sceneBtn0.setPosition(x, y);
        sceneBtn0Action = CCRepeat.action(CCSequence.actions(
            CCMoveTo.action(2.0F, CGPoint.ccp(x - 5.0F * SCALING, y - 5.0F * SCALING)),
            CCMoveTo.action(2.0F, CGPoint.ccp(x + 5.0F * SCALING, y - 5.0F * SCALING)),
            CCMoveTo.action(2.0F, CGPoint.ccp(x + 5.0F * SCALING, y + 5.0F * SCALING)),
            CCMoveTo.action(2.0F, CGPoint.ccp(x - 5.0F * SCALING, y + 5.0F * SCALING))),
            Integer.MAX_VALUE);
        addChild(sceneBtn0, 1, index++);

        x = centerHorizontal + 20.0F;
        sceneBtn1 = ScaledButtonSprite.sprite("sceneButton1.png", "sceneButtonHighlight1.png");
        sceneBtn1.setAnchorPoint(0.0F, 1.0F);
        sceneBtn1.setPosition(x, y);
        sceneBtn1Action = CCRepeat.action(CCSequence.actions(
            CCMoveTo.action(2.0F, CGPoint.ccp(x + 5.0F * SCALING, y + 5.0F * SCALING)),
            CCMoveTo.action(2.0F, CGPoint.ccp(x - 5.0F * SCALING, y + 5.0F * SCALING)),
            CCMoveTo.action(2.0F, CGPoint.ccp(x - 5.0F * SCALING, y - 5.0F * SCALING)),
            CCMoveTo.action(2.0F, CGPoint.ccp(x + 5.0F * SCALING, y - 5.0F * SCALING))),
            Integer.MAX_VALUE);
        addChild(sceneBtn1, 1, index++);

        soundBtn = ScaledButtonSprite.sprite("sound_sel.png", "sound.png");
        soundBtn.setAnchorPoint(0.0f, 0.0f);
        soundBtn.setPosition(mLeftFuncBtnPos);
        addChild(soundBtn, 1, index++);

        moreBtn = ScaledButtonSprite.sprite("more0.png", "more1.png");
        moreBtn.setAnchorPoint(0.5f, 0.0f);
        moreBtn.setPosition(mMiddleFuncBtnPos);
        addChild(moreBtn, 1, index++);

        aboutBtn = ScaledButtonSprite.sprite("about_sel.png", "about.png");
        aboutBtn.setAnchorPoint(1.0f, 0.0f);
        aboutBtn.setPosition(mRightFuncBtnPos);
        addChild(aboutBtn, 1, index);
    }

    private void initSoundState() {
        mSoundManager.playSound(mContext, R.raw.sound_bg_music_home, true);
        if (mSoundManager.isMute()) {
            soundBtn.normal();
        } else {
            soundBtn.highlight();
        }
    }

    private ScaledButtonSprite contains(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);
        List<CCNode> nodes = getChildren();

        for (CCNode node : nodes) {
            if (node instanceof ScaledButtonSprite && contains(node, point)) {
                return (ScaledButtonSprite)node;
            }
        }
        return null;
    }

    private void toggleSound() {
        if (mSoundManager.isMute()) {
            soundBtn.highlight();
            mSoundManager.unmute();
        } else {
            soundBtn.normal();
            mSoundManager.mute();
        }
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        ScaledButtonSprite sprite = contains(event);
        return sprite != null && sprite.ccTouchesBegan(event);
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        ScaledButtonSprite sprite = contains(event);
        return sprite != null && sprite.ccTouchesCancelled(event);
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        ScaledButtonSprite sprite = contains(event);
        if (sprite != null) {
            lastTouched = sprite.getTag();
            return sprite.ccTouchesEnded(event);
        } else {
            return true;
        }
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        ScaledButtonSprite sprite = contains(event);
        CCNode node;
        if (sprite != null) {
            int tag = sprite.getTag();
            if (lastTouched == index || lastTouched == tag) {
                if (lastTouched == index) {
                    lastTouched = tag;
                    sprite.highlight();
                }
                return sprite.ccTouchesMoved(event);
            }

            node = getChildByTag(lastTouched);
            if (node instanceof ButtonSprite) {
                ((ScaledButtonSprite)node).normal();
            }

            sprite.highlight();
            lastTouched = tag;
        } else {
            node = getChildByTag(lastTouched);
            if (node instanceof ScaledButtonSprite) {
                ScaledButtonSprite button = (ScaledButtonSprite)node;
                if (button == soundBtn) {
                    toggleSound();
                } else {
                    button.normal();
                }
            }

            lastTouched = index;
        }

        return false;
    }

    public void onEnter() {
        super.onEnter();
        sceneBtn0.addCallback(this);
        sceneBtn1.addCallback(this);
        aboutBtn.addCallback(this);
        soundBtn.addCallback(this);
        moreBtn.addCallback(this);
        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
        titleSprite.runAction(titleAction);
        cloudSprite.runAction(cloudAction);
        sceneBtn0.runAction(sceneBtn0Action);
        sceneBtn1.runAction(sceneBtn1Action);
    }

    public void onExit() {
        super.onExit();
        sceneBtn0.removeCallBack();
        sceneBtn1.removeCallBack();
        aboutBtn.removeCallBack();
        soundBtn.removeCallBack();
        moreBtn.removeCallBack();
        CCTouchDispatcher.sharedDispatcher().removeDelegate(this);
        titleSprite.stopAllActions();
        cloudSprite.stopAllActions();
        sceneBtn0.stopAllActions();
        sceneBtn1.stopAllActions();
    }

    public boolean onTouchesBegan(MotionEvent event, int tag) {
        if (tag != soundBtn.getTag()) {
            mSoundManager.playEffect(mContext, R.raw.sound_button);
        }
        return true;
    }

    public boolean onTouchesEnded(MotionEvent event, int tag) {
        if (tag == soundBtn.getTag()) {
            toggleSound();
            return true;
        } else if (tag == aboutBtn.getTag()) {
            CCScene scene = mSceneManager.getScene(SceneManager.SCENE_ABOUT);
            CCDirector.sharedDirector().pushScene(CCShrinkGrowTransition.transition(1.0F, scene));
            mSoundManager.playEffect(mContext, R.raw.sound_ui_switch);
            return true;
        } else if (tag == moreBtn.getTag()) {
            CCScene scene = mSceneManager.getScene(SceneManager.SCENE_MORE);
            CCDirector.sharedDirector().pushScene(CCShrinkGrowTransition.transition(1.0F, scene));
            mSoundManager.playEffect(mContext, R.raw.sound_ui_switch);
            return true;
        } else if (tag == sceneBtn0.getTag()) {
            CCScene scene = mSceneManager.getScene(SceneManager.SCENE_SELECT, 0);
            CCDirector.sharedDirector().pushScene(CCShrinkGrowTransition.transition(1.0F, scene));
            mSoundManager.playEffect(mContext, R.raw.sound_ui_switch);
            return true;
        } else if (tag == sceneBtn1.getTag()) {
            CCScene scene = mSceneManager.getScene(SceneManager.SCENE_SELECT, 1);
            CCDirector.sharedDirector().pushScene(CCShrinkGrowTransition.transition(1.0F, scene));
            mSoundManager.playEffect(mContext, R.raw.sound_ui_switch);
            return true;
        }
        return false;
    }

    public boolean onTouchesCancelled(MotionEvent event, int tag) {
        return false;
    }
}