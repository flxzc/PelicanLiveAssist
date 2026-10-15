package com.pelican.liveassist;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
import android.graphics.Typeface;

public class MainActivity extends Activity {
    int bg=Color.rgb(7,18,27), teal=Color.rgb(0,229,176);
    @Override public void onCreate(Bundle b){super.onCreate(b); render();}
    TextView text(String s,int size,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);return t;}
    Button btn(String s){Button b=new Button(this);b.setText(s);b.setTextColor(Color.BLACK);b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(teal));return b;}
    void render(){
        LinearLayout page=new LinearLayout(this);page.setOrientation(1);page.setPadding(22,22,22,18);page.setBackgroundColor(bg);
        TextView title=text("PELICAN  /  LIVE ASSIST",23,teal);title.setTypeface(null,Typeface.BOLD);page.addView(title);
        page.addView(text("FLOATING VISUAL CONTROL PANEL",11,Color.LTGRAY));
        TextView note=text("A movable overlay for manual visual guides. It does not read the game screen or perform shots automatically.",13,Color.WHITE);note.setPadding(0,18,0,18);page.addView(note);
        Button grant=btn("1. ALLOW DISPLAY OVER OTHER APPS");page.addView(grant);
        grant.setOnClickListener(v->{Intent i=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));startActivity(i);});
        Button start=btn("2. START FLOATING PANEL");LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=14;page.addView(start,p);
        start.setOnClickListener(v->{if(!Settings.canDrawOverlays(this)){Toast.makeText(this,"Allow overlay permission first",Toast.LENGTH_LONG).show();return;}startService(new Intent(this,FloatingService.class));Toast.makeText(this,"Floating panel started. Return here to stop it.",Toast.LENGTH_SHORT).show();});
        Button stop=btn("STOP FLOATING PANEL");p=new LinearLayout.LayoutParams(-1,-2);p.topMargin=8;page.addView(stop,p);
        stop.setOnClickListener(v->stopService(new Intent(this,FloatingService.class)));
        TextView features=text("\nPANEL FEATURES\n• Draggable floating bubble\n• Expand/collapse menu\n• Full-line visual guide toggle\n• Ball-marker toggle\n• Manual angle and power guides\n\nLIMITS\nNo live game capture, hidden-ball ESP, or automatic shooting.",14,Color.WHITE);page.addView(features);
        setContentView(page);
    }
}
