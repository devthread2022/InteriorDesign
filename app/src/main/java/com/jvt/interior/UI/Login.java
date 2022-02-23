package com.jvt.interior.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jvt.interior.HomeActivity;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentLoginBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login extends Fragment {
    private FragmentLoginBinding binding;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static Fragment activeFragment;
    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        binding.signup.setOnClickListener(view1 -> {
            Fragment fragment = new SignUp();
            loadFragment(fragment,"SignUp");
        });
        binding.submit.setOnClickListener(view1 -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            if (email.isEmpty()){
                binding.email.requestFocus();
                binding.email.setError("Email Id");
            }else if (password.isEmpty()){
                binding.password.setError("Password");
                binding.password.requestFocus();
            }else {
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Login successful..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            getActivity().finish();
                        }else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }
    private void loadFragment(Fragment fragment, String tag) {
        executorService.execute(() -> {
            if (fragment != null) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();

            }
            handler.post(() -> {
                activeFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            });
        });
    }
}