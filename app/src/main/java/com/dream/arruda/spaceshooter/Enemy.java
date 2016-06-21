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
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by ti on 21/06/2016.
 */
public class Enemy {
    private Bitmap enemy;		// the animation sequence
    private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
    private int frameNr;		// number of frames in animation
    private int currentFrame;	// the current frame
    private long frameTicker;	// the time of the last frame update
    private int framePeriod;	// milliseconds between each frame (1000/fps)
    private int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;	// the height of the sprite
    private int posOrigin1;				// the X coordinate of the object (top left of the image)
    private int posOrigin2;				// the Y coordinate of the object (top left of the image)

    public Enemy(Context cont){
        enemy= BitmapFactory.decodeResource(cont.getResources(),R.drawable.enemy);
        posOrigin1=300;
        posOrigin2=300;
        currentFrame = 0;
        frameNr = 4;
        spriteWidth = enemy.getWidth() / frameNr;
        spriteHeight = enemy.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / frameNr;
        frameTicker = 0l;
    }

    // the update method for Elaine
    public void update(long gameTime) {
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
    }

    // the draw method which draws the corresponding frame
    public void draw(Canvas canvas) {
        // where to draw the sprite
        Rect destRect = new Rect(posOrigin1, posOrigin2, posOrigin1 + spriteWidth, posOrigin2 + spriteHeight);
        canvas.drawBitmap(enemy, sourceRect, destRect, null);
        //canvas.drawBitmap(bitmap, 20, 150, null);
       // Paint paint = new Paint();
       // paint.setARGB(50, 0, 255, 0);
       // canvas.drawRect(20 + (currentFrame * destRect.width()), 150, 20 + (currentFrame * destRect.width()) + destRect.width(), 150 + destRect.height(),  paint);
    }
}
