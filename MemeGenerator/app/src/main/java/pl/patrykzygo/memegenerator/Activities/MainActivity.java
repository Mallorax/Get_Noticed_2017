package pl.patrykzygo.memegenerator.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import pl.patrykzygo.memegenerator.R;

public class MainActivity extends ActivityManagePermission implements MemeListAdapter.OnEntryClickListener{

    private RecyclerView memeListRecyclerView;
    private MemeListAdapter memeListAdapter;
    private Toolbar myToolbar;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOAD_IMAGE = 2;
    private String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(myToolbar);

        memeListRecyclerView = (RecyclerView) findViewById(R.id.meme_list_recycler);
        memeListRecyclerView.addItemDecoration(new LineDivider(this));
        memeListRecyclerView.setHasFixedSize(true);

        memeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMemeList();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadMemeList();
    }

    private void loadMemeList(){
        memeListAdapter = new MemeListAdapter(getMemes());
        memeListAdapter.setOnEntryClickListener(this);
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
        askCompactPermission(PermissionUtils.Manifest_READ_EXTERNAL_STORAGE, new PermissionResult() {
            @Override
            public void permissionGranted() {
                galleryRequestGranted();
            }

            @Override
            public void permissionDenied() {

            }

            @Override
            public void permissionForeverDenied() {

            }
        });
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

    private void cameraRequestGranted() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = createImageFile();
            Uri photoURI = FileProvider.getUriForFile(this,
                    "pl.patrykzygo.memegenerator.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            filePath = photoFile.getAbsolutePath();
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private void galleryRequestGranted(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent i = new Intent(this, AddMemeActivity.class);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            i.putExtra("path", filePath);
            startActivity(i);
        }else if((requestCode == REQUEST_LOAD_IMAGE && resultCode == RESULT_OK)){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = ImageConverter.downscalePic(picturePath);
            byte[] bitmapInBytes = ImageConverter.getBytes(bitmap);
            i.putExtra("image", bitmapInBytes);
            startActivity(i);
        }else{
            Toast.makeText(this, "Something went wrong. Try something else", Toast.LENGTH_LONG).show();
        }
    }


    private File createImageFile(){
        File storageDir = new File(getFilesDir(),"Pics");
        storageDir.mkdirs();
        File image = null;
        try {
            image = File.createTempFile(AbstractImageSaver.getFileNameWithoutSuffix(), ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


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

    @Override
    public void onEntryLongClick(View view, Meme memeClicked) {
        if (memeClicked instanceof UsersMeme){
            Boolean isSuccessful = new MemeDBHelper(this).deleteEntry((UsersMeme) memeClicked);
            if (isSuccessful){
                loadMemeList();
                Toast.makeText(this, "Successfully deleted", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
}
