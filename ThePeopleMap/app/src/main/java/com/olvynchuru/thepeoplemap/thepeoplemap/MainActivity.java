package com.olvynchuru.thepeoplemap.thepeoplemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    FirebaseDatabase mFirebaseInstance;
    DatabaseReference mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("thing", "gjgil");
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
        mFirebaseInstance = mFirebaseInstance.getInstance();
        final Toolbar toolbar = findViewById(R.id.toolbar);

        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "App title updated");
                String newTitle = dataSnapshot.getValue(String.class);
                toolbar.setTitle(newTitle);
            }
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"Failed to read data.", databaseError.toException());
            }
        });

    }
    public void goto_SetData(View view) {
        Intent intent = new Intent(this, SetData.class);
        startActivity(intent);
    }
    public void goto_GetData(View view) {
        Intent intent = new Intent(this, GetData.class);
        startActivity(intent);
    }

    public void goto_MapDisplay(View view) {
        Intent intent = new Intent(this, MapDisplay.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}