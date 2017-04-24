package pl.patrykzygo.memegenerator.ImageSavers;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.Log;
import android.view.View;

public abstract class AbstractImageSaver {

    public static final String IMAGE_LOG = "IMAGE_MANAGER";
    private Activity activity;
    private Uri bitmapUri;

    public AbstractImageSaver(Activity activity) {
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

    //Method for saving meme. Should return meme's Uri or null if wasn't saved successfully
    public abstract Uri saveMeme(Bitmap bitmap);


    protected Activity getActivity() {
        return activity;
    }

}
