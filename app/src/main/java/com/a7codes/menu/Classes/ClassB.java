package com.a7codes.menu.Classes;

public class ClassB {

    //Class B Item
    int _id;
    String title;
    int parent;
    String img;

    public ClassB(int _id, String title, int parent, String img) {
        this._id = _id;
        this.title = title;
        this.parent = parent;
        this.img = img;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
