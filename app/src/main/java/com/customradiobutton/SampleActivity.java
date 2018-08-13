package com.customradiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SampleActivity extends AppCompatActivity {

    private CustomRadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        radioGroup = (CustomRadioGroup) findViewById(R.id.customradiogroup);

        radioGroup.setOnCheckedChangeListener(new CustomRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {

                CustomRadioButton selectedradiobutton = ((CustomRadioButton) radioButton);

            }
        });

    }
}
