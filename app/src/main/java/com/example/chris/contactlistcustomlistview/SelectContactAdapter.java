package com.example.chris.contactlistcustomlistview;

        import android.annotation.TargetApi;
        import android.content.Context;
        import android.os.Build;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.CheckBox;
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

    public List<SelectContact> _data;
    private ArrayList<SelectContact> arraylist;
    Context _c;
    ViewHolder v;
//    RoundImage roundedImage;

    public SelectContactAdapter(List<SelectContact> selectContacts, Context context) {
        _data = selectContacts;
        _c = context;
        this.arraylist = new ArrayList<SelectContact>();
        this.arraylist.addAll(_data);
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int i) {
        return _data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.inflate_listview, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

//        we are making a cell format in the ListView, which will contain info like
//        number, name... the layout for this, with name, no, pic etc...
//        is contained in inflate_listview.xml, which describes how each cell data
//        loads into the listview
        v = new ViewHolder();

//      So, for example, title is cast to the name id, in activity main,
//        phone is cast to the id called no etc
        v.title = (TextView) view.findViewById(R.id.name);
        v.check = (CheckBox) view.findViewById(R.id.check);
        v.phone = (TextView) view.findViewById(R.id.no);
//        v.imageView = (ImageView) view.findViewById(R.id.pic);

//        for each new cell with title, name, number etc...
//
        final SelectContact data = (SelectContact) _data.get(i);
        v.title.setText(data.getName());
        v.check.setChecked(data.getCheckedBox());
        v.phone.setText(data.getPhone());

        // Set image if exists
//        try {
//
//            if (data.getThumb() != null) {
//                v.imageView.setImageBitmap(data.getThumb());
//            } else {
//                v.imageView.setImageResource(R.drawable.image);
//            }
//            // Seting round image
//            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.image); // Load default image
////            roundedImage = new RoundImage(bm);
////            v.imageView.setImageDrawable(roundedImage);
//        } catch (OutOfMemoryError e) {
//            // Add default picture
//            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.image));
//            e.printStackTrace();
//        }

        Log.e("Image Thumb", "--------------" + data.getThumb());

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
//        _data is our list of Users, or contacts
        _data.clear();
//        If there is nothing in the searchview,
//        then show all the contacts
        if (charText.length() == 0) {
            _data.addAll(arraylist);
//            or else....
        } else {
            for (SelectContact wp : arraylist) {
//                If a contact's phone number matches the input thus far that the user
//                is filtering for, then include it in the listview.
                if (wp.getPhone().toLowerCase(Locale.getDefault())
                        .contains(charText)) {


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





                    _data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


    static class ViewHolder {
//        In each cell in the listview show a name and phone number
//        ImageView imageView;
        TextView title, phone;
        CheckBox check;
    }
}