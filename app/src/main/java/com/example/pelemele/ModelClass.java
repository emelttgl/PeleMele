package com.example.pelemele;

public class ModelClass {
    protected  int imageview1;
    protected String textview1;
    protected String textview2;

    ModelClass(int imageview1,String textview1, String textview2){
        this.imageview1=imageview1;
        this.textview1=textview1;
        this.textview2=textview2;
    }

    public int getImageview1() {
        return imageview1;
    }

    public String getTextview1() {
        return textview1;
    }

    public String getTextview2() {
        return textview2;
    }
}
