package pl.patrykzygo.memegenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView memeListRecyclerView;
    private RecyclerView.LayoutManager memeListLayoutManager;
    private MemeListAdapter memeListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memeListRecyclerView = (RecyclerView) findViewById(R.id.meme_list_recycler);
        memeListRecyclerView.setHasFixedSize(true);

        memeListLayoutManager = new LinearLayoutManager(this);
        memeListRecyclerView.setLayoutManager(memeListLayoutManager);

        memeListAdapter = new MemeListAdapter(getMemeList());
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
