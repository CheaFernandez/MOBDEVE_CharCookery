package com.mobdeve.s17.charcookery.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mobdeve.s17.charcookery.R;

public class MenuBar extends LinearLayout {
    public MenuBar(Context context) {
        super(context);
        inflate(getContext(), R.layout.layout_menu, this);
    }

    public MenuBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.layout_menu, this);
    }

    public MenuBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.layout_menu, this);
    }
}
