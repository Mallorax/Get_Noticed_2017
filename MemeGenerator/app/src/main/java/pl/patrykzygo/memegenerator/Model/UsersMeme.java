package pl.patrykzygo.memegenerator.Model;


import android.graphics.Bitmap;

public class UsersMeme extends Meme {

    private Bitmap bitmap;

    public UsersMeme(String name, Bitmap bitmap){
        super(name, -1);
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
