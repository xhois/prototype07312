package app.softpower.prototype07312;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.AppOpsManagerCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import app.softpower.prototype07312.databinding.ActivityAuthorityBinding;
import app.softpower.prototype07312.databinding.ActivityTutorialBinding;

public class AuthorityActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAuthorityBinding binding;

    private boolean isCreateAccountPageOpen = false;
    private boolean isWhoPageOpen = false;
    private boolean isConnectDevicePageOpen = false;

    //권한관련
    private final static String LOG_TAG = "cis";
    DevicePolicyManager tDevicePolicyManager;
    ComponentName tDevicePolicyAdmin;

    Dialog dialog2_7;
    Dialog dialog3_7;
    Dialog dialog4_7;
    Dialog dialog5_7;
    Dialog dialog6_7;
    Dialog dialog7_7;
    boolean isShowDialog3_7 = false;
    boolean isShowDialog5_7 = false;


    private PermissionSupport permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthorityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        tDevicePolicyAdmin = new ComponentName(this, MyDevicePolicyReceiver.class);

        binding.buttonCreateAcount.setOnClickListener(this);
        binding.buttonLogin.setOnClickListener(this);
        binding.buttonCancel.setOnClickListener(this);
        binding.buttonCreate.setOnClickListener(this);
        binding.buttonParent.setOnClickListener(this);
        binding.buttonChild.setOnClickListener(this);
        binding.imageButtonUserChoice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.buttonCreateAcount) {
            binding.layoutLogin.setVisibility(View.GONE);
            binding.layoutCreate.setVisibility(View.VISIBLE);
            isCreateAccountPageOpen = true;
            return;
        }
        if (v == binding.buttonLogin) {
            binding.layoutLogin.setVisibility(View.GONE);
            binding.layoutWho.setVisibility(View.VISIBLE);
            isWhoPageOpen = true;
            return;
        }
        if (v == binding.buttonCancel) {
            binding.layoutCreate.setVisibility(View.GONE);
            binding.layoutLogin.setVisibility(View.VISIBLE);
            isCreateAccountPageOpen = false;
            return;
        }
        if (v == binding.buttonCreate) {
            Dialog dialog01;
            dialog01 = new Dialog(AuthorityActivity.this);
            dialog01.setContentView(R.layout.custom_dialog_confirm_account);
            dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

            dialog01.getWindow().setAttributes(params);
            dialog01.setCancelable(false);

            dialog01.show();
            Button agree = dialog01.findViewById(R.id.agree);
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog01.dismiss();

                    Dialog dialog02;
                    dialog02 = new Dialog(AuthorityActivity.this);
                    dialog02.setContentView(R.layout.custom_dialog_confirm_account2);
                    dialog02.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams params = dialog02.getWindow().getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;

                    dialog02.getWindow().setAttributes(params);
                    dialog02.setCancelable(false);

                    dialog02.show();
                    Button agree2 = dialog02.findViewById(R.id.agree2);
                    agree2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog02.dismiss();

                            binding.layoutCreate.setVisibility(View.GONE);
                            binding.layoutLogin.setVisibility(View.VISIBLE);
                            isCreateAccountPageOpen = false;
                        }
                    });
                }
            });
            Button disagree = dialog01.findViewById(R.id.disagree);
            disagree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog01.dismiss();
                }
            });

            return;
        }

        if (v == binding.buttonParent) {
            Dialog dialog01;
            dialog01 = new Dialog(AuthorityActivity.this);
            dialog01.setContentView(R.layout.custom_dialog_confirm_account2);
            dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            TextView title = dialog01.findViewById(R.id.textViewTitle);
            title.setText("사용 중인 권한 안내");
            TextView contents = dialog01.findViewById(R.id.textViewContents);
            contents.setText("모바일펜스는 아래의 기기의 권한을 다음과 같은 용도로 사용하고 있습니다.\n\n" +
                    "* 기기관리자 권한(BIND_DEVICE_ADMIN)\n" +
                    "- 기기 분실 시 원격에서 기기초기화\n" +
                    "- 기기 분실 시 원격에서 화면잠금/해제\n" +
                    "- 기기관리자 활성화/해제 모니터링\n\n" +
                    "* 접근성(BIND_ACCESSIBILITY_SERVICE)\n" +
                    "자녀가 크롬 시크릿 모드에서 성인,유해사이트에 방문하는 것을 차단\n\n" +
                    "* 위치권한\n" +
                    "이 앱은 자녀의 위치 파악을 위해서 앱을 닫거나 사용하지 않는 경우에도 위치 데이터를 수집합니다.\n\n" +
                    "모바일펜스는 전세계 수많은 사용자들로부터 사랑을 받고 있는 신뢰할 수 있는 앱 입니다. 사용권한에 대한" +
                    " 보다 투명한 정보제공을 위해서 안내를 드렸습니다. 감사합니다!");

            dialog01.getWindow().setAttributes(params);
            dialog01.setCancelable(false);

            dialog01.show();
            Button agree = dialog01.findViewById(R.id.agree2);
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog01.dismiss();
                    binding.layoutConnectDevice.setVisibility(View.VISIBLE);
                    binding.layoutWho.setVisibility(View.GONE);
                    isWhoPageOpen = false;
                    isConnectDevicePageOpen = true;
                }
            });
            return;
        }

        if (v == binding.imageButtonUserChoice) {
            Dialog dialog01;
            dialog01 = new Dialog(AuthorityActivity.this);
            dialog01.setContentView(R.layout.custom_dialog_authority);
            dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog01.getWindow().setAttributes(params);
            dialog01.setCancelable(false);

            dialog01.show();
            Button agree = dialog01.findViewById(R.id.agree);
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog01.dismiss();

                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, tDevicePolicyAdmin);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.admin_explanation));
