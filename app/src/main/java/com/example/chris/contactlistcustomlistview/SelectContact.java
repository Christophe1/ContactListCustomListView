package com.example.chris.contactlistcustomlistview;

/**
 * Created by Chris on 25/03/2016.
 */
import android.graphics.Bitmap;

public class SelectContact {

    Bitmap thumb;

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    Boolean checkedBox = false;

    public Boolean getCheckedBox() {
        return checkedBox;
    }

    public void setCheckedBox(Boolean checkedBox) {
        this.checkedBox = checkedBox;
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String lookup;

    public String getLookup() {
        return lookup;
    }

    public void setLookup(String lookup) {
        this.lookup = lookup;
    }
}