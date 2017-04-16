package pl.patrykzygo.memegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import pl.patrykzygo.memegenerator.ImageHandlers.AbstractImageHandler;
import pl.patrykzygo.memegenerator.ImageHandlers.ExternalImageHandler;

public class MemeEditorActivity extends AppCompatActivity {

    private ImageView memeImage;
    private TextView topTextView, bottomTextView;
    private EditText topEditText, bottomEditText;
    private Button saveButton;
    private RelativeLayout memeLayout;
    private AbstractImageHandler imageHandler;

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
                    imageHandler = new ExternalImageHandler(MemeEditorActivity.this);
                    if(imageHandler.saveMeme(AbstractImageHandler.getBitmapFromView(memeLayout))){
                        Toast.makeText(MemeEditorActivity.this, "The meme has been saved on your External storage", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MemeEditorActivity.this, "Couldn't save your meme", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
