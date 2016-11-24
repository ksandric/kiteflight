package com.example.dreamcast.KiteFlightAndroidShvaiba.additional;

/**
 * Created by Dreamcast on 19.09.2016.
 */
import android.graphics.Bitmap;

public class SelectUser {
    String name;

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    Bitmap thumb;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone;

    public Boolean getCheckedBox() {
        return checkedBox;
    }

    public Boolean getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(Boolean radioButton) {
        this.radioButton = radioButton;
    }


    public void setCheckedBox(Boolean checkedBox) {
        this.checkedBox = checkedBox;
    }

    Boolean radioButton = false;
    Boolean checkedBox = false;

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


}
