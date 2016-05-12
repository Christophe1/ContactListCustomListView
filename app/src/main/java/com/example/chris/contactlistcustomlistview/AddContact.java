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
    TextView textcategory;
    EditText nameofcontact;
    EditText numberofcontact;
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
        setContentView(R.layout.addcontact);

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
         checkBox = (CheckBox) findViewById( R.id.checkBox );

        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                        if (isChecked) {
                            // Now Set your animation
                   textcategory.startAnimation(fadeInAnimation);
                            fadeInAnimation.setFillAfter(true);
                   textAddress.startAnimation(fadeInAnimation);
                            fadeInAnimation.setFillAfter(true);
                   textComment.startAnimation(fadeInAnimation);
                            fadeInAnimation.setFillAfter(true);

                        }
        else {
//            numberofcontact.setText("This checkbox is: unchecked");
                            // Now Set your animation
                   textcategory.startAnimation(fadeOutAnimation);
                            textAddress.startAnimation(fadeOutAnimation);
                            textComment.startAnimation(fadeOutAnimation);


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





    public void createButton(View view) {
        contactname = nameofcontact.getText().toString();
        contactnumber = numberofcontact.getText().toString();

        if (contactname.length() == 0) {

            Toast.makeText(this, "Please enter a name",
                    Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();
        //insert raw contact using RawContacts.CONTENT_URI
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
        //insert contact display name using Data.CONTENT_URI
        Log.d("ffff","wwww");
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,contactname ).build());
        //insert mobile number using Data.CONTENT_URI
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
