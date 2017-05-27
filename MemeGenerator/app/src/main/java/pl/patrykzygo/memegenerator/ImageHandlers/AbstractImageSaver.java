package pl.patrykzygo.memegenerator.ImageHandlers;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import java.util.Random;

public abstract class AbstractImageSaver {

    public static final String IMAGE_LOG = "IMAGE_SAVER";
    private Activity activity;

    public AbstractImageSaver(Activity activity) {
        this.activity = activity;

    }

    //Method for converting view into bitmap. Returns bitmap.
    public static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        Log.v(IMAGE_LOG, "View converted to image");
        return bitmap;
    }

    public static String getFileName(){
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fileName = "Image-" + n + ".jpg";
        return fileName;
    }

    //Method for saving meme. Should return meme's Uri or null if wasn't saved successfully
    public abstract Uri saveMeme(Bitmap bitmap);

    protected void galleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public Activity getActivity() {
        return activity;
    }

}
