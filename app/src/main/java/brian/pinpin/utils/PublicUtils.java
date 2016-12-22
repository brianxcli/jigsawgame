package brian.pinpin.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

import java.io.IOException;

public class PublicUtils {
    public static void splitPicture(String res, int split, CCSprite[][] sprites, int[][] ids) {
        if (sprites == null || ids == null) {
            return;
        }

        Bitmap bitmap = null;
        Context context = CCDirector.sharedDirector().getActivity().getApplicationContext();
        try {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open(res));
            CCSprite sprite = CCSprite.sprite(bitmap, "original");
            CGSize size = sprite.getContentSize();
            float w = size.getWidth();
            float h = size.getHeight();
            float blockW = w / split;
            float blockH = h / split;
            int id = 1;

            for (int i = 0; i < split; i++) {
                for (int j = 0; j < split; j++) {
                    Bitmap bm = Bitmap.createBitmap(bitmap, (int)(i * blockW), (int)(j * blockH), (int)blockW, (int)blockH);
                    sprites[i][j] = CCSprite.sprite(bm, id + "");
                    ids[i][j] = id++;
                    bm.recycle();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
        }
    }

    public static int[] randomSeries(int range) {
        int[] res = new int[range];

        for (int i = 0; i < range; i++) {
            res[i] = i;
        }

        for (int i = 0; i < range; i++) {
            swap(res, i, (int)(Math.random() * range));
        }

        return res;
    }

    private static void swap(int[] array, int idx1, int idx2) {
        int temp = array[idx1];
        array[idx1] = array[idx2];
        array[idx2] = temp;
    }
}
