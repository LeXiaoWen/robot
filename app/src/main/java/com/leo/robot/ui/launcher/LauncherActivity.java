package com.leo.robot.ui.launcher;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leo.robot.R;
import com.leo.robot.service.NettyService;
import com.leo.robot.ui.main.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import cree.mvp.util.permissions.PermissionsUtils;
import cree.mvp.util.permissions.rx.PerAction1;
import cree.mvp.util.permissions.rx.PerActionError;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
* 启动页
*
*@author Leo
*created at 2019/4/14 6:09 PM
*/

public class LauncherActivity extends AppCompatActivity {

    private int count = 0;
    private int sumCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wire_stripping1);
        ButterKnife.bind(this);
//        initService();
//        initPermisson();
    }

    /**
    * 申请权限
    *
    *@author Leo
    *created at 2019/4/14 6:08 PM
    */

    private void initPermisson() {
        applyPermisson(Manifest.permission.CAMERA);
        applyPermisson(Manifest.permission.READ_EXTERNAL_STORAGE);
        applyPermisson(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /**
    * 启动service
    *
    *@author Leo
    *created at 2019/4/14 6:08 PM
    */
    private void initService() {
        final Intent intent = new Intent(getApplication(), NettyService.class);
        startService(intent);
    }


    private void applyPermisson(String... permission) {
        PermissionsUtils
                .<Boolean>getInstance(this)
                .request(permission)
                .subscrbe(new PerAction1() {
                    @Override
                    public void onCallBack(Boolean aBoolean) {
                        count++;
                        if (aBoolean) {
                            if (count == sumCount) {
                                init();
                            }
                        }
                    }
                }, new PerActionError() {
                    @Override
                    public void onCallEror(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }


    private void init() {
        countdown(3)
                .doOnSubscribe(() -> {
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                    }
                });
    }


    /**
     * 倒计时
     *
     * @author Leo
     * created at 2019/4/14 6:08 PM
     */
    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(increaseTime -> countTime - increaseTime.intValue())
                .take(countTime + 1);

    }

}
