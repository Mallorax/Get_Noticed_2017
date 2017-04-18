package pl.patrykzygo.memegenerator.ImageHandlers;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import java.io.File;

public abstract class AbstractImageHandler {

    public static final String IMAGE_LOG = "IMAGE_MANAGER";
    private Activity activity;

    public AbstractImageHandler(Activity activity) {
        this.activity = activity;
    }

    //Method for converting view into bitmap. Returns bitmap.
    public static Bitmap getBitmapFromView(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        Log.v(IMAGE_LOG, "View converted to image");
        return bitmap;
    }

    //Method for saving meme. Should return true if meme was saved successfully.
    public abstract boolean saveMeme(Bitmap bitmap);

    //Method for scanning gallery. It's necessary if you want to show image in gallery.
    protected void scanGallery(File file){
        MediaScannerConnection.scanFile(activity, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i(IMAGE_LOG, "Scanned " + path + ":");
                        Log.i(IMAGE_LOG, "-> uri=" + uri);
                    }
                });
        Log.v(IMAGE_LOG, "Gallery scanned");
    }

    protected Activity getActivity() {
        return activity;
    }
}
