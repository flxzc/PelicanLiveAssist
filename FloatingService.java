package com.pelican.liveassist;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

public class FloatingService extends Service {
    WindowManager wm; WindowManager.LayoutParams lp; LinearLayout panel; TextView bubble;
    int teal=Color.rgb(0,229,176), bg=Color.rgb(7,18,27); boolean expanded=false;
    @Override public void onCreate(){super.onCreate();wm=(WindowManager)getSystemService(WINDOW_SERVICE);makeBubble();}
    int type(){return android.os.Build.VERSION.SDK_INT>=26?WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY:WindowManager.LayoutParams.TYPE_PHONE;}
    WindowManager.LayoutParams params(int w,int h){return new WindowManager.LayoutParams(w,h,type(),WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT);}
    void makeBubble(){
        bubble=new TextView(this);bubble.setText("8");bubble.setTextColor(Color.BLACK);bubble.setTextSize(22);bubble.setTypeface(null,Typeface.BOLD);bubble.setGravity(Gravity.CENTER);bubble.setBackgroundColor(teal);
        lp=params(dp(54),dp(54));lp.gravity=Gravity.TOP|Gravity.LEFT;lp.x=dp(18);lp.y=dp(180);
        bubble.setOnTouchListener(new View.OnTouchListener(){float sx,sy;int ox,oy;boolean moved;
            public boolean onTouch(View v,MotionEvent e){switch(e.getAction()){
                case MotionEvent.ACTION_DOWN:sx=e.getRawX();sy=e.getRawY();ox=lp.x;oy=lp.y;moved=false;return true;
                case MotionEvent.ACTION_MOVE:lp.x=ox+(int)(e.getRawX()-sx);lp.y=oy+(int)(e.getRawY()-sy);moved=true;wm.updateViewLayout(bubble,lp);return true;
                case MotionEvent.ACTION_UP:if(!moved)toggle();return true;}return true;}});
        wm.addView(bubble,lp);
    }
    void toggle(){if(expanded){wm.removeView(panel);panel=null;expanded=false;return;}showPanel();}
    TextView label(String s){TextView t=new TextView(this);t.setText(s);t.setTextColor(Color.WHITE);t.setTextSize(14);t.setPadding(dp(8),dp(7),dp(8),dp(7));return t;}
    void showPanel(){
        panel=new LinearLayout(this);panel.setOrientation(LinearLayout.VERTICAL);panel.setPadding(dp(12),dp(10),dp(12),dp(10));panel.setBackgroundColor(bg);
        TextView head=label("PELICAN • VISUAL ASSIST");head.setTextColor(teal);head.setTypeface(null,Typeface.BOLD);panel.addView(head);
        panel.addView(label("Drag the 8 bubble to move this panel."));
        CheckBox line=new CheckBox(this);line.setText("Full-line guide");line.setTextColor(Color.WHITE);line.setButtonTintList(android.content.res.ColorStateList.valueOf(teal));line.setChecked(true);panel.addView(line);
        CheckBox markers=new CheckBox(this);markers.setText("Ball markers");markers.setTextColor(Color.WHITE);markers.setButtonTintList(android.content.res.ColorStateList.valueOf(teal));markers.setChecked(true);panel.addView(markers);
        panel.addView(label("Manual angle guide:"));SeekBar angle=new SeekBar(this);angle.setMax(360);angle.setProgress(90);panel.addView(angle);
        panel.addView(label("Power guide:"));SeekBar power=new SeekBar(this);power.setMax(100);power.setProgress(70);panel.addView(power);
        panel.addView(label("Guides are controls only; they do not overlay trajectory graphics on the game."));
        Button close=new Button(this);close.setText("CLOSE PANEL");close.setOnClickListener(v->toggle());panel.addView(close);
        WindowManager.LayoutParams pp=params(dp(260),-2);pp.gravity=Gravity.TOP|Gravity.LEFT;pp.x=lp.x;pp.y=lp.y+dp(60);
        wm.addView(panel,pp);expanded=true;
    }
    int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+0.5f);}
    @Override public int onStartCommand(Intent i,int f,int id){return START_STICKY;}
    @Override public void onDestroy(){try{if(panel!=null)wm.removeView(panel);if(bubble!=null)wm.removeView(bubble);}catch(Exception ignored){}super.onDestroy();}
    @Override public IBinder onBind(Intent i){return null;}
}
