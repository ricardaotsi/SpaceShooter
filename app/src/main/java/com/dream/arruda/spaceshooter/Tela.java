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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;

/**
 * Created by Arruda on 16/06/2016.
 */
public class Tela extends View implements Runnable {

    private Parallax bg;
    private Ship shp;
    private LinkedList<Enemy> enemylist;
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
        enemylist=new LinkedList<Enemy>();
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
                shp.MoverLaser(elapsed);
                if(enemylist.size()<=8)
                    enemylist.addLast(new Enemy(this.getContext(),width,height));
                for(int i=0; i<=enemylist.size()-1;i++){
                    if(enemylist.get(i).Mover(currentTime,elapsed))
                        enemylist.remove(i);
                }
                Collision();

                elapsed = (System.currentTimeMillis() - lastFrameTime) * .001f;//convert ms to seconds
                lastFrameTime = currentTime;
            //}
        }
    }

    private void Collision(){
        for(int i=0; i<=enemylist.size()-1;i++){
            if(CollisionBounds(shp.ship,shp.shipposx,shp.shipposy,
                    enemylist.get(i).enemy,(int)enemylist.get(i).posOrigin1,(int)enemylist.get(i).posOrigin2, enemylist.get(i).spriteWidth))
                enemylist.remove(i);
            for(int j=0; j<=shp.laserlist.size()-1;j++){
                if (CollisionBounds(shp.laserlist.get(j).laser,shp.laserlist.get(j).laserpos,shp.laserlist.get(j).posOrigin2,
                        enemylist.get(i).enemy,(int)enemylist.get(i).posOrigin1,(int)enemylist.get(i).posOrigin2, enemylist.get(i).spriteWidth)){
                    shp.laserlist.remove(j);
                    enemylist.remove(i);
                }
            }
        }
    }

    public boolean CollisionBounds(Bitmap bitmap1, int x1, int y1, Bitmap bitmap2, int x2, int y2, int bm2width) {
        Rect bounds1 = new Rect(x1, y1, x1+bitmap1.getWidth(), y1+bitmap1.getHeight());
        Rect bounds2 = new Rect(x2, y2, x2+bm2width, y2+bitmap2.getHeight());
        if (Rect.intersects(bounds1, bounds2)) {
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int bitmap1Pixel = bitmap1.getPixel(i-x1, j-y1);
                    int bitmap2Pixel = bitmap2.getPixel(i-x2, j-y2);
                    if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = (int) Math.max(rect1.left, rect2.left);
        int top = (int) Math.max(rect1.top, rect2.top);
        int right = (int) Math.min(rect1.right, rect2.right);
        int bottom = (int) Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    private boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
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
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = event.getActionIndex();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if(event.getX()<=shp.finger.right)
                    shp.Mover(event.getY());
                if(event.getPointerCount()>1){
                    if(event.getX(1)<=shp.finger.right)
                        shp.Mover(event.getY(1));
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(event.getX()>=shp.finger.right)
                    shp.AddLaser();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getX(pointerIndex) >= shp.finger.right)
                    shp.AddLaser();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(isrunning) {
            bg.Draw(canvas);
            shp.Draw(canvas);
            for(int i=0; i<=enemylist.size()-1;i++)
                enemylist.get(i).Draw(canvas);
        }
       // if(timer>0)
        //    canvas.drawText(String.valueOf(timer),width/2-width/12,height/2-height/10,paint);
        invalidate();
    }
}
