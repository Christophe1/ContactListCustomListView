package com.example.chris.contactlistcustomlistview;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    String n;
    String s;

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
        s = extras.getString("thecontactname");
//        get the string, thelookupkey, from MainActivity and make it equal to l
        l = extras.getString("thelookupkey");
        // get the string, thelookupkey, from MainActivity and make it equal to n
        n = extras.getString("thecontactnumber");

//        String s= getIntent().getStringExtra("thecontactname");
        System.out.println("the name is" + s);

//        String l= getIntent().getStringExtra("thelookupkey");
        System.out.println("the lookup is" + l);

        //        String l= getIntent().getStringExtra("thephonenumber");
        System.out.println("the number is" + n);

//      set the text in the view edittext to s, which is grabbing the String thecontactname
//      from MainActivity
        EditText edittext = (EditText) findViewById(R.id.edittextname);
        edittext.setText(s);

        EditText edittext2 = (EditText) findViewById(R.id.edittextnumber);
        edittext2.setText(n);

        EditText edittext1 = (EditText) findViewById(R.id.edittextlookup);
        edittext1.setText(l);

    }

    public void deleteButton(View view) {
        System.out.println("delete works");
//        String thelookupkey;
//        thelookupkey = "1885r1463-4B373D3D372F2D4333";

//      in our cursor query let's focus on the LOOKUP_KEY column
//      this will give us all the strings in that column
        String[] PROJECTION = new String[]{ContactsContract.Contacts.LOOKUP_KEY};

//      we're going to query all the LOOKUP_KEY strings ; that is, the unique ids of all our contacts
//      which we can find in the LOOKUP_KEY column of the CONTENT_URI table
        Cursor cur = getContentResolver().query
                (ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);

        try {
            if (cur.moveToFirst()) {
                do {
                    if
//               If a LOOKUP_KEY value is equal to our look up key string, which is l
                            (cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)).equalsIgnoreCase(l)) {
//               then delete that LOOKUP_KEY value, including all associated details, like number, name etc...
                        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, l);
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

    public void editButton(View view) {


//        public void update()
//        catch dog
//        087456658
        {
            String id = "1885r1464-3F3733354D574B3543494D2D4333";
            String firstname = "burtyy dog";
//            String lastname = "Last name";
            String number = "886085456";
            String number2 = "000";
            String photo_uri = "android.resource://com.my.package/drawable/default_photo";

            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            // Name
            ContentProviderOperation.Builder builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//            The selection and arguments to use. An occurrence of '?' in the selection will be replaced with
//            the corresponding occurence of the selection argument. So, LOOKUP_KEY + "=?" will be replaced with String.valueOf(id)
            builder.withSelection(ContactsContract.Contacts.LOOKUP_KEY + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?", new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE});
//            builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?", new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE});
            builder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, firstname);
//            builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number2);
//            builder.withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, firstname);
            ops.add(builder.build());

//            Number
//            builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//            builder.withSelection(ContactsContract.Contacts.LOOKUP_KEY + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?"+ " AND " + ContactsContract.CommonDataKinds.Phone.NUMBER + "=?", new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, String.valueOf(number)});
//            builder.withValue(ContactsContract.CommonDataKinds.Phone.DATA, number2);
//            ops.add(builder.build());

            // Number
//            builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//            builder.withSelection(ContactsContract.Contacts.LOOKUP_KEY + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?"+ " AND " + ContactsContract.CommonDataKinds.Organization.TYPE + "=?", new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)});
//            builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
//            ops.add(builder.build());
//

            // Picture
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(photo_uri));
//                ByteArrayOutputStream image = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG , 100, image);
//
//                builder = ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI);
//                builder.withSelection(ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "=?", new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE});
//                builder.withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, image.toByteArray());
//                ops.add(builder.build());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            // Update
            try {
                getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
        }


//        *********

////        the text in the 'nameofcontact' edittext box, can be modified by the user
//        contactname = nameofcontact.getText().toString();
////        the text in the 'numberofcontact' edittext box, can be modified by the user
//        contactnumber = numberofcontact.getText().toString();
//
//        ContentResolver cr = getContentResolver();
//
//        String where = ContactsContract.Data.DISPLAY_NAME + " = ? AND " +
//                ContactsContract.Data.MIMETYPE + " = ? AND " +
//                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE) + " = ? ";
//        String[] params = new String[] {contactname,
//                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
//                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)};
//
//        Cursor phoneCur = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, where, params, null);
//
//        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//
////        if ( (null == phoneCur)  ) {
////            createContact(name, phone);
////        } else
//
//            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
//                    .withSelection(where, params)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.DATA, contactnumber)
//                    .build());
//
//
//        phoneCur.close();
//
//        try {
//            cr.applyBatch(ContactsContract.AUTHORITY, ops);
//        } catch (RemoteException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (OperationApplicationException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        System.out.println (contactname);
//        System.out.println (contactnumber);
//        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
//
//
//    }
//
//**************************
    }
}



