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

        //providing file's URI for other apps
        Uri contentUri = FileProvider.getUriForFile(getActivity(), "pl.patrykzygo.memegenerator.fileprovider", file);
        FileOutputStream out;

        // saving file
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            bitmap.recycle();
            Log.v(IMAGE_LOG, "Saved successfully");
            galleryAddPic(contentUri);
            return contentUri;
        } catch (Exception e) {
            Log.v(IMAGE_LOG, "Something went wrong!");
            e.printStackTrace();
            return null;
        }
    }
}
