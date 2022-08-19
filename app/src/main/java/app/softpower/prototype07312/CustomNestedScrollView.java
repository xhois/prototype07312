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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import java.util.function.Consumer;

public class CustomNestedScrollView extends NestedScrollView {
    GestureDetector gestureDetector;

    private Runnable scrollerTask;
    private int initialPosition;

    private int newCheck = 10;
    private static final String TAG = "CustomScrollView";

    public interface OnScrollStoppedListener {
        void onScrollStopped();
    }

    private CustomNestedScrollView.OnScrollStoppedListener onScrollStoppedListener;

    public CustomNestedScrollView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
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
                    CustomNestedScrollView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };

        init(context);
    }

    public void setOnScrollStoppedListener(CustomNestedScrollView.OnScrollStoppedListener listener){
        onScrollStoppedListener = listener;
    }

    public void startScrollerTask() {
        initialPosition = getScrollY();
        CustomNestedScrollView.this.postDelayed(scrollerTask, newCheck);
    }

    public CustomNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.i("cis", "NestedScroll " + "onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.i("cis", "NestedScroll " + "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i("cis", "NestedScroll " + "onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i("cis", "NestedScroll " + "onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("cis", "NestedScroll " + "onLongPress");

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i("cis", "NestedScroll " + "onFling");
                return false;
            }
        });
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Log.i("cis", "onFocusChanged");
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        Log.i("cis", "onFocusChanged");
        super.onPopulateAccessibilityEvent(event);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        Log.i("cis", "onPopulateAccessibilityEvent");
        super.onInitializeAccessibilityEvent(event);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        Log.i("cis", "onInitializeAccessibilityNodeInfo");
        super.onInitializeAccessibilityNodeInfo(info);
    }

    @Override
    public void onProvideStructure(ViewStructure structure) {
        Log.i("cis", "onProvideStructure");
        super.onProvideStructure(structure);
    }

    @Override
    public void onProvideAutofillStructure(ViewStructure structure, int flags) {
        Log.i("cis", "onProvideAutofillStructure");
        super.onProvideAutofillStructure(structure, flags);
    }

    @Override
    public void onProvideContentCaptureStructure(@NonNull ViewStructure structure, int flags) {
        Log.i("cis", "onProvideContentCaptureStructure");
        super.onProvideContentCaptureStructure(structure, flags);
    }

    @Override
    public void onProvideVirtualStructure(ViewStructure structure) {
        Log.i("cis", "onProvideVirtualStructure");
        super.onProvideVirtualStructure(structure);
    }

    @Override
    public void onProvideAutofillVirtualStructure(ViewStructure structure, int flags) {
        Log.i("cis", "onProvideAutofillVirtualStructure");
        super.onProvideAutofillVirtualStructure(structure, flags);
    }

    @Nullable
    @Override
    public ContentInfo onReceiveContent(@NonNull ContentInfo payload) {
        Log.i("cis", "onReceiveContent");
        return super.onReceiveContent(payload);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        Log.i("cis", "onApplyWindowInsets");
        return super.onApplyWindowInsets(insets);
    }

    @Override
    public void onStartTemporaryDetach() {
        Log.i("cis", "onStartTemporaryDetach");
        super.onStartTemporaryDetach();
    }

    @Override
    public void onFinishTemporaryDetach() {
        Log.i("cis", "onFinishTemporaryDetach");
        super.onFinishTemporaryDetach();
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        Log.i("cis", "onFilterTouchEventForSecurity");
        return super.onFilterTouchEventForSecurity(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.i("cis", "onWindowFocusChanged");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        Log.i("cis", "onVisibilityChanged");
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onDisplayHint(int hint) {
        Log.i("cis", "onDisplayHint");
        super.onDisplayHint(hint);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        Log.i("cis", "onWindowVisibilityChanged");
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        Log.i("cis", "onVisibilityAggregated");
        super.onVisibilityAggregated(isVisible);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        Log.i("cis", "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Log.i("cis", "onKeyPreIme");
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("cis", "onKeyDown");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        Log.i("cis", "onKeyLongPress");
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("cis", "onKeyUp");
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        Log.i("cis", "onKeyMultiple");
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        Log.i("cis", "onKeyShortcut");
        return super.onKeyShortcut(keyCode, event);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        Log.i("cis", "onCheckIsTextEditor");
        return super.onCheckIsTextEditor();
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Log.i("cis", "onCreateInputConnection");
        return super.onCreateInputConnection(outAttrs);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        Log.i("cis", "onCreateContextMenu");
        super.onCreateContextMenu(menu);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        Log.i("cis", "onTrackballEvent");
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onRequestSendAccessibilityEvent(View child, AccessibilityEvent event) {
        Log.i("cis", "onRequestSendAccessibilityEvent");
        return super.onRequestSendAccessibilityEvent(child, event);
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        Log.i("cis", "onResolvePointerIcon");
        return super.onResolvePointerIcon(event, pointerIndex);
    }

    @Override
    public void onPointerCaptureChange(boolean hasCapture) {
        Log.i("cis", "onPointerCaptureChange");
        super.onPointerCaptureChange(hasCapture);
    }

    @Override
    public boolean onCapturedPointerEvent(MotionEvent event) {
        Log.i("cis", "onCapturedPointerEvent");
        return super.onCapturedPointerEvent(event);
    }

    @Override
    public void onScrollCaptureSearch(@NonNull Rect localVisibleRect, @NonNull Point windowOffset, @NonNull Consumer<ScrollCaptureTarget> targets) {
        Log.i("cis", "onScrollCaptureSearch");
        super.onScrollCaptureSearch(localVisibleRect, windowOffset, targets);
    }

    @Override
    public void onCreateViewTranslationRequest(@NonNull int[] supportedFormats, @NonNull Consumer<ViewTranslationRequest> requestsCollector) {
        Log.i("cis", "onCreateViewTranslationRequest");
        super.onCreateViewTranslationRequest(supportedFormats, requestsCollector);
    }

    @Override
    public void onCreateVirtualViewTranslationRequests(@NonNull long[] virtualIds, @NonNull int[] supportedFormats, @NonNull Consumer<ViewTranslationRequest> requestsCollector) {
        Log.i("cis", "onCreateVirtualViewTranslationRequests");
        super.onCreateVirtualViewTranslationRequests(virtualIds, supportedFormats, requestsCollector);
    }

    @Override
    public void onViewTranslationResponse(@NonNull ViewTranslationResponse response) {
        Log.i("cis", "onViewTranslationResponse");
        super.onViewTranslationResponse(response);
    }

    @Override
    public void onVirtualViewTranslationResponses(@NonNull LongSparseArray<ViewTranslationResponse> response) {
        Log.i("cis", "onVirtualViewTranslationResponses");
        super.onVirtualViewTranslationResponses(response);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        Log.i("cis", "onInterceptHoverEvent");
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        Log.i("cis", "onNestedScroll");
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.i("cis", "onStartNestedScroll");
        return super.onStartNestedScroll(child, target, axes, type);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.i("cis", "onNestedScrollAccepted");
        super.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        Log.i("cis", "onStopNestedScroll");
        super.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.i("cis", "onNestedScroll");
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.i("cis", "onNestedPreScroll");
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        Log.i("cis", "onStartNestedScroll");
        return super.onStartNestedScroll(child, target, axes);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        Log.i("cis", "onNestedScrollAccepted");
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        Log.i("cis", "onStopNestedScroll");
        super.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i("cis", "onNestedScroll");
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        Log.i("cis", "onNestedPreScroll");
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.i("cis", "onNestedFling");
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        Log.i("cis", "onNestedPreFling");
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.i("cis", "onScrollChanged");
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("cis", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onAnimationStart() {
        Log.i("cis", "onAnimationStart");
        super.onAnimationStart();
    }

    @Override
    protected void onAnimationEnd() {
        Log.i("cis", "onAnimationEnd");
        super.onAnimationEnd();
    }

    @Override
    protected boolean onSetAlpha(int alpha) {
        Log.i("cis", "onSetAlpha");
        return super.onSetAlpha(alpha);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        Log.i("cis", "onDragEvent");
        return super.onDragEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("cis", "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("cis", "onTouchEvent");
        gestureDetector.onTouchEvent(ev);

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.i("cis", "onGenericMotionEvent");
        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        Log.i("cis", "onHoverEvent");
        return super.onHoverEvent(event);
    }

    @Override
    public void onHoverChanged(boolean hovered) {
        Log.i("cis", "onHoverChanged");
        super.onHoverChanged(hovered);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        Log.i("cis", "onOverScrolled");
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        Log.i("cis", "onRequestFocusInDescendants");
        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }

    @Override
    public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle args) {
        Log.i("cis", "onNestedPrePerformAccessibilityAction");
        return super.onNestedPrePerformAccessibilityAction(target, action, args);
    }

    @Override
    public void onViewAdded(View child) {
        Log.i("cis", "onViewAdded");
        super.onViewAdded(child);
    }

    @Override
    public void onViewRemoved(View child) {
        Log.i("cis", "onViewRemoved");
        super.onViewRemoved(child);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("cis", "onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onFinishInflate() {
        Log.i("cis", "onFinishInflate");
        super.onFinishInflate();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        Log.i("cis", "onCreateDrawableState");
        return super.onCreateDrawableState(extraSpace);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        Log.i("cis", "onDrawForeground");
        super.onDrawForeground(canvas);
    }

    @Override
    public void onAttachedToWindow() {
        Log.i("cis", "onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        Log.i("cis", "onScreenStateChanged");
        super.onScreenStateChanged(screenState);
    }

    @Override
    public void onRtlPropertiesChanged(int layoutDirection) {
        Log.i("cis", "onRtlPropertiesChanged");
        super.onRtlPropertiesChanged(layoutDirection);
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.i("cis", "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    public void onCancelPendingInputEvents() {
        Log.i("cis", "onCancelPendingInputEvents");
        super.onCancelPendingInputEvents();
    }

    @Override
    public void onDescendantInvalidated(@NonNull View child, @NonNull View target) {
        Log.i("cis", "onDescendantInvalidated");
        super.onDescendantInvalidated(child, target);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("cis", "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("cis", "onDraw");
        super.onDraw(canvas);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.i("cis", "onRestoreInstanceState");
        super.onRestoreInstanceState(state);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.i("cis", "onSaveInstanceState");
        return super.onSaveInstanceState();
    }
}
