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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by ti on 16/06/2016.
 */
public class Menu extends View
{
    public Rect play;
    private Vector2D playtext;
    public Paint playpaint;
    private Paint playtextpaint;

    public Menu(Context context, int w, int h)
    {
        super(context);
        //set button position (middle of the screen)
        play = new Rect(w/2-w/5, h/2-h/8, w/2+w/5, h/2+h/8);
        playpaint = new Paint();
        playtext = new Vector2D(w/2-w/13, h/2+h/20);
        playtextpaint = new Paint();
        playtextpaint.setColor(Color.WHITE);
        playtextpaint.setTextSize(w/11);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawRect(play,playpaint);
        canvas.drawText("Play", (float)playtext.getX(), (float)playtext.getY(),playtextpaint);
        invalidate();
    }
}