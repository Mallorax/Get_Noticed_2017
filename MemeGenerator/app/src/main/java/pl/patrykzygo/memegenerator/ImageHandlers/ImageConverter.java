package pl.patrykzygo.memegenerator.ImageHandlers;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageConverter {

    public static Bitmap downscalePic(String path){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 5;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
        return bitmap;
    }
}
