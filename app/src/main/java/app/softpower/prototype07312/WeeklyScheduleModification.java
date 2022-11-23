package app.softpower.prototype07312;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import app.softpower.prototype07312.databinding.ActivityAuthorityBinding;
import app.softpower.prototype07312.databinding.ActivityWeeklyScheduleModificationBinding;

public class WeeklyScheduleModification extends AppCompatActivity {

    private ActivityWeeklyScheduleModificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeeklyScheduleModificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("주간 일정");
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
                Toast.makeText(this, "취소 클릭됨", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.save:
                Toast.makeText(this, "저장 클릭됨", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.initialize:
                Snackbar.make(binding.toolbar, "초기화 클릭됨", Snackbar.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}