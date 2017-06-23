package com.example.chris.contactlistcustomlistview;

import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.ArrayList;

/**
 * Created by Chris on 06/05/2016.
 */
public class AddContact extends AppCompatActivity {

    CheckBox checkBox;
    EditText textcategory;
    EditText nameofcontact;
    EditText numberofcontact;
    EditText textAddress;
    EditText textComment;

    //    these are the string values of the EditTexts, above
    String category;
    String contactname;
    String contactnumber;
    String contactaddress;
    String comment;
    Integer whocansee;

    EditText editText;
    Animation fadeInAnimation;
    Animation fadeOutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontact);

//      initialise the edittexts
        textcategory = (EditText) findViewById(R.id.textCategory);
        textcategory.setVisibility(View.INVISIBLE);
        nameofcontact = (EditText) findViewById(R.id.edittextname);
        numberofcontact = (EditText) findViewById(R.id.edittextnumber);
        textAddress = (EditText) findViewById(R.id.textAddress);
        textAddress.setVisibility(View.INVISIBLE);
        textComment = (EditText) findViewById(R.id.textComment);
        textComment.setVisibility(View.INVISIBLE);

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fadeout);

//      lets be able to manipuate the 'Populisto Contact' checkbox,
//        make other things visible or not when checked
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if (isChecked) {
                    // Now Set your animation
                    textcategory.startAnimation(fadeInAnimation);
                    fadeInAnimation.setFillAfter(true);
//                    need to set the visibility
                    textcategory.setVisibility(View.VISIBLE);
                    textAddress.startAnimation(fadeInAnimation);
                    fadeInAnimation.setFillAfter(true);
                    textAddress.setVisibility(View.VISIBLE);
                    textComment.startAnimation(fadeInAnimation);
                    fadeInAnimation.setFillAfter(true);
                    textComment.setVisibility(View.VISIBLE);

                } else {
//            numberofcontact.setText("This checkbox is: unchecked");
                    // Now Set your animation
                    textcategory.startAnimation(fadeOutAnimation);
//                    set the view to invisible
                    textcategory.setVisibility(View.INVISIBLE);
                    textAddress.startAnimation(fadeOutAnimation);
                    textAddress.setVisibility(View.INVISIBLE);
                    textComment.startAnimation(fadeOutAnimation);
                    textComment.setVisibility(View.INVISIBLE);

                }

            }
        });

    }

//    public void onCheckedChanged(CompoundButton buttonView,
//                                 boolean isChecked) {
//        if (isChecked) {
//            numberofcontact.setText("This checkbox is: checked");
//        }
//        else {
//            numberofcontact.setText("This checkbox is: unchecked");
//        }}


//    CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//
//        }
//    }
//    );


    public void cancelButton(View view) {
//        just finish the activity, don't save anything
        finish();

    }


    public void doneButton(View view) {

//      save the details the user types in
        contactname = nameofcontact.getText().toString();
        contactnumber = numberofcontact.getText().toString();

//        If Populisto checkbox is ticked...
        if (checkBox.isChecked()) {
//            set string values for the Populisto edittexts, radio buttons...
            category = textcategory.getText().toString();
            contactaddress = textAddress.getText().toString();
            comment = textComment.getText().toString();
//            Integer whocansee;


            String method = "addcontact";
//            we want this class to be able to recognise the AddBackgroundTask object and its
//            AsyncTask functionality, so we can use calls to the database in the background
//            (in this case adding a new contact's details) so we instantiate it and use its context
            AddContactBackgroundTask addContactBackgroundTask = new AddContactBackgroundTask(this);
//            now we can use its methods, in this case the doInBackground
            addContactBackgroundTask.execute(method, contactname, contactnumber, category, contactaddress, comment);
            finish();

            System.out.println("checked");
        } else

        {
            System.out.println("not checked");
            if (contactname.length() == 0) {
                Toast.makeText(this, "Please enter a name",
                        Toast.LENGTH_LONG).show();
                return;
            }

            //        save the contact name and number in the user's phone
            ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();
            //insert raw contact using RawContacts.CONTENT_URI
            contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
            //insert contact display name using Data.CONTENT_URI
            Log.d("ffff", "wwww");
            contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactname).build());
            //insert phone number using Data.CONTENT_URI
            contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactnumber).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
            try {
                //apply the changes
                getApplicationContext().getContentResolver().
                        applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }

//        SelectContactAdapter.changeCursor(cursor);

            Toast.makeText(this, "Contact saved",
                    Toast.LENGTH_SHORT).show();

            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //This clears the name of the contact the next time user starts the application, rather than
//    having the same stuff there, which the user probably doesn't want anymore
    protected void onResume() {
        super.onResume();
        nameofcontact.setText("");
    }

}

