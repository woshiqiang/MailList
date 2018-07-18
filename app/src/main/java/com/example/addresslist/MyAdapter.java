package com.example.addresslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 列表数据适配器
 */
public class MyAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<AddressBean> notes;
    private boolean isShowSelect;

    public MyAdapter(Context context, List<AddressBean> notes) {
        inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    public void updateAll(List<AddressBean> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return notes == null ? 0 : notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes == null ? null : notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            viewHolder.ivSelect = (ImageView) convertView.findViewById(R.id.iv_select);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(notes.get(position).getName());
        viewHolder.tvPhone.setText(notes.get(position).getPhone());
        //是否显示右侧勾选框
        if (isShowSelect) {
            viewHolder.ivSelect.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivSelect.setVisibility(View.GONE);
        }

        if (notes.get(position).isChecked()) {
            viewHolder.ivSelect.setImageResource(R.mipmap.ic_checked);
        } else {
            viewHolder.ivSelect.setImageResource(R.mipmap.ic_unchecked);
        }
        viewHolder.ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes.get(position).setChecked(!notes.get(position).isChecked());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public void setShowSelect(boolean isShowSelect) {
        this.isShowSelect = isShowSelect;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvPhone;
        ImageView ivSelect;
    }
}
