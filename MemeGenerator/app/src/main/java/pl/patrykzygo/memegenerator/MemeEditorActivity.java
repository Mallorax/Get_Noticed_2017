package pl.patrykzygo.memegenerator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;
import pl.patrykzygo.memegenerator.ImageHandlers.ExternalImageSaver;
import pl.patrykzygo.memegenerator.ImageHandlers.ImageTask;
import pl.patrykzygo.memegenerator.ImageHandlers.InternalImageSaver;

public class MemeEditorActivity extends ActivityManagePermission {

    private ImageView memeImage;
    private TextView topTextView, bottomTextView;
    private EditText topEditText, bottomEditText;
    private Button saveButton, shareButton;
    private RelativeLayout memeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_editor);

        memeImage = (ImageView) findViewById(R.id.editor_meme_view);
        topTextView = (TextView) findViewById(R.id.meme_top_text);
        bottomTextView = (TextView) findViewById(R.id.meme_bottom_text);
        topEditText = (EditText) findViewById(R.id.top_text_edit);
        bottomEditText = (EditText) findViewById(R.id.bottom_text_edit);
        saveButton = (Button) findViewById(R.id.save_meme_button);
        memeLayout = (RelativeLayout) findViewById(R.id.editor_meme_layout);
        shareButton = (Button) findViewById(R.id.share_meme_button);


        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            int imageID = b.getInt("image");
            memeImage.setImageResource(imageID);

            topEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    topTextView.setText(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            bottomEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bottomTextView.setText(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveButtonClicked(v);
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InternalImageSaver imageSaver = new InternalImageSaver(MemeEditorActivity.this);
                    new ImageTask(imageSaver, memeLayout, v).execute();
                }
            });
        }


    }


    public void saveButtonClicked(View v){
        final View caller = v;
        if (Build.VERSION.SDK_INT >= 23) {
            askCompactPermission(PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE, new PermissionResult() {

                @Override
                public void permissionGranted() {
                    onPermissionGranted(caller);
                }

                @Override
                public void permissionDenied() {
                    onPermissionDenied(caller);
                }

                @Override
                public void permissionForeverDenied() {
                    onPermissionDenied(caller);
                }
            });
        } else {
            onPermissionGranted(caller);
        }
    }

    public void onPermissionGranted(View caller) {
        new ImageTask(new ExternalImageSaver(this), memeLayout, caller).execute();
    }

    public void onPermissionDenied(View caller){
        new ImageTask(new InternalImageSaver(this), memeLayout, caller).execute();
    }

}
