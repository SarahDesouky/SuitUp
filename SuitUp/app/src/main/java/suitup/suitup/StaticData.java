package suitup.suitup;

import java.util.Date;

/**
 * Created by sarah on 11/26/2015.
 */
public class StaticData {

    public static User CurrentUser = new User();
    static User f1 = new User("Reem", "reem@gmail.com", new Date("1/9/2015"));
    static User f2 = new User("Salma", "salma@gmail.com", new Date("1/9/2015"));
    static User f3 = new User("Rana", "rana@gmail.com", new Date("1/9/2015"));
    static User f4 = new User("Lobna", "lobna@gmail.com", new Date("1/9/2015"));
    static User[] friends = {f1,f2,f3,f4};

    static {
        CurrentUser.setFriends(friends);
    }

}

class User{
    String username;
    String email;
    Date dob;
    User[] friends;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    String avatar;

    User(){
    }


    public void setFriends(User[] f) {
        friends = f;
    }

    void AddFriend(User friend) {
        friends[friends.length] = friend;
    }
    User[] getAllFriends() {
    return friends;
    }

    User(String n, String e, Date d) {
        username = n;
        email = e;
        dob = d;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }


}

class Post {
    String text;
    User owner;
    User profile;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;
    public Post(){}
    public Post(String t, User o, User p, String i) {
        text =t;
        owner = o;
        profile = p;
        image = i;
    }
}
class Comment {
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text;
    User owner;
    public Comment() {}
    public Comment(String t, User o) {
        text = t;
        owner = o;
    }
}
