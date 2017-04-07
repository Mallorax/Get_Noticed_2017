package pl.patrykzygo.memegenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView memeListRecyclerView;
    private MemeListAdapter memeListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memeListRecyclerView = (RecyclerView) findViewById(R.id.meme_list_recycler);
        memeListRecyclerView.setHasFixedSize(true);

        memeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        memeListAdapter = new MemeListAdapter(getMemeList());
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

    //temporary method that provides place holders to display our list
    private List<Meme> getMemeList(){
        List<Meme> list = new ArrayList<>();
        for(int x = 0; x < 50; x++){
            Meme meme = new Meme("Place Holder", R.mipmap.ic_launcher);
            list.add(meme);
        }
        return list;
    }
}
