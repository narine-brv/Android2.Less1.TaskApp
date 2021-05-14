package com.narine.android2less1taskapp.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.narine.android2less1taskapp.R;
import com.narine.android2less1taskapp.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpListeners();

    }

    private void setUpListeners() {
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerChooser();
            }
        });
    }

    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                String photo = result.getData().getDataString();
                binding.profileImage.setImageURI(Uri.parse(photo));
            });

    private void triggerChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mLauncher.launch(intent);
    }
}