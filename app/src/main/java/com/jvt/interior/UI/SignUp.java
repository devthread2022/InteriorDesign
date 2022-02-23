package com.jvt.interior.UI;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jvt.interior.Model.UserModel;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentSignUpBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUp extends Fragment {
    private FragmentSignUpBinding binding;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static Fragment activeFragment;
    String profilePic, userName, userEmail, userMobile, uid, userAddress;
    String password, confirmPassword;
    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        binding.login.setOnClickListener(view1 -> {
            Fragment fragment = new Login();
            loadFragment(fragment,"Login");
        });
        binding.submit.setOnClickListener(view1 -> {
            userName = binding.name.getText().toString();
            userEmail = binding.email.getText().toString();
            password = binding.password.getText().toString();
            confirmPassword = binding.confirmPassword.getText().toString();
            if (userName.isEmpty()){
                binding.name.requestFocus();
                binding.name.setError("Name");
            }else if (userEmail.isEmpty()){
                binding.email.requestFocus();
                binding.email.setError("Email");
            }else if (password.isEmpty()){
                binding.password.requestFocus();
                binding.password.setError("Password");
            }else if (confirmPassword.isEmpty()){
                binding.confirmPassword.requestFocus();
                binding.confirmPassword.setError("Confirm Password");
            }else if (!password.equals(confirmPassword)){
                Toast.makeText(getContext(), "Both password does not match. Please check..", Toast.LENGTH_SHORT).show();
            }else {
                firebaseAuth.createUserWithEmailAndPassword(userEmail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            profilePic = "https://firebasestorage.googleapis.com/v0/b/interior-d466b.appspot.com/o/pexels-photo-220453.jpeg?alt=media&token=2dd8b7f1-549a-457a-8a74-11526751b3d9";
                            uid = firebaseAuth.getCurrentUser().getUid();
                            userMobile = "";
                            userAddress = "";
                            UserModel userModel = new UserModel(profilePic, userName, userEmail, userMobile, uid, userAddress);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("User").child(uid).child("UserInfo").setValue(userModel);
                            Toast.makeText(getContext(), "Registered...", Toast.LENGTH_SHORT).show();
                            Fragment fragment = new Login();
                            loadFragment(fragment,"Login");
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