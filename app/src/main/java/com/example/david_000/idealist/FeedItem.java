package com.example.david_000.idealist;

/**
 * Created by david_000 on 7/3/2015.
 */
public class FeedItem {
    private String ideaTitle, ideaCategory, ideaText, imageUrl, postingDate;

    public FeedItem(){

    }


    public FeedItem(String ideaTitle, String ideaCategory, String ideaText){
        super();
        this.ideaTitle = ideaTitle;
        this.ideaCategory = ideaCategory;
        this.ideaText = ideaText;
/*        this.imageUrl = imageUrl;
        this.postingDate = postingDate;*/

    }

    public String getIdeaText() {
        return ideaText;
    }

    public void setIdeaText(String ideaText) {
        this.ideaText = ideaText;
    }

    public String getIdeaCategory() {
        return ideaCategory;
    }

    public void setIdeaCategory(String ideaCategory) {
        this.ideaCategory = ideaCategory;
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
