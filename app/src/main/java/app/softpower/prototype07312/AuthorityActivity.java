package app.softpower.prototype07312;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.AppOpsManagerCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import app.softpower.prototype07312.databinding.ActivityAuthorityBinding;
import app.softpower.prototype07312.databinding.ActivityTutorialBinding;

public class AuthorityActivity extends AppCompatActivity implements View.OnClickListener {
    public static Context mContext;

    private ActivityAuthorityBinding binding;

    private boolean isCreateAccountPageOpen = false;
    private boolean isWhoPageOpen = false;
    private boolean isConnectDevicePageOpen = false;

    //????????????
    private final static String LOG_TAG = "cis";
    DevicePolicyManager tDevicePolicyManager;
    ComponentName tDevicePolicyAdmin;

    private Dialog dialog1_7;
    private Dialog dialog2_7;
    private Dialog dialog3_7;
    private Dialog dialog4_7;
    private Dialog dialog5_7;
    private Dialog dialog6_7;
    private Dialog dialog62_7;
    private Dialog dialog7_7;

    boolean isShowDialog3_7 = false;
    boolean isShowDialog5_7 = false;

    private PermissionSupport permission;

    private boolean isFirstTimeRequest = true;

    // ???????????????(BIND_DEVICE_ADMIN) ??????
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        showDialog2_7();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
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
            title.setText("?????? ?????? ?????? ??????");
            TextView contents = dialog01.findViewById(R.id.textViewContents);
            contents.setText("?????????????????? ????????? ????????? ????????? ????????? ?????? ????????? ???????????? ????????????.\n\n" +
                    "* ??????????????? ??????(BIND_DEVICE_ADMIN)\n" +
                    "- ?????? ?????? ??? ???????????? ???????????????\n" +
                    "- ?????? ?????? ??? ???????????? ????????????/??????\n" +
                    "- ??????????????? ?????????/?????? ????????????\n\n" +
                    "* ?????????(BIND_ACCESSIBILITY_SERVICE)\n" +
                    "????????? ?????? ????????? ???????????? ??????,?????????????????? ???????????? ?????? ??????\n\n" +
                    "* ????????????\n" +
                    "??? ?????? ????????? ?????? ????????? ????????? ?????? ????????? ???????????? ?????? ???????????? ?????? ???????????? ???????????????.\n\n" +
                    "?????????????????? ????????? ????????? ????????????????????? ????????? ?????? ?????? ????????? ??? ?????? ??? ?????????. ??????????????? ??????" +
                    " ?????? ????????? ??????????????? ????????? ????????? ???????????????. ???????????????!");

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
            showDialog1_7();
        }
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
                getIntent().removeExtra("key");
                dialog3_7.dismiss();
                showDialog4_7();
                return;
            }
            if (key.equals("ok5_7")) {
                getIntent().removeExtra("key");
                dialog5_7.dismiss();
                showDialog6_7();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("cis", "onResume Exception: "+e.toString());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {
            setIntent(intent);
        }
    }

    private void showDialog1_7() {                                                                  // Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        dialog1_7 = new Dialog(AuthorityActivity.this);
        dialog1_7.setContentView(R.layout.custom_dialog_authority);
        dialog1_7.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog1_7.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog1_7.getWindow().setAttributes(params);
        dialog1_7.setCancelable(false);

        dialog1_7.show();
        Button agree = dialog1_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1_7.dismiss();

                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, tDevicePolicyAdmin);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.admin_explanation));
