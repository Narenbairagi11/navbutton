package com.narenbairagi.navbutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.button.MaterialButton;


public class NavButton extends LinearLayout {

    private MaterialCardView card;
    private MaterialButton icon;

    public NavButton(Context context) {
        super(context);
        init(context, null);
    }

    public NavButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NavButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.nav_button, this, true);
        card = findViewById(R.id.card);
        icon = findViewById(R.id.icon);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavButtonv, 0, 0);
            int iconSrc = a.getResourceId(R.styleable.NavButtonv_iconSrc, 0);
            if (iconSrc != 0) {
                icon.setIconResource(iconSrc);
            }
            a.recycle();
        }
    }

    @SuppressLint("ResourceType")
    public void setSelected(boolean selected) {
        if (selected) {
            card.setCardBackgroundColor(ContextCompat.getColor(getContext() , androidx.appcompat.R.attr.colorPrimary ));
            icon.setIconTint(ColorStateList.valueOf(Color.WHITE));
        } else {
            card.setCardBackgroundColor( Color.TRANSPARENT);
            icon.setIconTint(ColorStateList.valueOf(ContextCompat.getColor(getContext(), androidx.appcompat.R.attr.colorPrimary)));

        }
    }

    public boolean getSelected() {
        if (icon.getIconTint()==ColorStateList.valueOf(Color.WHITE)){
            return true;
        }else {
            return false;
        }
    }

}
