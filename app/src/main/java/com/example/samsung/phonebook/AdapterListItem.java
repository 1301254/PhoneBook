package com.example.samsung.phonebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samsung on 2018-04-19.
 */

public class AdapterListItem extends ArrayAdapter<BeanItem> {
    private List<BeanItem> itemDatas = null;
    private static final int ACTIVITY_PHONE = 2;
    private ImageButton callButton;
    Context mContext;       //Activityの情報を含む
    public AdapterListItem(Context context, int resource, List<BeanItem> objects){
        super(context, resource, objects);
        this.itemDatas = objects;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        ViewHolder holder;
//        BeanItem contacts = itemDatas.get(position);
        mContext = parent.getContext();

        //viewがなけれは、新たなviewを取得
        if(convertView == null){
            //新たなlayoutを呼び入れ
            LayoutInflater inflater =
                    (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.phone_list,null);

            //現在のviewをViewholderという編集部で編集してもらう
            holder = new ViewHolder(convertView);

            //現在のviewをメモリにあげる
            convertView.setTag(holder);
        }else{
            //メモリのあるviewを持って来る
            holder = (ViewHolder) convertView.getTag();
        }
        callButton = (ImageButton)convertView.findViewById(R.id.callButtonImage);
        callButton.setTag(position);

        BeanItem list = getItem(position);
        holder.memberName.setText(list.member_name);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                BeanItem p = itemDatas.get(position);
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+p.member_hp));
                ((Activity)mContext).startActivityForResult(intent,ACTIVITY_PHONE);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView memberName;
        public ImageButton memberHp;

        public ViewHolder(View view){
            memberName  = (TextView)view.findViewById(R.id.member_name);
            memberHp    = (ImageButton)view.findViewById(R.id.callButtonImage);
        }
    }
}
