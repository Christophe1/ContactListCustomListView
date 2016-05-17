package com.example.chris.contactlistcustomlistview;

        import android.annotation.TargetApi;
        import android.content.Context;
        import android.os.Build;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

//        import com.trinitygetcontact.R;
//        import com.trinitygetcontact.getset.SelectContact;
//        import com.trinitygetcontact.utils.RoundImage;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

/**
 * Created by Chris on 25/03/2016.
 */

public class SelectContactAdapter extends BaseAdapter {

    //define a list made out of SelectContacts and call it theContactsList
    public List<SelectContact> theContactsList;
    //define an array list made out of SelectContacts and call it arraylist
    private ArrayList<SelectContact> arraylist;
    Context _c;

    //define a ViewHolder to hold our name and number info, instead of constantly querying
    // findviewbyid. Makes the ListView run smoother
    ViewHolder v;

//    RoundImage roundedImage;

    public SelectContactAdapter(List<SelectContact> selectContacts, Context context) {
        theContactsList = selectContacts;
        _c = context;
        this.arraylist = new ArrayList<SelectContact>();
        this.arraylist.addAll(theContactsList);
    }

    @Override
    public int getCount() {
        return theContactsList.size();
    }

    @Override
    public Object getItem(int i) {
        return theContactsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    static class ViewHolder {
        //        In each cell in the listview show the items you want to have
//        Having a ViewHolder caches our ids, instead of having to call and load each one again and again
        ImageView imageView;
        TextView title, phone;
//        CheckBox check;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        //we're naming our convertView as view
        View view = convertView;

        if (view == null) {

            //if there is nothing there (if it's null) inflate the layout for each row
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.inflate_listview, null);
//            Log.e("Inside", "here--------------------------- In view1");


            //or else use the view (what we can see in each row) that is already there
        } else {
            view = convertView;
//            Log.e("Inside", "here--------------------------- In view2");
        }


        //        we are making a cell format in the ListView, which will contain info like
//        number, name... the layout for this, with name, no, pic etc...
//        is contained in inflate_listview.xml, which describes how each cell data
//        loads into the listview
        //Viewholder stores component views together so we can
        // immediately access them without the need to lookup repeatedly.
        // Saves on resources, makes the listview smoother
        v = new ViewHolder();

//      So, for example, title is cast to the name id, in inflate_listview,
//        phone is cast to the id called no etc
        v.title = (TextView) view.findViewById(R.id.name);
//        v.check = (CheckBox) view.findViewById(R.id.check);
        v.phone = (TextView) view.findViewById(R.id.no);
        v.imageView = (ImageView) view.findViewById(R.id.arrowright);

//        store the holder with the view
        final SelectContact data = (SelectContact) theContactsList.get(i);
        v.title.setText(data.getName());
//        v.check.setChecked(data.getCheckedBox());
        v.phone.setText(data.getPhone());
        v.imageView.setTag(data.getName());


//        Log.e("Image Thumb", "--------------" + data.getThumb());

        /*// Set check box listener android
        v.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    data.setCheckedBox(true);
                  } else {
                    data.setCheckedBox(false);
                }
            }
        });*/

        view.setTag(data);

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
//        theContactsList is our list of contacts
        theContactsList.clear();
//        If there is nothing in the searchview, if the charText length is 0,
//        then show all the contacts
        if (charText.length() == 0) {
            theContactsList.addAll(arraylist);
//            or else....
        } else {
            for (SelectContact wp : arraylist) {
//                If a contact's name matches the input thus far, which is charText,
//                then include it in the listview.
                if ((wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) || (wp.getPhone().toLowerCase(Locale.getDefault())
                        .contains(charText)))
                     {


//                    int flag = 0;
//
//                    for(int i=0;i<arraylist.size();i++){
//
//                        if(!arraylist.get(i).getPhone().trim().equals(name)){
//                            flag = 1;
//
//                        }else{
//                            flag =0;
//                            break;
//                        }
//
//                    }
//                    if(flag == 1){
//                        arraylist.add(new SelectContact(name, phoneNumber));
//                    }


                        theContactsList.add(wp);
                    }
            }
        }
        notifyDataSetChanged();
    }



}