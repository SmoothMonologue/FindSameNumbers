package com.example.gh;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NumberButton extends FrameLayout {
    int value;
    Character cover;
    TextView textView;

    public NumberButton(Context context) {
        super(context);
        textView=new TextView(context);
        addView(textView);
        setClickable(true);
        setBackgroundResource(R.drawable.button_selector);
        textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        textView.setTextSize(40);
    }

    public void hidden() {
        textView.setText(" ");
    }

    public void set(int a) {
        value=a;
    }

    public void set(Character unit) {
        cover=unit;
        textView.setText(String.valueOf(unit));
        if (unit == '^') {
            textView.setText("x²+y²");
        }
    }

    public void openCover() {
        textView.setText(String.valueOf(value));
    }

    public void closeCover() {
        textView.setText(String.valueOf(cover));
    }


    public int getValue() {
        return value;
    }

    public Character getCover() {
        return cover;
    }

    public void wrong() {
        textView.setBackgroundColor(Color.RED);
    }

    public void correct() {
        textView.setBackgroundColor(Color.WHITE);
    }
}
