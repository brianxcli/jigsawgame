package brian.pinpin.layers;

import android.view.MotionEvent;

import brian.pinpin.R;
import brian.pinpin.events.TouchCallbacks;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.nodes.ScaledButtonSprite;
import brian.pinpin.scenes.IBaseScene;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

public class AboutLayer extends BaseLayer implements TouchCallbacks {
    public AboutLayer() {
        addBackground("about_bg.png");
        addChild(backBtn, 1, BACK_ID);
    }

    private boolean isBackEvent(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);
        return contains(backBtn, point);
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        return isBackEvent(event) && backBtn.ccTouchesBegan(event);
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        return isBackEvent(event) && backBtn.ccTouchesCancelled(event);
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        return isBackEvent(event) && backBtn.ccTouchesEnded(event);
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        return isBackEvent(event) && backBtn.ccTouchesMoved(event);
    }

    public void onEnter() {
        super.onEnter();
        backBtn.addCallback(this);
        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
    }

    public void onExit() {
        super.onExit();
        backBtn.removeCallBack();
        CCTouchDispatcher.sharedDispatcher().removeDelegate(this);
    }

    public boolean onTouchesBegan(MotionEvent event, int tag) {
        return false;
    }

    public boolean onTouchesEnded(MotionEvent event, int tag) {
        if (tag == backBtn.getTag()) {
            mSoundManager.playEffect(mContext, R.raw.sound_back_to_prev);
            CCDirector.sharedDirector().popScene();
            ((IBaseScene)getParent()).cleanupScene();
            return true;
        } else {
            return false;
        }
    }

    public boolean onTouchesCancelled(MotionEvent event, int tag) {
        return false;
    }
}
