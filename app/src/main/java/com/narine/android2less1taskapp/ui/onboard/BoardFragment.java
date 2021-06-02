package com.narine.android2less1taskapp.ui.onboard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.narine.android2less1taskapp.Prefs;
import com.narine.android2less1taskapp.R;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.io.BufferedReader;


public class BoardFragment extends Fragment implements BoardAdapter.OpenHome {

    private Button skip;
    DotsIndicator dotsIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        DotsIndicator dotsIndicator = view.findViewById(R.id.dots_swipe);
        Button skip = view.findViewById(R.id.btn_skip);
        BoardAdapter adapter = new BoardAdapter();
        viewPager.setAdapter(adapter);

        dotsIndicator.setViewPager2(viewPager);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close (R.id.navigation_home);
            }
        });

        adapter.setOpenHome(this::btnOpenHome);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), //
                //чтобы при нажатии назад он выходил из приложения с этого фрагменты
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }

    private void close (int navigate) {
        new Prefs(requireContext()).saveBoardState();
        NavController navController =
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        navController.navigateUp();
    }


    @Override
    public void btnOpenHome() {
        close (R.id.navigation_home);
    }
}