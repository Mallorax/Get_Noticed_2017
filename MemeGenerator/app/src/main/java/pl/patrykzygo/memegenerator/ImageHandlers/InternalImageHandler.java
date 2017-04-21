package pl.patrykzygo.memegenerator.ImageHandlers;


import android.app.Activity;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class InternalImageHandler extends AbstractImageHandler {

    public InternalImageHandler(Activity activity) {
        super(activity);
    }

    @Override
    public boolean saveMeme(Bitmap bitmap) {
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fileName = "Image-" + n + ".jpg";


        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File file = new File(cw.getFilesDir(), fileName);
        FileOutputStream foStream;
        try {
            foStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, foStream);
            foStream.close();
            Log.v(IMAGE_LOG, "Saved successfuly");
            return file.exists();
        } catch (Exception e) {
            Log.v(IMAGE_LOG, "Something went wrong!");
            e.printStackTrace();
            return false;
        }
    }
}
