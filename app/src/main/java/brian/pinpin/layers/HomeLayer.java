package brian.pinpin.layers;

import android.view.MotionEvent;
import brian.pinpin.R;
import brian.pinpin.events.TouchDelegateProtocol;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.scenes.SelectScene;
import java.util.List;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRepeat;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.transitions.CCShrinkGrowTransition;

public class HomeLayer extends BaseLayer {
    private CCAction titleAction;
    private CCAction cloudAction;
    private CCAction sceneBtn0Action;
    private CCAction sceneBtn1Action;

    public final String a = "HomeLayer";
    private int lastTouched;
    private int index = 0;
    private CCSprite titleSprite;
    private CCSprite cloudSprite;
    private ButtonSprite moreBtn;
    private ButtonSprite soundBtn;
    private ButtonSprite aboutBtn;
    private ButtonSprite sceneBtn0;
    private ButtonSprite sceneBtn1;
    private HomeLayerTouchCallback callback = new HomeLayerTouchCallback(this);

    public HomeLayer() {
        addBackground("root_bg.png");
        initButtons();
        initSoundState();
    }

    private void initButtons() {
        titleSprite = new CCSprite("title.png");
        titleSprite.setAnchorPoint(0.5F, 1.0F);
        titleSprite.setPosition(centerHorizontal, (float)(height - 20));
        titleAction = CCRepeat.action(CCSequence.actions(CCMoveTo.action(2.0F, CGPoint.ccp(titleSprite.getPositionRef().x, titleSprite.getPositionRef().y - 5.0F)),
                new CCFiniteTimeAction[]{CCMoveTo.action(2.0F, CGPoint.ccp(titleSprite.getPositionRef().x, titleSprite.getPositionRef().y + 5.0F))}), 2147483647);
        addChild(titleSprite, 1, index++);

        cloudSprite = new CCSprite("cloud.png");
        cloudSprite.setPosition(centerHorizontal, 157.0F);
        cloudAction = CCRepeat.action(CCSequence.actions(CCMoveTo.action(2.0F, CGPoint.ccp(cloudSprite.getPositionRef().x - 3.0F, cloudSprite.getPositionRef().y)),
                new CCFiniteTimeAction[]{CCMoveTo.action(2.0F, CGPoint.ccp(cloudSprite.getPositionRef().x + 3.0F, cloudSprite.getPositionRef().y))}), 2147483647);
        addChild(cloudSprite, 1, index++);

        float var2 = centerHorizontal - 20.0F;
        float var3 = titleSprite.getPosition().y - titleSprite.getContentSize().height + 40.0F;

        sceneBtn0 = ButtonSprite.create("sceneButton0.png", "sceneButtonHighlight0.png");
        sceneBtn0.setAnchorPoint(1.0F, 1.0F);
        sceneBtn0.setPosition(var2, var3);
        sceneBtn0Action = CCRepeat.action(CCSequence.actions(CCMoveTo.action(2.0F, CGPoint.ccp(var2 - 5.0F, var3 - 5.0F)),
                new CCFiniteTimeAction[]{CCMoveTo.action(2.0F, CGPoint.ccp(var2 + 5.0F, var3 - 5.0F)),
                        CCMoveTo.action(2.0F, CGPoint.ccp(var2 + 5.0F, var3 + 5.0F)),
                        CCMoveTo.action(2.0F, CGPoint.ccp(var2 - 5.0F, var3 + 5.0F))}), 2147483647);
        addChild(sceneBtn0, 1, index++);

        var2 = centerHorizontal + 20.0F;
        sceneBtn1 = ButtonSprite.create("sceneButton1.png", "sceneButtonHighlight1.png");
        sceneBtn1.setAnchorPoint(0.0F, 1.0F);
        sceneBtn1.setPosition(var2, var3);
        sceneBtn1Action = CCRepeat.action(CCSequence.actions(CCMoveTo.action(2.0F, CGPoint.ccp(var2 + 5.0F, var3 + 5.0F)),
                new CCFiniteTimeAction[]{CCMoveTo.action(2.0F, CGPoint.ccp(var2 - 5.0F, var3 + 5.0F)),
                        CCMoveTo.action(2.0F, CGPoint.ccp(var2 - 5.0F, var3 - 5.0F)),
                        CCMoveTo.action(2.0F, CGPoint.ccp(var2 + 5.0F, var3 - 5.0F))}), 2147483647);
        addChild(sceneBtn1, 1, index++);

        moreBtn = ButtonSprite.create("more0.png", "more1.png");
        moreBtn.setAnchorPoint(0.5F, 1.0F);
        moreBtn.setPosition(centerHorizontal - 120.0F, moreBtn.getContentSize().height - 5.0F);
        addChild(moreBtn, 1, index++);

        soundBtn = ButtonSprite.create("sound_sel.png", "sound.png");
        soundBtn.setPosition(d);
        addChild(this.soundBtn, 1, index++);

        aboutBtn = ButtonSprite.create("about_sel.png", "about.png");
        aboutBtn.setPosition((float)width - d.x, d.y);
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

    private ButtonSprite contains(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);
        List<CCNode> nodes = getChildren();

        for (CCNode node : nodes) {
            if (node instanceof ButtonSprite && contains((ButtonSprite)node, point)) {
                return (ButtonSprite)node;
            }
        }
        return null;
    }

