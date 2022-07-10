package com.a7codes.menu.Classes;

public class ClassA {

    //Class A Item
    int  _id;
    String  title;
    int  parent;

    public ClassA(int _id, String title, int parent) {
        this._id = _id;
        this.title = title;
        this.parent = parent;
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
}
