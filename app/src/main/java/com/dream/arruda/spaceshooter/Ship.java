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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by ricardo on 6/19/16.
 */
public class Ship {
    private Bitmap ship;
    private int posOrigin;
    private int shipposx;
    private int shipposy;
    private Paint p;
    public Rect finger;

    public Ship(Context context, int w, int h){
        ship= BitmapFactory.decodeResource(context.getResources(),R.drawable.spaceship);
        posOrigin=h/2-ship.getHeight()/2;
        shipposy = posOrigin;
        shipposx=w/15*2;
        p=new Paint();
        p.setColor(Color.GRAY);
        p.setAlpha(80);
        finger=new Rect(0,0,w/15*2,h);
    }

    public void Mover(float y){
        shipposy=(int)y-ship.getHeight()/2;
    }

    public void Draw(Canvas canvas){
        canvas.drawRect(finger,p);
        canvas.drawBitmap(ship, shipposx, shipposy, null);
    }
}
