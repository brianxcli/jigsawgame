package brian.pinpin.layers;

import android.view.MotionEvent;

import brian.pinpin.R;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.nodes.ButtonSprite;
import brian.pinpin.events.TouchCallbacks;
import brian.pinpin.scenes.IBaseScene;
import brian.pinpin.scenes.SelectScene;
import brian.pinpin.utils.PublicUtils;

import java.util.List;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCBlink;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCFadeIn;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRepeat;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class PlayLayer extends BaseLayer {
    private final int[][] mTrialsNums = new int[][]{{3, 4, 4, 3, 3, 4, 3}, {3, 3, 3, 3, 3, 3, 4}};
    private static final int TAG_BLACKBOARD = 2;
    private static final int TAG_MAGIC_WAND = 3;
    private static int[] TAG_TRIAL_ICONS = {4, 5, 6, 7};
    private static int TAG_MAGNIFIER = 8;
    private static int TAG_ORIGIN_BIG = 9;
    private static int[] TAG_STARS = {10, 11, 12};

    private int mSide;
    private int mNumber;

    private CCSprite mBlackboard;
    private ButtonSprite mMagicWand;
    private CCSprite[] mStars;
    private CCSprite[] mTrialIcons;
    private CCSprite mOriginalBig;
    private CCSprite mOriginIcon;
    private CCSprite mGrayBg;
    private ButtonSprite mMagnifier;

    private CGPoint mBlackboardPos;
    private CGPoint[] mStarPos;
    private CGPoint mOriginalBigPos;

    private String[][] mOriginPics;
    private String[][] mStageIconRes;

    private CCAction mMagicWandAction;
    private int[][] mFragmentIDs;
    private CCSprite[][] mFragments;

    private ButtonSprite mLastButton;
    private int U;
    private int V;

    private CCSprite mLastFragment;
    private int mLastFragmentX;
    private int mLastFragmentY;
    private CGPoint mLastFragmentPos;

    private int mCurrentTrial;
    private int passedNum;
    private boolean mTouchEnable;
    private int mLastPointerId;
    private CCSprite mSwitchTarget;

    private PlayLayerCallback callback;

    public PlayLayer(int side, int number) {
        mSide = side;
        mNumber = number;
        callback = new PlayLayerCallback(this);

        this.U = -1;
        this.V = -1;
        mLastFragmentX = -1;
        mLastFragmentY = -1;
        mCurrentTrial = 1;
        mTouchEnable = true;
        mLastPointerId = -1;

        passedNum = mSaveManager.getPassedCount(mSide, mNumber);
        mCurrentTrial = getCurrentTrial();
        initUI();
    }

    /**
     * Find the trial to display
     * @return the last trial of the current animal if all trials of this animal are passed;
     * otherwise returns the first trial that are not passed before.
     */
    private int getCurrentTrial() {
        int trials = mTrialsNums[mSide][mNumber];
        int result = 0;
        if (passedNum == trials) {
            result = passedNum;
        } else if (passedNum < trials) {
            result = passedNum + 1;
        }

        return result;
    }

    private void initUI() {
        addBackground("scenebg.png");

        mGrayBg = CCSprite.sprite("gray.png");
        mGrayBg.setAnchorPoint(0.0F, 0.0F);
        mGrayBg.setVisible(false);

        mBlackboard = CCSprite.sprite("blackboard.png");
        mBlackboard.setAnchorPoint(1.0F, 0.0F);
        mBlackboardPos = CGPoint.ccp(mScreenWidth - 80, 20.0F);
        mBlackboard.setPosition(mBlackboardPos);

        mMagicWand = ButtonSprite.create("magic.png", "magicHighlight.png");
        mMagicWand.setAnchorPoint(1.0F, 0.0F);
        CGPoint magicWandPos = CGPoint.ccp(mBlackboardPos.x + 60.0F, mBlackboardPos.y - 20.0F);
        mMagicWand.setPosition(magicWandPos);

        CGPoint var2 = CGPoint.ccp(magicWandPos.x - 3.0F, magicWandPos.y + 3.0F);
        CGPoint var3 = CGPoint.ccp(magicWandPos.x + 3.0F, magicWandPos.y - 3.0F);
        mMagicWandAction = CCRepeat.action(CCSequence.actions(CCMoveTo.action(2.0F, var2), CCMoveTo.action(2.0F, var3)), Integer.MAX_VALUE);

        backBtn = ButtonSprite.create("back.png", "back_sel.png");
        backBtn.setPosition(mBackPos);

        addChild(backBtn, 1, BACK_ID);
        addChild(mBlackboard, 1, TAG_BLACKBOARD);
        addChild(mMagicWand, 3, TAG_MAGIC_WAND);
        addChild(mGrayBg, 1);

        initPlay();
    }

    private void initPlay() {
        initGame();
        playBgMusic();
    }

    private void initGame() {
        initGamePics();
        initGameElements();
    }

    private void initGamePics() {
        switch (mSide) {
            case 0:
                if (mNumber == 0) {
                    mOriginPics = new String[][] {
                            {"deer01.png", "deerButton01.png"},
                            {"deer02.png", "deerButton02.png"},
                            {"deer03.png", "deerButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"deerIcon01.png", "deerIconHighlight01.png"},
                            {"deerIcon02.png", "deerIconHighlight02.png"},
                            {"deerIcon03.png", "deerIconHighlight03.png"}};
                } else if (mNumber == 1) {
                    mOriginPics = new String[][] {
                            {"butterfly01.png", "butterflyButton01.png"},
                            {"butterfly02.png", "butterflyButton02.png"},
                            {"butterfly03.png", "butterflyButton03.png"},
                            {"butterfly04.png", "butterflyButton04.png"}};
                    mStageIconRes = new String[][] {
                            {"butterflyIcon01.png", "butterflyIconHighlight01.png"},
                            {"butterflyIcon02.png", "butterflyIconHighlight02.png"},
                            {"butterflyIcon03.png", "butterflyIconHighlight03.png"},
                            {"butterflyIcon04.png", "butterflyIconHighlight04.png"}};
                } else if (mNumber == 2) {
                    mOriginPics = new String[][] {
                            {"lion01.png", "lionButton01.png"},
                            {"lion02.png", "lionButton02.png"},
                            {"lion03.png", "lionButton03.png"},
                            {"lion04.png", "lionButton04.png"}};
                    mStageIconRes = new String[][] {
                            {"lionIcon01.png", "lionIconHighlight01.png"},
                            {"lionIcon02.png", "lionIconHighlight02.png"},
                            {"lionIcon03.png", "lionIconHighlight03.png"},
                            {"lionIcon04.png", "lionIconHighlight04.png"}};
                } else if (mNumber == 3) {
                    mOriginPics = new String[][] {
                            {"panda01.png", "pandaButton01.png"},
                            {"panda02.png", "pandaButton02.png"},
                            {"panda03.png", "pandaButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"pandaIcon01.png", "pandaIconHighlight01.png"},
                            {"pandaIcon02.png", "pandaIconHighlight02.png"},
                            {"pandaIcon03.png", "pandaIconHighlight03.png"}};
                } else if (mNumber == 4) {
                    mOriginPics = new String[][] {
                            {"snake01.png", "snakeButton01.png"},
                            {"snake02.png", "snakeButton02.png"},
                            {"snake03.png", "snakeButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"snakeIcon01.png", "snakeIconHighlight01.png"},
                            {"snakeIcon02.png", "snakeIconHighlight02.png"},
                            {"snakeIcon03.png", "snakeIconHighlight03.png"}};
                } else if (mNumber == 5) {
                    mOriginPics = new String[][] {
                            {"frog01.png", "frogButton01.png"},
                            {"frog02.png", "frogButton02.png"},
                            {"frog03.png", "frogButton03.png"},
                            {"frog04.png", "frogButton04.png"}};
                    mStageIconRes = new String[][] {
                            {"frogIcon01.png", "frogIconHighlight01.png"},
                            {"frogIcon02.png", "frogIconHighlight02.png"},
                            {"frogIcon03.png", "frogIconHighlight03.png"},
                            {"frogIcon04.png", "frogIconHighlight04.png"}};
                } else if (mNumber == 6) {
                    mOriginPics = new String[][] {
                            {"turtle01.png", "turtleButton01.png"},
                            {"turtle02.png", "turtleButton02.png"},
                            {"turtle03.png", "turtleButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"turtleIcon01.png", "turtleIconHighlight01.png"},
                            {"turtleIcon02.png", "turtleIconHighlight02.png"},
                            {"turtleIcon03.png", "turtleIconHighlight03.png"}};
                }
                break;
            case 1:
                if (mNumber == 0) {
                    mOriginPics = new String[][] {
                            {"sheep01.png", "sheepButton01.png"},
                            {"sheep02.png", "sheepButton02.png"},
                            {"sheep03.png", "sheepButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"sheepIcon01.png", "sheepIconHighlight01.png"},
                            {"sheepIcon02.png", "sheepIconHighlight02.png"},
                            {"sheepIcon03.png", "sheepIconHighlight03.png"}};
                } else if (mNumber == 1) {
                    mOriginPics = new String[][] {
                            {"cow01.png", "cowButton01.png"},
                            {"cow02.png", "cowButton02.png"},
                            {"cow03.png", "cowButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"cowIcon01.png", "cowIconHighlight01.png"},
                            {"cowIcon02.png", "cowIconHighlight02.png"},
                            {"cowIcon03.png", "cowIconHighlight03.png"}};
                } else if (mNumber == 2) {
                    mOriginPics = new String[][] {
                            {"goose01.png", "gooseButton01.png"},
                            {"goose02.png", "gooseButton02.png"},
                            {"goose03.png", "gooseButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"gooseIcon01.png", "gooseIconHighlight01.png"},
                            {"gooseIcon02.png", "gooseIconHighlight02.png"},
                            {"gooseIcon03.png", "gooseIconHighlight03.png"}};
                } else if (mNumber == 3) {
                    mOriginPics = new String[][] {
                            {"horse01.png", "horseButton01.png"},
                            {"horse02.png", "horseButton02.png"},
                            {"horse03.png", "horseButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"horseIcon01.png", "horseIconHighlight01.png"},
                            {"horseIcon02.png", "horseIconHighlight02.png"},
                            {"horseIcon03.png", "horseIconHighlight03.png"}};
                } else if (mNumber == 4) {
                    mOriginPics = new String[][] {
                            {"pig01.png", "pigButton01.png"},
                            {"pig02.png", "pigButton02.png"},
                            {"pig03.png", "pigButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"pigIcon01.png", "pigIconHighlight01.png"},
                            {"pigIcon02.png", "pigIconHighlight02.png"},
                            {"pigIcon03.png", "pigIconHighlight03.png"}};
                } else if (mNumber == 5) {
                    mOriginPics = new String[][] {
                            {"duck01.png", "duckButton01.png"},
                            {"duck02.png", "duckButton02.png"},
                            {"duck03.png", "duckButton03.png"}};
                    mStageIconRes = new String[][] {
                            {"duckIcon01.png", "duckIconHighlight01.png"},
                            {"duckIcon02.png", "duckIconHighlight02.png"},
                            {"duckIcon03.png", "duckIconHighlight03.png"}};
                } else if (mNumber == 6) {
                    mOriginPics = new String[][] {
                            {"chicken01.png", "chickenButton01.png"},
                            {"chicken02.png", "chickenButton02.png"},
                            {"chicken03.png", "chickenButton03.png"},
                            {"chicken04.png", "chickenButton04.png"}};
                    mStageIconRes = new String[][] {
                            {"chickenIcon01.png", "chickenIconHighlight01.png"},
                            {"chickenIcon02.png", "chickenIconHighlight02.png"},
                            {"chickenIcon03.png", "chickenIconHighLight03.png"},
                            {"chickenIcon04.png", "chickenIconHighlight04.png"}};
                }
        }
    }

    private void initGameElements() {
        initTrialIconSprites();
        initOriginIcon();
        initStars();
        playOriginalHint();
    }

    private void initTrialIconSprites() {
        // At most 4 trials for each animal
        CGPoint[] trialPos = new CGPoint[4];
        float top = mScreenHeight - 80;
        trialPos[0] = CGPoint.ccp(120.0F, top);
        trialPos[1] = CGPoint.ccp(120.0F, top - 110.0F);
        trialPos[2] = CGPoint.ccp(140 + 120.0F, top - 20);
        trialPos[3] = CGPoint.ccp(140 + 120.0F, top - 20 - 110.0F);

        int trials = mTrialsNums[mSide][mNumber];
        mTrialIcons = new CCSprite[trials];

        for (int i = 0; i < trials; i++) {
            if (i <= passedNum) {
                mTrialIcons[i] = ButtonSprite.create(mStageIconRes[i][0], mStageIconRes[i][1]);
                mTrialIcons[i].setPosition(trialPos[i]);
                ((ButtonSprite)mTrialIcons[i]).addCallback(callback);
                addChild(mTrialIcons[i], 0, TAG_TRIAL_ICONS[i]);
            } else {
                mTrialIcons[i] = CCSprite.sprite("lock.png");
                mTrialIcons[i].setPosition(trialPos[i]);
                addChild(mTrialIcons[i], 0, TAG_TRIAL_ICONS[i]);
            }
        }
    }

    private void initOriginIcon() {
        mMagnifier = ButtonSprite.create("originalButton.png", "originalButtonHighlight.png");
        mMagnifier.setAnchorPoint(1.0F, 0.0F);
        CGPoint magnifierPos = CGPoint.ccp(mBlackboardPos.x - mBlackboard.getContentSize().getWidth() - 20, mBlackboardPos.y);
        mMagnifier.setPosition(magnifierPos);

        mOriginalBig = CCSprite.sprite(mOriginPics[mCurrentTrial - 1][0]);
        mOriginalBig.setAnchorPoint(1.0F, 0.0F);
        mOriginalBigPos = CGPoint.ccp(mBlackboard.getPosition().x - 50, mBlackboard.getPosition().y + 80);
        mOriginalBig.setPosition(mOriginalBigPos);
        mOriginalBig.setVisible(false);

        mOriginIcon = CCSprite.sprite(mOriginPics[mCurrentTrial - 1][1]);
        mOriginIcon.setAnchorPoint(0.5F, 0.5F);
        CGPoint originIconPos = CGPoint.ccp(magnifierPos.x - mMagnifier.getContentSize().getWidth() / 2 + 10.0F,
                magnifierPos.y + mMagnifier.getContentSize().getHeight() / 2 + 20.0F);
        mOriginIcon.setPosition(originIconPos);

        addChild(mMagnifier, 1, TAG_MAGNIFIER);
        addChild(mOriginalBig, 1, TAG_ORIGIN_BIG);
        addChild(mOriginIcon, 1);
    }

    private void initStars() {
        initStarPositions();

        int difficulty = getDifficulty(mCurrentTrial);
        mStars = new CCSprite[3];
        for (int i = 0; i < 3; ++i) {
            if(i < difficulty) {
                mStars[i] = CCSprite.sprite("starHighlight.png");
                mStars[i].setPosition(mStarPos[i]);
            } else {
                mStars[i] = CCSprite.sprite("star.png");
                mStars[i].setPosition(mStarPos[i]);
            }

            addChild(mStars[i], 1, TAG_STARS[i]);
        }
    }

    private void initStarPositions() {
        float var1 = mBlackboardPos.x;
        float var2 = mBlackboard.getContentSize().width / 2.0F;
        float var3 = mBlackboardPos.y;
        float var4 = mBlackboard.getContentSize().height;
        mStarPos = new CGPoint[3];

        for (int i = 0; i < 3; i++) {
            mStarPos[i] = CGPoint.ccp((float)(i * 90) + (var1 - var2 - 90.0F), var3 + var4 - 80.0F);
        }
    }

    private void playBgMusic() {
        mSoundManager.playSound(mContext, R.raw.sound_bg_music_play, true);
    }

    private void playOriginalHint() {
        setTouchDisable();
        mOriginalBig.runAction(CCSequence.actions(
            CCBlink.action(1.5F, 3),
            CCCallFunc.action(this, "originDisappear"),
            CCCallFunc.action(this, "playResetEffect"),
            CCCallFunc.action(this, "addFragments"),
            CCCallFunc.action(this, "setTouchIdle")));
    }

    private int getDifficulty(int stage) {
        int difficulty = 0;
        if (stage == 1) {
            difficulty = 1;
        } else if (2 <= stage && stage <= 3) {
            difficulty = 2;
        } else if (stage == 4) {
            difficulty = 3;
        }

        return difficulty;
    }

    private int getSplitByStage(int stage) {
        switch (getDifficulty(stage)) {
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 5;
            default:
                return 0;
        }
    }

    /**
     * Used by touch event handler methods.
     * @param sprite target sprite
     * @return true if the sprite is not the fragment that is currently
     * processed by the event handler; false otherwise
     */
    private boolean isSwappableFragment(CCSprite sprite) {
        if (sprite == null) {
            mLastFragmentX = -1;
            mLastFragmentY = -1;
            return false;
        }

        int length = mFragments.length;
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < length; ++j) {
                if (mFragments[i][j].equals(sprite) && (mLastFragment == null || !mLastFragment.equals(sprite))) {
                    mLastFragmentX = i;
                    mLastFragmentY = j;
                    return true;
                }
            }
        }

        return false;
    }

    public void addFragments() {
        int split = getSplitByStage(mCurrentTrial);
        mFragments = new CCSprite[split][split];
        mFragmentIDs = new int[split][split];

        CGSize size = mOriginalBig.getContentSize();
        float w = size.getWidth();
        float h = size.getHeight();
        float blockW = w / split;
        float blockH = h / split;

        PublicUtils.splitPicture(mOriginPics[mCurrentTrial - 1][0], split, mFragments, mFragmentIDs);

        CGPoint pos = CGPoint.ccp(mOriginalBigPos.x - w, mOriginalBigPos.y + h);

        int[] randomRow = PublicUtils.randomSeries(split);
        int[] randomCol = PublicUtils.randomSeries(split);
        float interval = 1.5F / (split * split);
        float half = interval / 2.0F;

        // to save randomized fragments and their ids
        CCSprite[][] ranFrags = new CCSprite[split][split];

        int tag = TAG_STARS[TAG_STARS.length - 1] + 1;
        for (int i = 0; i < split; i++) {
            for (int j = 0; j < split; j++) {
                CCSprite sprite = mFragments[randomRow[i]][randomCol[j]];
                ranFrags[i][j] = sprite;
                mFragmentIDs[i][j] = randomRow[i] * split + randomCol[j];

                sprite.setAnchorPoint(0.5F, 0.5F);
                sprite.setPosition(pos.x + (float)(i + 0.5) * blockW,
                    pos.y - (float)(j + 0.5) * blockH);
                sprite.setOpacity(0);
                addChild(sprite, 2, tag++);

                if (i + 1 == split && j + 1 == split) {
                    sprite.runAction(CCSequence.actions(
                        CCDelayTime.action((split * j + i) * half),
                        CCFadeIn.action(interval),
                        CCCallFunc.action(this, "setTouchIdle")));
                } else {
                    sprite.runAction(CCSequence.actions(
                        CCDelayTime.action((split * j + i) * half),
                        CCFadeIn.action(interval)));
                }
            }
        }

        mFragments = ranFrags;
    }

    private void swapFragments(int oriIdxX, int oriIdxY, int tarIdxX, int tarIdxY) {
        mTouchEnable = false;

        // swap their ids
        int tempId = mFragmentIDs[oriIdxX][oriIdxY];
        mFragmentIDs[oriIdxX][oriIdxY] = mFragmentIDs[tarIdxX][tarIdxY];
        mFragmentIDs[tarIdxX][tarIdxY] = tempId;

        mLastFragment.runAction(CCSequence.actions(
                CCMoveTo.action(0.5F, mFragments[tarIdxX][tarIdxY].getPosition()),
                CCCallFunc.action(this, "playSwapEffect"),
                CCScaleTo.action(0.2F, 1.25F),
                CCScaleTo.action(0.2F, 1.0F)));

        mSwitchTarget = mFragments[tarIdxX][tarIdxY];
        reorderChild(mSwitchTarget, 3);
        mSwitchTarget.runAction(CCSequence.actions(
                CCMoveTo.action(0.5F, mLastFragmentPos),
                CCScaleTo.action(0.2F, 1.25F),
                CCScaleTo.action(0.2F, 1.0F),
                CCCallFunc.action(this, "endActions"),
                CCCallFunc.action(this, "checkComplete"),
                CCCallFunc.action(this, "setTouchIdle")));

        // swap fragment instances
        CCSprite tempFrag = mFragments[oriIdxX][oriIdxY];
        mFragments[oriIdxX][oriIdxY] = mFragments[tarIdxX][tarIdxY];
        mFragments[tarIdxX][tarIdxY] = tempFrag;
    }

    public boolean ccTouchesBegan(MotionEvent event) {
        if (!mTouchEnable || mLastPointerId != -1) {
            // If there has already been one previous pointer being
            // processed. Simply swallow this event such that no
            // more than one events are processed simultaneously.
            return true;
        }

        mLastPointerId = event.getPointerId(event.getActionIndex());
        if (mGrayBg != null && mGrayBg.getVisible()) {
            return true;
        } else {
            CCNode elem = getVisibleEventButton(event);
            if (elem instanceof ButtonSprite) {
                mLastButton = (ButtonSprite)elem;
                return mLastButton.ccTouchesBegan(event);
            }

            if (elem instanceof CCSprite) {
                if (isFragment((CCSprite)elem)) {
                    mLastFragment = (CCSprite)elem;
                    U = mLastFragmentX;
                    V = mLastFragmentY;
                    reorderChild(mLastFragment, 3);
                    mLastFragmentPos = mLastFragment.getPosition();
                }
                return true;
            }

            return false;
        }
    }

    private CCNode getVisibleEventButton(MotionEvent event) {
        CGPoint point = new CGPoint();
        CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);
        List<CCNode> children = getChildren();
        for (CCNode node : children) {
            if ((node.getTag() > TAG_BLACKBOARD || node.getTag() == BACK_ID) && contains(node, point) && node.getVisible()) {
                return node;
            }
        }

        return null;
    }

    private boolean isFragment(CCSprite sprite) {
        if (sprite == null || mFragments == null) {
            mLastFragmentX = -1;
            mLastFragmentY = -1;
            return false;
        }

        int tag = sprite.getTag();
        int length = mFragments.length;
        boolean isFrag = false;

        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < length; ++j) {
                if (mFragments[i][j] != null && mFragments[i][j].getTag() == tag) {
                    mLastFragmentX = i;
                    mLastFragmentY = j;
                    isFrag = true;
                    break;
                }
            }
        }

        return isFrag;
    }

    public boolean ccTouchesCancelled(MotionEvent event) {
        if (mLastFragment != null) {
            mTouchEnable = false;
            mLastFragment.runAction(CCSequence.actions(
                CCMoveTo.action(0.2F, mLastFragmentPos),
                CCCallFunc.action(this, "endActions"),
                CCCallFunc.action(this, "setTouchIdle")));
        }

        CCNode node = getVisibleEventButton(event);
        return node instanceof ButtonSprite && ((ButtonSprite)node).ccTouchesCancelled(event);
    }

    public boolean ccTouchesEnded(MotionEvent event) {
        if (!mTouchEnable) {
            return true;
        }

        int pointId = event.getPointerId(event.getActionIndex());
        if (mLastPointerId != pointId) {
            return true;
        }

        if (mGrayBg != null && mGrayBg.getVisible()) {
            mOriginalBig.setVisible(false);
            reorderChild(mOriginalBig, 0);
            mGrayBg.setVisible(false);
            mLastPointerId = -1;
            return true;
        }

        // the last node that contains the event
        CCNode target = getVisibleEventButton(event);
        if (target instanceof ButtonSprite && mLastFragment == null) {
            if (mLastButton != null) {
                mLastButton = null;
            }
            mLastPointerId = -1;
            return ((ButtonSprite)target).ccTouchesEnded(event);
        }

        if (target instanceof CCSprite) {
            if (mLastFragment == null) {
                mLastPointerId = -1;
                return true;
            }

            CCSprite sprite = (CCSprite)target;
            if (isSwappableFragment(sprite)) {
                CGPoint curPos = mLastFragment.getPosition();
                if (this.contains(sprite, curPos)) {
                    // if the middle point of the current fragment reaches
                    // the boundary of the target fragment, swap the two fragments.
                    swapFragments(U, V, mLastFragmentX, mLastFragmentY);
                } else {
                    // it is not recognized as getPassedCout successful swapping,
                    // move the fragment to its original position.
                    mTouchEnable = false;
                    mLastFragment.runAction(CCSequence.actions(
                            CCMoveTo.action(0.2F, mLastFragmentPos),
                            CCCallFunc.action(this, "endActions"),
                            CCCallFunc.action(this, "setTouchIdle")));
                }
            } else {
                mLastFragment.setPosition(mLastFragmentPos);
                reorderChild(mLastFragment, 1);
                mLastPointerId = -1;
                mLastFragment = null;
                mLastFragmentPos = null;
            }
        }

        mLastPointerId = -1;
        return true;
    }

    public boolean ccTouchesMoved(MotionEvent event) {
        if (!mTouchEnable || (mGrayBg != null && mGrayBg.getVisible())) {
            return true;
        }

        int pointerId = event.getPointerId(event.getActionIndex());
        if (pointerId != mLastPointerId) {
            return true;
        }

        if (mLastFragment != null && isFragment(mLastFragment)) {
            CGPoint point = new CGPoint();
            CCDirector.sharedDirector().convertToGL(event.getX(), event.getY(), point);
            mLastFragment.setPosition(point);
            return true;
        }

        return false;
    }

    public void checkComplete() {
        if (hasCompleteCurrentTrial()) {
            mTouchEnable = false;
            performComplete();
        }
    }

    private boolean hasCompleteCurrentTrial() {
        int length = mFragmentIDs.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (mFragmentIDs[i][j] != length * i + j) {
                    return false;
                }
            }
        }

        return true;
    }

    private void performComplete() {
        mSoundManager.playEffect(mContext, R.raw.sound_play_complete);
        mSaveManager.setPassedCount(mSide, mNumber, passedNum);
        mSaveManager.setCompleted(mOriginPics[mCurrentTrial - 1][0], true);

        removeAllFragments();

        if (mOriginalBig != null) {
            mOriginalBig.setVisible(true);
            mOriginalBig.runAction(CCSequence.actions(
                    CCBlink.action(1.0F, 2),
                    CCCallFunc.action(this, "originAppear"),
                    CCCallFunc.action(this, "setTouchIdle")));
        }

        int total = mTrialsNums[mSide][mNumber];
        if (mCurrentTrial == passedNum + 1 && mCurrentTrial < total) {
            passedNum++;
        }

        removeTrialIcons();

        if (mCurrentTrial < total) {
            mCurrentTrial++;
        }

        initTrialIconSprites();
    }

    private void removeAllFragments() {
        if (mFragments == null) {
            return;
        }

        int length = mFragments.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; ++j) {
                removeChild(mFragments[i][j], true);
                mFragments[i][j] = null;
            }
        }
    }

    private void removeTrialIcons() {
        if (mTrialIcons != null) {
            int length = mTrialIcons.length;
            for (int i = 0; i < length; ++i) {
                if (mTrialIcons[i] instanceof ButtonSprite) {
                    ((ButtonSprite)mTrialIcons[i]).removeCallBack();
                }

                removeChild(mTrialIcons[i], true);
                mTrialIcons[i] = null;
            }
        }
    }

    public void endActions() {
        //resetFocusedFragmentBgColor();
        if (mSwitchTarget != null) {
            reorderChild(mSwitchTarget, 1);
            // resetTargetFragmentBgColor();
            mSwitchTarget = null;
        }

        if (mLastFragment != null) {
            reorderChild(mLastFragment, 1);
            mLastFragment = null;
        }

        mLastFragmentPos = null;
        mLastPointerId = -1;
    }

    public void onEnter() {
        super.onEnter();
        if (backBtn != null) {
            backBtn.addCallback(callback);
        }

        if (mMagicWand != null) {
            mMagicWand.addCallback(callback);
            mMagicWand.runAction(mMagicWandAction);
        }

        if (mMagnifier != null) {
            mMagnifier.addCallback(callback);
        }

        CCTouchDispatcher.sharedDispatcher().addDelegate(this, 0);
    }

    public void onExit() {
        if(mLastFragment != null && mTouchEnable) {
            mLastFragment.runAction(CCSequence.actions(
                CCMoveTo.action(0.2F, mLastFragmentPos),
                CCCallFunc.action(this, "endActions"),
                CCCallFunc.action(this, "setTouchIdle")));
        }

        super.onExit();
        if (backBtn != null) {
            backBtn.removeCallBack();
        }

        if (mMagicWand != null) {
            mMagicWand.removeCallBack();
            mMagicWand.stopAllActions();
        }

        if (mMagnifier != null) {
            mMagnifier.removeCallBack();
        }

        CCTouchDispatcher.sharedDispatcher().removeDelegate(this);
    }

    public void originAppear() {
        reorderChild(mOriginalBig, 2);
        mOriginalBig.setVisible(true);
    }

    public void originDisappear() {
        reorderChild(mOriginalBig, 1);
        mOriginalBig.setVisible(false);
    }

    public void playResetEffect() {
        mSoundManager.playEffect(mContext, R.raw.sound_wand_reset);
    }

    public void playSwapEffect() {
        mSoundManager.playEffect(mContext, R.raw.sound_swap_position);
    }

    public void saveFinished() {
        mSaveManager.setPassedCount(mSide, mNumber, passedNum);
    }

    public void setTouchDisable() {
        mTouchEnable = false;
    }

    public void setTouchIdle() {
        mTouchEnable = true;
    }

    private void removeOriginPic() {
        if (mOriginalBig != null) {
            removeChild(mOriginalBig, true);
            mOriginalBig = null;
        }

        if (mOriginIcon != null) {
            removeChild(mOriginIcon, true);
            mOriginIcon = null;
        }
    }

    private void removeStars() {
        if (mStars == null) {
            return;
        }

        int size = mStars.length;
        for (int i = 0; i < size; i++) {
            removeChild(mStars[i], true);
            mStars[i] = null;
        }
    }

    private static class PlayLayerCallback implements TouchCallbacks {
        private PlayLayer layer;

        PlayLayerCallback(PlayLayer layer) {
            this.layer = layer;
        }

        public boolean onTouchesBegan(MotionEvent event, int tag) {
            return true;
        }

        public boolean onTouchesEnded(MotionEvent event, int tag) {
            if (tag == BACK_ID) {
                layer.mSoundManager.playEffect(layer.mContext, brian.pinpin.R.raw.sound_back_to_prev);
                layer.mSaveManager.setPassedCount(layer.mSide, layer.mNumber, layer.passedNum);
                SelectScene scene = (SelectScene) layer.mSceneManager.getScene(SceneManager.SCENE_SELECT);
                scene.setSide(layer.getDifficulty(layer.mSide));
                CCDirector.sharedDirector().replaceScene(scene);
                ((IBaseScene)layer.getParent()).cleanupScene();
            } else if (tag == layer.mMagicWand.getTag()) {
                layer.mSoundManager.playEffect(layer.mContext, R.raw.sound_wand_reset);
                layer.removeAllFragments();
                layer.addFragments();
            } else if (PlayLayer.TAG_TRIAL_ICONS[0] <= tag &&
                    tag <= PlayLayer.TAG_TRIAL_ICONS[PlayLayer.TAG_TRIAL_ICONS.length - 1]) {
                int index = 0;
                for (; index < layer.mTrialIcons.length; index++) {
                    if (tag == layer.mTrialIcons[index].getTag()) {
                        break;
                    }
                }

                if (index <= layer.passedNum) {
                    layer.removeOriginPic();
                    layer.removeStars();
                    layer.removeAllFragments();
                    layer.mCurrentTrial = index + 1;
                    layer.initOriginIcon();
                    layer.initStars();
                    layer.playOriginalHint();
                }
            } else if (tag == layer.mMagnifier.getTag()) {
                layer.mGrayBg.setVisible(true);
                layer.reorderChild(layer.mGrayBg, 4);
                layer.mOriginalBig.setVisible(true);
                layer.reorderChild(layer.mOriginalBig, 4);
            }

            return true;
        }

        public boolean onTouchesCancelled(MotionEvent event, int tag) {
            return false;
        }
    }
}