//                    startActivityForResult(intent, REQUEST_ENABLE);
                    launcher.launch(intent);

                }
            });

            Button disagree = dialog01.findViewById(R.id.disagree);
            disagree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog01.dismiss();
                }
            });

            return;
        }

    }

    // 기기관리자(BIND_DEVICE_ADMIN) 권한
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        dialog2_7 = new Dialog(AuthorityActivity.this);
                        dialog2_7.setContentView(R.layout.custom_dialog_authority_image);
                        dialog2_7.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        WindowManager.LayoutParams params = dialog2_7.getWindow().getAttributes();
                        params.width = WindowManager.LayoutParams.MATCH_PARENT;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        dialog2_7.getWindow().setAttributes(params);
                        dialog2_7.setCancelable(false);

                        dialog2_7.show();
                        Button agree = dialog2_7.findViewById(R.id.agree);
                        agree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (hasPermissionToReadNetworkHistory()) {
                                    dialog2_7.dismiss();
                                    showDialog3_7();
                                }
                            }
                        });

                        Button disagree = dialog2_7.findViewById(R.id.disagree);
                        disagree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2_7.dismiss();
                            }
                        });

                    }
                }
            });

    private boolean hasPermissionToReadNetworkHistory() {
        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }
        appOps.startWatchingMode(AppOpsManager.OPSTR_GET_USAGE_STATS,
                getApplicationContext().getPackageName(),
                new AppOpsManager.OnOpChangedListener() {
                    @Override
                    public void onOpChanged(String op, String packageName) {
                        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                                android.os.Process.myUid(), getPackageName());
                        if (mode != AppOpsManager.MODE_ALLOWED) {
                            return;
                        }
                        appOps.stopWatchingMode(this);
                        Intent intent = new Intent(AuthorityActivity.this, AuthorityActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        isShowDialog3_7 = true;
//                        dialog2_7.dismiss();
//                        showDialog3_7();
                    }
                });

        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS, Uri.parse("package:" + getPackageName()));
        startActivity(intent);

        return false;
    }

    private boolean hasPermissionToSystemAlertWindow() {
        final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.permissionToOp("android.permission.SYSTEM_ALERT_WINDOW"), android.os.Process.myUid(), getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }
        appOps.startWatchingMode(AppOpsManager.permissionToOp("android.permission.SYSTEM_ALERT_WINDOW"),
                getApplicationContext().getPackageName(),
                new AppOpsManager.OnOpChangedListener() {
                    @Override
                    public void onOpChanged(String op, String packageName) {
                        int mode = appOps.checkOpNoThrow(AppOpsManager.permissionToOp("android.permission.SYSTEM_ALERT_WINDOW"),
                                android.os.Process.myUid(), getPackageName());
                        if (mode != AppOpsManager.MODE_ALLOWED) {
                            return;
                        }
                        appOps.stopWatchingMode(this);
                        Intent intent = new Intent(AuthorityActivity.this, AuthorityActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        isShowDialog5_7 = true;
//                        Log.e("cis", "ok");
                    }
                });

        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
        startActivity(intent);

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShowDialog3_7) {
            isShowDialog3_7 = false;
            dialog2_7.dismiss();
            showDialog3_7();
            return;
        }
        if (isShowDialog5_7) {
            isShowDialog5_7 = false;
            dialog4_7.dismiss();
            showDialog5_7();
            return;
        }

        try {
            Intent i = getIntent();
            String key = i.getStringExtra("key");
            if (key.equals("ok3_7")) {
                dialog3_7.dismiss();
                showDialog4_7();
            }
            if (key.equals("ok5_7")) {
                dialog5_7.dismiss();
                showDialog6_7();
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("cis", "onResume Exception: "+e.toString());
        }
    }

    private void showDialog3_7() {
        dialog3_7 = new Dialog(AuthorityActivity.this);
        dialog3_7.setContentView(R.layout.custom_dialog_authority_image);
        dialog3_7.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog3_7.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog3_7.getWindow().setAttributes(params);
        dialog3_7.setCancelable(false);
        ImageView imageView = dialog3_7.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.image_authority_3_7);
        TextView title = dialog3_7.findViewById(R.id.textViewTitle);
        title.setText("단계 3/7) 모니터링 기능 강화");
        TextView contents = dialog3_7.findViewById(R.id.textViewContents);
        contents.setText("웹사이트 모니터링, 유해차단을 위해 \"접근성\" 권한이 필요합니다. " +
                "아래 \"설정으로 이동\"을 누르고 [설치된 서비스] → 모바일펜스]" +
                " 로 이동하여 권한을 허용해주세요. 혹 권한을 켰는데 인식을 못하는 경우 권한을 한번 껐다가 다시 켜면 정상적으로 활성화됩니다.");

        dialog3_7.show();
        Button agree = dialog3_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAccessibilityPermissions();
            }
        });

        Button disagree = dialog3_7.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3_7.dismiss();
            }
        });
    }

    private void showDialog4_7() {
        dialog4_7 = new Dialog(AuthorityActivity.this);
        dialog4_7.setContentView(R.layout.custom_dialog_authority);
        dialog4_7.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog4_7.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog4_7.getWindow().setAttributes(params);
        dialog4_7.setCancelable(false);
        TextView title = dialog4_7.findViewById(R.id.textViewTitle);
        title.setText("단계 4/7) 안드로이드 권한 요청");
        TextView contents = dialog4_7.findViewById(R.id.textViewContents);
        contents.setText("사용을 제한할 앱 위에 차단 화면을 띄울 수 있도록 \"다른 앱 위에 표시되는 앱\" 권한이 필요합니다.");

        dialog4_7.show();
        Button agree = dialog4_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermissionToSystemAlertWindow()) {
                    dialog4_7.dismiss();
                    showDialog5_7();
                }
            }
        });

        Button disagree = dialog4_7.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog4_7.dismiss();
            }
        });
    }

    private void showDialog5_7() {
        dialog5_7 = new Dialog(AuthorityActivity.this);
        dialog5_7.setContentView(R.layout.custom_dialog_authority);
        dialog5_7.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog5_7.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog5_7.getWindow().setAttributes(params);
        dialog5_7.setCancelable(false);
        TextView title = dialog5_7.findViewById(R.id.textViewTitle);
        title.setText("단계 5/7) 안드로이드 권한 요청");
        TextView contents = dialog5_7.findViewById(R.id.textViewContents);
        contents.setText("아이가 전화를 받지 않을 때 기기의 무음 상태를 해제하고 벨소리를 최대로 높이는 기능을 제공하고 있습니다. 이를 위해서 \"알림 접근 허용\"권한이 필요합니다.");

        dialog5_7.show();
        Button agree = dialog5_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNotificationListenerPermissions();
            }
        });

        Button disagree = dialog5_7.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5_7.dismiss();
                finish();
            }
        });
    }

    private void showDialog6_7() {
        dialog6_7 = new Dialog(AuthorityActivity.this);
        dialog6_7.setContentView(R.layout.custom_dialog_authority);
        dialog6_7.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog6_7.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog6_7.getWindow().setAttributes(params);
        dialog6_7.setCancelable(false);
        TextView title = dialog6_7.findViewById(R.id.textViewTitle);
        title.setText("단계 6/7) 안드로이드 권한 요청");
        TextView contents = dialog6_7.findViewById(R.id.textViewContents);
        contents.setText("연락처, 전화, 내 신체 활동 정보, 기기 위치, 사진 및 미디어");

        dialog6_7.show();
        Button agree = dialog6_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission = new PermissionSupport(AuthorityActivity.this, getApplicationContext());  // 클래스 객체 생성
                if (!permission.checkPermission()){  // 권한 체크한 후에 리턴이 false 일 경우 권한 요청을 해준다.
                    permission.requestPermission();
                }
            }
        });

        Button disagree = dialog6_7.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog6_7.dismiss();
                finish();
            }
        });
    }

    private void showDialog7_7() {
        dialog7_7 = new Dialog(AuthorityActivity.this);
        dialog7_7.setContentView(R.layout.custom_dialog_authority);
        dialog7_7.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog7_7.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog7_7.getWindow().setAttributes(params);
        dialog7_7.setCancelable(false);
        TextView title = dialog7_7.findViewById(R.id.textViewTitle);
        title.setText("단계 7/7) 장치 연결");
        TextView contents = dialog7_7.findViewById(R.id.textViewContents);
        contents.setText("이 장치를 [xxxxx] 의 기기로 연결합니다.\n" +
                "맞습니까?\n\n" +
                "이 장치가 자녀의 기기이면 부모 프로필(경찰 이미지)에 절대 연결하지 마십시오. 연결할 사용자가 정확한지 다시 한번 확인하세요.");
        dialog7_7.show();
        Button agree = dialog7_7.findViewById(R.id.agree);
        agree.setText("확인");
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog7_7.dismiss();
            }
        });

        Button disagree = dialog7_7.findViewById(R.id.disagree);
        disagree.setText("취소");
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog7_7.dismiss();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {  // Request Permission 에 대한 결과 값을 받는다.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (!permission.permissionResult(requestCode, permissions, grantResults)){
            permission.requestPermission();
            return;
        }
        showDialog7_7();
    }

    public void setAccessibilityPermissions() {                   // 접근성 권한
//        AccessibilityManager accessibilityManager = (AccessibilityManager) getApplicationContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
//        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.DEFAULT);
//        Log.e("cis", "list: "+list.toString());
//        Log.e("cis", "getPackageName(): "+getPackageName());
//        if (list.contains(getPackageName())){
//            dialog3_7.dismiss();
//            showDialog4_7();
//        } else {
//            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//            startActivity(intent);
//        }
        //----------------------------------------------------------------------------------------------
//        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
//        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.DEFAULT);
//        Log.e("cis", "list: "+list.toString());
//
//        boolean ok = false;
//        for (int i = 0; i<list.size(); i++){
//            AccessibilityServiceInfo info = list.get(i);
//            Log.e("cis", "info: "+info.getResolveInfo().serviceInfo.packageName);
//            Log.e("cis", "getPackageName: "+getApplication().getPackageName());
//            if (info.getResolveInfo().serviceInfo.packageName.equals(getApplication().getPackageName())) {
//                ok = true;
//                break;
//            }
//        }
//        if (ok) {
//            dialog3_7.dismiss();;
//            showDialog4_7();
//        } else {
//            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//            startActivity(intent);
//        }
        //-------------------------------------------------------------------------

        AccessibilityManager accessibilityManager = (AccessibilityManager) getApplicationContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager.isEnabled()){
            dialog3_7.dismiss();
            showDialog4_7();
        } else {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }
    }

    public void setNotificationListenerPermissions() {                   // 알림접근허용 권한
        Set<String> sets = NotificationManagerCompat.getEnabledListenerPackages(getApplicationContext());
        if (sets != null && sets.contains(getPackageName())) {
            dialog5_7.dismiss();
            showDialog6_7();
        } else {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            setIntent(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (isCreateAccountPageOpen) {
            onClick(binding.buttonCancel);
            return;
        }
        if (isWhoPageOpen) {
            binding.layoutWho.setVisibility(View.GONE);
            binding.layoutLogin.setVisibility(View.VISIBLE);
            isWhoPageOpen = false;
            return;
        }
        if (isConnectDevicePageOpen) {
            binding.layoutWho.setVisibility(View.VISIBLE);
            binding.layoutConnectDevice.setVisibility(View.GONE);
            isConnectDevicePageOpen = false;
            return;
        }
        finish();
        Intent intent = new Intent(AuthorityActivity.this, TutorialActivity.class);
        startActivity(intent);

//        super.onBackPressed();
    }

    public static class MyDevicePolicyReceiver extends DeviceAdminReceiver {

        @Override
        public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
            super.onDisabled(context, intent);
            Toast.makeText(context, "Device Admin Disabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEnabled(Context context, Intent intent) {
            super.onEnabled(context, intent);
            Toast.makeText(context, "Device Admin is now enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public CharSequence onDisableRequested(Context context, Intent intent) {
            CharSequence disableRequestedSeq = "기기관리자 해제 시 해제 사실이 관리자(부모님)께 즉시 통보됩니다. 진행을 원하시면 동의 버튼을 누리세요.";
            return disableRequestedSeq;
        }

        @Override
        public void onPasswordChanged(Context context, Intent intent) {
            super.onPasswordChanged(context, intent);
//            Toast.makeText(context, "Device password is now changed", Toast.LENGTH_SHORT).show();
//            DevicePolicyManager localDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
//            ComponentName localComponent = new ComponentName(context, MyDevicePolicyReceiver.class);
//            localDPM.setPasswordExpirationTimeout(localComponent, 0L);
        }

        @Override
        public void onPasswordExpiring(Context context, Intent intent) {
            super.onPasswordExpiring(context, intent);
            // This would require API 11 an above
//            Toast.makeText(context, "Device password is going to expire, please change to a new password", Toast.LENGTH_SHORT).show();
//            DevicePolicyManager localDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
//            ComponentName localComponent = new ComponentName(context, MyDevicePolicyReceiver.class);
//            long expr = localDPM.getPasswordExpiration(localComponent);
//            long delta = expr - System.currentTimeMillis();
//            boolean expired = delta < 0L;
//            if (expired) {
//                localDPM.setPasswordExpirationTimeout(localComponent, 10000L);
//                Intent passwordChangeIntent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
//                passwordChangeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(passwordChangeIntent);
//            }
        }

        @Override
        public void onPasswordFailed(Context context, Intent intent) {
            super.onPasswordFailed(context, intent);
//            Toast.makeText(context, "Password failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPasswordSucceeded(Context context, Intent intent) {
            super.onPasswordSucceeded(context, intent);
//            Toast.makeText(context, "Access Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
        }
    }


}


































