package com.customradiobutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomRadioButton extends RelativeLayout implements RadioCheckable {


    // Attribute Variables
    private String mTitleValue;
    private String mDesValue;
    private boolean mIsSelected;


    // Views
    private TextView mTitle, mDes;
    private ImageButton mselected;

    private OnTouchListener mOnTouchListener;
    private OnClickListener mOnClickListener;
    private boolean mChecked;
    private ArrayList<OnCheckedChangeListener> mOnCheckedChangeListeners = new ArrayList<>();



    public CustomRadioButton(Context context) {
        super(context);
        setupView();

    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(attrs);
        setupView();
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(attrs);
        setupView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttributes(attrs);
        setupView();
    }

    // Template method
    private void setupView() {
        inflateView();
        bindView();
        setCustomTouchListener();
    }


    private void parseAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.CustomRadioButton, 0, 0);
        Resources resources = getContext().getResources();
        try {
            mTitleValue = a.getString(R.styleable.CustomRadioButton_title);
            mDesValue = a.getString(R.styleable.CustomRadioButton_description);
            mIsSelected = a.getBoolean(R.styleable.CustomRadioButton_isChecked, false);

        } finally {
            a.recycle();
        }
    }

    protected void inflateView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_radiobutton, this, true);
        mTitle = (TextView) findViewById(R.id.title);
        mDes = (TextView) findViewById(R.id.des);
        mselected = (ImageButton) findViewById(R.id.radio_view);
    }

    protected void bindView() {
        mTitle.setText(mTitleValue);
        mDes.setText(mDesValue);
        setChecked(mIsSelected);
    }

    protected void setCustomTouchListener() {
        super.setOnTouchListener(new TouchListener());
    }

    public String getmTitleValue() {
        return mTitleValue;
    }

    public void setmTitleValue(String mTitleValue) {
        this.mTitleValue = mTitleValue;
        mTitle.setText(mTitleValue);

    }

    public String getmDesValue() {
        return mDesValue;
    }

    public void setmDesValue(String mDesValue) {
        this.mDesValue = mDesValue;
        mDes.setText(mDesValue);

    }

    public boolean ismIsSelected() {
        return mIsSelected;
    }

    public void setmIsSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
        setChecked(mIsSelected);
    }

    @Override
    public void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.add(onCheckedChangeListener);

    }

    @Override
    public void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener);

    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            if (!mOnCheckedChangeListeners.isEmpty()) {
                for (int i = 0; i < mOnCheckedChangeListeners.size(); i++) {
                    mOnCheckedChangeListeners.get(i).onCheckedChanged(this, mChecked);
                }
            }
            if (mChecked) {
                setCheckedState();
            } else {
                setNormalState();
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;

    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    public void setCheckedState() {
        mselected.setSelected(true);

    }

    public void setNormalState() {
        mselected.setSelected(false);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOnClickListener = l;
    }


    private final class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onTouchDown(event);
                    break;
                case MotionEvent.ACTION_UP:
                    onTouchUp(event);
                    break;
            }
            if (mOnTouchListener != null) {
                mOnTouchListener.onTouch(v, event);
            }
            return true;
        }
    }


    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    public OnTouchListener getOnTouchListener() {
        return mOnTouchListener;
    }

    private void onTouchDown(MotionEvent motionEvent) {
        setChecked(true);
    }

    private void onTouchUp(MotionEvent motionEvent) {
        // Handle user defined click listeners
        if (mOnClickListener != null) {
            mOnClickListener.onClick(this);
        }
    }
}
