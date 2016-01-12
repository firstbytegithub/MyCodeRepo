package com.example.chengqi.mycoderepo.expert;

/**
 * Created by archermind on 12/23/15.
 */
public class GameData {
    private float radius1;
    private final int incre1 = 1;
    private float radius2;
    private final int incre2 = 2;

    public GameData() {
        radius1 = 0;
        radius2 = 0;
    }

    public int getIncre1() {
        return incre1;
    }

    public int getIncre2() {
        return incre2;
    }

    public float getRadius1() {
        return radius1;
    }

    public float getRadius2() {
        return radius2;
    }

    public void setRadius1(float radius1) {
        this.radius1 = radius1;
        if (this.radius1 > 100) {
            this.radius1 = 0;
        }
    }

    public void setRadius2(float radius2) {
        this.radius2 = radius2;
        if (this.radius2 > 100) {
            this.radius2 = 0;
        }
    }
}
