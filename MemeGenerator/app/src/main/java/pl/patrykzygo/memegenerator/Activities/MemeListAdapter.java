package pl.patrykzygo.memegenerator.Activities;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.patrykzygo.memegenerator.Model.Meme;
import pl.patrykzygo.memegenerator.Model.UsersMeme;
import pl.patrykzygo.memegenerator.R;


public class MemeListAdapter extends RecyclerView.Adapter<MemeListAdapter.MemeHolder> {

    private List<Meme> memeList;
    private OnEntryClickListener onEntryClickListener;

    public MemeListAdapter(List<Meme> memeList) {
        this.memeList = memeList;
    }

    //setter for our listener
    public void setOnEntryClickListener(OnEntryClickListener onEntryClickListener) {
        this.onEntryClickListener = onEntryClickListener;
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
        if(meme instanceof UsersMeme) {
            holder.memeImageView.setImageBitmap(((UsersMeme) meme).getBitmap());
        }else {
            holder.memeImageView.setImageResource(meme.getImageResource());
        }
    }

    @Override
    public int getItemCount() {
        return memeList.size();
    }

    //interface with a methods that class must implement if it wants to define action that occur when list item is clicked
    public interface OnEntryClickListener {
        void onEntryClick(View view, Meme memeClicked);
        void onEntryLongClick(View view, Meme memeClicked);
    }

    //ViewHolder class that provides a references to the views
    //It implements OnClickListener, so RecyclerView will not only be able to display list
    public class MemeHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView memeNameTextView;
        private ImageView memeImageView;


        public MemeHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            memeNameTextView = (TextView) view.findViewById(R.id.meme_text_view);
            memeImageView = (ImageView) view.findViewById(R.id.meme_list_image);
        }

        //onClick method uses a method of inner interface making possible to implement specific action inside class that uses adapter
        @Override
        public void onClick(View v) {
            if (onEntryClickListener != null) {
                onEntryClickListener.onEntryClick(v, memeList.get(getLayoutPosition()));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onEntryClickListener != null) {
                onEntryClickListener.onEntryLongClick(v, memeList.get(getLayoutPosition()));
                return true;
            }
            return true;
        }
    }
}
