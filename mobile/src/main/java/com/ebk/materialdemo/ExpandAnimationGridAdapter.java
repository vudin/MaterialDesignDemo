package com.ebk.materialdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by dmontiel on 11/2/14.
 */
public class ExpandAnimationGridAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final Context context;
    private int itemsNumber;
    ViewHolder holder;

    public ExpandAnimationGridAdapter(Context context, int itemsNumber) {
        this.context = context;
        this.itemsNumber = itemsNumber;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemsNumber;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_number, null);
            holder = new ViewHolder();
            holder.numberText = (TextView) convertView.findViewById(R.id.item_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.numberText.setText(String.valueOf(position + 1));

        if (convertView.getVisibility() != View.VISIBLE) {
            expandAnimation(position, convertView);
        } else {
            collapseAnimation(position, convertView);
        }
        return convertView;
    }

    private void expandAnimation(int position, View convertView) {
        final View finalConvertView = convertView;
        convertView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a = AnimationUtils.loadAnimation(context, R.anim.scale_up_from_center);
                finalConvertView.setAnimation(a);
                finalConvertView.setVisibility(View.VISIBLE);
            }
        }, position * 30);
    }


    private void collapseAnimation(int position, View convertView) {
        final View finalConvertView = convertView;
        convertView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a = AnimationUtils.loadAnimation(context, R.anim.scale_down_from_center);
                finalConvertView.setAnimation(a);
                finalConvertView.setVisibility(View.INVISIBLE);
            }
        }, (itemsNumber - position) * 30);
    }

    private static class ViewHolder {
        private TextView numberText;
    }
}
