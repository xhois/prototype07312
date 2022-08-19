package app.softpower.prototype07312;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.ContentInfo;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.ScrollCaptureTarget;
import android.view.View;
import android.view.ViewStructure;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.translation.ViewTranslationRequest;
import android.view.translation.ViewTranslationResponse;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.function.Consumer;

public class CustomScrollView extends ScrollView {
    GestureDetector gestureDetector;

    private Runnable scrollerTask;
    private int initialPosition;

    private int newCheck = 10;
    private static final String TAG = "CustomScrollView";

    public interface OnScrollStoppedListener {
        void onScrollStopped();
    }

    private OnScrollStoppedListener onScrollStoppedListener;

    public CustomScrollView(Context context) {
        super(context);
        init(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        scrollerTask = new Runnable() {
            @Override
            public void run() {
                int newPosition = getScrollY();
                if (initialPosition - newPosition == 0) { // has stopped
                    if (onScrollStoppedListener != null) {
                        onScrollStoppedListener.onScrollStopped();
                    }
                } else {
                    initialPosition = getScrollY();
                    CustomScrollView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };

        init(context);
    }

    public void setOnScrollStoppedListener(CustomScrollView.OnScrollStoppedListener listener){
        onScrollStoppedListener = listener;
    }

    public void startScrollerTask() {
        initialPosition = getScrollY();
        CustomScrollView.this.postDelayed(scrollerTask, newCheck);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.i("cis", "Scroll " + "onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.i("cis", "Scroll " + "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i("cis", "Scroll " + "onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i("cis", "Scroll " + "onScroll ");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("cis", "Scroll " + "onLongPress");

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i("cis", "Scroll " + "onFling");
                return false;
            }
        });
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction,
                                  @Nullable Rect previouslyFocusedRect) {
        Log.i("cis", "ScrollView  " + "onFocusChanged");
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        Log.i("cis", "ScrollView  " + "onPopulateAccessibilityEvent");
        super.onPopulateAccessibilityEvent(event);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        Log.i("cis", "ScrollView  " + "onInitializeAccessibilityEvent");
        super.onInitializeAccessibilityEvent(event);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        Log.i("cis", "ScrollView  " + "onInitializeAccessibilityNodeInfo");
        super.onInitializeAccessibilityNodeInfo(info);
    }

    @Override
    public void onProvideStructure(ViewStructure structure) {
        Log.i("cis", "ScrollView  " + "onProvideStructure");
        super.onProvideStructure(structure);
    }

    @Override
    public void onProvideAutofillStructure(ViewStructure structure, int flags) {
        Log.i("cis", "ScrollView  " + "onProvideAutofillStructure");
        super.onProvideAutofillStructure(structure, flags);
    }

    @Override
    public void onProvideContentCaptureStructure(@NonNull ViewStructure structure, int flags) {
        Log.i("cis", "ScrollView  " + "onProvideContentCaptureStructure");
        super.onProvideContentCaptureStructure(structure, flags);
    }

    @Override
    public void onProvideVirtualStructure(ViewStructure structure) {
        Log.i("cis", "ScrollView  " + "onProvideVirtualStructure");
        super.onProvideVirtualStructure(structure);
    }

    @Override
    public void onProvideAutofillVirtualStructure(ViewStructure structure, int flags) {
        Log.i("cis", "ScrollView  " + "onProvideAutofillVirtualStructure");
        super.onProvideAutofillVirtualStructure(structure, flags);
    }

    @Nullable
    @Override
    public ContentInfo onReceiveContent(@NonNull ContentInfo payload) {
        Log.i("cis", "ScrollView  " + "onReceiveContent");
        return super.onReceiveContent(payload);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        Log.i("cis", "ScrollView  " + "onApplyWindowInsets");
        return super.onApplyWindowInsets(insets);
    }

    @Override
    public void onStartTemporaryDetach() {
        Log.i("cis", "ScrollView  " + "onStartTemporaryDetach");
        super.onStartTemporaryDetach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        Log.i("cis", "ScrollView  " + "onFinishTemporaryDetach");
        super.onFinishTemporaryDetach();
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        Log.i("cis", "ScrollView  " + "onFilterTouchEventForSecurity");
        return super.onFilterTouchEventForSecurity(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.i("cis", "ScrollView  " + "onWindowFocusChanged");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        Log.i("cis", "ScrollView  " + "onVisibilityChanged");
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onDisplayHint(int hint) {
        Log.i("cis", "ScrollView  " + "onDisplayHint");
        super.onDisplayHint(hint);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        Log.i("cis", "ScrollView  " + "onWindowVisibilityChanged");
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        Log.i("cis", "ScrollView  " + "onVisibilityAggregated");
        super.onVisibilityAggregated(isVisible);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        Log.i("cis", "ScrollView  " + "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Log.i("cis", "ScrollView  " + "onKeyPreIme");
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("cis", "ScrollView  " + "onKeyDown");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.i("cis", "ScrollView  " + "onKeyLongPress");
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("cis", "ScrollView  " + "onKeyUp");
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        Log.i("cis", "ScrollView  " + "onKeyMultiple");
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        Log.i("cis", "ScrollView  " + "onKeyShortcut");
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        Log.i("cis", "ScrollView  " + "onCheckIsTextEditor");
        return super.onCheckIsTextEditor();
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Log.i("cis", "ScrollView  " + "onCreateInputConnection");
        return super.onCreateInputConnection(outAttrs);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        Log.i("cis", "ScrollView  " + "onCreateContextMenu");
        super.onCreateContextMenu(menu);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        Log.i("cis", "ScrollView  " + "onTrackballEvent");
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onRequestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        Log.i("cis", "ScrollView  " + "onRequestSendAccessibilityEvent");
        return super.onRequestSendAccessibilityEvent(child, event);
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        Log.i("cis", "ScrollView  " + "onResolvePointerIcon");
        return super.onResolvePointerIcon(event, pointerIndex);
    }

    @Override
    public void onPointerCaptureChange(boolean hasCapture) {
        Log.i("cis", "ScrollView  " + "onPointerCaptureChange");
        super.onPointerCaptureChange(hasCapture);
    }

    @Override
    public boolean onCapturedPointerEvent(MotionEvent event) {
        Log.i("cis", "ScrollView  " + "onCapturedPointerEvent");
        return super.onCapturedPointerEvent(event);
    }

    @Override
    public void onScrollCaptureSearch(@NonNull Rect localVisibleRect, @NonNull Point
            windowOffset, @NonNull Consumer<ScrollCaptureTarget> targets) {
        Log.i("cis", "ScrollView  " + "onScrollCaptureSearch");
        super.onScrollCaptureSearch(localVisibleRect, windowOffset, targets);
    }

    @Override
    public void onCreateViewTranslationRequest(@NonNull int[] supportedFormats,
                                               @NonNull Consumer<ViewTranslationRequest> requestsCollector) {
        Log.i("cis", "ScrollView  " + "onCreateViewTranslationRequest");
        super.onCreateViewTranslationRequest(supportedFormats, requestsCollector);
    }

    @Override
    public void onCreateVirtualViewTranslationRequests(@NonNull long[] virtualIds,
                                                       @NonNull int[] supportedFormats, @NonNull Consumer<ViewTranslationRequest> requestsCollector) {
        Log.i("cis", "ScrollView  " + "onCreateVirtualViewTranslationRequests");
        super.onCreateVirtualViewTranslationRequests(virtualIds, supportedFormats, requestsCollector);
    }

    @Override
    public void onViewTranslationResponse(@NonNull ViewTranslationResponse response) {
        Log.i("cis", "ScrollView  " + "onViewTranslationResponse");
        super.onViewTranslationResponse(response);
    }

    @Override
    public void onVirtualViewTranslationResponses
            (@NonNull LongSparseArray<ViewTranslationResponse> response) {
        Log.i("cis", "ScrollView  " + "onVirtualViewTranslationResponses");
        super.onVirtualViewTranslationResponses(response);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        Log.i("cis", "ScrollView  " + "onInterceptHoverEvent");
        return super.onInterceptHoverEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("cis", "ScrollView  " + "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onAnimationStart() {
        Log.i("cis", "ScrollView  " + "onAnimationStart");
        super.onAnimationStart();
    }

    @Override
    protected void onAnimationEnd() {
        Log.i("cis", "ScrollView  " + "onAnimationEnd");
        super.onAnimationEnd();
    }

    @Override
    protected boolean onSetAlpha(int alpha) {
        Log.i("cis", "ScrollView  " + "onSetAlpha");
        return super.onSetAlpha(alpha);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        Log.i("cis", "ScrollView  " + "onDragEvent");
        return super.onDragEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("cis", "ScrollView  " + "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("cis", "ScrollView  " + "onTouchEvent");
        gestureDetector.onTouchEvent(ev);

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i("cis3", "ScrollView  " + "onScrollChanged");
        Log.i("cis3", "l: " + l + ", t: " + t + ", oldl: " + oldl + ", oldt: " + oldt);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.i("cis", "ScrollView  " + "onGenericMotionEvent");
        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        Log.i("cis", "ScrollView  " + "onHoverEvent");
        return super.onHoverEvent(event);
    }

    @Override
    public void onHoverChanged(boolean hovered) {
        Log.i("cis", "ScrollView  " + "onHoverChanged");
        super.onHoverChanged(hovered);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        Log.i("cis", "ScrollView  " + "onOverScrolled");
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        Log.i("cis", "ScrollView  " + "onRequestFocusInDescendants");
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }

    @Override
    public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle args) {
        Log.i("cis", "ScrollView  " + "onNestedPrePerformAccessibilityAction");
        return super.onNestedPrePerformAccessibilityAction(target, action, args);
    }

    @Override
    public void onViewAdded(View child) {
        Log.i("cis", "ScrollView  " + "onViewAdded");
        super.onViewAdded(child);
    }

    @Override
    public void onViewRemoved(View child) {
        Log.i("cis", "ScrollView  " + "onViewRemoved");
        super.onViewRemoved(child);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.i("cis", "ScrollView  " + "onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        Log.i("cis", "ScrollView  " + "onScreenStateChanged");
        super.onScreenStateChanged(screenState);
    }

    @Override
    public void onRtlPropertiesChanged(int layoutDirection) {
        Log.i("cis", "ScrollView  " + "onRtlPropertiesChanged");
        super.onRtlPropertiesChanged(layoutDirection);
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.i("cis", "ScrollView  " + "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    public void onCancelPendingInputEvents() {
        Log.i("cis", "ScrollView  " + "onCancelPendingInputEvents");
        super.onCancelPendingInputEvents();
    }

    @Override
    public void onDescendantInvalidated(@NonNull View child, @NonNull View target) {
        Log.i("cis", "ScrollView  " + "onDescendantInvalidated");
        super.onDescendantInvalidated(child, target);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("cis", "ScrollView  " + "onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onFinishInflate() {
        Log.i("cis", "ScrollView  " + "onFinishInflate");
        super.onFinishInflate();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        Log.i("cis", "ScrollView  " + "onCreateDrawableState");
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        Log.i("cis", "ScrollView  " + "onDrawForeground");
        super.onDrawForeground(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("cis", "ScrollView  " + "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("cis", "ScrollView  " + "onDraw");
        super.onDraw(canvas);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.i("cis", "ScrollView  " + "onStartNestedScroll");
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.i("cis", "ScrollView  " + "onNestedScrollAccepted");
        super.onNestedScrollAccepted(child, target, axes);
    }

//    public interface OnStopNestedScrollListener {
//        public void onStopNestedScroll(View target);
//    }
//
//    private OnStopNestedScrollListener mOnStopNestedScrollListener;
//
//    public void setOnStopNestedScrollListener(OnStopNestedScrollListener listener){
//        this.mOnStopNestedScrollListener = listener;
//    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.i("cis2", "ScrollView  " + "onStopNestedScroll, " + target);
        Log.i("cis2", "getTranslation  " + getTranslationY());
        Log.i("cis2", "getY  " + getY());
        Log.i("cis2", "getScrollY  " + getScrollY());
        Log.i("cis2", "getPivotY  " + getPivotY());
        Log.i("cis2", "getScaleY  " + getScaleY());
        super.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed) {
        Log.i("cis", "ScrollView  " + "onNestedScroll");
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.i("cis", "ScrollView  " + "onNestedPreScroll");
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i("cis", "ScrollView  " + "onNestedFling");
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.i("cis", "ScrollView  " + "onNestedPreFling");
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.i("cis", "ScrollView  " + "onRestoreInstanceState");
        super.onRestoreInstanceState(state);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.i("cis", "ScrollView  " + "onSaveInstanceState");
        return super.onSaveInstanceState();
    }
}
