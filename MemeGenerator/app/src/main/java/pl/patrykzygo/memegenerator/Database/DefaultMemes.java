package pl.patrykzygo.memegenerator.Database;


import java.util.ArrayList;
import java.util.List;

import pl.patrykzygo.memegenerator.Model.Meme;
import pl.patrykzygo.memegenerator.R;

public abstract class DefaultMemes {

    public static List<Meme> getDefaultMemes(){
        List<Meme> memes = new ArrayList<>();
        memes.add(new Meme("Awkward seal", R.drawable.awkward_seal));
        memes.add(new Meme("Butthurt dweller", R.drawable.butthurt_dweller));
        memes.add(new Meme("Confession bear", R.drawable.confession_bear));
        memes.add(new Meme("Grumpy cat", R.drawable.grumphy_cat));
        memes.add(new Meme("Joker mind loss", R.drawable.joker_mind_loss));
        memes.add(new Meme("Lazy college senior", R.drawable.lazy_college_senior));
        memes.add(new Meme("Not sure if", R.drawable.not_sure_if_troll));
        memes.add(new Meme("Unhelpful teacher", R.drawable.unhelpful_teacher));
        memes.add(new Meme("What if i told you", R.drawable.what_if_i_told_you));
        memes.add(new Meme("Winter is coming", R.drawable.winter_is_coming));
        return memes;
    }
}
