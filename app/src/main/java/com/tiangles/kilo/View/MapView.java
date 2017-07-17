package com.tiangles.kilo.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import com.tiangles.kilo.game.Game;
import com.tiangles.kilo.game.Vec2;

public class MapView extends View implements View.OnTouchListener{
    private int offsetX = 100;
    private int offsetY = 500;
    private int cellSize = 200;
    private static int GRID_LINE_COLOR = Color.rgb(193, 225, 249);
    private static int GRID_LINE_WIDTH = 8;
    private static int GRID_TEXT_COLOR = Color.rgb(223, 173, 0);
    private static int GRID_TEXT_SIZE = 80;

    private Game mGame;
    private GestureDetector mDetector;

    public MapView(Context context) {
        super(context);
        init(context);
    }
    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setBackgroundColor(Color.rgb(49, 125, 183));
        super.setOnTouchListener(this);
        super.setClickable(true);
        super.setLongClickable(true);
        super.setFocusable(true);

        mDetector = new GestureDetector(context, new SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2,
                                   float velocityX, float velocityY) {
                if(Math.abs(velocityX) > Math.abs(velocityY)) {
                    if(velocityX>0) {
                        mGame.updateMap(new Vec2(1, 0));
                    } else {
                        mGame.updateMap(new Vec2(-1, 0));
                    }
                } else {
                    if(velocityY>0) {
                        mGame.updateMap(new Vec2(0, 1));
                    } else {
                        mGame.updateMap(new Vec2(0, -1));
                    }
                }
                invalidate();
                return true;
            }

            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }
        });

        mGame = new Game();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        int minW = Math.min(w, h);
        cellSize = minW/5;
        offsetX = (w-cellSize*4)/2;
        offsetY = (h-cellSize*4)/2;
    }

    private void drawGrid(Canvas canvas){
        Paint  p =new Paint();
        p.setColor(GRID_LINE_COLOR);
        p.setStrokeWidth(GRID_LINE_WIDTH);

        for(int x=0; x<5; ++x) {
            canvas.drawLine(offsetX + x*cellSize, offsetY, offsetX + x*cellSize, offsetY+cellSize*4, p);
        }
        for(int y=0; y<5; ++y) {
            canvas.drawLine(offsetX, offsetY+y*cellSize, offsetX + 4*cellSize, offsetY+y*cellSize, p);
        }

        p.setColor(GRID_TEXT_COLOR);
        p.setTextSize(GRID_TEXT_SIZE);
        for(int x=0; x<4; ++x) {
            for (int y=0; y<4; ++y){
                int val = mGame.getValue(x, y);
                drawItem(canvas, p, x, y, val);
            }
        }
    }

    private void drawItem(Canvas canvas, Paint  p, int x, int y, int value) {
        if(value == 0) {
            return;
        }
        String str = String.format("%d", value);

        Rect bounds = new Rect();
        p.getTextBounds(str, 0, str.length(), bounds);
        Paint.FontMetricsInt fontMetrics = p.getFontMetricsInt();
        int baseline = (cellSize - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(str,
                x*cellSize+offsetX + cellSize/2 - bounds.width() / 2,
                y*cellSize+offsetY + baseline,
                p);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }
}


