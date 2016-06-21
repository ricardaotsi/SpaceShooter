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
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by ti on 21/06/2016.
 */
public class Enemy {
    public Bitmap enemy;		// the animation sequence
    private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
    private int frameNr;		// number of frames in animation
    private int currentFrame;	// the current frame of animation
    private long frameTicker;	// the time of the last frame update
    private int framePeriod;	// milliseconds between each frame (1000/fps) of animation
    public int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;	// the height of the sprite
    public float posOrigin1;				// the X coordinate of the object (top left of the image)
    public float posOrigin2;				// the Y coordinate of the object (top left of the image)
    private float enemyvel;  //velocity

    public Enemy(Context cont, int w, int h){
        enemy= BitmapFactory.decodeResource(cont.getResources(),R.drawable.enemy);
        posOrigin1=new Random(Rand()).nextInt(w/2*3)+w;
        posOrigin2=new Random(Rand()).nextInt(h-enemy.getHeight());
        currentFrame = 0;
        frameNr = 4;
        spriteWidth = enemy.getWidth() / frameNr;
        spriteHeight = enemy.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / frameNr;
        frameTicker = 0l;
        enemyvel = w/(new Random(Rand()).nextInt(16)+8);
    }

    private int Rand(){
        long seed = System.nanoTime();
        seed ^= (seed << 21);
        seed ^= (seed >>> 35);
        seed ^= (seed << 4);
        int out = (int) seed;
        return out;
    }

    // the update method
    public boolean Mover(long gameTime, float fps) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            // increment the frame
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }
        }
        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
        //update position
        posOrigin1-=enemyvel*fps;
        if(posOrigin1+spriteWidth<=0)
            return true;
        else
            return false;
    }

    public void Draw(Canvas canvas) {
        // where to draw the sprite
        Rect destRect = new Rect((int)posOrigin1, (int)posOrigin2, (int)posOrigin1 + spriteWidth, (int)posOrigin2 + spriteHeight);
        canvas.drawBitmap(enemy, sourceRect, destRect, null);
    }
}
