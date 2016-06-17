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
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Arruda on 16/06/2016.
 */
public class GameActivity extends Activity {
    private Tela t;
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
        //Create our game class
        t = new Tela(this,metrics.widthPixels,metrics.heightPixels);
        setContentView(t);
    }

    @Override
    protected void onResume(){
        super.onResume();
        t.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        t.stopThread();
    }
}
