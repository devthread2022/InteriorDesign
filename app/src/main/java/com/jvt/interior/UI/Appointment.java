package com.jvt.interior.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jvt.interior.Model.AppointmentModel;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentAppointmentBinding;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Appointment extends Fragment {
    private FragmentAppointmentBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String name, email, mobile, state, city, whatsApp, id;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    public static Fragment activeFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppointmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        whatsApp = "NO";
        binding.whatsapp.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                whatsApp = "YES";
            }
        });
        binding.submit.setOnClickListener(view1 -> {
            name = binding.name.getText().toString();
            email = binding.email.getText().toString();
            mobile = binding.mobile.getText().toString();
            state = binding.state.getText().toString();
            city = binding.city.getText().toString();
            if (name.isEmpty()){
                Toast.makeText(getContext(), "Name", Toast.LENGTH_SHORT).show();
            }else if (email.isEmpty()){
                Toast.makeText(getContext(), "Email", Toast.LENGTH_SHORT).show();
            }else if (mobile.isEmpty()){
                Toast.makeText(getContext(), "Mobile", Toast.LENGTH_SHORT).show();
            }else if (state.isEmpty()){
                Toast.makeText(getContext(), "State", Toast.LENGTH_SHORT).show();
            }else if (city.isEmpty()){
                Toast.makeText(getContext(), "City", Toast.LENGTH_SHORT).show();
            }else {
                Random random = new Random();
                int i = random.nextInt(99999)+10000;
                id = String.valueOf(i);
                AppointmentModel appointmentModel = new AppointmentModel(name, email, mobile, state, city, whatsApp, id);
                databaseReference.child("Appointments").child(id).setValue(appointmentModel);
                Toast.makeText(getContext(), "Appointment booked successfully. Our executive will contact you shortly..", Toast.LENGTH_SHORT).show();
            }
        });
        binding.notification.setOnClickListener(view1 -> {
            Fragment fragment = new Notification();
            loadFragment(fragment,"Notification");
        });
        return view;
    }
    private void loadFragment(Fragment fragment, String tag) {
        executorService.execute(() -> {
            if (fragment != null) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment, tag).addToBackStack(tag).commit();

            }
            handler.post(() -> {
                activeFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
            });
        });
    }
}