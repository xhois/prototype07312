package app.softpower.prototype07312;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class MyNotificationListenerService extends NotificationListenerService {

    // 서비스가 연결되었을때 콜백됨
    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.e("cis", "MyNotificationListener.onListenerConnected() 호출됨");

        SharedPreferences pref = getSharedPreferences("firstPermissionNotificationListenerService", Activity.MODE_PRIVATE);
        boolean firstPermissionNotificationListenerService = pref.getBoolean("firstPermissionNotificationListenerService", true);

        if (firstPermissionNotificationListenerService) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("firstPermissionNotificationListenerService", false);
            editor.commit();

            Intent intent = new Intent(this, AuthorityActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("key", "ok5_7");
            startActivity(intent);
        }
    }

    // 서비스가 연결 해제되었을때 콜백됨
    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.e("cis", "MyNotificationListener.onListenerDisconnected() 호출됨");


    }

    // 알림 왔을때 콜백됨
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.e("cis", String.valueOf("MyNotificationListener.onNotificationPosted(sbn): "+sbn));
    }

    // 알림이 삭제 되었을때 콜백됨
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.e("cis", String.valueOf("MyNotificationListener.onNotificationRemoved(sbn): "+sbn));
    }
}