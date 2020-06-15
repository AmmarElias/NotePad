package com.example.android.notepad;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyDialog extends Dialog {

    private ListAdapter listAdapter;
    private ItemClickListener itemClickListener;
    //    style引用style样式
    public MyDialog(Context context, int width, int height, int style, final ItemClickListener listener) {

        super(context, style);
        this.itemClickListener = listener;

        View view = View.inflate(getContext(),R.layout.dialog_layout,null);

        ListView listView = view.findViewById(R.id.list_view);
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("未分组");
        list.add("生活");
        list.add("学习");
        list.add("工作");

        // 使用ArrayAdapter适配器
        listAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String kind = (String) listAdapter.getItem(i);
                if(itemClickListener != null){
                    itemClickListener.onClick(kind);
                }
            }
        });

        setContentView(view);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.START;
        params.width = 500;

        window.setAttributes(params);
    }

    interface ItemClickListener{
        void onClick(String s);
    }
}