package brian.pinpin.managers;

import android.content.Context;
import org.cocos2d.sound.SoundEngine;

public class SoundManager implements IService {
    private SoundEngine mSoundEngine;

    SoundManager() {
        mSoundEngine = SoundEngine.sharedEngine();
        mSoundEngine.setSoundVolume(0.5F);
        mSoundEngine.setEffectsVolume(0.5F);
    }

    public void mute() {
        mSoundEngine.mute();
    }

    public void unmute() {
        mSoundEngine.unmute();
    }

    public void preloadEffect(Context context, int resId) {
        mSoundEngine.preloadEffect(context, resId);
    }

    public void playEffect(Context context, int resId) {
        mSoundEngine.playEffect(context, resId);
    }

    public void preloadSound(Context context, int resId) {
        mSoundEngine.preloadSound(context, resId);
    }

    public void playSound(Context context, int resId, boolean loop) {
        mSoundEngine.playSound(context, resId, loop);
    }

    public boolean isMute() {
        return mSoundEngine.isMute();
    }

    public void pauseSound() {
        mSoundEngine.pauseSound();
    }

    public void resumeSound() {
        mSoundEngine.resumeSound();
    }

    public void onCreate() {

    }

    public void onDestroy() {
    }
}
