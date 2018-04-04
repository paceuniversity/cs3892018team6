package com.olvynchuru.thepeoplemap.thepeoplemap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetData extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.olvynchuru.thepeoplemap.thepeoplemap";

    private static final String TAG = SetData.class.getSimpleName();
    FirebaseDatabase mFirebaseInstance;
    DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_data);
        mFirebaseInstance = mFirebaseInstance.getInstance();
        mFirebaseInstance.getReference("country_count").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Country count updated.");
                Country.numCountries = dataSnapshot.getValue(Integer.class);
            }
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"Failed to read data.", databaseError.toException());
            }
        });
    }
    public void uploadData(View view) {
        EditText nameText = (EditText) findViewById(R.id.II_country_name_enter);
        String countryName = nameText.getText().toString();
        EditText populationText = (EditText) findViewById(R.id.II_country_population_enter);
        String countryPopulation = populationText.getText().toString();
        if (!countryName.equals("")) {
            Country country;
            mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
            if(countryPopulation.equals("")) {
                country = new Country(countryName);
            }
            else {
                country = new Country(countryName, Integer.parseInt(countryPopulation));

            }
            mFirebaseDatabase.child("country_count").setValue(Country.numCountries);
            mFirebaseDatabase.child("country").child(countryName).setValue(country);
        }
    }

}
