package pl.patrykzygo.memegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import pl.patrykzygo.memegenerator.Database.DefaultMemes;

public class MainActivity extends AppCompatActivity {

    private RecyclerView memeListRecyclerView;
    private MemeListAdapter memeListAdapter;
    private Toolbar myToolbar;


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
}
