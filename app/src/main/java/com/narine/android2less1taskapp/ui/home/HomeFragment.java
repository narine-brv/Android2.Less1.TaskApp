package com.narine.android2less1taskapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.narine.android2less1taskapp.App;
import com.narine.android2less1taskapp.R;
import com.narine.android2less1taskapp.models.Task;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { //этот метод срабатывает
        // только один раз при запуске и все, не будет обновляться
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(); //поэтому чтоб recycleview сохранял элементы необх-о адаптер вызывать здесь
        List<Task> list = App.getAppDatabase().taskDao().getAll();
        adapter.addItems(list);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_sort:
                sortRoom();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void sortRoom() {
        App.getAppDatabase().taskDao().sortByAsc().observe(getViewLifecycleOwner(),
                new Observer<List<Task>>() {
                    @Override
                    public void onChanged(List<Task> tasks) {
                        adapter.notifyDataSetChanged();
                        adapter.addItems(tasks);
                    }
                });
    }
}