package com.danbai.ys.entity;

/**
 * @author danbai
 * @date 2019-10-18 12:30
 */
public class Acces {
    private String name;
    private int count;

    public Acces(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
