package com.a7codes.menu.Classes;

public class ClassC {

    int _id;
    String title;
    int parent;
    String img;
    String DESC;

    public ClassC(int _id, String title, int parent, String img, String DESC) {
        this._id = _id;
        this.title = title;
        this.parent = parent;
        this.img = img;
        this.DESC = DESC;
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

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
    }
}
