package com.leo.robot.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.leo.robot.JNIUtils;
import com.leo.robot.R;

public class URTestActivity extends Activity {

    byte[] chars = new byte[8];
    @BindView(R.id.sample_text)
    TextView mSampleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urtest);
        ButterKnife.bind(this);
        initData1();
        JNIUtils utils = JNIUtils.getInstance();
        String s = utils.getDouble(3.141592);
        String stopJ = utils.ActionStopJ();
        String joint = utils.ActionJoint("ACTION_J0_1");
        double v = utils.HexToDouble(chars);
        mSampleText.setText(String.valueOf(v));
    }

    private void initData() {
        chars[0] = 0x54;
        chars[1] = (byte) 0xE3;
        chars[2] = (byte) 0xA5;
        chars[3] = (byte) 0x9B;
        chars[4] = (byte) 0xC4;
        chars[5] = 0x20;
        chars[6] = 0x09;
        chars[7] = 0x40;
    }

    private void initData1() {
        chars[7] = (byte) 0xD5;
        chars[6] = (byte) 0x78;
        chars[5] = (byte) 0xE9;
        chars[4] = (byte) 0x26;
        chars[3] = (byte) 0x31;
        chars[2] = (byte) 0x48;
        chars[1] = (byte) 0x24;
        chars[0] = (byte) 0x40;
    }

}
