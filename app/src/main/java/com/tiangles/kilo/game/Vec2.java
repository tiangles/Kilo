package com.tiangles.kilo.game;

public class Vec2 {
    private int x;
    private int y;

    public int x(){
        return x;
    }
    public int y(){
        return y;
    }
    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(Vec2 v){
        this.x = v.x;
        this.y = v.y;
    }

    public Vec2 sum(Vec2 v){
        return new Vec2(this.x + v.x, this.y + v.y);
    }

    public Vec2 sub(Vec2 v){
        return new Vec2(this.x - v.x, this.y - v.y);
    }


    public boolean equals(Vec2 v) {
        return this.x == v.x && this.y == v.y;
    }
}
