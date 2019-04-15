package com.leo.robot.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leo.robot.R;
import com.leo.robot.base.NettyActivity;
import com.leo.robot.netty.NettyClient;
import com.leo.robot.netty.bean.ChatInfo;
import com.leo.robot.netty.bean.NettyBaseFeed;
import com.leo.robot.view.RockerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.leo.robot.view.RockerView.DirectionMode.DIRECTION_8;

/**
 * created by Leo on 2019/4/14 18 : 11
 */


public class MainActivity extends NettyActivity<MainActivityPresenter> implements RockerView.OnShakeListener {

    @BindView(R.id.terminal)
    TextView mTerminal;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.my_rocker)
    RockerView mMyRocker;
    @BindView(R.id.tv_now_shake)
    TextView mTvNowShake;
    @BindView(R.id.tv_now_angle)
    TextView mTvNowAngle;
    @BindView(R.id.tv_now_level)
    TextView mTvNowLevel;
    @BindView(R.id.tv_now_model)
    TextView mTvNowModel;

    private Gson mGson;

    @Override
    protected void notifyData(String message) {
        mTerminal.append("[接收]" + message);
        mTerminal.append("\n");
    }

    @Override
    protected void bindingDagger2(@Nullable Bundle bundle) {
        DaggerMainActivityComponent.create().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGson = new Gson();
        mMyRocker.setOnShakeListener(DIRECTION_8, this);
        mMyRocker.setOnAngleChangeListener(new RockerView.OnAngleChangeListener() {
            @Override
            public void onShakeStart() {

            }

            @Override
            public void angle(double angle) {
                mTvNowAngle.setText("当前角度：" + angle);
            }

            @Override
            public void onFinish() {

            }
        });
        mMyRocker.setOnDistanceLevelListener(new RockerView.OnDistanceLevelListener() {
            @Override
            public void onDistanceLevel(int level) {
                mTvNowLevel.setText("当前距离级别："+level);
            }
        });
    }


    private void sendMessage() {
        final String message = mEtContent.getText().toString();
        if (TextUtils.isEmpty(message)) {
            return;
        }
        final NettyBaseFeed<ChatInfo> baseFeed = new NettyBaseFeed<>();
        baseFeed.setModule(1);
        baseFeed.setCmd(2);
        final ChatInfo chatInfo = new ChatInfo();
        baseFeed.setData(chatInfo);
        chatInfo.setChatType(2);
        chatInfo.setFrom(1);
        chatInfo.setTo(50);
        chatInfo.setMsgType(1);
        chatInfo.setMessage(message);
        NettyClient.getInstance().sendMessage(baseFeed, null);
//        notifyData(mGson.toJson(baseFeed));
        mEtContent.setText(null);
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        sendMessage();
    }

    @Override
    public void onShakeStart() {

    }

    @Override
    public void direction(RockerView.Direction direction) {
        if (direction == RockerView.Direction.DIRECTION_CENTER) {
            mTvNowShake.setText("当前方向：中心");
        } else if (direction == RockerView.Direction.DIRECTION_DOWN) {
            mTvNowShake.setText("当前方向：下");
        } else if (direction == RockerView.Direction.DIRECTION_LEFT) {
            mTvNowShake.setText("当前方向：左");
        } else if (direction == RockerView.Direction.DIRECTION_UP) {
            mTvNowShake.setText("当前方向：上");
        } else if (direction == RockerView.Direction.DIRECTION_RIGHT) {
            mTvNowShake.setText("当前方向：右");
        } else if (direction == RockerView.Direction.DIRECTION_DOWN_LEFT) {
            mTvNowShake.setText("当前方向：左下");
        } else if (direction == RockerView.Direction.DIRECTION_DOWN_RIGHT) {
            mTvNowShake.setText("当前方向：右下");
        } else if (direction == RockerView.Direction.DIRECTION_UP_LEFT) {
            mTvNowShake.setText("当前方向：左上");
        } else if (direction == RockerView.Direction.DIRECTION_UP_RIGHT) {
            mTvNowShake.setText("当前方向：右上");
        }
    }

    @Override
    public void onFinish() {

    }
}
