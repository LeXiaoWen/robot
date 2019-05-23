package com.leo.robot.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.leo.robot.R;
import com.leo.robot.constant.UrlConstant;
import com.leo.robot.test.netty.NettyClient;
import com.leo.robot.test.netty.NettyListener;
import cree.mvp.util.develop.LogUtils;

public class TestActivity extends AppCompatActivity {






    @BindView(R.id.view)
    LinearLayout mView;
    @BindView(R.id.tv)
    TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        initNetty();
    }

    private void initNetty() {
        NettyClient client = new NettyClient();

        client.setListener(new NettyListener() {

            @Override
            public void onMessageResponse(byte[] messageHolder) {
                LogUtils.e("接收到的数据  ： "  + messageHolder);
//                try {
//                    Thread.sleep(2000);
//                    Message message = new Message();
//                    message.what = 0X11;
//                    message.obj = messageHolder;
//                    myHandler.handleMessage(message);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {

            }
        });

        if (!client.getConnectStatus()) {
            new Thread(() -> {
                client.connect(UrlConstant.MASTER_NETTY_HOST, UrlConstant.SOCKET_PORT);//连接服务器
            }).start();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0X11:
                    byte[] bytes = (byte[]) msg.obj;
                    byte[] bytes1 = subByte(bytes, 252, 48);
                    LogUtils.e("截取到的数据 ： " + bytesToHexString(bytes));

                    break;
            }
        }
    };

    public byte[] subByte(byte[] b,int off,int length){
        byte[] b1 = new byte[length];
        System.arraycopy(b, off, b1, 0, length);
        return b1;
    }

    public String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private String test(byte[] result){
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        for(int i=0;i<result.length;i++){
            sb.append(result[i]+",");
        }
        sb.append("}");
        return sb.toString();
    }

}
