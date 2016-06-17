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

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by ti on 16/06/2016.
 */
public class MainActivity extends Activity {

    private boolean doubleBackToExitPressedOnce = false;
    private Menu menu;
    private Intent intent;
    private DisplayMetrics metrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set window properties
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //get screen metrics for dynamic graphics
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        intent = new Intent(this,GameActivity.class);
        //create menu class
        menu = new Menu(this, metrics.widthPixels,metrics.heightPixels);
        setContentView(menu);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if((event.getX()> menu.play.left && event.getX()<menu.play.right)&&(event.getY()>menu.play.top && event.getY()<menu.play.bottom))
                    menu.playpaint.setColor(Color.GRAY);
                break;
            case MotionEvent.ACTION_UP:
                if((event.getX()> menu.play.left && event.getX()<menu.play.right)&&(event.getY()>menu.play.top && event.getY()<menu.play.bottom))
                    startActivity(intent);
                menu.playpaint.setColor(Color.BLACK);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}