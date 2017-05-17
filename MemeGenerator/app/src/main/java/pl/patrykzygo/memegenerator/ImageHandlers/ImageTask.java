package pl.patrykzygo.memegenerator.ImageHandlers;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;

import pl.patrykzygo.memegenerator.R;

public class ImageTask extends AsyncTask<Void, Void, Void> {

    private AbstractImageSaver saver;
    private Uri uri;
    private View meme, caller;
    private ProgressDialog progress;


    public ImageTask(AbstractImageSaver saver, View viewToSave, View caller) {
        this.saver = saver;
        this.meme = viewToSave;
        this.caller = caller;
        this.progress = new ProgressDialog(saver.getActivity());
    }

    @Override
    protected void onPreExecute() {
        //displays different texts based on caller
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
        //save operation performed in background
        this.uri = saver.saveMeme(AbstractImageSaver.getBitmapFromView(meme));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // whichever button has been clicked (save or share) different operation is performed
        ImageSharer sharer = new ImageSharer(saver.getActivity());
        if (caller.getId() == R.id.save_meme_button) {
            progress.dismiss();
            sharer.sendToGallery(uri, saver);
        }else {
            sharer.shareWithOtherApps(uri);
            progress.dismiss();
        }
    }
}
