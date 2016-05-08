package com.example.chris.contactlistcustomlistview;

        import android.app.Activity;
        import android.content.ContentResolver;
        import android.content.ContentUris;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.DatabaseUtils;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.provider.BaseColumns;
        import android.provider.ContactsContract;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.PopupMenu;
        import android.widget.SearchView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.HashSet;
        import java.util.List;
        import java.util.Set;

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener {


    // ArrayList called selectContacts that will contain SelectContact info
    ArrayList<SelectContact> selectContacts;
//    List<SelectContact> temp;

    ListView listView;

    SearchView search;
    SelectContactAdapter adapter;
    String phoneContactId;
    String name;
    String phoneNumber;
    CharSequence nameofcontact;

    //    *****18-04-2016***
    Cursor cursor;
    ListView mainListView;
    ArrayList hashMapsArrayList;

    public String cleartext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //selectContacts is an empty array list that will hold our SelectContct info
        selectContacts = new ArrayList<SelectContact>();

        listView = (ListView) findViewById(R.id.contacts_list);


//        this is the original phone cursor, which counts 196 (has duplicates)
//        from https://trinitytuts.com/get-contact-list-and-show-in-custom-listview-android/
//        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

//        if (phones != null) {
//            phones.moveToFirst();
//        }
//
//// this query only return contacts with phone number and is not duplicated
//        phones = getContentResolver().query(
////                the table to query
//                ContactsContract.Contacts.CONTENT_URI,
////                the columns to return
//                null,
////               selection criteria :
//// we only want contacts that have a name and a phone number. If they have a phone number, the value is 1 (if not, it is 0)
//                ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '" + ("1") + "'" + " AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1",
////               selection criteria
//                null,
////                display in ascending order
//                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
//
//        Log.e("phonesblahh", "" + phones.getCount());
//
//        if (phones != null) {
//            phones.moveToFirst();
//        }
//        String phoneContactId = phones.getString(phones.getColumnIndexOrThrow(BaseColumns._ID));
//
//        if (pCur != null) {
//            pCur.moveToFirst();
//        }
////        pCur.moveToFirst();
//        pCur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{phoneContactId}, null);
//        Log.e("pCurcount", "" + pCur.getCount());


//            Remember to move the cursor to the first row, whenever dealing with cursors
//            phones.moveToFirst();
//        String phoneContactId = phones.getString(phones.getColumnIndexOrThrow(BaseColumns._ID));
//        Log.e("The number of IDs:", phoneContactId);

//        LoadContact loadContact = new LoadContact();
//        loadContact.execute();

        search = (SearchView) findViewById(R.id.searchView);


        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                adapter.filter(newText);
                return false;
            }
        });


    }

    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Void doInBackground(Void... voids) {

//          we want to delete the old selectContacts from the listview when the Activity loads
//          because it may need to be updated and we want the user to see the updated listview,
//          like if the user adds new names and numbers to their phone contacts.
            selectContacts.clear();

//          we have this here to avoid cursor errors
            if (cursor != null) {
                cursor.moveToFirst();

            }
            try {

//                get a handle on the Content Resolver, so we can query the provider,
                cursor = getApplicationContext().getContentResolver()
//                the table to query
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//               Null. This means that we are not making any conditional query into the contacts table.
//               Hence, all data is returned into the cursor.
//                                Projection - the columns you want to query
                                null,
//                                Selection - with this you are extracting records with assigned (by you) conditions and rules
                                null,
//                                SelectionArgs - This replaces any question marks (?) in the selection string
//                               if you have something like String[] args = { "first string", "second@string.com" };
                                null,
//                                display in ascending order
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");

//                get the column number of the Contact_ID column, make it an integer.
//                I think having it stored as a number makes for faster operations later on.
                int Idx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
//                get the column number of the DISPLAY_NAME column
                int nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
//                 get the column number of the NUMBER column
                int phoneNumberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

//                int photoIdIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI);


                cursor.moveToFirst();

//              We make a new Hashset to hold all our contact_ids, including duplicates, if they come up
                Set<String> ids = new HashSet<>();
                do {
                    System.out.println("=====>in while");
//                  get a handle on the contactid, which is a string. Loop through all the contact_ids
                    String contactid = cursor.getString(Idx);
//                  if our Hashset doesn't already contain the contactid string,
//                    then add it to the hashset
                    if (!ids.contains(contactid)) {
                        ids.add(contactid);

                        HashMap<String, String> hashMap = new HashMap<String, String>();
//                        get a handle on the display name, which is a string
                        name = cursor.getString(nameIdx);
//                        get a handle on the phone number, which is a string
                         phoneNumber = cursor.getString(phoneNumberIdx);
//                        String image = cursor.getString(photoIdIdx);
//                    System.out.println("Id--->"+contactid+"Name--->"+name);
                        System.out.println("Id--->" + contactid + " Name--->" + name);
                        System.out.println("Id--->" + contactid + " Number--->" + phoneNumber);

//                        if (!phoneNumber.contains("*")) {
//                            hashMap.put("contactid", "" + contactid);
//                            hashMap.put("name", "" + name);
//                            hashMap.put("phoneNumber", "" + phoneNumber);
//                            hashMap.put("image", "" + image);
                            // hashMap.put("email", ""+email);
//                            if (hashMapsArrayList != null) {
//                                hashMapsArrayList.add(hashMap);
//                            }
//                    hashMapsArrayList.add(hashMap);
//                        }

                        SelectContact selectContact = new SelectContact();
//                    selectContact.setThumb(bit_thumb);
                        selectContact.setName(name);
                        selectContact.setPhone(phoneNumber);
//                    selectContact.setEmail(id);
//                    selectContact.setCheckedBox(false);
                        selectContacts.add(selectContact);
                    }


                } while (cursor.moveToNext());


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//                if (cursor != null) {

//                }
            }
//            cursor.close();
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

//into each inflate_listview, put a name and phone number, which are the details making
//            our SelectContact, above. And SelectContacts is all these inflate_listviews together
//            This is the first property of our SelectContactAdapter, a list
//            The next part, MainActivity.this, is our context, which is where we want the list to appear
            adapter = new SelectContactAdapter(selectContacts, MainActivity.this);
            listView.setAdapter(adapter);

            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                    cursor = getApplicationContext().getContentResolver()
//                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
//
//                    if (cursor != null) {
////                        cursor.moveToFirst();
////                                    get the cursor id of the clicked position
//                        cursor.moveToPosition(i);
                         nameofcontact = ((TextView)view.findViewById(R.id.name)).getText();
//                    }
                    // Creates a new Intent to edit a contact
                        Intent intent = new Intent(Intent.ACTION_EDIT);
//                        //Add the bundle to the intent
//                        intent.putExtras(bundle);
//                        start the intent

//                        startActivity(editIntent);
//                    Intent intent = new Intent(Intent.ACTION_EDIT);
//                    intent.setData(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY)));
//                    // Sets the special extended data for navigation
//                    intent.putExtra("finishActivityOnSaveCompleted", true);
//                    intent.putExtra(ContactsContract.Intents.Insert.NAME, nameofcontact);

                    startActivity(intent);


//******************************************
//                        String contactlookupkey = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY));
////
//                        String contactname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//
//                        String contactphoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                        Intent intent = new Intent(getApplicationContext(), EditorNewContact.class);
//
                        //Create the bundle
//                        Bundle bundle = new Bundle();
//
//                    }
                        //Add your data to bundle
//                        bundle.putString("lookup_key", contactlookupkey);
//                      ****************************************8

    /*
     * Once the user has selected a contact to edit,
     * this gets the contact's lookup key and _ID values from the
     * cursor and creates the necessary URI.
     */


                        // Creates a new Intent to edit a contact
//                    Intent editIntent = new Intent(Intent.ACTION_EDIT);
//                        Log.e("lookupkey", contactlookupkey);
//                        String contactphonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                        Log.e("Name", toString(nameddd));
                    System.out.println(nameofcontact);
//                        Log.e("phone", phoneNumber);
                        Log.d("index value", String.valueOf(i));
//                    Log.v("cursor position", DatabaseUtils.dumpCursorToString(cursor.getPosition(i)));
//                    DatabaseUtils.dumpCursorToString(cursor);
//                    public static String dumpCursorToString(cursor);
//                        Log.d("cursor position", String.valueOf(cursor.moveToPosition(i)));

//                    cursor.close();

    /*
     * Sets the contact URI to edit, and the data type that the
     * Intent must match
     */

//
//
//
////                        Toast.makeText(getApplicationContext(), usercontactid, Toast.LENGTH_LONG).show();

//
//
//                }
//
//
//
//
//            });

                        listView.setFastScrollEnabled(true);
//                    we need to notify the listview that changes may have been made on
//                    the background thread, doInBackground, like adding or deleting contacts,
//                    and these changes need to be reflected visibly in the listview. It works
//                    in conjunction with selectContacts.clear()
                    adapter.notifyDataSetChanged();


                }



            });
        }}

        //the is the arrow image, it opens the activity for edit or new contact
        public void EditorCreateContact(View v) {

//                    Intent intent = new Intent(getApplicationContext(), EditorNewContact.class);
//
//
//                        startActivity(intent);
        }


        @Override
        protected void onStop() {
            super.onStop();

        }

    //    this is for the settings menu
    public void settingsPopUp(View view) {

        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(MainActivity.this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_actions, popup.getMenu());
        popup.show();
    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
//            from the popup_actions.xml, identify the New_contact id and make it
//            open the AddContact class
            case R.id.New_contact:{
                Intent intent = new Intent(this, AddContact.class);
                                startActivity(intent);

//                startActivityForResult(intent,0);
//                setResult(RESULT_OK);
//                finish();
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish(); // Call once you redirect to another activity
            }

//                Toast.makeText(getBaseContext(), "You slected New Contact",Toast.LENGTH_SHORT).show();
//
            return true;
//
//            default:
//                return false;
        }
        return false;
    }

@Override
    protected void onResume() {
//I want to clear the searchview text when my app resumes or closes, but I keep getting an error, my app shuts down
//    cleartext =  findViewById(R.id.searchView).toString();
//    cleartext.isEmpty();
//        search.setQuery("", false);
        super.onResume();
//    load the contacts again, refresh them, when the user resumes the activity
    LoadContact loadContact = new LoadContact();
    loadContact.execute();
//    cursor.close();
    }


//    protected void onActivityResult (int requestCode, int resultCode, Intent data)
//    {
//
//    }

    }


