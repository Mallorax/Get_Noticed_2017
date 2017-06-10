package pl.patrykzygo.memegenerator.Model;

public class Meme {

    private String name;
    private int imageResource;

    //constructor
    public Meme(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }


    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
