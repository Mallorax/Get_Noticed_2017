package pl.patrykzygo.memegenerator.ImageHandlers;


import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ExternalImageSaver extends AbstractImageSaver {

    public ExternalImageSaver(Activity activity) {
        super(activity);
    }

    @Override
    public Uri saveMeme(Bitmap bitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
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
            Log.v(IMAGE_LOG, "Image saved to external storage");
            return contentUri;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v(IMAGE_LOG, "IOException occurred");
            return null;
        }
    }

    private void scanGallery(File file){
        MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i(IMAGE_LOG, "Scanned " + path + ":");
                        Log.i(IMAGE_LOG, "-> uri=" + uri);
                    }
                });
        Log.v(IMAGE_LOG, "Gallery scanned");
    }
}
