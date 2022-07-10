package com.a7codes.menu.ImportClasses;

public class Rec2Class {

    Integer _id        ;
    String  arTitle    ;
    String  enTitle    ;
    String  img        ;
    String  description;
    String  price      ;
    Integer parent     ;

    public Rec2Class(Integer _id, String arTitle, String enTitle, String img, String description, String price, Integer parent) {
        this._id = _id;
        this.arTitle = arTitle;
        this.enTitle = enTitle;
        this.img = img;
        this.description = description;
        this.price = price;
        this.parent = parent;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getArTitle() {
        return arTitle;
    }

    public void setArTitle(String arTitle) {
        this.arTitle = arTitle;
    }

    public String getEnTitle() {
        return enTitle;
    }

    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

}
