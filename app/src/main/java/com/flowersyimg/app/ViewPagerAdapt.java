package com.flowersyimg.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapt extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    private int[] images = {
            R.drawable.pict2,
            R.drawable.pict3,
            R.drawable.pict4,
            R.drawable.pict5,
            R.drawable.pict6,
            R.drawable.pict7,
            R.drawable.pict8,
            R.drawable.pict9
    };

    public ViewPagerAdapt(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView ivPager = itemView.findViewById(R.id.ivPager);
        ivPager.setImageResource(images[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}

