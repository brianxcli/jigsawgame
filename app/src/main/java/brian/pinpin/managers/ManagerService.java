package brian.pinpin.managers;

public class ManagerService {
    public static final int SCENE_MANAGER = 0;
    public static final int SOUND_MANAGER = SCENE_MANAGER + 1;
    public static final int SAVE_MANAGER = SOUND_MANAGER + 1;

    private static ManagerService mService = null;

    private SceneManager mSceneManager;
    private SoundManager mSoundManager;
    private SaveManager mSaveManager;

    public static ManagerService getInstance() {
        if (mService == null) {
            mService = new ManagerService();
        }

        return mService;
    }

    private ManagerService() {
        mSceneManager = new SceneManager();
        mSoundManager = new SoundManager();
        mSaveManager = new SaveManager();
    }

    public IService getService(int service) {
        switch(service) {
            case SCENE_MANAGER:
                return mSceneManager;
            case SOUND_MANAGER:
                return mSoundManager;
            case SAVE_MANAGER:
                return mSaveManager;
        }

        return null;
    }
}
