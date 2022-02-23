package com.jvt.interior.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jvt.interior.Common;
import com.jvt.interior.Model.UserModel;
import com.jvt.interior.R;
import com.jvt.interior.databinding.FragmentEditProfileBinding;

import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class EditProfile extends Fragment {
    private FragmentEditProfileBinding binding;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String imageUrl,uid;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference,databaseReference1;
    Uri filePath = null;
    private final int PICK_IMAGE_REQUEST = 10;
    String profilePic, userName, userEmail, userMobile, userAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        binding.email.setText(Common.userEmail);
        binding.name.setText(Common.userName);
        storageReference = firebaseStorage.getReference();
        binding.changeProfile.setOnClickListener(view1 -> {
            selectGalleryImage();
        });
        binding.submit.setOnClickListener(view1 -> {
            String name = binding.name.getText().toString();
            String email = binding.email.getText().toString();
            String mobile = binding.mobile.getText().toString();
            String address = binding.address.getText().toString();
            if (name.isEmpty()){
                Toast.makeText(getContext(), "Enter name..", Toast.LENGTH_SHORT).show();
            }else if (email.isEmpty()){
                Toast.makeText(getContext(), "Enter email..", Toast.LENGTH_SHORT).show();
            }else if (mobile.isEmpty()){
                Toast.makeText(getContext(), "Enter mobile..", Toast.LENGTH_SHORT).show();
            }else if (address.isEmpty()){
                Toast.makeText(getContext(), "Enter address..", Toast.LENGTH_SHORT).show();
            }else {
                profilePic = Common.imageUrl;
                userName = name;
                userAddress = address;
                userEmail = email;
                userMobile = mobile;
                UserModel userModel = new UserModel(profilePic, userName, userEmail, userMobile, uid, userAddress);
                databaseReference1.child("User").child(uid).child("UserInfo").setValue(userModel);
                Toast.makeText(getContext(), "Profile updated..", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void selectGalleryImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent,"Select image from here.."),PICK_IMAGE_REQUEST
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            Glide.with(getActivity()).load(filePath).into(binding.image);
            uploadImageToFirebase(filePath);
        }
    }

    private void uploadImageToFirebase(Uri filePath) {
        if (filePath != null){
            Random random = new Random();
            int id = random.nextInt(999999999);

            String imageId = String.valueOf(id);
            StorageReference reference = storageReference.child("image/"+imageId);
            reference.putFile(filePath).addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl()
                    .addOnCompleteListener(task -> {
                        imageUrl = task.getResult().toString();
                        Common.imageUrl = imageUrl;
                        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
                        databaseReference2.child("User").child(uid).child("UserInfo").child("profilePic").setValue(imageUrl);
                    })
            );
        }else {
            Toast.makeText(getContext(), "Something wrong..", Toast.LENGTH_SHORT).show();
        }
    }
}