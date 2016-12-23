package brian.pinpin.layers;

import android.view.MotionEvent;

import brian.pinpin.R;
import brian.pinpin.events.TouchDelegateProtocol;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.scenes.IBaseScene;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class AboutLayer extends BaseLayer {
    private AboutLayerCallback callback;

    public AboutLayer() {
        addBackground("about_bg.png");
        backBtn = ButtonSprite.create("back.png", "back_sel.png");
        backBtn.setPosition(this.mBackPos);
        addChild(backBtn, 1, 0);
        callback = new AboutLayerCallback(this);
    }

    private boolean containsPoint(CCSprite sprite, CGPoint point) {
        return CGRect.containsPoint(sprite.getBoundingBox(), point);
    }

    private boolean isBackEvent(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);
        return containsPoint(backBtn, point);
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
        backBtn.addCallback(callback);
        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
    }

    public void onExit() {
        super.onExit();
        this.backBtn.removeCallBack();
        CCTouchDispatcher.sharedDispatcher().removeDelegate(this);
    }

    private static class AboutLayerCallback implements TouchDelegateProtocol {
        private AboutLayer layer;

        public AboutLayerCallback(AboutLayer layer) {
            this.layer = layer;
        }

        public boolean onTouchesBegan(MotionEvent event, int tag) {
            // layer.mSoundManager.playEffect(layer.SelectLayerCallback, R.raw.sound_button);
            return false;
        }

        public boolean onTouchesEnded(MotionEvent event, int tag) {
            if (tag == layer.backBtn.getTag()) {
                layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_back_to_prev);
                CCDirector.sharedDirector().replaceScene(layer.mSceneManager.getScene(SceneManager.SCENE_HOME));
                ((IBaseScene)layer.getParent()).cleanupScene();
                return true;
            } else {
                return false;
            }
        }

        public boolean onTouchesCancelled(MotionEvent event, int tag) {
            return false;
        }
    }
}
