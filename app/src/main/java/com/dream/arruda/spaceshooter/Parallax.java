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
import java.util.LinkedList;

/**
 * Created by ti on 17/06/2016.
 */
public class Parallax {
    private Bitmap sky;
    private Bitmap land;
    private LinkedList<Cloud> cloudList;
    private Context cont;
    private int width;
    private int height;
    private float posOrigin1;
    private float posOrigin2;
    private float skypos1;
    private float skypos2;
    private float landpos1;
    private float landpos2;
    private float skyvel;
    private float landvel;

    public Parallax(Context context, int w, int h){
        sky = BitmapFactory.decodeResource(context.getResources(),R.drawable.sky);
        land = BitmapFactory.decodeResource(context.getResources(),R.drawable.land);
        cloudList = new LinkedList<Cloud>();
        cloudList.add(new Cloud(context,w,h));
        posOrigin1=0;
        posOrigin2=w-1;
        skypos1 =posOrigin1;
        skypos2 =posOrigin2;
        landpos1=posOrigin1;
        landpos2=posOrigin2;
        skyvel = w/8;
        landvel = w/5;
        cont=context;
        width=w;
        height=h;
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
        skypos1-=skyvel*fps;
        skypos2-=skyvel*fps;
        landpos1-=landvel*fps;
        landpos2-=landvel*fps;
        if(cloudList.size()<=8)
            cloudList.addLast(new Cloud(cont, width, height));
        if(!cloudList.isEmpty()){
            for(int i=1; i<cloudList.size(); i++) {
                if(cloudList.get(i).Mover(fps)==1)
                    cloudList.remove(i);
            }
        }
    }

    public void Draw(Canvas canvas){
        canvas.drawBitmap(sky, skypos1, 0, null);
        canvas.drawBitmap(sky, skypos2, 0, null);
        canvas.drawBitmap(land, landpos1, 0, null);
        canvas.drawBitmap(land, landpos2, 0, null);
        if(!cloudList.isEmpty()) {
            for(int i=0; i<cloudList.size(); i++){
                cloudList.get(i).Draw(canvas);
            }
        }

    }
}
