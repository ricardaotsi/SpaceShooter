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

/**
 * Created by ti on 20/06/2016.
 */
public class Laser {
    public Bitmap laser;
    private int posOrigin1;
    public int posOrigin2;
    private int laservel;
    public int laserpos;
    private int width;

    public Laser(Context context, int x, int y, int w){
        laser = BitmapFactory.decodeResource(context.getResources(),R.drawable.laser);
        posOrigin1=x+150;
        posOrigin2=y;
        laserpos=posOrigin1;
        laservel=w;
        width=w;
    }

    public boolean Mover(float fps){
        laserpos+=laservel*fps;
        if(laserpos>=width)
            return true;
        else
            return false;
    }

    public void Draw(Canvas canvas){
        canvas.drawBitmap(laser, laserpos, posOrigin2, null);
    }
}
