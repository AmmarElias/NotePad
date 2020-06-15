# NotePad
a note pad App


**项目中用到了Dialog和BottonSheetDialog，由于特殊需求，Dialog需要从屏幕左边弹出，而BottonSheetDialog是Android提供的底部窗口控件，从屏幕底部弹出，下面分别介绍一下两种Dialog自定义布局的方法和相关使用介绍**

## 1、Dialog

dialog自定义布局，创建一个MyDialog类并继承自Dialog，在构造函数中加载布局，同时，使用接口将点击事件暴露给调用者，下面是相关代码：

```
public class MyDialog extends Dialog {

    private ListAdapter listAdapter;
    private ItemClickListener itemClickListener;
    //    style引用style样式
    public MyDialog(Context context, int width, int height, int style, final ItemClickListener listener) {

        super(context, style);
        this.itemClickListener = listener;//暴露给外界的接口

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

        window.setAttributes(params);//通过window来设置窗口的位置，Start就是屏幕左侧，设置宽度为500px
    }

    //自定义接口
    interface ItemClickListener{
        void onClick(String s);
    }
}
```

布局文件代码如下：

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="160dp"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:cardCornerRadius="10dp"
    app:cardElevation="0dp"
    app:cardBackgroundColor="#ffffff">

    <RelativeLayout
        android:layout_width="160dp"
        android:layout_height="match_parent">

        <TextView
            android:id="@+id/title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="便签分组"
            android:textSize="17sp"
            android:layout_centerHorizontal="true"
            android:gravity="center"
            android:layout_marginTop="20dp"
            android:layout_marginBottom="20dp"/>

        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:layout_below="@+id/title"
            android:background="#000000"/>

        <ListView
            android:id="@+id/list_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_below="@+id/title"/>

    </RelativeLayout>
</androidx.cardview.widget.CardView>
```


## 2、BottomSheetDialog

BottomSheetDialog已经给我们做了非常好的封装，所以使用非常方便，我将BottomSheetDialog的初始化和显示放在一个函数中，调用该函数即可，下面是相关代码：

```
private void showBottomSheetDialog() {
        if(bottomSheetDialog != null) {//已经初始化就不再重新初始化了
            bottomSheetDialog.show();
            return ;
        }

        bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet_layout, null);//加载自定义布局
        ImageView close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.cancel();
            }
        });
        CardView one = view.findViewById(R.id.one);
        
        initListener(1,one);//设置点击事件
        

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(true);//设置点击窗口外是否关闭窗口
        bottomSheetDialog.getBehavior().setDraggable(false);//设置是否可以向下滑动来关闭窗口
        bottomSheetDialog.show();
    }
```
