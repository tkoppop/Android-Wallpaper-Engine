package com.example.wallpaperengine;

public class DataHandler {
    String title, thumbnail, image;
    DataHandler(String title, String thumbnail, String image){
        this.title= title;
        this.thumbnail = thumbnail;
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public String getThumbnail(){
        return thumbnail;

    }
    public String getImage(){
        return image;
    }


}
