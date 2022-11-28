package app.softpower.prototype07312;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.softpower.prototype07312.databinding.ActivityAuthorityBinding;
import app.softpower.prototype07312.databinding.ActivityWeeklyScheduleModificationBinding;

public class WeeklyScheduleModification extends AppCompatActivity implements View.OnTouchListener {

    private final List<View> viewList = new ArrayList<View>();
    private int newColor;
    private boolean newBoolean;
    private final boolean[] booleans = new boolean[336];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app.softpower.prototype07312.databinding.ActivityWeeklyScheduleModificationBinding binding = ActivityWeeklyScheduleModificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("주간 일정");

        binding.linearLayout.setOnTouchListener(this);

        TableLayout tl = binding.tableLayout;

        for (int i = 1; i < tl.getChildCount(); i++) {
            TableRow tr = (TableRow) tl.getChildAt(i);
            for (int j = 1; j < tr.getChildCount(); j++) {
                viewList.add(tr.getChildAt(j));
            }
        }

        initializing();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel:
                showDialogCancel();
                return true;
            case R.id.save:
                Toast.makeText(getBaseContext(), "저장 되었습니다.", Toast.LENGTH_SHORT).show();
//                Snackbar.make(binding.toolbar, "저장 되었습니다.", Snackbar.LENGTH_SHORT).show();
                save();
                finish();
                return true;
            case R.id.initialize:
                initializingCompletely();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < viewList.size(); i++) {
                View vv = viewList.get(i);
                boolean chk = chkTouchInside(vv, (int) event.getRawX(), (int) event.getRawY());
                if (chk) {
                    ColorDrawable color = (ColorDrawable) vv.getBackground();
                    if (color.getColor() == this.getColor(R.color.red)) {
                        newColor = R.color.white;
                        newBoolean = false;
                    } else {
                        newColor = R.color.red;
                        newBoolean = true;
                    }
                    vv.setBackgroundColor(this.getColor(newColor));
                    booleans[i] = newBoolean;
                    break;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            for (int i = 0; i < viewList.size(); i++) {
                View vv = viewList.get(i);
                boolean chk = chkTouchInside(vv, (int) event.getRawX(), (int) event.getRawY());
                if (chk) {
                    vv.setBackgroundColor(this.getColor(newColor));
                    booleans[i] = newBoolean;
                    break;
                }
            }
        }
        return false;
    }

    private boolean chkTouchInside(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (x >= location[0]) {
            if (x <= location[0] + view.getWidth()) {
                if (y >= location[1]) {
                    if (y <= location[1] + view.getHeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void showDialogCancel() {
        Dialog dialog;

        dialog = new Dialog(WeeklyScheduleModification.this);
        dialog.setContentView(R.layout.custom_dialog_authority);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
//        dialog.setCancelable(false);
        TextView title = dialog.findViewById(R.id.textViewTitle);
        title.setText("확인해 주세요");
        TextView contents = dialog.findViewById(R.id.textViewContents);
        contents.setText("  취소 하시겠습니까?");
        Button agree = dialog.findViewById(R.id.agree);
        agree.setText("네");
        Button disagree = dialog.findViewById(R.id.disagree);
        disagree.setText("아니오");

        dialog.show();
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void initializing() {

        SharedPreferences pref = getSharedPreferences("weeklySchedule", Activity.MODE_PRIVATE);
        Set<String> mySet = new HashSet<String>(Arrays.asList(getResources().getStringArray(R.array.weeklySchedule)));
        Set<String> weeklySchedule = pref.getStringSet("weeklySchedule", mySet);

        Arrays.fill(booleans, false);

        for (String value : weeklySchedule){
            int i = Integer.parseInt(value);
            booleans[i] = true;
        }

        for (int i = 0; i < booleans.length; i++) {
            View vv = viewList.get(i);
            if (booleans[i]) {
                vv.setBackgroundColor(this.getColor(R.color.red));
            } else {
                vv.setBackgroundColor(this.getColor(R.color.white));
            }
        }
    }

    private void initializingCompletely() {

        Set<String> mySet = new HashSet<String>(Arrays.asList(getResources().getStringArray(R.array.weeklySchedule)));
        Arrays.fill(booleans, false);

        for (String value : mySet){
            int i = Integer.parseInt(value);
            booleans[i] = true;
        }

        for (int i = 0; i < booleans.length; i++) {
            View vv = viewList.get(i);
            if (booleans[i]) {
                vv.setBackgroundColor(this.getColor(R.color.red));
            } else {
                vv.setBackgroundColor(this.getColor(R.color.white));
            }
        }
    }

    private void save() {
        Set<String> mySet = new HashSet<String>();

        for (int i=0; i<booleans.length; i++) {
            if (booleans[i]) {
                mySet.add(String.valueOf(i));
            }
        }

        SharedPreferences pref = getSharedPreferences("weeklySchedule", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet("weeklySchedule", mySet);
        editor.apply();
    }
}


























