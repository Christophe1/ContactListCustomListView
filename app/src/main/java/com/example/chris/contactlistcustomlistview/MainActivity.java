package com.example.chris.contactlistcustomlistview;

        import android.app.Activity;
        import android.content.ContentResolver;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.provider.BaseColumns;
        import android.provider.ContactsContract;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.SearchView;
        import android.widget.Toast;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.HashSet;
        import java.util.List;
        import java.util.Set;

public class MainActivity extends Activity {

    // ArrayList
    ArrayList<SelectContact> selectContacts;
    List<SelectContact> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones, email;
    Cursor pCur;

    // Pop up
    ContentResolver resolver;
    SearchView search;
    SelectContactAdapter adapter;
    String phoneContactId;
    String name;
    String phoneNumber;

    //    *****18-04-2016***
    Cursor cursor;
    ListView mainListView;
    ArrayList hashMapsArrayList;
//    *****

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectContacts = new ArrayList<SelectContact>();
        resolver = this.getContentResolver();
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

        LoadContact loadContact = new LoadContact();
        loadContact.execute();

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
            // Get Contact list from Phone


            if (cursor != null) {
                cursor.moveToFirst();
            }
            try {

                cursor = getApplicationContext().getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
                int Idx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
                int nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                int phoneNumberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int photoIdIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI);
                cursor.moveToFirst();


                Set<String> ids = new HashSet<>();
                do {
                    System.out.println("=====>in while");
                    String contactid = cursor.getString(Idx);
                    if (!ids.contains(contactid)) {
                        ids.add(contactid);
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        String name = cursor.getString(nameIdx);
                        String phoneNumber = cursor.getString(phoneNumberIdx);
                        String image = cursor.getString(photoIdIdx);
//                    System.out.println("Id--->"+contactid+"Name--->"+name);
                        System.out.println("Id--->" + contactid + "Name--->" + name);
                        System.out.println("Id--->" + contactid + "Number--->" + phoneNumber);

                        if (!phoneNumber.contains("*")) {
                            hashMap.put("contactid", "" + contactid);
                            hashMap.put("name", "" + name);
                            hashMap.put("phoneNumber", "" + phoneNumber);
                            hashMap.put("image", "" + image);
                            // hashMap.put("email", ""+email);
                            if (hashMapsArrayList != null) {
                                hashMapsArrayList.add(hashMap);
                            }
//                    hashMapsArrayList.add(hashMap);
                        }

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
                if (cursor != null) {
                    cursor.close();
                }
            }
//            if (phones != null) {
//                phones.moveToFirst();
//
//                if (phones.getCount() == 0) {
//                    Toast.makeText(MainActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
//                }
//
//
//                while (phones.moveToNext()) {
//
//                    String name = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
////                    String phoneContactId = phones.getString(phones.getColumnIndexOrThrow(BaseColumns._ID));
//                    Log.e("phonescount", "" + phones.getCount());
//                    Log.e("names", "" + name);
//                    Log.e("The number of ID:", phoneContactId);


//                    Cursor pCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { phoneContactId }, null);
//

//            if (pCur != null) {
//                if (pCur.getCount() == 0);
//                pCur.moveToFirst();
//                {
//                    Toast.makeText(MainActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
//                }


//            if (phones != null) {
//                if (phones.getCount() == 0) {
//                    Toast.makeText(MainActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
//                }
//
//                while (pCur.moveToNext()) {
////                    int phoneType = pCur.getInt(pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE));
//                    String phoneNumber = pCur.getString(pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
////                    Log.e("phone type", phoneType);
//                    Log.e("phone number", phoneNumber);
//                    Log.e("phone numberrr", "hi there");
//                }
//            }
//                    **************************
            //        this is the original phone cursor, which counts 196 (has duplicates)
//        from https://trinitytuts.com/get-contact-list-and-show-in-custom-listview-android/

//                    Bitmap bit_thumb = null;
//                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
//                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
//                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
//                    try {
//                        if (image_thumb != null) {
//                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
//                        } else {
//                            Log.e("No Image Thumb", "--------------");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//**************************************************
//            SelectContact selectContact = new SelectContact();
////                    selectContact.setThumb(bit_thumb);
//            selectContact.setName(name);
//            selectContact.setPhone(phoneNumber);
////                    selectContact.setEmail(id);
////                    selectContact.setCheckedBox(false);
//            selectContacts.add(selectContact);

//             else {
//                Log.e("Cursor close 1", "----------------");

//            phones.close();


        return null;

    }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new SelectContactAdapter(selectContacts, MainActivity.this);
            listView.setAdapter(adapter);

            // Select item on listclick
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    if (phones != null) {
//                        phones.moveToFirst();
//                    }
//                    Log.e("search", "here---------------- listener");
//                    Log.e("phonescount", "" + phones.getCount());
//                    Log.e("names", "" + name);
//                    Log.e("phone number", phoneNumber);
////                    String err = (phoneContactId==null)?"phonecontactID":phoneContactId;
//                    Log.i("phonecontactID", phoneContactId);
//
//                    SelectContact data = selectContacts.get(i);
//                }
//            });

            listView.setFastScrollEnabled(true);
        }

    }



    @Override
    protected void onStop() {
        super.onStop();

    }

}
