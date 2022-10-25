package app.softpower.prototype07312;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.softpower.prototype07312.databinding.ActivityAuthorityBinding;
import app.softpower.prototype07312.databinding.ActivityTutorialBinding;

public class AuthorityActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityAuthorityBinding binding;

    private boolean isCreateAccountPageOpen = false;
    private boolean isWhoPageOpen = false;
    private boolean isConnectDevicePageOpen = false;

    //권한관련
    private final static String LOG_TAG = "cis";
    DevicePolicyManager tDevicePolicyManager;
    ComponentName tDevicePolicyAdmin;
    protected static final int REQUEST_ENABLE = 1;
    protected static final int SET_PASSWORD = 2;

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
        if (v == binding.buttonCreateAcount){
            binding.layoutLogin.setVisibility(View.GONE);
            binding.layoutCreate.setVisibility(View.VISIBLE);
            isCreateAccountPageOpen = true;
            return;
        }
        if (v == binding.buttonLogin){
            binding.layoutLogin.setVisibility(View.GONE);
            binding.layoutWho.setVisibility(View.VISIBLE);
            isWhoPageOpen = true;
            return;
        }
        if (v == binding.buttonCancel){
            binding.layoutCreate.setVisibility(View.GONE);
            binding.layoutLogin.setVisibility(View.VISIBLE);
            isCreateAccountPageOpen = false;
            return;
        }
        if (v == binding.buttonCreate){
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

        if (v == binding.buttonParent){
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

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
//                        Log.e(LOG_TAG, "result : " + result);

                    }
                }
            });



    @Override
    public void onBackPressed() {
        if (isCreateAccountPageOpen){
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
//            super.onDisabled(context, intent);
            Toast.makeText(context, "Device Admin Disabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEnabled(Context context, Intent intent){
            Toast.makeText(context, "Device Admin is now enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public CharSequence onDisableRequested(Context context, Intent intent){
            CharSequence disableRequestedSeq = "Requesting to disable Device Admin";
            return disableRequestedSeq;
        }

        @Override
        public void onPasswordChanged(Context context, Intent intent){
            Toast.makeText(context, "Device password is now changed", Toast.LENGTH_SHORT).show();
            DevicePolicyManager localDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName localComponent = new ComponentName(context, MyDevicePolicyReceiver.class);
            localDPM.setPasswordExpirationTimeout(localComponent, 0L);
        }

        @Override
        public void onPasswordExpiring(Context context, Intent intent){
            // This would require API 11 an above
            Toast.makeText(context, "Device password is going to expire, please change to a new password", Toast.LENGTH_SHORT).show();
            DevicePolicyManager localDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName localComponent = new ComponentName(context, MyDevicePolicyReceiver.class);
            long expr = localDPM.getPasswordExpiration(localComponent);
            long delta = expr - System.currentTimeMillis();
            boolean expired = delta < 0L;
            if (expired) {
                localDPM.setPasswordExpirationTimeout(localComponent, 10000L);
                Intent passwordChangeIntent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
                passwordChangeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(passwordChangeIntent);
            }
        }

        @Override
        public void onPasswordFailed(Context context, Intent intent) {
            Toast.makeText(context, "Password failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPasswordSucceeded(Context context, Intent intent) {
            Toast.makeText(context, "Access Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(LOG_TAG, "MyDevicePolicyReceiver Received: " + intent.getAction());
            super.onReceive(context,intent);
        }
    }
}


































