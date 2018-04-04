package com.olvynchuru.thepeoplemap.thepeoplemap;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.PathParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Vector;


public class MapDisplay extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display);

        View myView = findViewById(R.id.imageButton);
        myView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                ImageButton img = findViewById(R.id.imageButton);
                img.setDrawingCacheEnabled(true);
                Bitmap imgbmp = Bitmap.createBitmap(img.getDrawingCache());
                img.setDrawingCacheEnabled(false);

                int pixel = imgbmp.getPixel(x, y);
                Log.d("thing", "" + pixel);
                if (pixel == 0xFFAA0000) {
                    Log.d("Selection", "China selected.");
                }
                else if (pixel == 0xFFD4FF2A) {
                    Log.d("Selection", "India selected.");
                }
                return true;
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {

        // TODO Auto-generated method stub




        return true;
    }
}