package com.example.breathe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.breathe.util.Prefs;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView breathsTxt, timeTxt, sessionTxt, guideTxt;
    private Button button;
    private Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.flower);
        breathsTxt=findViewById(R.id.breathtakentxt);
        timeTxt=findViewById(R.id.lastbreathtxt);
        sessionTxt=findViewById(R.id.todayminutestxt);
        button=findViewById(R.id.startbtn);
        guideTxt=findViewById(R.id.guidetxt);
        prefs=new Prefs(this);

        sessionTxt.setText(MessageFormat.format("{0} min today", prefs.getSessions()));
        breathsTxt.setText(MessageFormat.format("{0} breaths", prefs.getBreaths()));
        timeTxt.setText(prefs.getDate());

        startIntroAnimation();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });


    }
    private void startIntroAnimation(){
        ViewAnimator
                .animate(guideTxt)
                .scale(0,1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Breathe");
                    }
                })
                .start();

    }

    private void startAnimation(){
        ViewAnimator
                .animate(imageView)
                .alpha(0,1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Inhale....\n....Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.02f,1.5f,0.02f)
                .rotation(360)
                .repeatCount(3)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideTxt.setText("Good job!");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs.setSessions(prefs.getSessions()+1);
                        prefs.setBreaths(prefs.getBreaths()+1);
                        prefs.setDate(System.currentTimeMillis());

                        new CountDownTimer(2000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                        }.start();

                    }
                })
                .start();

    }
}/*
    .animate(imageView)
        .translationY(-1000,0)
        .alpha(0,1)
        //.andAnimate(text)
        .dp().translationX(-20,0)
        .decelerate()
        .duration(2000)
        .thenAnimate(imageView)
        .scale(1f,0.5f,1f)
        .rotation(270)
        .repeatCount(2)
        .accelerate()
        .duration(2000)
        .start();*/