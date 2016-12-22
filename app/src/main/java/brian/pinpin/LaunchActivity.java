package brian.pinpin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.Display;
import android.widget.Button;

//import brian.pinpin.scenes.PlayScene;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.opengl.CCGLSurfaceView;

import brian.pinpin.managers.ManagerService;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.managers.SoundManager;

public class LaunchActivity extends Activity implements OnClickListener {
    private CCGLSurfaceView mSurfaceView;
    private ProgressDialog mDialog;
    private Button mExitBtn;
    private Button mBackBtn;

    private SceneManager mSceneManager = (SceneManager) ManagerService.getInstance().getService(ManagerService.SCENE_MANAGER);
    private SoundManager mSoundManager = (SoundManager) ManagerService.getInstance().getService(ManagerService.SOUND_MANAGER);

    private void showExit() {
        this.mDialog = new ProgressDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.exit_dialog, null);
        this.mExitBtn = (Button)view.findViewById(R.id.exit_ok_btn);
        this.mExitBtn.setOnClickListener(this);
        this.mBackBtn = (Button)view.findViewById(R.id.exit_back_btn);
        this.mBackBtn.setOnClickListener(this);
        this.mDialog.show();
        this.mDialog.setContentView(view);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.exit_ok_btn:
                CCScene var2 = CCDirector.sharedDirector().getRunningScene();
//                if(var2 != null && var2 instanceof PlayScene) {
//                    ((PlayScene)var2).saveFinished();
//                }
                this.mDialog.dismiss();
                this.finish();
                return;
            case R.id.exit_back_btn:
                this.mDialog.dismiss();
                return;
            default:
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(1024, 1024);
        this.getWindow().setFlags(128, 128);

        Display display = this.getWindowManager().getDefaultDisplay();
        SceneManager.sceneWidth = display.getWidth();
        SceneManager.sceneHeight = display.getHeight();

        mSurfaceView = new CCGLSurfaceView(this);
        setContentView(mSurfaceView);
        CCDirector.sharedDirector().setLandscape(true);
        CCDirector.sharedDirector().attachInView(mSurfaceView);
        CCDirector.sharedDirector().setDisplayFPS(false);
        CCDirector.sharedDirector().setAnimationInterval(0.01666666753590107D);
        CCDirector.sharedDirector().runWithScene(mSceneManager.getScene(SceneManager.SCENE_FLASH));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            showExit();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void onPause() {
        super.onPause();
        CCDirector.sharedDirector().pause();
        mSoundManager.pauseSound();
    }

    public void onResume() {
        super.onResume();
        CCDirector.sharedDirector().resume();
        mSoundManager.resumeSound();
    }

    public void onStart() {
        super.onStart();
        CCScene scene = CCDirector.sharedDirector().getRunningScene();
        if (scene != null) {
            CCNode node = scene.getChildByTag(0);
            if (node != null) {
                node.onEnter();
            }
        }
    }

    public void onStop() {
        super.onStop();
        CCScene scene = CCDirector.sharedDirector().getRunningScene();
        if(scene != null) {
            CCNode node = scene.getChildByTag(0);
            if(node != null) {
                node.onExit();
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        CCDirector.sharedDirector().end();
    }
}
