package brian.pinpin.nodes;

import android.view.MotionEvent;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.protocols.CCTouchDelegateProtocol;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import javax.microedition.khronos.opengles.GL10;

import brian.pinpin.events.TouchCallbacks;

public class ScaledButtonSprite extends ScaledSprite implements CCTouchDelegateProtocol {
    private ScaledSprite mHighlightSprite;
    private boolean isHighlight = false;
    private boolean isEnable = true;
    private TouchCallbacks protocol;

    protected ScaledButtonSprite(String normalRes, String highlightRes, float scale, boolean autoAdjusted) {
        super(normalRes, scale, autoAdjusted);
        if (normalRes == null) {
            throw new NullPointerException("normal cann\'t be null.");
        } else {
            if (highlightRes != null && !highlightRes.equals("")) {
                mHighlightSprite = ScaledSprite.sprite(highlightRes, scale, autoAdjusted);
            }

            normal();
        }
    }

    public static ScaledButtonSprite sprite(String normalRes, String highlightRes, float scale, boolean autoAdjusted) {
        return new ScaledButtonSprite(normalRes, highlightRes, scale, autoAdjusted);
    }

    public static ScaledButtonSprite sprite(String normalRes, String highlightRes) {
        return new ScaledButtonSprite(normalRes, highlightRes, 0.0F, true);
    }

    public static ButtonSprite create(String normalRes, String highlightRes) {
        return new ButtonSprite(normalRes, highlightRes);
    }

    private boolean containsEvent(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);
        return CGRect.containsPoint(getBoundingBox(), point);
    }

    public void addCallback(TouchCallbacks protocol) {
        this.protocol = protocol;
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        if (isEnable && containsEvent(event)) {
            highlight();
            if (protocol != null) {
                return protocol.onTouchesBegan(event, getTag());
            }
        }

        return false;
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        if (isEnable && isHighlight && containsEvent(event)) {
            normal();
            if (protocol != null) {
                return protocol.onTouchesEnded(event, getTag());
            }
        }

        return false;
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        if (isEnable && containsEvent(event)) {
            normal();
            if (protocol != null) {
                return protocol.onTouchesCancelled(event, getTag());
            }
        }

        return false;
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        if (isEnable && isHighlight && !containsEvent(event)) {
            normal();
        }

        return false;
    }

    public void highlight() {
        isHighlight = true;
    }

    public void draw(GL10 gl) {
        if (isHighlight && mHighlightSprite != null) {
            mHighlightSprite.draw(gl);
        } else {
            super.draw(gl);
        }
    }

    public void normal() {
        isHighlight = false;
    }

    public void removeCallBack() {
        protocol = null;
    }
}
