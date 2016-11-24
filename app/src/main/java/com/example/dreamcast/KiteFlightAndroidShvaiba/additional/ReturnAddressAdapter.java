package com.example.dreamcast.KiteFlightAndroidShvaiba.additional;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dreamcast.KiteFlightAndroidShvaiba.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dreamcast on 26.09.2016.
 */

public class ReturnAddressAdapter extends BaseAdapter {

    public List<ReturnAddress> _data;
    private ArrayList<ReturnAddress> arraylist;
    Context _c;
    ReturnAddressAdapter.ViewHolder v;

    public ReturnAddressAdapter(List<ReturnAddress> returnAddresss, Context context) {
        _data = returnAddresss;
        _c = context;
        this.arraylist = new ArrayList<ReturnAddress>();
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.list_view_return, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ReturnAddressAdapter.ViewHolder();

        v.nameReturn = (TextView) view.findViewById(R.id.nameReturn);
        v.phoneReturn = (TextView) view.findViewById(R.id.phoneReturn);
        v.adr1 = (TextView) view.findViewById(R.id.adr1);
        v.adr2 = (TextView) view.findViewById(R.id.adr2);
//        v.mail = (TextView) view.findViewById(R.id.mail);
//        v.imageView = (ImageView) view.findViewById(R.id.pic);

        final ReturnAddress data = (ReturnAddress) _data.get(i);

        //v.title.setText(data.getName());
        v.nameReturn.setText(data.getName());
        v.phoneReturn.setText(data.getPhone());
        v.adr1.setText(data.getAdr1());
        v.adr2.setText(data.getAdr2());

        //v.mail.setText(data.getAdr1());

        //v.phone.setText(data.getPhone());
        //v.mail.setText(data.getEmail());




        // Set check box listener android
//        v.check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CheckBox checkBox = (CheckBox) view;
//                if (checkBox.isChecked()) {
//                    data.setCheckedBox(true);
//                } else {
//                    data.setCheckedBox(false);
//                }
//            }
//        });
//        v.radioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RadioButton radioButton = (RadioButton) view;
//                if (radioButton.isChecked()) {
//                    data.setRadioButton(true);
//                } else {
//                    data.setRadioButton(false);
//                }
//            }
//        });

        view.setTag(data);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _data.clear();
        if (charText.length() == 0) {
            _data.addAll(arraylist);
        } else {
            for (ReturnAddress wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    _data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    static class ViewHolder {
//        ImageView imageView;
        TextView nameReturn, phoneReturn, adr1, adr2;
//        CheckBox check;
//        RadioButton radioButton;
    }
}
