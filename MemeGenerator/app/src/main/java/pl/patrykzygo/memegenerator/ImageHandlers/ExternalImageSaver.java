package pl.patrykzygo.memegenerator.ImageHandlers;


import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExternalImageSaver extends AbstractImageSaver {

    public ExternalImageSaver(Activity activity) {
        super(activity);
    }

    @Override
    public Uri saveMeme(Bitmap bitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();

        //create directories
        File myDir = new File(root + "/Memes");
        myDir.mkdirs();
        File file = new File(myDir, getFileName());
        Uri contentUri = Uri.fromFile(file);

        FileOutputStream out;
        //save operations
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            bitmap.recycle();
            galleryAddPic(contentUri);
            Log.v(IMAGE_LOG, "Image saved to external storage");
            return contentUri;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(IMAGE_LOG, "IOException occurred");
            return null;
        }
    }

}
