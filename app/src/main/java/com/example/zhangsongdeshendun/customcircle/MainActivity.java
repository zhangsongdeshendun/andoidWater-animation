package com.example.zhangsongdeshendun.customcircle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomBudgetProgress progress1;
    private CustomBudgetProgress progress2;
    private CustomBudgetProgress progress3;
    private Button btn;

    private static final int MSG_UPDATE = 0X110;

    private int mPercent = 70;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int progresss1 = progress1.getProgress();
            int progresss2 = progress2.getProgress();
            int progresss3 = progress3.getProgress();
            if (progresss3 >= 100) {
                mHandler.removeMessages(MSG_UPDATE);
                return;
            }
            if (progresss1 < 50) {
                progress1.setProgress(++progresss1);
            }
            if (progresss2 < 70) {
                progress2.setProgress(++progresss2);
            }
            if (progresss3 < 100) {
                progress3.setProgress(++progresss3);
            }
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE, 30);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void drawProgress() {
        progress1.invalidate();
        progress2.invalidate();
        progress3.invalidate();
        mHandler.sendEmptyMessage(MSG_UPDATE);
    }

    private void initView() {
        progress1 = (CustomBudgetProgress) findViewById(R.id.progress1);
        progress2 = (CustomBudgetProgress) findViewById(R.id.progress2);
        progress3 = (CustomBudgetProgress) findViewById(R.id.progress3);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        drawProgress();
    }
}
