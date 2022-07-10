package com.a7codes.menu.ImportClasses;

public class Rec1Class {
    int _id;
    String Img;
    String Title;

    public Rec1Class(int _id, String img, String title) {
        this._id = _id;
        Img = img;
        Title = title;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
