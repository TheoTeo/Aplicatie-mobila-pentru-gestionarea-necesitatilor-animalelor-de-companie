package com.ase.dam.licenta01;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class User implements Parcelable {

    private long id;
    private String fullname;
    private String email;
    private String phone;
    private String gender;
    private String password;
    private String profilePic;

    public User(long id, String fullname, String email, String phone, String gender, String password, String profilePic) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.profilePic = profilePic;
    }
    public User(String fullname, String email, String phone, String gender, String password, String profilePic) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.profilePic = profilePic;
    }
    public User(String email) {
        this.email = email;
    }

    protected User(Parcel in) {
        id = in.readLong();
        fullname = in.readString();
        email = in.readString();
        phone = in.readString();
        gender = in.readString();
        password = in.readString();
        profilePic = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(fullname);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(gender);
        dest.writeString(password);
        dest.writeString(profilePic);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", fullname='").append(fullname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", profilePic='").append(profilePic).append('\'');
        sb.append('}');
        return sb.toString();
    }

}

