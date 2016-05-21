package com.example.chris.contactlistcustomlistview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
        String l = extras.getString("thelookupkey");

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
        // Creates a new Intent to insert a contact
//        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
// Sets the MIME type to match the Contacts Provider
//        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

         /* Sends the Intent
     */
//        startActivity(intent);
//        //Get the bundle
//        Bundle bundle = getIntent().getExtras();
//
////Extract the data…
//        String lookupkeyvalue = bundle.getString("lookup_key");
//        Log.e("this is the lookup key:", lookupkeyvalue);
//
//        Intent intenttwo = new Intent(Intent.ACTION_VIEW);
//        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(lookupkeyvalue));
//        intenttwo.setData(uri);
//        startActivity(intenttwo);
        //Get the bundle
//        Bundle bundle = getIntent().getExtras();
//
////Extract the data…
//        String stuff = bundle.getString("stuff");
//        Log.e("this is stuff:", stuff);

//        Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
//        intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
//        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "example@example.com");
//        startActivity(intent);
    }
}