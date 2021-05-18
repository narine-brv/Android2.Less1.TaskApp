package com.narine.android2less1taskapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.narine.android2less1taskapp.R;
import com.narine.android2less1taskapp.models.Task;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { //этот метод срабатывает
        // только один раз при запуске и все, не будет обновляться
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(); //поэтому чтоб recycleview сохранял элементы необх-о адаптер вызывать здесь
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_home);
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTaskFragment ();
            }
        });
        setResultListener();
        initList();
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
    }

    private void setResultListener(){  //возвращаемый эл-т, приходит текст
        getParentFragmentManager().setFragmentResultListener("rk_task",
                getViewLifecycleOwner(),
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String text = result.getString("text");
                        Task task = new Task(text);
                        adapter.addItem(task);
                        Log.e("Home", "text:" + text);
                    }
                });
    }

    private void openTaskFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.taskFragment);
    }
}