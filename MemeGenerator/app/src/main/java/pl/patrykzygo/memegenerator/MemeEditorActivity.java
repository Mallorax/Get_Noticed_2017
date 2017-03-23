package pl.patrykzygo.memegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MemeEditorActivity extends AppCompatActivity {

    private ImageView memeImage;
    private TextView topTextView, bottomTextView;
    private EditText topEditText, bottomEditText;
    private Button saveButton;

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

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b != null){
            int imageID = b.getInt("image");
            memeImage.setImageResource(imageID);
        }
    }
}