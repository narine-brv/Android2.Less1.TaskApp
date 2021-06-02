package com.narine.android2less1taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.narine.android2less1taskapp.App;
import com.narine.android2less1taskapp.R;
import com.narine.android2less1taskapp.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter <TaskAdapter.ViewHolder> {


    private ArrayList <Task> list = new ArrayList();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));

        if (position % 2 == 0){ //проверка на остаток, если при делении остаток равен 0 то выполни следующее
            holder.itemView.setBackgroundColor(Color.parseColor("gray"));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setTitle("Удалить");
                alertDialog.setMessage("Вы уверены в этом?");
                alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        App.getAppDatabase().taskDao().remove(list.get(position));
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });
                alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addItem(Task task) {
        list.add(task);
        notifyItemInserted(list.indexOf(task));
    }

    public void addItems(List<Task> list) {
        this.list.clear(); //без этого он добавляет еще элементы
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.rv_text_title);
        }

        public void bind(Task task) {
            textTitle.setText(task.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),String.valueOf(getAdapterPosition())
                            + " : " + task.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}


