package pl.patrykzygo.memegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MemeEditorActivity extends AppCompatActivity {

    private ImageView memeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_editor);

        memeImage = (ImageView) findViewById(R.id.editor_meme_view);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b != null){
            int imageID = b.getInt("image");
            memeImage.setImageResource(imageID);
        }
    }
}
