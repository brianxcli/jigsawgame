package brian.pinpin.layers;

import android.view.MotionEvent;

import brian.pinpin.R;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.events.TouchDelegateProtocol;
import brian.pinpin.scenes.IBaseScene;
import brian.pinpin.scenes.PlayScene;

import java.util.List;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.transitions.CCShrinkGrowTransition;

public class SelectLayer extends BaseLayer {
    public final String a = "SelectLayer";
    private static final int ANIMAL_PER_SCENE = 7;

    private ButtonSprite[] mTrials = new ButtonSprite[ANIMAL_PER_SCENE];
    private SelectLayerCallback callback;
    private int r = -2;
    private int mType = -1;

    public SelectLayer(int type) {
        mType = type;
        callback = new SelectLayerCallback(this);
        initViews(type);
        playBgMusic();
    }

    private ButtonSprite findSprite(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);

        List<CCNode> children = getChildren();
        for (CCNode node : children) {
            if(node != null && node instanceof ButtonSprite && contains(node, point)) {
                return (ButtonSprite)node;
            }
        }

        return null;
    }

    private void playBgMusic() {
        mSoundManager.playSound(mContext, R.raw.sound_bg_music_home, true);
    }

    private void initViews(int type) {
        String scene = "scene" + type;
        addBackground(scene + ".png");
        backBtn = ButtonSprite.create("back.png", "back_sel.png");
        backBtn.setPosition(mBackPos);
        addChild(backBtn, 2, BaseLayer.BACK_ID);

        for (int i = 0; i < ANIMAL_PER_SCENE; i++) {
            mTrials[i] = ButtonSprite.create(scene + "Play" + i + ".png", scene + "PlayHighlight" + i + ".png");
            addChild(mTrials[i], 1, i);
        }

        mTrials[0].setPosition(150F, 430.0F);
        mTrials[1].setPosition(315.0F, 459F);
        mTrials[2].setPosition(570F, 375F);
        mTrials[3].setPosition(855.0F, 415F);
        mTrials[4].setPosition(280F, 180F);
        mTrials[5].setPosition(560F, 120F);
        mTrials[6].setPosition(845F, 155F);
    }

    private void addAnimalCallbacks() {
        for (int i = 0; i < ANIMAL_PER_SCENE; i++) {
            mTrials[i].addCallback(callback);
        }
    }

    private void removeAnimalCallBacks() {
        for (int i = 0; i < ANIMAL_PER_SCENE; i++) {
            mTrials[i].removeCallBack();
        }
    }

    private boolean contains(CCNode var1, CGPoint var2) {
        return CGRect.containsPoint(var1.getBoundingBox(), var2);
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        ButtonSprite var2 = this.findSprite(event);
        return var2 != null && var2.ccTouchesBegan(event);
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        ButtonSprite var2 = findSprite(event);
        return var2 != null && var2.ccTouchesCancelled(event);
    }

    public boolean ccTouchesEnded(MotionEvent var1) {
        ButtonSprite var2 = this.findSprite(var1);
        if(var2 != null) {
            this.r = var2.getTag();
            return var2.ccTouchesEnded(var1);
        } else {
            return true;
        }
    }

    public boolean ccTouchesMoved(MotionEvent var1) {
        ButtonSprite var3 = this.findSprite(var1);
        CCNode var4;
        if(var3 != null) {
            int var2 = var3.getTag();
            if(this.r == -2 || this.r == var2) {
                if(this.r == -2) {
                    this.r = var2;
                    var3.highlight();
                }

                return var3.ccTouchesMoved(var1);
            }

            var4 = this.getChildByTag(this.r);
            if(var4 != null && var4 instanceof ButtonSprite) {
                ((ButtonSprite)var4).normal();
            }

            var3.highlight();
            this.r = var2;
        } else {
            var4 = this.getChildByTag(this.r);
            if(var4 != null && var4 instanceof ButtonSprite) {
                ((ButtonSprite)var4).normal();
            }

            this.r = -2;
        }

        return false;
    }

    public void onEnter() {
        super.onEnter();
        backBtn.addCallback(callback);
        addAnimalCallbacks();
        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
    }

    public void onExit() {
        super.onExit();
        backBtn.removeCallBack();
        removeAnimalCallBacks();
        CCTouchDispatcher.sharedDispatcher().removeDelegate(this);
    }

    private static class SelectLayerCallback implements TouchDelegateProtocol {
        private SelectLayer layer;

        SelectLayerCallback(SelectLayer layer) {
            this.layer = layer;
        }

        public boolean onTouchesBegan(MotionEvent event, int tag) {
            if(BaseLayer.BACK_ID == tag) {
                return true;
            } else if(layer.mType == 0) {
                if (tag == 0) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_deer);
                    return true;
                }

                if (1 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_butterfly);
                    return true;
                }

                if (2 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_lion);
                    return true;
                }

                if (3 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_panda);
                    return true;
                }

                if (4 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_snake);
                    return true;
                }

                if (5 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_frog);
                    return true;
                }

                if (6 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_turtle);
                    return true;
                }
            } else if(layer.mType == 1) {
                if (tag == 0) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_goat);
                    return true;
                }

                if (1 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_cattle);
                    return true;
                }

                if (2 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_goose);
                    return true;
                }

                if (3 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_horse);
                    return true;
                }

                if (4 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_pig);
                    return true;
                }

                if (5 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_duck);
                    return true;
                }

                if (6 == tag) {
                    layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_role_rooster);
                    return true;
                }
            }

            return false;
        }

        public boolean onTouchesEnded(MotionEvent event, int tag) {
            if (tag == BaseLayer.BACK_ID) {
                layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_back_to_prev);
                CCDirector.sharedDirector().replaceScene(layer.mSceneManager.getScene(SceneManager.SCENE_HOME));
                ((IBaseScene)layer.getParent()).cleanupScene();
                return true;
            } else if (layer.mTrials[0].getTag() <= tag && tag <= layer.mTrials[ANIMAL_PER_SCENE - 1].getTag()) {
                PlayScene scene = (PlayScene)layer.mSceneManager.getScene(SceneManager.SCENE_PLAY);
                scene.setPlayLayer(layer.mType, tag);
                CCDirector.sharedDirector().replaceScene(CCShrinkGrowTransition.transition(1.0F, scene));
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