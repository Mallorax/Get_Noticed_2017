package pl.patrykzygo.memegenerator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import pl.patrykzygo.memegenerator.Database.MemeDBHelper;
import pl.patrykzygo.memegenerator.ImageHandlers.AbstractImageSaver;
import pl.patrykzygo.memegenerator.ImageHandlers.ImageConverter;

public class AddMemeActivity extends AppCompatActivity {

    private ImageView memeToAdd;
    private Button addButton;
    private EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meme);

        memeToAdd = (ImageView) findViewById(R.id.image_to_add);
        addButton = (Button) findViewById(R.id.add_button);
        nameInput = (EditText) findViewById(R.id.name_input);

        Intent i = getIntent();
        String path = i.getStringExtra("path");
        memeToAdd.setImageBitmap(ImageConverter.downscalePic(path));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonClicked();
            }
        });
    }

    public void saveButtonClicked(){
        Bitmap usersMeme = AbstractImageSaver.getBitmapFromView(memeToAdd);
        byte[] memeInBytes = ImageConverter.getBytes(usersMeme);
        String name = nameInput.getText().toString();
        new MemeDBHelper(this).addEntry(name, memeInBytes);
        Toast.makeText(this, "New meme addade", Toast.LENGTH_LONG).show();
    }


}
