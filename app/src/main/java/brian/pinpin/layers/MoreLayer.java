package brian.pinpin.layers;

import android.view.MotionEvent;

import brian.pinpin.R;
import brian.pinpin.events.TouchCallbacks;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.nodes.ScaledButtonSprite;
import brian.pinpin.scenes.IBaseScene;

import java.util.List;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

public class MoreLayer extends BaseLayer implements TouchCallbacks {
    private static final int TAG_PAIPAI = 2;
    private static final int TAG_COLOR = 3;

    private ScaledButtonSprite mPaipaiBtn;
    private ScaledButtonSprite mColorBtn;

    public MoreLayer() {
        addBackground("more_bg.png");
        addChild(backBtn, 1, BaseLayer.BACK_ID);

        float x = centerHorizontal / 4 + 110F * SCALING;
        mPaipaiBtn = ScaledButtonSprite.sprite("paipai.png", "paipai_sel.png");
        mPaipaiBtn.setPosition(x, (float)(mScreenHeight - mScreenHeight / 4) - 100.0F * SCALING);
        addChild(mPaipaiBtn, 1, TAG_PAIPAI);

        mColorBtn = ScaledButtonSprite.sprite("color.png", "color_sel.png");
        mColorBtn.setPosition(x, (float)(mScreenHeight - mScreenHeight / 2) - 160F * SCALING);
        addChild(mColorBtn, 1, TAG_COLOR);
    }

    private ScaledButtonSprite findEventButton(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);

        List<CCNode> children = getChildren();
        for (CCNode node : children) {
            if (node instanceof ScaledButtonSprite && contains(node, point)) {
                return (ScaledButtonSprite)node;
            }
        }

        return null;
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        ScaledButtonSprite button = findEventButton(event);
        return button != null && button.ccTouchesBegan(event);
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        ScaledButtonSprite button = findEventButton(event);
        return button != null && button.ccTouchesCancelled(event);
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        ScaledButtonSprite button = findEventButton(event);
        return button != null && button.ccTouchesEnded(event);
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        ScaledButtonSprite button = findEventButton(event);
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