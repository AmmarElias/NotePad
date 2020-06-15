package com.example.android.notepad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class MyListAdapter extends BaseAdapter {

    private Cursor cursor;   //创建一个StudentData 类的对象 集合
    private LayoutInflater inflater;
    private Context context;

    public  MyListAdapter (Cursor cursor, Context context) {
        this.cursor = cursor;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        cursor.moveToPosition(position);
        return cursor;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.noteslist_item,null);
        Cursor cursor = (Cursor)getItem(position);

        RelativeLayout root = view.findViewById(R.id.root);
        TextView text1 = view.findViewById(R.id.text1);
        TextView textData = view.findViewById(R.id.text_data);

        text1.setText(cursor.getString(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_TITLE)));
        textData.setText(cursor.getString(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE)));

        TextView textKind = view.findViewById(R.id.text_kind);
        String kind = cursor.getString(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_KIND));
        textKind.setText(kind.equals("未分类") ? "" : kind);

        initTheme(root,text1,textKind,textData,cursor.getInt(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_THEME)));

        //返回含有数据的view
        return view;
    }

    private void initTheme(RelativeLayout Root, TextView mText,TextView kind,TextView data, int flag) {
        int drawable;
        String color;
        if(flag == 1){
            drawable = R.drawable.one;
            color = "#454545";
        }else if(flag == 2){
            drawable = R.drawable.two;
            color= "#101010";
        }else if(flag == 3){
            drawable = R.drawable.three;
            color= "#FF6767";
        }else if(flag == 4){
            drawable = R.drawable.four;
            color= "#007E0C";
        }else if(flag == 5){
            drawable = R.drawable.five;
            color= "#EFEFEF";
        }else if(flag == 6){
            drawable = R.drawable.six;
            color= "#EFEFEF";
        }else if(flag == 7){
            drawable = R.drawable.seven;
            color= "#F8F8F8";
        }else if(flag == 8){
            drawable = R.drawable.eight;
            color= "#F8F8F8";
        }else{
            drawable = R.drawable.one;
            color= "#454545";
        }
        Root.setBackground(context.getResources().getDrawable(drawable));
        mText.setTextColor(Color.parseColor(color));
        kind.setTextColor(Color.parseColor(color));
        data.setTextColor(Color.parseColor(color));
    }
}
