package app.softpower.prototype07312;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.List;

public class AppInfoUtil {
    public void getAppList(Context context){
        PackageManager pkgm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> AppInfos = pkgm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : AppInfos) {
            ActivityInfo ai = info.activityInfo;
            Log.i("cis : App title: ", ai.loadLabel(pkgm).toString());
            Log.i("cis : App Package Name: ", ai.packageName);
            Log.i("cis : App Class name: ", ai.name);
            int resId = ai.applicationInfo.icon;
            Log.i("cis : App icon: ", String.valueOf(resId));
        }
    }
}
