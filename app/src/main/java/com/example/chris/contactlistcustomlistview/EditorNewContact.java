package com.example.chris.contactlistcustomlistview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by Chris on 21/04/2016.
 */
public class EditorNewContact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Get the bundle
        Bundle bundle = getIntent().getExtras();

//Extract the data…
        String lookupkeyvalue = bundle.getString("lookup_key");
        Log.e("this is the lookup key:", lookupkeyvalue);

        Intent intenttwo = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(lookupkeyvalue));
        intenttwo.setData(uri);
        startActivity(intenttwo);
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