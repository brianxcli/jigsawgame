package brian.pinpin.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveManager implements IService {
    private static String[][] mTrials = {
        {"deer", "butterfly", "lion", "panda", "snake", "frog", "turtle"},
        {"goat", "cow", "goose", "horse", "pig", "duck", "rooster"}
    };

    private SharedPreferences mPreference;

    SaveManager() {

    }

    public void setContext(Context context) {
        mPreference = context.getSharedPreferences("save", 0);
    }

    public int getPassedCount(int side, int animal) {
        return mPreference.getInt(mTrials[side][animal], 0);
    }

    public void setPassedCount(int side, int animal, int value) {
        mPreference.edit().putInt(mTrials[side][animal], value).apply();
    }

    public void setCompleted(String animal, boolean value) {
        mPreference.edit().putBoolean(animal, value).apply();
    }

    public boolean isCompleted(String animalPic) {
        return mPreference.getBoolean(animalPic, false);
    }

    public void onCreate() {

    }

    public void onDestroy() {

    }
}