package com.leo.robot.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leo.robot.R;

import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }



}
