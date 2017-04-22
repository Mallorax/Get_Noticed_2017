package pl.patrykzygo.memegenerator.ImageHandlers;


import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;

public class ExternalImageSaver extends AbstractImageSaver {

    public ExternalImageSaver(ActivityManagePermission activity) {
        super(activity);
    }

    @Override
    public boolean saveMeme(Bitmap bitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fileName = "Image-" + n + ".jpg";

        File myDir = new File(root + "/Memes");
        myDir.mkdirs();
        File file = new File(myDir, fileName);
        Uri contentUri = Uri.fromFile(file);

        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            scanGallery(file);
            sharer.sendToGallery(contentUri, this);
            Log.v(IMAGE_LOG, "Image saved to external storage");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(IMAGE_LOG, "IOException occurred");
            return false;
        }
    }

    protected void scanGallery(File file){
        MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i(IMAGE_LOG, "Scanned " + path + ":");
                        Log.i(IMAGE_LOG, "-> uri=" + uri);
                    }
                });
        Log.v(IMAGE_LOG, "Gallery scanned");
    }

    @Override
    public void run() {

    }
}
