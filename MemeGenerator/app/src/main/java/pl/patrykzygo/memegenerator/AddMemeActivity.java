package pl.patrykzygo.memegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
        memeToAdd.setImageBitmap(ImageConverter.setPic(path));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_LONG).show();
            }
        });
    }


}
