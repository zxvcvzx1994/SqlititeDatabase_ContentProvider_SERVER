package com.example.kh.myapplication.Module;

/**
 * Created by kh on 7/4/2017.
 */

public class HOCSINH {
    private int id;
    private String name;
    private String lop;
    public HOCSINH(int id, String name, String lop){
        this.id = id;
        this.name = name;
        this.lop = lop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }
}
