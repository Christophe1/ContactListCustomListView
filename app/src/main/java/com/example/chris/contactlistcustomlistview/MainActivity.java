package com.example.chris.contactlistcustomlistview;

        import android.app.Activity;
        import android.content.ContentResolver;
        import android.database.Cursor;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
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
        import java.util.List;


public class MainActivity extends Activity {

    // ArrayList
    ArrayList<SelectContact> selectContacts;
//    List<SelectContact> temp;
    // Contact List
    ListView listView;


    // Cursor to load contacts list
    Cursor phones;
    Cursor phonestwo;
//Cursor curt;
    // Pop up
    ContentResolver resolver;
    SearchView search;
    SelectContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectContacts = new ArrayList<SelectContact>();
        resolver = this.getContentResolver();
        listView = (ListView) findViewById(R.id.contacts_list);

        //************************************************
//        String[] selectionArgs = new String[] { Contacts.DISPLAY_NAME };

//        int counter = selectionArgs.length();
//        Cursor cursortest = getContentResolver().query (ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        Log.e("count contacts", "" + cursortest.getCount());
//        Log.e("count", "" + selectionArgs.length());

//        String[] selectionArgs = { ContactsContract.Contacts.LOOKUP_KEY };
//
//        Cursor cursortest = getContentResolver().query (ContactsContract.Contacts.CONTENT_FILTER_URI,
//                selectionArgs,
//                null,
//                null,
//                null);

//
//        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
//                + ("1") + "'";
//        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

//        curt = getContentResolver().query(
//                ContactsContract.Contacts.CONTENT_URI,
//                null,
//                selection + " AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1",
//                null,
//                sortOrder);// this query only return contacts which had phone number and not duplicated
//
//        Log.e("CntactsCntract.bahhh", "" + curt.getCount());
//        Log.e("count", "" + selectionArgs.length());

//        ***********************
// this query only return contacts with phone number and is not duplicated
        phones = getContentResolver().query(
//                the table to query
                ContactsContract.Contacts.CONTENT_URI,
//                the columns to return
                null,
//               selection criteria :
// we only want contacts that have a name and a phone number. If they have a phone number, the value is 1 (if not, it is 0)
                ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '" + ("1") + "'" + " AND " + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1",
//               selection criteria
                null,
//                display in ascending order
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");


//        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                null,
//                null,
//                null,
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

//        Cursor thephonenumber = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);


        Log.e("phones", "" + phones.getCount());


//**********************8
//        if (phones.getCount() > 0) {

//            while (phones.moveToNext()) {
//                String id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                Log.i("Names", name);
//                if (Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
//                {
                    // Query phone here. Covered next
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
//        ,
//                ContactsContract.CommonDataKinds.Phone.NUMBER};

                     phonestwo = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                             null,
                             ContactsContract.CommonDataKinds.Phone.IN_VISIBLE_GROUP + " = '" + ("1") + "'" + " AND " + ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + "=1",
//                             ContactsContract.CommonDataKinds.Phone.NUMBER,
                             null,
                             null);
//                    phonestwo.moveToFirst();
        Log.e("phonestwo", "" + phonestwo.getCount());
//                        while (phonestwo.moveToNext()) {
//                        String phoneNumber = phonestwo.getString(phonestwo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        String theName = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC"));
//
//                        Log.i("Name and Number", theName + "and" + phoneNumber);
//                    }




//            phones.close();







//        retrieves contact information
        LoadContact loadContact = new LoadContact();
        loadContact.execute();

//        let's set up our search box,
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
                // when the text in searchView changes, call the filter function
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


            if (phones != null) {
                Log.e("ContactsContract count", "" + phones.getCount());
                if (phones.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }



                while (phones.moveToNext()) {
////                    Bitmap bit_thumb = null;
////                    String nametwo = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
////                    String id = phones.get
//// String(phones.getColumnIndex(ContactsContract.Contacts.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
////                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//                    {
//                        // Query phone here. Covered next
//                        while (thephonenumber.moveToNext()) {
//                            String aphoneNumber = thephonenumber.getString(thephonenumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            Log.i("Number", aphoneNumber);
//                        }


//                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS));
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
//what's happening here? For every user in the phonebook, show an image, the name, number, an id and maybe a checkbox?
                    SelectContact selectContact = new SelectContact();
//                    selectContact.setThumb(bit_thumb);
//                    selectContact.setName(nametwo);
                    selectContact.setName(name);
//                    selectContact.setPhone(phoneNumber);
//                    selectContact.setEmail(id);
//                    selectContact.setCheckedBox(false);
                    selectContacts.add(selectContact);
                }
                Log.e("Cursor close 1", "----------------");
            }
            phones.close();
            return null;
        }

        @Override
//        when DoInBackground is finished, when we have our phone number, name etc... display the results in our listview.
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new SelectContactAdapter(selectContacts, MainActivity.this);
            listView.setAdapter(adapter);

            // Select item on listclick
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Log.e("search", "here---------------- listener");

                    SelectContact data = selectContacts.get(i);
                }
            });

            listView.setFastScrollEnabled(true);
        }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        phones.close();
//    }
}}


//*****************************
