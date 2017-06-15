package pl.patrykzygo.memegenerator.ImageHandlers;


import android.app.Activity;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

public class InternalImageSaver extends AbstractImageSaver {

    public InternalImageSaver(Activity activity) {
        super(activity);
    }

    @Override
    public Uri saveMeme(Bitmap bitmap) {
        //creating directories
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File cat = new File(cw.getFilesDir(), "Memes");
        cat.mkdirs();
        File file = new File(cat, getFileName());

        Log.v(IMAGE_LOG, "new file " + file.exists());

        // saving file
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            bitmap.recycle();
            Log.v(IMAGE_LOG, "Saved successfully");
            Uri contentUri = FileProvider.getUriForFile(getActivity(), "pl.patrykzygo.memegenerator.fileprovider", file);
            galleryAddPic(contentUri);
            return contentUri;
        } catch (Exception e) {
            Log.v(IMAGE_LOG, "Something went wrong!");
            e.printStackTrace();
            return null;
        }
    }
}
