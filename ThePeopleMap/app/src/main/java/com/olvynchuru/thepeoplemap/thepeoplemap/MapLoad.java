package com.olvynchuru.thepeoplemap.thepeoplemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Scanner;

public class MapLoad extends AppCompatActivity {

    private static final String TAG = MapLoad.class.getSimpleName();
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private static final int[] ASCIIHEX = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                                           0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,6,7,8,9,0,0,0,0,0,0,
                                           0,10,11,12,13,14,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                                           0,10,11,12,13,14,15,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    public String ASCIIHEX_To_Int_String(String ascii) {
        int x = 0;
        for (int inc = 0, dec = ascii.length() - 1; dec >= 0; inc++, dec--)
            if (ascii.charAt(dec) < 128)
                x += (Math.pow(16, inc) * ASCIIHEX[ascii.charAt(dec)]);
        return ((Integer)x).toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("thing", "gjgil");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase = mFirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference("country_count").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Country count updated.");
                Country.numCountries = dataSnapshot.getValue(Integer.class);
            }
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,"Failed to read data.", databaseError.toException());
            }
        });




        if (Country.numCountries == 0) {
            String fileName = "Afghanistan\n" +
                    "36373176\n" +
                    "67\n" +
                    "78\n" +
                    "20\n" +
                    "Albania\n" +
                    "2934363\n" +
                    "c4\n" +
                    "c8\n" +
                    "b7\n" +
                    "Algeria\n" +
                    "42008054\n" +
                    "aa\n" +
                    "de\n" +
                    "86\n" +
                    "American Samoa\n" +
                    "55679\n" +
                    "fa\n" +
                    "1e\n" +
                    "77\n" +
                    "Andorra\n" +
                    "76953\n" +
                    "fa\n" +
                    "49\n" +
                    "8b\n" +
                    "Angola\n" +
                    "30774205\n" +
                    "ff\n" +
                    "dd\n" +
                    "55\n" +
                    "Anguilla\n" +
                    "15045\n" +
                    "fd\n" +
                    "a4\n" +
                    "77\n" +
                    "Antigua and Barbuda\n" +
                    "103050\n" +
                    "f4\n" +
                    "3a\n" +
                    "34\n" +
                    "Argentina\n" +
                    "44688864\n" +
                    "af\n" +
                    "dd\n" +
                    "e9\n" +
                    "Armenia\n" +
                    "2934152\n" +
                    "78\n" +
                    "44\n" +
                    "21\n" +
                    "Aruba\n" +
                    "105670\n" +
                    "ff\n" +
                    "81\n" +
                    "52\n" +
                    "Australia\n" +
                    "24772247\n" +
                    "2c\n" +
                    "89\n" +
                    "a0\n" +
                    "Austria\n" +
                    "8751820\n" +
                    "ff\n" +
                    "dd\n" +
                    "54\n" +
                    "Azerbaijan\n" +
                    "9923914\n" +
                    "99\n" +
                    "ff\n" +
                    "55\n" +
                    "Bahamas\n" +
                    "399285\n" +
                    "5f\n" +
                    "d3\n" +
                    "bc\n" +
                    "Bahrain\n" +
                    "1566993\n" +
                    "a0\n" +
                    "5a\n" +
                    "2b\n" +
                    "Bangladesh\n" +
                    "166368149\n" +
                    "5a\n" +
                    "a0\n" +
                    "2c\n" +
                    "Barbados\n" +
                    "286388\n" +
                    "ef\n" +
                    "9f\n" +
                    "eb\n" +
                    "Belarus\n" +
                    "9452113\n" +
                    "ff\n" +
                    "d4\n" +
                    "2a\n" +
                    "Belgium\n" +
                    "11498519\n" +
                    "ff\n" +
                    "80\n" +
                    "80\n" +
                    "Belize\n" +
                    "382444\n" +
                    "00\n" +
                    "88\n" +
                    "aa\n" +
                    "Benin\n" +
                    "11485674\n" +
                    "ff\n" +
                    "e6\n" +
                    "83\n" +
                    "Bermuda\n" +
                    "61070\n" +
                    "f0\n" +
                    "dc\n" +
                    "1c\n" +
                    "Bhutan\n" +
                    "817054\n" +
                    "ff\n" +
                    "99\n" +
                    "52\n" +
                    "Bolivia\n" +
                    "11215674\n" +
                    "9d\n" +
                    "ac\n" +
                    "92\n" +
                    "Bosnia and Herzegovina\n" +
                    "3503554\n" +
                    "80\n" +
                    "ff\n" +
                    "76\n" +
                    "Botswana\n" +
                    "2333201\n" +
                    "2a\n" +
                    "ff\n" +
                    "d5\n" +
                    "Brazil\n" +
                    "210867954\n" +
                    "2a\n" +
                    "ff\n" +
                    "85\n" +
                    "British Virgin Islands\n" +
                    "31719\n" +
                    "c4\n" +
                    "a6\n" +
                    "21\n" +
                    "Brunei\n" +
                    "434076\n" +
                    "ef\n" +
                    "9a\n" +
                    "37\n" +
                    "Bulgaria\n" +
                    "7036848\n" +
                    "c8\n" +
                    "ab\n" +
                    "36\n" +
                    "Burkina Faso\n" +
                    "19751651\n" +
                    "de\n" +
                    "cd\n" +
                    "95\n" +
                    "Burundi\n" +
                    "11216450\n" +
                    "de\n" +
                    "cd\n" +
                    "86\n" +
                    "Cambodia\n" +
                    "16245729\n" +
                    "de\n" +
                    "87\n" +
                    "aa\n" +
                    "Cameroon\n" +
                    "24678234\n" +
                    "d3\n" +
                    "bc\n" +
                    "5f\n" +
                    "Canada\n" +
                    "36953765\n" +
                    "87\n" +
                    "de\n" +
                    "aa\n" +
                    "Cape Verde\n" +
                    "553335\n" +
                    "ed\n" +
                    "94\n" +
                    "7d\n" +
                    "Cayman Islands\n" +
                    "62348\n" +
                    "eb\n" +
                    "5a\n" +
                    "2f\n" +
                    "Central African Republic\n" +
                    "4737423\n" +
                    "ff\n" +
                    "dd\n" +
                    "50\n" +
                    "Chad\n" +
                    "15353184\n" +
                    "ff\n" +
                    "e6\n" +
                    "82\n" +
                    "Chile\n" +
                    "18197209\n" +
                    "00\n" +
                    "aa\n" +
                    "89\n" +
                    "China\n" +
                    "1415045928\n" +
                    "aa\n" +
                    "00\n" +
                    "00\n" +
                    "Colombia\n" +
                    "49464683\n" +
                    "aa\n" +
                    "87\n" +
                    "de\n" +
                    "Comoros\n" +
                    "832347\n" +
                    "ee\n" +
                    "a9\n" +
                    "46\n" +
                    "Cook Islands\n" +
                    "17411\n" +
                    "d0\n" +
                    "88\n" +
                    "0d\n" +
                    "Costa Rica\n" +
                    "4953199\n" +
                    "e5\n" +
                    "80\n" +
                    "ff\n" +
                    "Croatia\n" +
                    "4164783\n" +
                    "8d\n" +
                    "d3\n" +
                    "5e\n" +
                    "Cuba\n" +
                    "11489082\n" +
                    "8d\n" +
                    "d3\n" +
                    "5f\n" +
                    "Curacao\n" +
                    "161577\n" +
                    "eb\n" +
                    "89\n" +
                    "56\n" +
                    "Cyprus\n" +
                    "876000\n" +
                    "66\n" +
                    "ff\n" +
                    "00\n" +
                    "Czech Republic\n" +
                    "10625250\n" +
                    "dd\n" +
                    "76\n" +
                    "55\n" +
                    "Denmark\n" +
                    "5754356\n" +
                    "a0\n" +
                    "2c\n" +
                    "2c\n" +
                    "Djibouti\n" +
                    "971408\n" +
                    "78\n" +
                    "67\n" +
                    "21\n" +
                    "Dominica\n" +
                    "74308\n" +
                    "eb\n" +
                    "25\n" +
                    "d5\n" +
                    "Dominican Republic\n" +
                    "10882996\n" +
                    "93\n" +
                    "9d\n" +
                    "ac\n" +
                    "DR Congo\n" +
                    "84004989\n" +
                    "99\n" +
                    "ff\n" +
                    "54\n" +
                    "Ecuador\n" +
                    "16863425\n" +
                    "af\n" +
                    "e9\n" +
                    "dd\n" +
                    "Egypt\n" +
                    "99375741\n" +
                    "dd\n" +
                    "ff\n" +
                    "54\n" +
                    "El Salvador\n" +
                    "6411558\n" +
                    "80\n" +
                    "e5\n" +
                    "fe\n" +
                    "Equatorial Guinea\n" +
                    "1313894\n" +
                    "00\n" +
                    "ff\n" +
                    "00\n" +
                    "Eritrea\n" +
                    "5187948\n" +
                    "ff\n" +
                    "b3\n" +
                    "80\n" +
                    "Estonia\n" +
                    "1306788\n" +
                    "6f\n" +
                    "8a\n" +
                    "91\n" +
                    "Ethiopia\n" +
                    "107534882\n" +
                    "d3\n" +
                    "bc\n" +
                    "5b\n" +
                    "Falkland Islands\n" +
                    "2922\n" +
                    "ea\n" +
                    "b4\n" +
                    "1c\n" +
                    "Faroe Islands\n" +
                    "49489\n" +
                    "e9\n" +
                    "c1\n" +
                    "80\n" +
                    "Fiji\n" +
                    "912241\n" +
                    "55\n" +
                    "99\n" +
                    "ff\n" +
                    "Finland\n" +
                    "5542517\n" +
                    "af\n" +
                    "af\n" +
                    "e7\n" +
                    "France\n" +
                    "65523034\n" +
                    "e9\n" +
                    "b5\n" +
                    "a5\n" +
                    "French Polynesia\n" +
                    "285859\n" +
                    "d0\n" +
                    "88\n" +
                    "0d\n" +
                    "Gabon\n" +
                    "2067561\n" +
                    "b3\n" +
                    "ff\n" +
                    "7f\n" +
                    "Gambia\n" +
                    "2163765\n" +
                    "d4\n" +
                    "55\n" +
                    "00\n" +
                    "Georgia\n" +
                    "3907131\n" +
                    "5f\n" +
                    "d3\n" +
                    "5f\n" +
                    "Germany\n" +
                    "82293457\n" +
                    "ff\n" +
                    "99\n" +
                    "55\n" +
                    "Ghana\n" +
                    "29463643\n" +
                    "ab\n" +
                    "c8\n" +
                    "37\n" +
                    "Greece\n" +
                    "11142161\n" +
                    "00\n" +
                    "cc\n" +
                    "ff\n" +
                    "Greenland\n" +
                    "56565\n" +
                    "e7\n" +
                    "14\n" +
                    "80\n" +
                    "Grenada\n" +
                    "108339\n" +
                    "e8\n" +
                    "7a\n" +
                    "cd\n" +
                    "Guam\n" +
                    "165718\n" +
                    "e5\n" +
                    "72\n" +
                    "08\n" +
                    "Guatemala\n" +
                    "17245346\n" +
                    "80\n" +
                    "ff\n" +
                    "80\n" +
                    "Guernsey\n" +
                    "63026\n" +
                    "e9\n" +
                    "35\n" +
                    "eb\n" +
                    "Guinea\n" +
                    "13052608\n" +
                    "c8\n" +
                    "ab\n" +
                    "37\n" +
                    "Guinea-Bissau\n" +
                    "1907268\n" +
                    "d3\n" +
                    "bc\n" +
                    "5e\n" +
                    "Guyana\n" +
                    "782225\n" +
                    "ff\n" +
                    "b3\n" +
                    "79\n" +
                    "Haiti\n" +
                    "11112945\n" +
                    "5f\n" +
                    "bc\n" +
                    "d3\n" +
                    "Honduras\n" +
                    "9417167\n" +
                    "2a\n" +
                    "d4\n" +
                    "ff\n" +
                    "Hong Kong\n" +
                    "7428887\n" +
                    "e5\n" +
                    "36\n" +
                    "29\n" +
                    "Hungary\n" +
                    "9688847\n" +
                    "de\n" +
                    "aa\n" +
                    "87\n" +
                    "Iceland\n" +
                    "337780\n" +
                    "55\n" +
                    "99\n" +
                    "fe\n" +
                    "India\n" +
                    "1354051854\n" +
                    "d4\n" +
                    "ff\n" +
                    "29\n" +
                    "Indonesia\n" +
                    "266794980\n" +
                    "ff\n" +
                    "7f\n" +
                    "2a\n" +
                    "Iran\n" +
                    "82011735\n" +
                    "8d\n" +
                    "d3\n" +
                    "5d\n" +
                    "Iraq\n" +
                    "39339753\n" +
                    "55\n" +
                    "ff\n" +
                    "54\n" +
                    "Ireland\n" +
                    "4803748\n" +
                    "c8\n" +
                    "b7\n" +
                    "c4\n" +
                    "Isle of Man\n" +
                    "84831\n" +
                    "e4\n" +
                    "28\n" +
                    "fe\n" +
                    "Israel\n" +
                    "8452841\n" +
                    "b3\n" +
                    "80\n" +
                    "ff\n" +
                    "Italy\n" +
                    "59290969\n" +
                    "7f\n" +
                    "ff\n" +
                    "2a\n" +
                    "Ivory Coast\n" +
                    "24905843\n" +
                    "ff\n" +
                    "99\n" +
                    "51\n" +
                    "Jamaica\n" +
                    "2898677\n" +
                    "87\n" +
                    "de\n" +
                    "85\n" +
                    "Japan\n" +
                    "127185332\n" +
                    "00\n" +
                    "ff\n" +
                    "ff\n" +
                    "Jersey\n" +
                    "100080\n" +
                    "e1\n" +
                    "c5\n" +
                    "5e\n" +
                    "Jordan\n" +
                    "9903802\n" +
                    "ff\n" +
                    "b3\n" +
                    "78\n" +
                    "Kazakhstan\n" +
                    "18403860\n" +
                    "00\n" +
                    "aa\n" +
                    "8a\n" +
                    "Kenya\n" +
                    "50950879\n" +
                    "aa\n" +
                    "de\n" +
                    "85\n" +
                    "Kiribati\n" +
                    "118414\n" +
                    "e1\n" +
                    "0b\n" +
                    "cf\n" +
                    "Kuwait\n" +
                    "4197128\n" +
                    "7f\n" +
                    "ff\n" +
                    "29\n" +
                    "Kyrgyzstan\n" +
                    "6132932\n" +
                    "a0\n" +
                    "5a\n" +
                    "2a\n" +
                    "Laos\n" +
                    "6961210\n" +
                    "df\n" +
                    "78\n" +
                    "75\n" +
                    "Latvia\n" +
                    "1929938\n" +
                    "5f\n" +
                    "8d\n" +
                    "d3\n" +
                    "Lebanon\n" +
                    "6093509\n" +
                    "37\n" +
                    "c8\n" +
                    "36\n" +
                    "Lesotho\n" +
                    "2263010\n" +
                    "d4\n" +
                    "ff\n" +
                    "28\n" +
                    "Liberia\n" +
                    "4853516\n" +
                    "ee\n" +
                    "d7\n" +
                    "f4\n" +
                    "Libya\n" +
                    "6470956\n" +
                    "e5\n" +
                    "ff\n" +
                    "7f\n" +
                    "Liechtenstein\n" +
                    "38155\n" +
                    "8a\n" +
                    "91\n" +
                    "6f\n" +
                    "Lithuania\n" +
                    "2876475\n" +
                    "87\n" +
                    "cd\n" +
                    "de\n" +
                    "Luxembourg\n" +
                    "590321\n" +
                    "ff\n" +
                    "dd\n" +
                    "52\n" +
                    "Macau\n" +
                    "632418\n" +
                    "da\n" +
                    "93\n" +
                    "be\n" +
                    "Macedonia\n" +
                    "2085051\n" +
                    "80\n" +
                    "ff\n" +
                    "79\n" +
                    "Madagascar\n" +
                    "26262810\n" +
                    "d3\n" +
                    "5f\n" +
                    "5d\n" +
                    "Malawi\n" +
                    "19164728\n" +
                    "c4\n" +
                    "b7\n" +
                    "c8\n" +
                    "Malaysia\n" +
                    "32042458\n" +
                    "ff\n" +
                    "dd\n" +
                    "51\n" +
                    "Maldives\n" +
                    "444259\n" +
                    "e2\n" +
                    "77\n" +
                    "9f\n" +
                    "Mali\n" +
                    "19107706\n" +
                    "e5\n" +
                    "ff\n" +
                    "7e\n" +
                    "Malta\n" +
                    "432089\n" +
                    "d8\n" +
                    "8d\n" +
                    "db\n" +
                    "Marshall Islands\n" +
                    "53167\n" +
                    "d8\n" +
                    "c5\n" +
                    "60\n" +
                    "Mauritania\n" +
                    "4540068\n" +
                    "bc\n" +
                    "d3\n" +
                    "5f\n" +
                    "Mauritius\n" +
                    "1268315\n" +
                    "d4\n" +
                    "ff\n" +
                    "2a\n" +
                    "Mexico\n" +
                    "130759074\n" +
                    "8d\n" +
                    "d3\n" +
                    "5c\n" +
                    "Micronesia\n" +
                    "106227\n" +
                    "e9\n" +
                    "4a\n" +
                    "66\n" +
                    "Moldova\n" +
                    "4041065\n" +
                    "ff\n" +
                    "d4\n" +
                    "2a\n" +
                    "Monaco\n" +
                    "38897\n" +
                    "d9\n" +
                    "8c\n" +
                    "4a\n" +
                    "Mongolia\n" +
                    "3121772\n" +
                    "37\n" +
                    "ab\n" +
                    "c8\n" +
                    "Montenegro\n" +
                    "629219\n" +
                    "87\n" +
                    "de\n" +
                    "87\n" +
                    "Montserrat\n" +
                    "5203\n" +
                    "d4\n" +
                    "f5\n" +
                    "b6\n" +
                    "Morocco\n" +
                    "36191805\n" +
                    "ff\n" +
                    "b3\n" +
                    "77\n" +
                    "Mozambique\n" +
                    "30528673\n" +
                    "d3\n" +
                    "bc\n" +
                    "5d\n" +
                    "Myanmar\n" +
                    "53855735\n" +
                    "aa\n" +
                    "88\n" +
                    "00\n" +
                    "Namibia\n" +
                    "2587801\n" +
                    "c4\n" +
                    "c8\n" +
                    "b6\n" +
                    "Nauru\n" +
                    "11312\n" +
                    "d2\n" +
                    "9d\n" +
                    "78\n" +
                    "Nepal\n" +
                    "29624035\n" +
                    "ff\n" +
                    "dd\n" +
                    "4f\n" +
                    "Netherlands\n" +
                    "17084459\n" +
                    "d3\n" +
                    "5f\n" +
                    "5f\n" +
                    "New Caledonia\n" +
                    "279821\n" +
                    "2c\n" +
                    "89\n" +
                    "9f\n" +
                    "New Zealand\n" +
                    "4749598\n" +
                    "7f\n" +
                    "2a\n" +
                    "ff\n" +
                    "Nicaragua\n" +
                    "6284757\n" +
                    "37\n" +
                    "ab\n" +
                    "c7\n" +
                    "Niger\n" +
                    "22311375\n" +
                    "ff\n" +
                    "dd\n" +
                    "50\n" +
                    "Nigeria\n" +
                    "195875237\n" +
                    "b3\n" +
                    "ff\n" +
                    "80\n" +
                    "Niue\n" +
                    "1624\n" +
                    "d3\n" +
                    "1e\n" +
                    "9c\n" +
                    "North Korea\n" +
                    "25610672\n" +
                    "ff\n" +
                    "2a\n" +
                    "2a\n" +
                    "Northern Cyprus\n" +
                    "314000\n" +
                    "33\n" +
                    "80\n" +
                    "00\n" +
                    "Northern Mariana Islands\n" +
                    "55194\n" +
                    "d8\n" +
                    "6f\n" +
                    "f2\n" +
                    "Norway\n" +
                    "5353363\n" +
                    "d3\n" +
                    "5f\n" +
                    "5e\n" +
                    "Oman\n" +
                    "4829946\n" +
                    "66\n" +
                    "ff\n" +
                    "02\n" +
                    "Pakistan\n" +
                    "200813818\n" +
                    "37\n" +
                    "c8\n" +
                    "37\n" +
                    "Palau\n" +
                    "21964\n" +
                    "d0\n" +
                    "a6\n" +
                    "82\n" +
                    "Palestine\n" +
                    "5052776\n" +
                    "dd\n" +
                    "ff\n" +
                    "53\n" +
                    "Panama\n" +
                    "4162618\n" +
                    "ff\n" +
                    "aa\n" +
                    "ee\n" +
                    "Papua New Guinea\n" +
                    "8418346\n" +
                    "89\n" +
                    "a0\n" +
                    "cf\n" +
                    "Paraguay\n" +
                    "6896908\n" +
                    "80\n" +
                    "ff\n" +
                    "77\n" +
                    "Peru\n" +
                    "32551815\n" +
                    "80\n" +
                    "e5\n" +
                    "ff\n" +
                    "Philippines\n" +
                    "106512074\n" +
                    "de\n" +
                    "cd\n" +
                    "87\n" +
                    "Poland\n" +
                    "38104832\n" +
                    "80\n" +
                    "ff\n" +
                    "78\n" +
                    "Portugal\n" +
                    "10291196\n" +
                    "ff\n" +
                    "66\n" +
                    "00\n" +
                    "Puerto Rico\n" +
                    "3659007\n" +
                    "80\n" +
                    "ff\n" +
                    "e6\n" +
                    "Qatar\n" +
                    "2694849\n" +
                    "a0\n" +
                    "45\n" +
                    "65\n" +
                    "Republic of the Congo\n" +
                    "5399895\n" +
                    "aa\n" +
                    "de\n" +
                    "87\n" +
                    "Romania\n" +
                    "19580634\n" +
                    "a0\n" +
                    "89\n" +
                    "2c\n" +
                    "Russia\n" +
                    "143964709\n" +
                    "80\n" +
                    "80\n" +
                    "00\n" +
                    "Rwanda\n" +
                    "12501156\n" +
                    "e5\n" +
                    "ff\n" +
                    "80\n" +
                    "Saint Kitts and Nevis\n" +
                    "55850\n" +
                    "dc\n" +
                    "28\n" +
                    "6e\n" +
                    "Saint Lucia\n" +
                    "179667\n" +
                    "db\n" +
                    "58\n" +
                    "80\n" +
                    "Saint Martin\n" +
                    "77741\n" +
                    "da\n" +
                    "2c\n" +
                    "98\n" +
                    "Saint Pierre and Miquelon\n" +
                    "6342\n" +
                    "ce\n" +
                    "6c\n" +
                    "94\n" +
                    "Saint Vincent and the Grenadines\n" +
                    "110200\n" +
                    "c5\n" +
                    "ba\n" +
                    "0b\n" +
                    "Samoa\n" +
                    "197695\n" +
                    "bc\n" +
                    "6d\n" +
                    "68\n" +
                    "San Marino\n" +
                    "33557\n" +
                    "ce\n" +
                    "dc\n" +
                    "78\n" +
                    "Sao Tome and Principe\n" +
                    "208818\n" +
                    "48\n" +
                    "3e\n" +
                    "37\n" +
                    "Saudi Arabia\n" +
                    "33554343\n" +
                    "78\n" +
                    "67\n" +
                    "20\n" +
                    "Senegal\n" +
                    "16294270\n" +
                    "bc\n" +
                    "d3\n" +
                    "5e\n" +
                    "Serbia\n" +
                    "8762027\n" +
                    "e9\n" +
                    "c6\n" +
                    "af\n" +
                    "Seychelles\n" +
                    "95235\n" +
                    "cd\n" +
                    "6d\n" +
                    "a0\n" +
                    "Sierra Leone\n" +
                    "7719729\n" +
                    "ee\n" +
                    "aa\n" +
                    "ff\n" +
                    "Singapore\n" +
                    "5791901\n" +
                    "ff\n" +
                    "00\n" +
                    "00\n" +
                    "Sint Maarten\n" +
                    "40552\n" +
                    "ce\n" +
                    "27\n" +
                    "c5\n" +
                    "Slovakia\n" +
                    "5449816\n" +
                    "c8\n" +
                    "71\n" +
                    "37\n" +
                    "Slovenia\n" +
                    "2081260\n" +
                    "55\n" +
                    "ff\n" +
                    "55\n" +
                    "Solomon Islands\n" +
                    "623281\n" +
                    "ce\n" +
                    "dc\n" +
                    "79\n" +
                    "Somalia\n" +
                    "11673925\n" +
                    "af\n" +
                    "af\n" +
                    "e9\n" +
                    "Somaliland\n" +
                    "3508000\n" +
                    "ff\n" +
                    "e6\n" +
                    "81\n" +
                    "South Africa\n" +
                    "57398421\n" +
                    "c4\n" +
                    "c8\n" +
                    "52\n" +
                    "South Korea\n" +
                    "51164435\n" +
                    "e9\n" +
                    "af\n" +
                    "af\n" +
                    "South Sudan\n" +
                    "12919053\n" +
                    "be\n" +
                    "c8\n" +
                    "b7\n" +
                    "Spain\n" +
                    "46397452\n" +
                    "ff\n" +
                    "99\n" +
                    "54\n" +
                    "Sri Lanka\n" +
                    "20950041\n" +
                    "d4\n" +
                    "55\n" +
                    "01\n" +
                    "Sudan\n" +
                    "41511526\n" +
                    "ff\n" +
                    "e6\n" +
                    "80\n" +
                    "Suriname\n" +
                    "568301\n" +
                    "c8\n" +
                    "37\n" +
                    "37\n" +
                    "Swaziland\n" +
                    "1391385\n" +
                    "ff\n" +
                    "00\n" +
                    "01\n" +
                    "Sweden\n" +
                    "9982709\n" +
                    "aa\n" +
                    "87\n" +
                    "dd\n" +
                    "Switzerland\n" +
                    "8544034\n" +
                    "2a\n" +
                    "ff\n" +
                    "80\n" +
                    "Syria\n" +
                    "18284407\n" +
                    "67\n" +
                    "78\n" +
                    "21\n" +
                    "Taiwan\n" +
                    "23694089\n" +
                    "ff\n" +
                    "55\n" +
                    "55\n" +
                    "Tajikistan\n" +
                    "9107211\n" +
                    "7f\n" +
                    "ff\n" +
                    "28\n" +
                    "Tanzania\n" +
                    "59091392\n" +
                    "bc\n" +
                    "d3\n" +
                    "5d\n" +
                    "Thailand\n" +
                    "69183173\n" +
                    "c8\n" +
                    "71\n" +
                    "36\n" +
                    "Timor-Leste\n" +
                    "1324094\n" +
                    "c9\n" +
                    "16\n" +
                    "e3\n" +
                    "Togo\n" +
                    "7990926\n" +
                    "ff\n" +
                    "cc\n" +
                    "00\n" +
                    "Tonga\n" +
                    "109008\n" +
                    "c8\n" +
                    "12\n" +
                    "a2\n" +
                    "Trinidad and Tobago\n" +
                    "1372598\n" +
                    "c7\n" +
                    "cc\n" +
                    "0e\n" +
                    "Tunisia\n" +
                    "11659174\n" +
                    "ff\n" +
                    "aa\n" +
                    "aa\n" +
                    "Turkey\n" +
                    "81916871\n" +
                    "de\n" +
                    "87\n" +
                    "87\n" +
                    "Turkmenistan\n" +
                    "5851466\n" +
                    "9d\n" +
                    "ac\n" +
                    "93\n" +
                    "Turks and Caicos Islands\n" +
                    "35963\n" +
                    "c9\n" +
                    "86\n" +
                    "2e\n" +
                    "Uganda\n" +
                    "44270563\n" +
                    "af\n" +
                    "af\n" +
                    "e8\n" +
                    "Ukraine\n" +
                    "44009214\n" +
                    "7f\n" +
                    "ff\n" +
                    "27\n" +
                    "United Arab Emirates\n" +
                    "9541615\n" +
                    "00\n" +
                    "ff\n" +
                    "66\n" +
                    "United Kingdom\n" +
                    "66573504\n" +
                    "de\n" +
                    "87\n" +
                    "cd\n" +
                    "United States\n" +
                    "326766748\n" +
                    "55\n" +
                    "ff\n" +
                    "dd\n" +
                    "United States Virgin Islands\n" +
                    "104914\n" +
                    "c1\n" +
                    "83\n" +
                    "79\n" +
                    "Uruguay\n" +
                    "3469551\n" +
                    "00\n" +
                    "aa\n" +
                    "88\n" +
                    "Uzbekistan\n" +
                    "32364996\n" +
                    "87\n" +
                    "de\n" +
                    "86\n" +
                    "Vanuatu\n" +
                    "282117\n" +
                    "93\n" +
                    "a7\n" +
                    "ac\n" +
                    "Vatican City\n" +
                    "801\n" +
                    "c6\n" +
                    "e3\n" +
                    "84\n" +
                    "Venezuela\n" +
                    "32381221\n" +
                    "c8\n" +
                    "b7\n" +
                    "be\n" +
                    "Vietnam\n" +
                    "96491146\n" +
                    "ff\n" +
                    "2a\n" +
                    "29\n" +
                    "Wallis and Futuna\n" +
                    "11683\n" +
                    "bf\n" +
                    "74\n" +
                    "70\n" +
                    "Western Sahara\n" +
                    "567421\n" +
                    "ff\n" +
                    "b3\n" +
                    "76\n" +
                    "Yemen\n" +
                    "28915284\n" +
                    "ff\n" +
                    "99\n" +
                    "53\n" +
                    "Zambia\n" +
                    "17609178\n" +
                    "ab\n" +
                    "c8\n" +
                    "36\n" +
                    "Zimbabwe\n" +
                    "16913261\n" +
                    "8d\n" +
                    "d3\n" +
                    "5b";


            Scanner scan = new Scanner(fileName);
            String line, name, color_red, color_green, color_blue;
            int population;
            while (scan.hasNext()) {
                line = scan.nextLine();

                name = line;

                line = scan.nextLine();
                population = Integer.parseInt(line);

                Country country = new Country(name, population);

                line = scan.nextLine();
                color_red = ASCIIHEX_To_Int_String(line);

                line = scan.nextLine();
                color_green = ASCIIHEX_To_Int_String(line);

                line = scan.nextLine();
                color_blue = ASCIIHEX_To_Int_String(line);

                mDatabaseReference.child("country").child(color_red).child(color_green).child(color_blue).setValue(country);

            }
            mDatabaseReference.child("country_count").setValue(Country.numCountries);

        }
        Intent intent = new Intent(this, MapDisplay.class);
        startActivity(intent);
    }



}