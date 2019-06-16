package com.leo.robot.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.ybq.android.spinkit.SpinKitView;
import com.leo.robot.JNIUtils;
import com.leo.robot.R;

public class URTestActivity extends Activity {
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.tv_signal)
    TextView mTvSignal;
    @BindView(R.id.tv_own_power)
    TextView mTvOwnPower;
    @BindView(R.id.tv_ground_power)
    TextView mTvGroundPower;
    @BindView(R.id.tv_line1)
    TextView mTvLine1;
    @BindView(R.id.tv_line2)
    TextView mTvLine2;
    @BindView(R.id.rl_main)
    RelativeLayout mRlMain;
    @BindView(R.id.ib_1)
    ImageButton mIb1;
    @BindView(R.id.ib_2)
    ImageButton mIb2;
    @BindView(R.id.tv_table1)
    TextView mTvTable1;
    @BindView(R.id.tv_table2)
    TextView mTvTable2;
    @BindView(R.id.btn_left)
    Button mBtnLeft;
    @BindView(R.id.btn_right)
    Button mBtnRight;
    @BindView(R.id.btn_horizontal_reset)
    Button mBtnHorizontalReset;
    @BindView(R.id.ll_level)
    LinearLayout mLlLevel;
    @BindView(R.id.btn_up)
    Button mBtnUp;
    @BindView(R.id.btn_down)
    Button mBtnDown;
    @BindView(R.id.btn_vertical_reset)
    Button mBtnVerticalReset;
    @BindView(R.id.ll_vertical)
    LinearLayout mLlVertical;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.spin_kit)
    SpinKitView mSpinKit;
    @BindView(R.id.ll_status)
    LinearLayout mLlStatus;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;

//    byte[] chars = new byte[8];
//    @BindView(R.id.sample_text)
//    TextView mSampleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fg_slide_table);
        ButterKnife.bind(this);
//        initData1();
        JNIUtils utils = JNIUtils.getInstance();
        String s = utils.getDouble(3.141592);
        String stopJ = utils.ActionStopJ();
        String joint = utils.ActionJoint("ACTION_J0_1");
//        double v = utils.HexToDouble(chars);
//        mSampleText.setText(String.valueOf(v));
    }


}
