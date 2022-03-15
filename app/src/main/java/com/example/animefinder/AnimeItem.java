package com.example.animefinder;

public class AnimeItem {
    private String image_url;
    private String title;
    private String synopsis;

    public AnimeItem(String imageURL, String title, String story) {
        this.image_url = imageURL;
        this.title = title;
        this.synopsis = story;
    }

    public String getImageURL() {
        return image_url;
    }
    public String getTitle() { return title; }
    public String getStory() { return synopsis;}
}
