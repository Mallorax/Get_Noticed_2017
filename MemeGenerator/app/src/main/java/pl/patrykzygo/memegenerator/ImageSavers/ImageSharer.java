package pl.patrykzygo.memegenerator.ImageSavers;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class ImageSharer {

    private Activity activity;

    public ImageSharer(Activity activity) {
        this.activity = activity;
    }

    public void sendToGallery(Uri uri, AbstractImageSaver imageSaver){
        Intent shareIntent = new Intent();
        if (imageSaver instanceof InternalImageSaver) {
           if (uri != null) {
               shareIntent.setAction(Intent.ACTION_VIEW);
               shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
               shareIntent.setDataAndType(uri, "image/*");
               shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
               activity.startActivity(Intent.createChooser(shareIntent, "Choose app"));
           }
       }else{
            shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_VIEW);
            shareIntent.setDataAndType(uri, "image/*");
            activity.startActivity(Intent.createChooser(shareIntent, "Choose app"));
       }
    }


    //    nice method for share button -> can be useful later
    public void shareWithOtherApps(Uri uri){
        if (uri != null){
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(uri, activity.getContentResolver().getType(uri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            activity.startActivity(Intent.createChooser(shareIntent, null));
        }
    }
}
