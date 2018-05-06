package com.olvynchuru.thepeoplemap.thepeoplemap;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MapDisplay extends AppCompatActivity {
    private static final String TAG = MapDisplay.class.getSimpleName();

    private static int COMMADISTANCE = 3;
    private DatabaseReference mDatabaseReference;
    private int pixel = 0;
    private float xDown = 0, xMovePrev = 0, xMove = 0, xUp = 0, yDown = 0, yMovePrev = 0, yMove = 0, yUp = 0;

    private boolean disableMove = false;

    private float defaultScale, maxScale;

    private ScaleGestureDetector mScaleDetector;
    private float previousSpan = 0, currentSpan = 0, spanDifference = 0;

    public String intToStringCommas(int num, int commaDistance) {
        String str = "";
        int placeCount = 0;
        if (num > 0)
            while (num > 0) {
                str = (num % 10) + str;
                num /= 10;
                placeCount++;
                if (placeCount >= commaDistance && num > 0) {
                    str = ',' + str;
                    placeCount = 0;
                }
            }
        else if (num == 0) {
            str = "0";
        }
        else {
            Log.d(TAG, "Warning: intToStringCommas does not work correctly with negative numbers.");
            str = ((Integer) num).toString();
        }

        return str;
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display);

        ImageView myView = findViewById(R.id.map_view);
        defaultScale = myView.getScaleX();
        maxScale = defaultScale * 5.f;

        mScaleDetector = new ScaleGestureDetector(myView.getContext(), new ScaleListener());
        myView.setOnTouchListener(new View.OnTouchListener() {


            public boolean onTouch(View v, MotionEvent event) {

                ImageView img = findViewById(R.id.map_view);

                int action = MotionEventCompat.getActionMasked(event);



                if (action == MotionEvent.ACTION_UP || event.getPointerCount() < 2) {
                    previousSpan = 0;
                    currentSpan = 0;
                }
                if (event.getPointerCount() >= 2) {
                    mScaleDetector.onTouchEvent(event);
                    currentSpan = mScaleDetector.getCurrentSpan();


                    if (previousSpan == 0)
                        previousSpan = currentSpan;

                    spanDifference = currentSpan / previousSpan;

                    Log.d(TAG, currentSpan + " / " + previousSpan + " = " + spanDifference);
                    float newScale = img.getScaleX() * spanDifference;
                    if (newScale < defaultScale) {
                        img.setScaleX(defaultScale);
                        img.setScaleY(defaultScale);
                    }
                    else if (newScale > maxScale) {
                        img.setScaleX(maxScale);
                        img.setScaleY(maxScale);
                    }
                    else {
                        img.setScaleX(newScale);
                        img.setScaleY(newScale);
                    }


                    previousSpan = currentSpan / spanDifference;

                    disableMove = true;
                }
                else if (action == MotionEvent.ACTION_DOWN) {

                    xDown = event.getX();
                    yDown = event.getY();

                }
                else if (action == MotionEvent.ACTION_MOVE && !disableMove) {
                    if (xMovePrev == 0)
                        xMovePrev = xDown;
                    if (yMovePrev == 0)
                        yMovePrev = yDown;
                        xMove = event.getX();
                        yMove = event.getY();
                    if (Math.hypot((double) (xMove - xDown), (double) (yMove - yDown)) > 10) {
                        img.scrollBy((int) (xMovePrev - xMove), (int) (yMovePrev - yMove));
                    }
                    xMovePrev = xMove;
                    yMovePrev = yMove;
                }
                else if (action == MotionEvent.ACTION_UP) {
                    disableMove = false;
                    xMove = 0;
                    xMovePrev = 0;
                    yMove = 0;
                    yMovePrev = 0;
                        xUp = event.getX();
                        yUp = event.getY();



                    if (xUp >= 0 && yUp >= 0) {
                        img.setDrawingCacheEnabled(true);
                        Bitmap imgbmp = Bitmap.createBitmap(img.getDrawingCache());
                        img.setDrawingCacheEnabled(false);

                        if (pixel != imgbmp.getPixel((int) xUp, (int) yUp)) {
                            pixel = imgbmp.getPixel((int) xUp, (int) yUp);

                            String pixel_red = ((Integer) Color.red(pixel)).toString();
                            String pixel_green = ((Integer) Color.green(pixel)).toString();
                            String pixel_blue = ((Integer) Color.blue(pixel)).toString();
                            mDatabaseReference = FirebaseDatabase.getInstance().getReference();

                            mDatabaseReference.child("country").child(pixel_red).child(pixel_green).child(pixel_blue).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        Country country = dataSnapshot.getValue(Country.class);
                                        String name = getResources().getText(R.string.name_label) + " " + country.getName();
                                        String population = getResources().getText(R.string.population_label) + " " + intToStringCommas(country.getPopulation(), COMMADISTANCE);
                                   //     String info = name + "\n" + population;
                                        TextView view_name = findViewById(R.id.map_view_name);
                                        TextView view_population = findViewById(R.id.map_view_population);
                                   /*     Button view_info = findViewById(R.id.map_country_info_window);
                                        if (population.length() > name.length())
                                            view_info.setLayoutParams(new ViewGroup.LayoutParams(population.length() * 4, 65));
                                        else
                                            view_info.setLayoutParams(new ViewGroup.LayoutParams(name.length() * 4, 65));

                                        view_info.setText(info); */
                                        view_name.setText(name);
                                        view_population.setText(population);


                                   //     view_info.setVisibility(View.VISIBLE);

                                        Log.d(TAG, "Country: " + country.getName());
                                        Log.d(TAG, "Population: " + country.getPopulation());
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.d(TAG, "Error: No country selected.");
                                }
                            });

                        }
                    }
                }
                return true;
            }
        });


    }
    public void onBackPressed() {
    }
    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
        /*    mScaleFactor *= detector.getScaleFactor();


            mScaleFactor = Math.max(defaultScale, Math.min(mScaleFactor, maxScale));

*/
            return true;
        }
    }
}