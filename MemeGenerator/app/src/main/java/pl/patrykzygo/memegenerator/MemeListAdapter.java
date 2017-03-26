package pl.patrykzygo.memegenerator;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MemeListAdapter extends RecyclerView.Adapter<MemeListAdapter.MemeHolder>{

    private List<Meme> memeList;

    public MemeListAdapter (List<Meme> memeList){
        this.memeList = memeList;
    }


    @Override
    public MemeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View memeView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meme_list_item, parent, false);
        return new MemeHolder(memeView);
    }

    @Override
    public void onBindViewHolder(MemeHolder holder, int position) {
        Meme meme = memeList.get(position);
        holder.memeNameTextView.setText(meme.getName());
        holder.memeImageView.setImageResource(meme.getImageResource());
    }

    @Override
    public int getItemCount() {
        return memeList.size();
    }


    //ViewHolder class that provides a references to the views
    public class MemeHolder extends RecyclerView.ViewHolder{

        private TextView memeNameTextView;
        private ImageView memeImageView;

        public MemeHolder(View view){
            super(view);
            memeNameTextView = (TextView) view.findViewById(R.id.meme_text_view);
            memeImageView = (ImageView) view.findViewById(R.id.meme_list_image);
        }

    }
}
