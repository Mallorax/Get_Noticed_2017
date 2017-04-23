package pl.patrykzygo.memegenerator.ImageHandlers;


import android.app.Activity;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class InternalImageSaver extends AbstractImageSaver {

    public InternalImageSaver(Activity activity) {
        super(activity);
    }

    @Override
    public Uri saveMeme(Bitmap bitmap) {
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fileName = "Image-" + n + ".jpg";

        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File cat = new File(cw.getFilesDir(), "Memes");
        cat.mkdirs();
        File file = new File(cat, fileName);
        Uri contentUri = FileProvider.getUriForFile(getActivity(), "pl.patrykzygo.memegenerator.fileprovider", file);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            Log.v(IMAGE_LOG, "Saved successfuly");
            return contentUri;
        } catch (Exception e) {
            Log.v(IMAGE_LOG, "Something went wrong!");
            e.printStackTrace();
            return null;
        }
    }
}
