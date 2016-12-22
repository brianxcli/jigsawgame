package brian.pinpin.layers;

import brian.pinpin.R;
import brian.pinpin.managers.SceneManager;

import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCShrinkGrowTransition;

public class FlashLayer extends BaseLayer {
    public FlashLayer() {
        addBackground("Default.png");
    }

    public void onEnter() {
        super.onEnter();
        mBackground.runAction(CCSequence.actions(CCCallFunc.action(this, "preloadEffects"), new CCFiniteTimeAction[]{CCCallFunc.action(this, "onWaitFinish")}));
    }

    public void onExit() {
        super.onExit();
    }

    public void onWaitFinish() {
        CCDirector.sharedDirector().replaceScene(CCShrinkGrowTransition.transition(1.0F, mSceneManager.getScene(SceneManager.SCENE_HOME)));
    }

    public void preloadEffects() {
        mSoundManager.preloadEffect(mContext, R.raw.sound_button);
        mSoundManager.preloadEffect(mContext, R.raw.sound_play_complete);
        mSoundManager.preloadEffect(mContext, R.raw.sound_ui_switch);
        mSoundManager.preloadEffect(mContext, R.raw.sound_back_to_prev);
        mSoundManager.preloadEffect(mContext, R.raw.sound_swap_position);
        mSoundManager.preloadEffect(mContext, R.raw.sound_wand_reset);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_butterfly);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_turtle);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_snake);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_rooster);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_pig);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_cattle);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_deer);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_duck);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_frog);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_goat);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_goose);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_horse);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_lion);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_panda);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_pig);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_rooster);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_snake);
        mSoundManager.preloadEffect(mContext, R.raw.sound_role_turtle);

        mSoundManager.preloadSound(mContext, R.raw.sound_bg_music_home);
        mSoundManager.preloadSound(mContext, R.raw.sound_bg_music_play);
        mSoundManager.preloadSound(mContext, R.raw.sound_play_complete);
    }
}