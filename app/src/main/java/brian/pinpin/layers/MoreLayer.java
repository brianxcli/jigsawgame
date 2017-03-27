package brian.pinpin.layers;

import android.view.MotionEvent;

import brian.pinpin.R;
import brian.pinpin.events.TouchCallbacks;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.scenes.IBaseScene;

import java.util.List;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

public class MoreLayer extends BaseLayer implements TouchCallbacks {
    private static final int TAG_PAIPAI = 2;
    private static final int TAG_COLOR = 3;

    private ButtonSprite mPaipaiBtn;
    private ButtonSprite mColorBtn;

    public MoreLayer() {
        addBackground("more_bg.png");

        backBtn = ButtonSprite.create("back.png", "back_sel.png");
        backBtn.setPosition(mLeftFuncBtnPos);
        addChild(backBtn, 1, BaseLayer.BACK_ID);

        float x = centerHorizontal / 4 + 100F;
        mPaipaiBtn = ButtonSprite.create("paipai.png", "paipai_sel.png");
        mPaipaiBtn.setPosition(x, (float)(mScreenHeight - mScreenHeight / 4) - 100.0F);
        addChild(mPaipaiBtn, 1, TAG_PAIPAI);

        mColorBtn = ButtonSprite.create("color.png", "color_sel.png");
        mColorBtn.setPosition(x, (float)(mScreenHeight - mScreenHeight / 2) - (120.0F + 40.0F));
        addChild(mColorBtn, 1, TAG_COLOR);
    }

    private ButtonSprite findEventButton(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);

        List<CCNode> children = getChildren();
        for (CCNode node : children) {
            if (node instanceof ButtonSprite && contains(node, point)) {
                return (ButtonSprite)node;
            }
        }

        return null;
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        ButtonSprite button = findEventButton(event);
        return button != null && button.ccTouchesBegan(event);
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        ButtonSprite button = findEventButton(event);
        return button != null && button.ccTouchesCancelled(event);
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        ButtonSprite button = findEventButton(event);
        return button != null && button.ccTouchesEnded(event);
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        ButtonSprite button = findEventButton(event);
        return button != null && button.ccTouchesMoved(event);
    }

    public void onEnter() {
        super.onEnter();
        mPaipaiBtn.addCallback(this);
        mColorBtn.addCallback(this);
        backBtn.addCallback(this);
        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
        playBgMusic();
    }

    private void playBgMusic() {
        mSoundManager.playSound(mContext, R.raw.sound_bg_music_home, true);
    }

    public void onExit() {
        super.onExit();
        mPaipaiBtn.removeCallBack();
        mColorBtn.removeCallBack();
        backBtn.removeCallBack();
        CCTouchDispatcher.sharedDispatcher().removeDelegate(this);
    }

    public boolean onTouchesBegan(MotionEvent event, int tag) {
        return false;
    }

    public boolean onTouchesEnded(MotionEvent event, int tag) {
        if (tag == backBtn.getTag()) {
            mSoundManager.playEffect(mContext, R.raw.sound_back_to_prev);
            CCDirector.sharedDirector().popScene();;
            ((IBaseScene)getParent()).cleanupScene();
            return true;
        }
        return false;
    }

    public boolean onTouchesCancelled(MotionEvent event, int tag) { return false; }
}