package com.olvynchuru.thepeoplemap.thepeoplemap;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
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
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    int pixel = 0;


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

        View myView = findViewById(R.id.map_view);
        myView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int x = 0, y = 0;
                try {
                    x = (int) event.getX();
                    y = (int) event.getY();
                } catch (IllegalArgumentException outOfBounds) {
                    Log.d(TAG, "Out of bounds");
                }

                ImageView img = findViewById(R.id.map_view);
                img.setDrawingCacheEnabled(true);
                Bitmap imgbmp = Bitmap.createBitmap(img.getDrawingCache());
                img.setDrawingCacheEnabled(false);
                if (x >= 0 && y >= 0) {
                    if (pixel != imgbmp.getPixel(x, y)) {
                        pixel = imgbmp.getPixel(x, y);

                        String pixel_red = ((Integer) Color.red(pixel)).toString();
                        String pixel_green = ((Integer) Color.green(pixel)).toString();
                        String pixel_blue = ((Integer) Color.blue(pixel)).toString();
                        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

                        mDatabaseReference.child("country").child(pixel_red).child(pixel_green).child(pixel_blue).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    Country country = dataSnapshot.getValue(Country.class);
                                    String name = getResources().getText(R.string.name_label) + " " + country.name;
                                    String population = getResources().getText(R.string.population_label) + " " + intToStringCommas(country.population, COMMADISTANCE);
                                    TextView view_name = findViewById(R.id.map_view_name);
                                    TextView view_population = findViewById(R.id.map_view_population);
                                    view_name.setText(name);
                                    view_population.setText(population);

                                    Log.d(TAG, "Country: " + country.name);
                                    Log.d(TAG, "Population: " + country.population);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d(TAG, "Error: No country selected.");
                            }
                        });
                    }
                }
                return true;
            }
        });
    }

}