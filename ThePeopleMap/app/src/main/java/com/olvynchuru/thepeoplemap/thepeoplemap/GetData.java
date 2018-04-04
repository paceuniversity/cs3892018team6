package com.olvynchuru.thepeoplemap.thepeoplemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetData extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
    }
    public void retrieveData(View view) {
        EditText editText = (EditText) findViewById(R.id.III_country_name_enter);
        String countryName = editText.getText().toString();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            mDatabaseReference.child("country").child(countryName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String population = ((Integer) dataSnapshot.getValue(Country.class).population).toString();
                    TextView label = findViewById(R.id.III_population_label);
                    label.setVisibility(View.VISIBLE);
                    TextView display = findViewById(R.id.III_display_population);
                    display.setText(population);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}
