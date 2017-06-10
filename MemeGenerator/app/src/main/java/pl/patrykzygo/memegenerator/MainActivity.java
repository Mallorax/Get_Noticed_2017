package pl.patrykzygo.memegenerator;

import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;
import pl.patrykzygo.memegenerator.Database.DefaultMemes;
import pl.patrykzygo.memegenerator.Database.MemeDBHelper;
import pl.patrykzygo.memegenerator.ImageHandlers.AbstractImageSaver;
import pl.patrykzygo.memegenerator.ImageHandlers.ImageConverter;
import pl.patrykzygo.memegenerator.Model.Meme;
import pl.patrykzygo.memegenerator.Model.UsersMeme;

public class MainActivity extends ActivityManagePermission {

    private RecyclerView memeListRecyclerView;
    private MemeListAdapter memeListAdapter;
    private Toolbar myToolbar;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(myToolbar);

        memeListRecyclerView = (RecyclerView) findViewById(R.id.meme_list_recycler);
        memeListRecyclerView.setHasFixedSize(true);

        memeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        memeListAdapter = new MemeListAdapter(getMemes());
        memeListAdapter.setOnEntryClickListener(new MemeListAdapter.OnEntryClickListener(){
            @Override
            public void onEntryClick(View view, Meme memeClicked) {
                Intent i = new Intent(view.getContext(), MemeEditorActivity.class);
                if (memeClicked instanceof UsersMeme) {
                    i.putExtra("bitmap", ImageConverter.getBytes(((UsersMeme)memeClicked).getBitmap()));
                    startActivity(i);
                }else{
                    i.putExtra("image", memeClicked.getImageResource());
                    startActivity(i);
                }
            }
        });
        memeListRecyclerView.setAdapter(memeListAdapter);
    }

    private List<Meme> getMemes() {
        List <Meme> memes = DefaultMemes.getDefaultMemes();
        memes.addAll(new MemeDBHelper(this).getMemesFromDatabase());
        return memes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.from_camera:
                fromCamera();
                return true;
            case R.id.from_gallery:
                fromGallery();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fromGallery(){
        Toast.makeText(this, "Chosen gallery option", Toast.LENGTH_LONG).show();
    }

    private void fromCamera(){
        askCompactPermission(PermissionUtils.Manifest_CAMERA, new PermissionResult() {
            @Override
            public void permissionGranted() {
                cameraRequestGranted();
            }

            @Override
            public void permissionDenied() {

            }

            @Override
            public void permissionForeverDenied() {

            }
        });
    }

    private void cameraRequestGranted(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "pl.patrykzygo.memegenerator.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                filePath = photoFile.getAbsolutePath();
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Intent i = new Intent(this, AddMemeActivity.class);
            i.putExtra("path", filePath);
            startActivity(i);
        }
    }


    private File createImageFile() throws IOException {
        ContextWrapper cw = new ContextWrapper(this);
        File storageDir = new File(cw.getFilesDir(),"Pics");
        storageDir.mkdirs();
        File image = File.createTempFile(AbstractImageSaver.getFileName(), null, storageDir);
        return image;
    }


}
