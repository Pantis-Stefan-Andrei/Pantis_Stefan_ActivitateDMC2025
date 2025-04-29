package com.example.lab1;

public class ImageItem {
    private String imageUrl;
    private String description;
    private String webLink;

    public ImageItem(String imageUrl, String description, String webLink) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.webLink = webLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getWebLink() {
        return webLink;
    }
}
