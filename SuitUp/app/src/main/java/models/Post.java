package models;

public class Post {
    String text;
    String imageURL;
    User owner;
    User profile;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getProfile() {
        return profile;
    }

    public void setProfile(User profile) {
        this.profile = profile;
    }
}
