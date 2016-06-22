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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import java.util.LinkedList;

/**
 * Created by ricardo on 6/19/16.
 */
public class Ship {
    public Bitmap ship;
    private Bitmap laser;
    private int posOrigin;
    public int shipposx;
    public int shipposy;
    private Paint p;
    public Rect finger;
    public LinkedList<Laser> laserlist;
    private int width;

    public Ship( Bitmap shipy,  Bitmap lasery, int w, int h){
        this.ship= shipy;
        this.laser=lasery;
        posOrigin=h/2-ship.getHeight()/2;
        shipposy = posOrigin;
        shipposx=w/25*2;
        p=new Paint();
        p.setColor(Color.GRAY);
        p.setAlpha(80);
        finger=new Rect(0,0,w/25*2,h);
        laserlist = new LinkedList<Laser>();
        width=w;
        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                laserlist.addLast(new Laser(laser, shipposx, shipposy+ship.getHeight()/3,width));
                handler.postDelayed(this, 200);
            }
        }, 200);*/
    }

    public void MoverLaser(float fps){
        for(int i=0;i<=laserlist.size()-1;i++){
            if(laserlist.get(i).Mover(fps)) {
                laserlist.set(i,null);
                laserlist.remove(i);
            }
        }
    }

    public void AddLaser(){
        laserlist.addLast(new Laser(laser, shipposx, shipposy+ship.getHeight()/3,width));
    }

    public void Mover(float y){
        shipposy=(int)y-ship.getHeight()/2;
    }

    public void Draw(Canvas canvas){
        canvas.drawRect(finger,p);
        if(!laserlist.isEmpty()){
            for(int i=0; i<=laserlist.size()-1; i++)
                laserlist.get(i).Draw(canvas);
        }
        canvas.drawBitmap(ship, shipposx, shipposy, null);
    }
}
