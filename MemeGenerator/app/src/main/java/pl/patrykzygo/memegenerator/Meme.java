package pl.patrykzygo.memegenerator;

public class Meme {

    private String name;
    private int imageResource;

    //constructor
    public Meme(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }


    //getters
    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
