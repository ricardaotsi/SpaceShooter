/*
Copyright 2015 Ricardo Arruda Sowek

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

import java.util.Random;

/**
 * Created by ti on 17/06/2016.
 */
public class Cloud {
    private Bitmap cloud;
    private float posOrigin;
    private float cloudposx;
    private float cloudposy;
    private float cloudvel;

    public Cloud(Context context, int w,int h){
        cloud = BitmapFactory.decodeResource(context.getResources(),R.drawable.cloud2);
        posOrigin=w;
        cloudposx=(new Random().nextInt(w/3*4)+w);
        cloudposy=AddCloud(h/2-cloud.getHeight()/2);
        cloudvel=w/(new Random().nextInt(20)+8);
    }

    private int AddCloud(int h){
        long seed = System.nanoTime();
        seed ^= (seed << 21);
        seed ^= (seed >>> 35);
        seed ^= (seed << 4);
        int out = (int) seed % h;
        return new Random(out < 0 ? -out : out).nextInt(h);//(out < 0 ? -out : out);

    }

    public int Mover(float fps){
        cloudposx-=cloudvel*fps;
        if (cloudposx<=-cloud.getWidth())
            return 1;
        else
            return 0;
    }

    public void Draw(Canvas canvas){
        canvas.drawBitmap(cloud, cloudposx, cloudposy, null);
    }

}
