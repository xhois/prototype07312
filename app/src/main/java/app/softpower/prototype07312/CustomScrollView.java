package app.softpower.prototype07312;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    boolean isScrollViewSettingTop = true;
    int oldY = -1;


    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
//                Log.i("cis", "getScrollY() " + String.valueOf(getScrollY()));
                if (getScrollY() >= 1979){
                    isScrollViewSettingTop = true;
                } else {
                    isScrollViewSettingTop = false;
                }
                Log.i("cis", "oldY: " + oldY + ", newY: " + getScrollY());
                Log.i("cis", String.valueOf(isScrollViewSettingTop));
                oldY = getScrollY();
            }
        });
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (!isScrollViewSettingTop) return false;
//        else return true;
//
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("cis", String.valueOf(ev));
        if (isScrollViewSettingTop) return false;
        return true;
    }


}
