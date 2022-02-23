package com.jvt.interior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.jvt.interior.UI.Appointment;
import com.jvt.interior.UI.Bookmark;
import com.jvt.interior.UI.Category;
import com.jvt.interior.UI.Dashboard;
import com.jvt.interior.UI.ProductList;
import com.jvt.interior.databinding.ActivityHomeBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    final FragmentManager fm = getSupportFragmentManager();
    public static Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Fragment fragment = new Dashboard();
        loadFragment(fragment,"Dashboard");
        binding.home.setOnClickListener(view1 -> {
            Fragment home = new Dashboard();
            loadFragment(home,"Dashboard");
        });
        binding.category.setOnClickListener(view1 -> {
            Fragment cat = new Category();
            loadFragment(cat,"Category");
        });
        binding.appointment.setOnClickListener(view1 -> {
            Fragment appoint = new Appointment();
            loadFragment(appoint,"Appointment");
        });
        binding.bookmark.setOnClickListener(view1 -> {
            Fragment book = new Bookmark();
            loadFragment(book,"Bookmark");
        });
    }
    @SuppressLint("StaticFieldLeak")
    private void loadFragment(Fragment fragment, String tag)
    {
        executorService.execute(() ->{
            if (fragment!=null)
            {
                fm.beginTransaction().replace(R.id.container, fragment,tag).addToBackStack(tag).commitAllowingStateLoss();
            }
            handler.post(() ->{
                activeFragment=fm.findFragmentById(R.id.container);
            });
        });
    }
}