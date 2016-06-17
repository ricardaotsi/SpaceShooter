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

/**
 * Created by ti on 17/06/2016.
 */
public class Parallax {
    public Bitmap sky;
    public Bitmap land;
    public Bitmap cloud;
    public float posOrigin1;
    public float posOrigin2;
    public float skypos1;
    public float skypos2;
    public float landpos1;
    public float landpos2;
    public float cloudpos1;
    public float cloudpos2;
    private float skyvel;
    private float landvel;
    private float cloudvel;

    public Parallax(Context context, int w){
        sky = BitmapFactory.decodeResource(context.getResources(),R.drawable.sky);
        land = BitmapFactory.decodeResource(context.getResources(),R.drawable.land);
        cloud = BitmapFactory.decodeResource(context.getResources(),R.drawable.cloud);
        posOrigin1=0;
        posOrigin2=w-1;
        skypos1 =posOrigin1;
        skypos2 =posOrigin2;
        landpos1=posOrigin1;
        landpos2=posOrigin2;
        cloudpos1=posOrigin1;
        cloudpos2=posOrigin2;
        skyvel = w/5;
        landvel = w/3;
        cloudvel= w/7;
    }

    public void Mover(float fps){
        if(skypos1 <=-posOrigin2)
            skypos1 =posOrigin2;
        if(skypos2 <=-posOrigin2)
            skypos2 = posOrigin2;
        if(landpos1<=-posOrigin2)
            landpos1=posOrigin2;
        if(landpos2<=-posOrigin2)
            landpos2=posOrigin2;
        if(cloudpos1<=-posOrigin2)
            cloudpos1=posOrigin2;
        if(cloudpos2<=-posOrigin2)
            cloudpos2=posOrigin2;
        skypos1-=skyvel*fps;
        skypos2-=skyvel*fps;
        landpos1-=landvel*fps;
        landpos2-=landvel*fps;
        cloudpos1-=cloudvel*fps;
        cloudpos2-=cloudvel*fps;
    }

    public void Draw(Canvas canvas){
        canvas.drawBitmap(sky, skypos1, 0, null);
        canvas.drawBitmap(sky, skypos2, 0, null);
        canvas.drawBitmap(land, landpos1, 0, null);
        canvas.drawBitmap(land, landpos2, 0, null);
        canvas.drawBitmap(cloud, cloudpos1, 0, null);
        canvas.drawBitmap(cloud, cloudpos2, 0, null);
    }
}
