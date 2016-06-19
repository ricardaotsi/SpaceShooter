/*
Copyright 2016 Ricardo Arruda Sowek

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.dream.arruda.spaceshooter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Arruda on 16/06/2016.
 */
public class Tela extends View implements Runnable {

    private Parallax bg;
    private Ship shp;
    private int width;
    private int height;
    private Thread game;
    private boolean isrunning;
    private Paint paint;
    //Following variables are for dynamic velocity between devices
    private long currentTime, lastFrameTime;
    private float elapsed;
    private long timer = 4;

    public Tela(Context context, int w, int h)
    {
        //Initialize variables
        super(context);
        width = w;
        height = h;
        bg = new Parallax(context,width, height);
        shp=new Ship(context,width,height);
        game = new Thread(this);
        isrunning = false;
        paint = new Paint();
        paint.setTextSize(w/3);
        currentTime = System.currentTimeMillis();
        lastFrameTime = System.currentTimeMillis();
        elapsed=0;
    }

    @Override
    public void run(){
        while (isrunning) {
            //if(timer==0) {
                currentTime = System.currentTimeMillis();

                bg.Mover(elapsed);

                elapsed = (System.currentTimeMillis() - lastFrameTime) * .001f;//convert ms to seconds
                lastFrameTime = currentTime;
            //}
        }
    }

    public void resume(){
        isrunning = true;
        game = new Thread(this);
        game.start();
        /*new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer = millisUntilFinished / 1000;
            }
            public void onFinish() {
                timer =0;
            }
        }.start();*/
    }

    public void stopThread(){
        isrunning = false;
        game.interrupt();
        game = null;
    }

    //single touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                if(event.getX()<=shp.finger.right)
                    shp.Mover(event.getY());
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(isrunning) {

        }
        bg.Draw(canvas);
        shp.Draw(canvas);
       // if(timer>0)
        //    canvas.drawText(String.valueOf(timer),width/2-width/12,height/2-height/10,paint);
        invalidate();
    }
}
