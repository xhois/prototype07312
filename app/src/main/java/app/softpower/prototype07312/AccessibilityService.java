package app.softpower.prototype07312;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import java.util.List;

public class AccessibilityService extends android.accessibilityservice.AccessibilityService {
    private static final String TAG = "cis";

    // 이벤트가 발생할 때마다 실행되는 부분
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Log.e(TAG, "Catch Event : " + event.toString());
//        Log.e(TAG, "Catch Event Package Name : " + event.getPackageName());
//        Log.e(TAG, "Catch Event TEXT : " + event.getText());
//        Log.e(TAG, "Catch Event ContentDescription : " + event.getContentDescription());
//        Log.e(TAG, "Catch Event getSource : " + event.getSource());
//        Log.e(TAG, "===================================================================");

//        int eventType = event.getEventType();
//        String packageName = event.getPackageName().toString();
//        String className = event.getClassName().toString();
//        List<CharSequence> text = event.getText();
//        boolean enabled = event.isEnabled();

//        Log.e("cis", "event.toString: "+event.toString());
//        Log.e("cis", String.valueOf("getPackageName: "+event.getPackageName() +
//                ", getEventType: "+event.getEventType() +
//                ", getSource: "+event.getSource() +
//                ", getText: "+event.getText() +
//                ", getContentDescription: "+event.getContentDescription() +
//                ", getAction: "+event.getAction() +
//                ", getContentChangeTypes: "+event.getContentChangeTypes() +
//                ", getMovementGranularity: "+event.getMovementGranularity() +
//                ", getClassName: "+event.getClassName()
//        ));


//        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && packageName.equals("com.android.settings") && className.equals("com.android.settings.SubSettings")
//                && text.equals(getPackageName()) && enabled) {
//            Log.e("cis", "안들어와?");
//            Intent intent = new Intent(this, AuthorityActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("key", "ok3_7");
//            getApplicationContext().startActivity(intent);
//        }


    }



    // 접근성 권한을 가지고, 연결이 되면 호출되는 함수
    public void onServiceConnected() {
//        Log.e("cis", "왔나?");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();

        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK; // 전체 이벤트 가져오기
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC | AccessibilityServiceInfo.FEEDBACK_HAPTIC;
        info.notificationTimeout = 100; // millisecond

        setServiceInfo(info);

        SharedPreferences pref = getSharedPreferences("firstPermissionAccessibility", Activity.MODE_PRIVATE);
        boolean firstPermissionAccessibility = pref.getBoolean("firstPermissionAccessibility", true);

        if (firstPermissionAccessibility) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("firstPermissionAccessibility", false);
            editor.commit();

            Intent intent = new Intent(this, AuthorityActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("key", "ok3_7");
            startActivity(intent);
        }
    }

    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub
        Log.e(TAG, "OnInterrupt");
    }

}