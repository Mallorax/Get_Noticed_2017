package pl.patrykzygo.memegenerator.ImageHandlers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;

import pl.patrykzygo.memegenerator.R;

public class ImageTask extends AsyncTask<Void, Void, Void> {

    private AbstractImageSaver saver;
    private Uri uri;
    private View meme, caller;
    private Activity activity;
    private ProgressDialog progress;


    public ImageTask(AbstractImageSaver saver, View viewToSave, View caller) {
        this.saver = saver;
        this.meme = viewToSave;
        this.caller = caller;
        this.activity = saver.getActivity();
        this.progress = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        if (caller.getId() == R.id.save_meme_button) {
            progress.setMessage("Saving...");
            progress.show();
        }else{
            progress.setMessage("Sharing...");
            progress.show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        this.uri = saver.saveMeme(AbstractImageSaver.getBitmapFromView(meme));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ImageSharer sharer = new ImageSharer(activity);
        if (caller.getId() == R.id.save_meme_button) {
            progress.dismiss();
            sharer.sendToGallery(uri, saver);
        }else {
            sharer.shareWithOtherApps(uri);
            progress.dismiss();
        }
    }
}
