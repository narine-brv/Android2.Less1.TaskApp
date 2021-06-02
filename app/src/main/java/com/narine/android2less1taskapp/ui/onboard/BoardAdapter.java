package com.narine.android2less1taskapp.ui.onboard;

import android.graphics.Path;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import com.narine.android2less1taskapp.R;

public class BoardAdapter extends RecyclerView.Adapter <BoardAdapter.ViewHolder> {

    private OpenHome openHome;

    private String [] titles = new String[] {"Hello", "Салам", "Привет"};
    private int [] pagerImages = new int[] {R.drawable.bot_hi, R.drawable.idea, R.drawable.task_list};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pager_board, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind (position);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

   interface OpenHome {
        void btnOpenHome();
   }

   public void setOpenHome (OpenHome openHome){
        this.openHome = openHome;
   }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle;
        ImageView imgPagerBoard;
        Button btnOpenHome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_Title);
            imgPagerBoard = itemView.findViewById(R.id.img_pagerBoard);
            btnOpenHome = itemView.findViewById(R.id.btn_openHome);

            btnOpenHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openHome.btnOpenHome();
                }
            });
        }

        public void bind(int position) {
            textTitle.setText(titles[position]);
            imgPagerBoard.setImageResource(pagerImages[position]);

            if (position == titles.length - 1) {
                btnOpenHome.setVisibility(View.VISIBLE);
            } else {
                btnOpenHome.setVisibility(View.GONE);
            }
        }
    }
}
