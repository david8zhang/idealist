package com.example.david_000.idealist;

/**
 * Created by david_000 on 7/3/2015.
 */
public class FeedItem {
    private String ideaTitle, ideaText, imageUrl, postingDate;

    public FeedItem(){

    }

    public FeedItem(String ideaTitle, String ideaText, String imageUrl, String postingDate){
        super();
        this.ideaTitle = ideaTitle;
        this.ideaText = ideaText;
        this.imageUrl = imageUrl;
        this.postingDate = postingDate;

    }

    public String getIdeaText() {
        return ideaText;
    }

    public void setIdeaText(String ideaText) {
        this.ideaText = ideaText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getIdeaTitle() {
        return ideaTitle;
    }

    public void setIdeaTitle(String ideaTitle) {
        this.ideaTitle = ideaTitle;
    }





}
