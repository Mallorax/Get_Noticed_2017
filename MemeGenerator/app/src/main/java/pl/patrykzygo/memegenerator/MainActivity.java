package pl.patrykzygo.memegenerator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;
import pl.patrykzygo.memegenerator.Database.DefaultMemes;

public class MainActivity extends ActivityManagePermission {

    private RecyclerView memeListRecyclerView;
    private MemeListAdapter memeListAdapter;
    private Toolbar myToolbar;
    private static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(myToolbar);

        memeListRecyclerView = (RecyclerView) findViewById(R.id.meme_list_recycler);
        memeListRecyclerView.setHasFixedSize(true);

        memeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        memeListAdapter = new MemeListAdapter(DefaultMemes.getDefaultMemes());
        memeListAdapter.setOnEntryClickListener(new MemeListAdapter.OnEntryClickListener(){
            @Override
            public void onEntryClick(View view, Meme memeClicked) {
                Intent i = new Intent(view.getContext(), MemeEditorActivity.class);
                i.putExtra("image", memeClicked.getImageResource());
                startActivity(i);
            }
        });
        memeListRecyclerView.setAdapter(memeListAdapter);
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

    public void fromCamera(){
        askCompactPermission(PermissionUtils.Manifest_CAMERA, new PermissionResult() {
            @Override
            public void permissionGranted() {

            }

            @Override
            public void permissionDenied() {

            }

            @Override
            public void permissionForeverDenied() {

            }
        });
    }

    public void cameraRequestGranted(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

        }
    }
}
