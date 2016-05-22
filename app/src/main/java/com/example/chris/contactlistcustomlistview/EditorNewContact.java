package com.example.chris.contactlistcustomlistview;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Chris on 21/04/2016.
 */
public class EditorNewContact extends Activity {

    CheckBox checkBox;
    TextView textcategory;
    EditText nameofcontact;
    EditText numberofcontact;
    EditText lookupofcontact;
    EditText textAddress;
    EditText textComment;
    public String contactname;
    public String contactnumber;
    EditText editText;
    Animation fadeInAnimation;
    Animation fadeOutAnimation;

    String l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        textcategory = (EditText) findViewById(R.id.textCategory);
        textcategory.setVisibility(View.INVISIBLE);
        nameofcontact = (EditText) findViewById(R.id.edittextname);
        numberofcontact = (EditText) findViewById(R.id.edittextnumber);
        lookupofcontact = (EditText) findViewById(R.id.edittextlookup);


        textAddress = (EditText) findViewById(R.id.textAddress);
        textAddress.setVisibility(View.INVISIBLE);
        textComment = (EditText) findViewById(R.id.textComment);
        textComment.setVisibility(View.INVISIBLE);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
//        get the string, thecontactname, from MainActivity and make it equal to s
        String s = extras.getString("thecontactname");
        l = extras.getString("thelookupkey");

//        String s= getIntent().getStringExtra("thecontactname");
        System.out.println("the name is" + s);

//        String l= getIntent().getStringExtra("thelookupkey");
        System.out.println("the lookup is" + l);

//      set the text in the view edittext to s, which is grabbing the String thecontactname
//      from MainActivity
        EditText edittext = (EditText) findViewById(R.id.edittextname);
        edittext.setText(s);

        EditText edittext1 = (EditText) findViewById(R.id.edittextlookup);
        edittext1.setText(l);

    }

    public void deleteButton(View view) {
        System.out.println("delete works");
        String thelookupkey;
        thelookupkey = "1885r1463-4B373D3D372F2D4333";

//      in our cursor query let's focus on the LOOKUP_KEY column
//      this will give us all the strings in that column
        String [] PROJECTION = new String [] {  ContactsContract.Contacts.LOOKUP_KEY };

//      we're going to query all the LOOKUP_KEY strings ; that is, the unique ids of all our contacts
//      which we can find in the LOOKUP_KEY column of the CONTENT_URI table
        Cursor cur = getContentResolver().query
                (ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);

        try {
            if (cur.moveToFirst()) {
                do {
                    if
//               If a LOOKUP_KEY value is equal to our look up key string..
                 (cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)).equalsIgnoreCase(thelookupkey)) {
//               then delete that LOOKUP_KEY value, including all associated details, like number, name etc...
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, thelookupkey);
                        getContentResolver().delete(uri, null, null);
                    }

                } while (cur.moveToNext());
            }
//      deal with any errors, should they arise
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
//            finally, close the cursor
            cur.close();
        }

    }


    }




