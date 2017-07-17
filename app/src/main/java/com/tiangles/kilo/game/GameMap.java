package com.tiangles.kilo.game;

import java.util.Vector;

public class GameMap {
    private int[] values = new int[36];

    public GameMap() {
        reset();
    }

    public void reset() {
        for(int i=0; i<36; ++i){
            values[i] = -1;
        }

        for(int x = 1; x<5; ++x) {
            for (int y=1; y<5; ++y){
                values[pos(x, y)] = 0;
            }
        }
    }

    public boolean fillOneSlot(){
        Vec2 pos = getEmptySlot();
        if(pos != null) {
            int val = 2;
            double v = Math.random();
            if(v<0.5) {
                val = 4;
            }
            set(pos, val);
            return true;
        }
        return false;
    }

    public int get(Vec2 p){
        return get(p.x(), p.y());
    }

    public int get(int x, int y) {
        return values[pos(x, y)];
    }

    public void set(Vec2 p, int v){
        set(p.x(), p.y(), v);
    }

    public void set(int x, int y, int v) {
        values[pos(x, y)] = v;
    }

    private int pos(Vec2 p){
        return pos(p.x(), p.y());
    }

    private int pos(int x, int y){
        return x + y*6;
    }

    private Vec2 getEmptySlot(){
        Vector<Vec2> slots = new Vector<>(16);
        for(int x = 1; x<5; ++x) {
            for (int y = 1; y < 5; ++y) {
                if(get(x, y) == 0) {
                    slots.add(new Vec2(x, y));
                }
            }
        }
        int size = slots.size();
        if(size>0) {
            int index = (int)(Math.random()*size);
            return slots.get(index);
        }

        return null;
    }

}