    private boolean contains(CCSprite sprite, CGPoint point) {
        return CGRect.containsPoint(sprite.getBoundingBox(), point);
    }

    public void toggleSound() {
        if (mSoundManager.isMute()) {
            soundBtn.highlight();
            mSoundManager.unmute();
        } else {
            soundBtn.normal();
            mSoundManager.mute();
        }
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        ButtonSprite sprite = contains(event);
        return (sprite != null) ? sprite.ccTouchesBegan(event) : false;
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        ButtonSprite sprite = contains(event);
        return (sprite != null) ? sprite.ccTouchesCancelled(event) : false;
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        ButtonSprite sprite = contains(event);
        if (sprite != null) {
            lastTouched = sprite.getTag();
            return sprite.ccTouchesEnded(event);
        } else {
            return true;
        }
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        ButtonSprite sprite = contains(event);
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
            if(node != null && node instanceof ButtonSprite) {
                ((ButtonSprite)node).normal();
            }

            sprite.highlight();
            this.lastTouched = tag;
        } else {
            node = getChildByTag(lastTouched);
            if(node != null && node instanceof ButtonSprite) {
                ButtonSprite var5 = (ButtonSprite)node;
                if (var5 == soundBtn) {
                    toggleSound();
                } else {
                    var5.normal();
                }
            }

            this.lastTouched = this.index;
        }

        return false;
    }

    public void onEnter() {
        super.onEnter();
        sceneBtn0.addCallback(callback);
        sceneBtn1.addCallback(callback);
        aboutBtn.addCallback(callback);
        soundBtn.addCallback(callback);
        moreBtn.addCallback(callback);
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

    private static class HomeLayerTouchCallback implements TouchDelegateProtocol {
        private HomeLayer layer;

        public HomeLayerTouchCallback(HomeLayer layer) {
            this.layer = layer;
        }

        public boolean onTouchesBegan(MotionEvent event, int tag) {
            if (tag != layer.soundBtn.getTag()) {
                layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_button);
            }
            return true;
        }

        public boolean onTouchesEnded(MotionEvent event, int tag) {
            if (tag == layer.soundBtn.getTag()) {
                layer.toggleSound();
                return true;
            } else if (tag == layer.aboutBtn.getTag()) {
                CCScene scene = layer.mSceneManager.getScene(SceneManager.SCENE_ABOUT);
                CCDirector.sharedDirector().replaceScene(CCShrinkGrowTransition.transition(1.0F, scene));
                layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_ui_switch);
                return true;
            } else if (tag == layer.moreBtn.getTag()) {
                CCScene scene = layer.mSceneManager.getScene(SceneManager.SCENE_MORE);
                CCDirector.sharedDirector().replaceScene(CCShrinkGrowTransition.transition(1.0F, scene));
                layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_ui_switch);
                return true;
            } else if (tag == layer.sceneBtn0.getTag()) {
                CCScene scene = layer.mSceneManager.getScene(SceneManager.SCENE_SELECT);
                ((SelectScene)scene).a(0);
                CCDirector.sharedDirector().replaceScene(CCShrinkGrowTransition.transition(1.0F, scene));
                layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_ui_switch);
                return true;
            } else if (tag == layer.sceneBtn1.getTag()) {
                CCScene scene = layer.mSceneManager.getScene(SceneManager.SCENE_SELECT);
                ((SelectScene) scene).a(1);
                CCDirector.sharedDirector().replaceScene(CCShrinkGrowTransition.transition(1.0F, scene));
                layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_ui_switch);
                return true;
            }
            return false;
        }

        public boolean onTouchesCancelled(MotionEvent event, int tag) {
            return false;
        }
    }
}