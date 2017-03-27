package brian.pinpin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.Display;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import brian.pinpin.scenes.PlayScene;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import brian.pinpin.managers.ManagerService;
import brian.pinpin.managers.SceneManager;
import brian.pinpin.managers.SoundManager;

public class LaunchActivity extends Activity implements OnClickListener {
    private ProgressDialog mDialog;
    private SceneManager mSceneManager = (SceneManager) ManagerService.getInstance().getService(ManagerService.SCENE_MANAGER);
    private SoundManager mSoundManager = (SoundManager) ManagerService.getInstance().getService(ManagerService.SOUND_MANAGER);

    private void showExit() {
        mDialog = new ProgressDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.exit_dialog, null);
        Button exit = (Button)view.findViewById(R.id.exit_ok_btn);
        exit.setOnClickListener(this);
        Button back = (Button)view.findViewById(R.id.exit_back_btn);
        back.setOnClickListener(this);
        mDialog.show();
        mDialog.setContentView(view);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit_ok_btn:
                CCScene scene = CCDirector.sharedDirector().getRunningScene();
                if (scene instanceof PlayScene) {
                    ((PlayScene)scene).saveFinished();
                }
                mDialog.dismiss();
                finish();
                return;
            case R.id.exit_back_btn:
                mDialog.dismiss();
                return;
            default:
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(LayoutParams.FLAG_KEEP_SCREEN_ON, LayoutParams.FLAG_KEEP_SCREEN_ON);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mSceneManager.setScreenParams(metrics.widthPixels, metrics.heightPixels, metrics.density);

        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);
        setContentView(surfaceView);
        CCDirector.sharedDirector().setLandscape(true);
        CCDirector.sharedDirector().attachInView(surfaceView);
        CCDirector.sharedDirector().setDisplayFPS(false);
        CCDirector.sharedDirector().runWithScene(mSceneManager.getScene(SceneManager.SCENE_FLASH));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
            scene.onEnter();
        }
    }

    public void onStop() {
        super.onStop();
        CCScene scene = CCDirector.sharedDirector().getRunningScene();
        if (scene != null) {
            scene.onExit();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        CCDirector.sharedDirector().end();
    }
}