//                    startActivityForResult(intent, REQUEST_ENABLE);
                launcher.launch(intent);
            }
        });

        Button disagree = dialog1_7.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1_7.dismiss();
            }
        });
    }                                                              // Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)

    private void showDialog2_7() {
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
                final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
                int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
                if (mode == AppOpsManager.MODE_ALLOWED) {
                    dialog2_7.dismiss();
                    showDialog3_7();
                } else {
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
                                }
                            });

                    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS, Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
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
    }                                                              // Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS

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
        title.setText("?????? 3/7) ???????????? ?????? ??????");
        TextView contents = dialog3_7.findViewById(R.id.textViewContents);
        contents.setText("???????????? ????????????, ??????????????? ?????? \"?????????\" ????????? ???????????????. " +
                "?????? \"???????????? ??????\"??? ????????? [????????? ?????????] ??? ???????????????]" +
                " ??? ???????????? ????????? ??????????????????. ??? ????????? ????????? ????????? ????????? ?????? ????????? ?????? ????????? ?????? ?????? ??????????????? ??????????????????.");

        dialog3_7.show();
        Button agree = dialog3_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessibilityManager accessibilityManager = (AccessibilityManager) getApplicationContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
                SharedPreferences pref = getSharedPreferences("firstPermissionAccessibility", Activity.MODE_PRIVATE);
                boolean firstPermissionAccessibility = pref.getBoolean("firstPermissionAccessibility", true);
                if (accessibilityManager.isEnabled() && !firstPermissionAccessibility) {
                    dialog3_7.dismiss();
                    showDialog4_7();
                } else {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("firstPermissionAccessibility", true);
                    editor.apply();

                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                }
            }
        });

        Button disagree = dialog3_7.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3_7.dismiss();
            }
        });
    }                                                              // Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)

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
        title.setText("?????? 4/7) ??????????????? ?????? ??????");
        TextView contents = dialog4_7.findViewById(R.id.textViewContents);
        contents.setText("????????? ????????? ??? ?????? ?????? ????????? ?????? ??? ????????? \"?????? ??? ?????? ???????????? ???\" ????????? ???????????????.");

        dialog4_7.show();
        Button agree = dialog4_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
                int mode = appOps.checkOpNoThrow(AppOpsManager.permissionToOp("android.permission.SYSTEM_ALERT_WINDOW"), android.os.Process.myUid(), getPackageName());
                if (mode == AppOpsManager.MODE_ALLOWED) {
                    dialog4_7.dismiss();
                    showDialog5_7();
                } else {
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
    }                                                              // Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION

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
        title.setText("?????? 5/7) ??????????????? ?????? ??????");
        TextView contents = dialog5_7.findViewById(R.id.textViewContents);
        contents.setText("????????? ????????? ?????? ?????? ??? ????????? ?????? ????????? ???????????? ???????????? ????????? ????????? ????????? ???????????? ????????????. ?????? ????????? \"?????? ?????? ??????\"????????? ???????????????.");

        dialog5_7.show();
        Button agree = dialog5_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> sets = NotificationManagerCompat.getEnabledListenerPackages(getApplicationContext());
                if (sets.contains(getPackageName())) {
                    dialog5_7.dismiss();
                    showDialog6_7();
                } else {
                    SharedPreferences pref = getSharedPreferences("firstPermissionNotificationListenerService", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("firstPermissionNotificationListenerService", true);
                    editor.apply();

                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                    startActivity(intent);
                }
            }
        });

        Button disagree = dialog5_7.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog5_7.dismiss();
            }
        });
    }                                                              // Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)

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
        title.setText("?????? 6/7) ??????????????? ?????? ??????");
        TextView contents = dialog6_7.findViewById(R.id.textViewContents);
        contents.setText("?????????, ??????, ??? ?????? ?????? ??????, ?????? ??????, ?????? ??? ?????????");

        dialog6_7.show();
        Button agree = dialog6_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                dialog6_7.dismiss();
                permission = new PermissionSupport(AuthorityActivity.this, getApplicationContext());  // ????????? ?????? ??????

                if (!permission.checkPermission()) {
                    List<Object> permissionList = new ArrayList<>();
                    for (String pm : permission.permissions) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(AuthorityActivity.this, pm)) {  // ????????? ????????? ??? ????????? ?????????
                            permissionList.add(pm);                                                                     // ???????????? ??????
                        }
                    }
                    if (!permissionList.isEmpty() || isFirstTimeRequest) {           // ???????????? ???????????? ?????? ?????????, ?????? ????????? ??????
                        isFirstTimeRequest = false;
                        permission.requestPermission();
                    } else {                                                         // ????????????????????? ???????????? ??????
                        showDialog62_7();
                    }
                } else {
                    showDialog7_7();
                }
            }
        });

        Button disagree = dialog6_7.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog6_7.dismiss();
            }
        });
    }                                                              // permission.requestPermission()

    private void showDialog62_7() {
        dialog62_7 = new Dialog(AuthorityActivity.this);
        dialog62_7.setContentView(R.layout.custom_dialog_authority_image);
        dialog62_7.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog62_7.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog62_7.getWindow().setAttributes(params);
        dialog62_7.setCancelable(false);
        TextView title = dialog62_7.findViewById(R.id.textViewTitle);
        title.setText("?????? 6/7) ??????????????? ?????? ??? ??????");
        TextView contents = dialog62_7.findViewById(R.id.textViewContents);
        contents.setText("??????????????? ??????????????? ?????????\n" +
                "[??????,??????,??????,????????????,?????????,?????????] ????????? ????????? ???????????????. ?????????????????? ??? ???????????? ????????????, ????????? [??????,??????/?????? ????????????, ??????????????? ??????] ?????? ????????? " +
                "???????????? ???????????? ????????????.\n\n" +
                "\"???????????? ??????\" ???, \"??????\" ???????????? ?????? ???????????? ?????? ?????? ???????????? ????????? ????????? ?????????.\n\n" +
                "?????? \"??????\" ????????? \"?????? ??????\"?????? ???????????? ????????? ????????? ??? ????????? ????????? ?????????.");

        ImageView imageView = dialog62_7.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.image_authority_62_7);

        dialog62_7.show();
        Button agree = dialog62_7.findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                dialog62_7.dismiss();

            }
        });

        Button disagree = dialog62_7.findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog62_7.dismiss();
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
        title.setText("?????? 7/7) ?????? ??????");
        TextView contents = dialog7_7.findViewById(R.id.textViewContents);
        contents.setText("??? ????????? [xxxxx] ??? ????????? ???????????????.\n" +
                "?????????????\n\n" +
                "??? ????????? ????????? ???????????? ?????? ?????????(?????? ?????????)??? ?????? ???????????? ????????????. ????????? ???????????? ???????????? ?????? ?????? ???????????????.");
        dialog7_7.show();
        Button agree = dialog7_7.findViewById(R.id.agree);
        agree.setText("??????");
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog7_7.dismiss();
                finish();
            }
        });

        Button disagree = dialog7_7.findViewById(R.id.disagree);
        disagree.setText("??????");
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog7_7.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {  // Request Permission ??? ?????? ?????? ?????? ?????????.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("cis", String.valueOf("requestCode: " + requestCode + ", permissions: " + Arrays.toString(permissions) + ", grantResults: " + Arrays.toString(grantResults)));

        if (permission.permissionResult(requestCode, permissions, grantResults)) {
            showDialog7_7();
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
            CharSequence disableRequestedSeq = "??????????????? ?????? ??? ?????? ????????? ?????????(?????????)??? ?????? ???????????????. ????????? ???????????? ?????? ????????? ????????????.";
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


































