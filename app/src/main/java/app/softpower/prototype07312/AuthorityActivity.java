package app.softpower.prototype07312;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import app.softpower.prototype07312.databinding.ActivityAuthorityBinding;
import app.softpower.prototype07312.databinding.ActivityTutorialBinding;

public class AuthorityActivity extends AppCompatActivity {

    private ActivityAuthorityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthorityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}