/*
 * Copyright (c) 2014, 青岛司通科技有限公司 All rights reserved.
 * File Name：LoadingDialog.java
 * Version：V1.0
 * Author：zhaokaiqiang
 * Date：2014-10-24
 */

package com.example.animationloading;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class LoadingDialog extends Dialog {

	protected static final String TAG = "LoadingDialog";
	// 动画间隔
	private static final int DURATION = 800;
	// 前景图片
	private ImageView img_front;
	// 定时器，用来不断的播放动画
	private Timer animationTimer;
	// 旋转动画
	private RotateAnimation animationL2R;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			img_front.setAnimation(animationL2R);
			animationL2R.start();
		};

	};

	public LoadingDialog(Context context) {
		super(context, R.style.dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loading);

		img_front = (ImageView) findViewById(R.id.img_front);
		animationTimer = new Timer();

		// 从左到右的旋转动画，设置旋转角度和旋转中心
		animationL2R = new RotateAnimation(0f, -90f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 设置动画的运行时长
		animationL2R.setDuration(DURATION);
		// 动画运行结束之后，保存结束之后的状态
		animationL2R.setFillAfter(true);
		// 设置重复的次数
		animationL2R.setRepeatCount(1);
		animationL2R.setRepeatMode(Animation.REVERSE);
		// 执行间隔任务，开始间隔是0，每隔DURATION * 2执行一次
		animationTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.sendEmptyMessage(1);
			}
		}, 0, DURATION * 2);

	}

	@Override
	protected void onStop() {
		super.onStop();
		animationTimer.cancel();
	}

}