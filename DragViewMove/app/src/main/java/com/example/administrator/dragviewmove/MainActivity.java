package com.example.administrator.dragviewmove;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView view;
    private int currentX,currentY;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);
        sp = getSharedPreferences("position", Context.MODE_PRIVATE);
        final int top = sp.getInt("top",-100);
        final int left = sp.getInt("left",-100);
        final int right = sp.getInt("right",-100);
        final int bottom = sp.getInt("bottom",-100);
        if(top!=-100 && left!=-100 && right!=-100 && bottom!=-100){
            Log.e("11111",top+"11111"+left);
            //view.layout(left,top,right,bottom);
            /*RelativeLayout.LayoutParams layout=(RelativeLayout.LayoutParams)view.getLayoutParams();
            layout.setMargins(left,top,right,bottom);
            view.setLayoutParams(layout);*/
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

                @Override
                public void onGlobalLayout() {
                    view.layout(left,top,right,bottom);
                }
            });
        }
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (view.getId()) {
                    case R.id.view:
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                currentX = (int) event.getRawX();
                                currentY = (int) event.getRawY();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                int x = (int) event.getRawX();
                                int y = (int) event.getRawY();
                                int distanceX = x - currentX;
                                int distanceY = y - currentY;
                                int l = view.getLeft();
                                int r = view.getRight();
                                int t = view.getTop();
                                int b = view.getBottom();
                                view.layout(l + distanceX, t + distanceY, r + distanceX, b + distanceY);
                                // 获取移动后的位置
                                currentX = (int) event.getRawX();
                                currentY = (int) event.getRawY();
                                break;
                            case MotionEvent.ACTION_UP:
                                int top = view.getTop();
                                int left = view.getLeft();
                                int right = view.getRight();
                                int bottom = view.getBottom();
                                sp = getSharedPreferences("position", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("top", top);
                                editor.putInt("left", left);
                                editor.putInt("right", right);
                                editor.putInt("bottom", bottom);
                                editor.commit();
                                break;
                        }
                        break;
                }
                return true;
            }
        });
    }
}
