package com.tiangles.kilo.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Game {
    private GameMap mMap = new GameMap();
    private Map<Vec2, Vector> mCellHeaders = new HashMap<>();

    public Game(){
        initializeMap();
    }

    public void updateMap(Vec2 dir){

        // find headers based on move direction
        Vector<Vec2> lineHeaders = null;
        for(Map.Entry<Vec2, Vector> e: mCellHeaders.entrySet()) {
            if(e.getKey().equals(dir)){
                lineHeaders = e.getValue();
                break;
            }
        }

        if(lineHeaders == null) {
            return;
        }

        boolean fill = false;
        for(Vec2 header: lineHeaders) {
            fill = moveCellInLine(header, dir) || fill;
            //we don't make recursive merge
            fill = mergeCellInLine(header, dir) || fill;
            fill = moveCellInLine(header, dir) || fill;
        }

        if(fill) {
            mMap.fillOneSlot();
        }
    }

    public int getValue(int x, int y) {
        return mMap.get(new Vec2(x+1, y+1));
    }

    private boolean moveCellInLine(Vec2 header, Vec2 dir) {
        boolean result = false;
        while (true) {
            boolean moved = false;
            Vec2 v = header;
            for(int j=0; j<3; ++j){
                Vec2 t = v.sub(dir);
                int vv =  mMap.get(v);
                int vt = mMap.get(t);
                if(vv == 0 && vt != 0) {
                    mMap.set(v, vt);
                    mMap.set(t, 0);
                    result = true;
                    moved = true;
                }
                v = t;
            }
            if(!moved) {
                break;
            }
        }
        return result;
    }

    private boolean mergeCellInLine(Vec2 header, Vec2 dir){
        boolean result = false;
        Vec2 v = header;
        for (int j = 0; j < 3; ++j) {
            Vec2 t = v.sub(dir);
            int vv = mMap.get(v);
            int vt = mMap.get(t);
            if (vv == vt && vt != 0) {
                mMap.set(v, vv + vt);
                mMap.set(t, 0);
                result = true;
            }
            v = t;
        }
        return result;
    }

    private void initializeMap(){
        Vector<Vec2> vecRight = new Vector<>();
        vecRight.add(new Vec2(4, 1));
        vecRight.add(new Vec2(4, 2));
        vecRight.add(new Vec2(4, 3));
        vecRight.add(new Vec2(4, 4));

        Vector<Vec2> vecLeft = new Vector<>();
        vecLeft.add(new Vec2(1, 1));
        vecLeft.add(new Vec2(1, 2));
        vecLeft.add(new Vec2(1, 3));
        vecLeft.add(new Vec2(1, 4));

        Vector<Vec2> vecUp = new Vector<>();
        vecUp.add(new Vec2(1, 1));
        vecUp.add(new Vec2(2, 1));
        vecUp.add(new Vec2(3, 1));
        vecUp.add(new Vec2(4, 1));

        Vector<Vec2> vecDown = new Vector<>();
        vecDown.add(new Vec2(1, 4));
        vecDown.add(new Vec2(2, 4));
        vecDown.add(new Vec2(3, 4));
        vecDown.add(new Vec2(4, 4));

        mCellHeaders.put(new Vec2(1, 0), vecRight);
        mCellHeaders.put(new Vec2(-1, 0), vecLeft);
        mCellHeaders.put(new Vec2(0, -1), vecUp);
        mCellHeaders.put(new Vec2(0, 1), vecDown);

        for(int i=0; i<4; ++i) {
            mMap.fillOneSlot();
        }
    }
}
