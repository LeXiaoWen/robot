package com.leo.robot.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.leo.robot.JNIUtils;
import com.leo.robot.R;

public class URTestActivity extends Activity {

    @BindView(R.id.sample_text)
    TextView mSampleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urtest);
        ButterKnife.bind(this);
        JNIUtils utils = JNIUtils.getInstance();
        String s = utils.getDouble(3.141592);
        String stopJ = utils.ActionStopJ();
        String joint = utils.ActionJoint("ACTION_J0_1");
        mSampleText.setText(joint);
    }

}
