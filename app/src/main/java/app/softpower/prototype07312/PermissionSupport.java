package app.softpower.prototype07312;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.Q)
public class PermissionSupport {

    private final Context context;
    private final Activity activity;

    protected final String[] permissions = {                                                                // Manifest 에 권한을 작성 후 요청할 권한을 배열로 저장
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACTIVITY_RECOGNITION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private List<Object> permissionList;                                                            // 권한 요청을 할 때 발생하는 창에 대한 결과값

    private final int MULTIPLE_PERMISSIONS = 1023;

    public PermissionSupport(Activity _activity, Context _context) {
        this.activity = _activity;
        this.context = _context;
    }

    public boolean checkPermission() {                                                              // 허용할 권한 요청이 남았는지 체크
        int result;
        permissionList = new ArrayList<>();

        for (String pm : permissions) {                                                             // 배열로 저장한 권한 중 허용되지 않은 권한이 있는지 체크
            result = ContextCompat.checkSelfPermission(context, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        return permissionList.isEmpty();
    }

    public void requestPermission() {                                                               // 권한 허용 요청
        ActivityCompat.requestPermissions(activity, permissionList.
                        toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
    }

    public boolean permissionResult(int requestCode, @NonNull String[] permissions,                 // 권한 요청에 대한 결과 처리
                                    @NonNull int[] grantResults){
        if (requestCode == MULTIPLE_PERMISSIONS && (grantResults.length > 0)) {
            for (int i = 0; i < grantResults.length; i++){
                // grantResults == 0 사용자가 허용한 것
                // grantResults == -1 사용자가 거부한 것
                if (grantResults[i] == -1){
                    return false;
                }
            }
        }
        return true;
    }
}























