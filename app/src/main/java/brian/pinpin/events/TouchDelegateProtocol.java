package brian.pinpin.events;

import android.view.MotionEvent;

public interface TouchDelegateProtocol {
    boolean onTouchesBegan(MotionEvent event, int tag);

    boolean onTouchesEnded(MotionEvent event, int tag);

    boolean onTouchesCancelled(MotionEvent event, int tag);
}
