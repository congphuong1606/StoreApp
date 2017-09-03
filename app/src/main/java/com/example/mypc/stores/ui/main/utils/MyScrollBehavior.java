package com.example.mypc.stores.ui.main.utils;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toolbar;

import com.example.mypc.stores.R;

/**
 * Created by congp on 9/4/2017.
 */

public class MyScrollBehavior extends AppBarLayout.Behavior {
    private View content;

    public MyScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, AppBarLayout appBarLayout, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        if(content == null) {
            content = parent.findViewById(R.id.container);
        }

        if(content != null) {
            boolean shouldNotScroll = content.findViewWithTag(parent.getContext().getString(R.string.contentShouldNotScrollTag)) != null;
            Toolbar toolbar = (Toolbar) appBarLayout.findViewById(R.id.toolbar);
            AppBarLayout.LayoutParams params =
                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            if (shouldNotScroll) {
                params.setScrollFlags(0);
                appBarLayout.setExpanded(true, true);
            } else {
                params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            }
        }

        return super.onMeasureChild(parent, appBarLayout, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }
}