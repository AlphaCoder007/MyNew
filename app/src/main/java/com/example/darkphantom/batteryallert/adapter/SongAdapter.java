package com.example.darkphantom.batteryallert.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darkphantom.batteryallert.R;
import com.example.darkphantom.batteryallert.model.SongModel;

import java.util.ArrayList;


public class SongAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<SongModel> songList;
    private  LayoutInflater layoutInflater;

   private OnLayoutClickListener onLayoutClickListener;
    private boolean selected=false;
    RadioButton radioView=null;
    private TextView textView;
    private static int mPosition = -1;

    private  RadioButton radioButton = null;

    public interface OnLayoutClickListener {
        void onClick(long id, String song);
    }

    public SongAdapter(ArrayList<SongModel> songList, Activity activity) {

        this.songList = songList;
       layoutInflater =  ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return songList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View view = convertView;
        if (convertView == null)
            view = layoutInflater.inflate(R.layout.list_row_layout, null);

      textView = view.findViewById(R.id.songName);
      final RadioButton mRadioButton = view.findViewById(R.id.songSelect);

        if (mPosition != position) {
            mRadioButton.setChecked(false);
        } else {
            mRadioButton.setChecked(true);
            radioButton = mRadioButton;
        }


        textView.setText(songList.get(position).getTitl());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioButton == null) {

                    if(mRadioButton.isChecked()) {
                        radioButton = mRadioButton;
                    } else {
                        mRadioButton.setChecked(true);
                        radioButton = mRadioButton;
                    }

                } else {

                    if (mRadioButton.isChecked()) {
                        radioButton.setChecked(false);
                        radioButton = mRadioButton;
                    } else {
                        mRadioButton.setChecked(true);
                        radioButton.setChecked(false);
                        radioButton = mRadioButton;
                    }

                }

                if(onLayoutClickListener != null) {
                    onLayoutClickListener.onClick(songList.get(position).getID(), songList.get(position).getTitl());
                }

                mPosition = position;
            }
        });

        mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton == null) {

                    if(mRadioButton.isChecked()) {
                        radioButton = mRadioButton;
                    } else {
                        mRadioButton.setChecked(true);
                        radioButton = mRadioButton;
                    }

                } else {

                    if (mRadioButton.isChecked()) {
                        radioButton.setChecked(false);
                        radioButton = mRadioButton;
                    } else {
                        mRadioButton.setChecked(true);
                        radioButton.setChecked(false);
                        radioButton = mRadioButton;
                    }

                }
                if(onLayoutClickListener != null) {
                    onLayoutClickListener.onClick(songList.get(position).getID(),songList.get(position).getTitl());
                }
            }
        });


        return view;
    }

    public void setOnLayoutClickListener(OnLayoutClickListener onLayoutClickListener) {
        this.onLayoutClickListener = onLayoutClickListener;
    }

    @Override
    public void onClick(View v) {


    }
}
