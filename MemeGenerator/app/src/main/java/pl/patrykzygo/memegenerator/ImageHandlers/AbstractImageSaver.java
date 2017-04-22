package pl.patrykzygo.memegenerator.ImageHandlers;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

public abstract class AbstractImageSaver implements Runnable {

    public static final String IMAGE_LOG = "IMAGE_MANAGER";
    private Activity activity;
    protected ImageSharer sharer;

    public AbstractImageSaver(Activity activity) {
        this.activity = activity;
        sharer = new ImageSharer(activity);
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



    protected Activity getActivity() {
        return activity;
    }
}
