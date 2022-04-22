package edu.neu.madcourse.memoryup.LevelSelectorScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.memoryup.R;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>{

    ArrayList<ViewPagerItem> viewPagerList;
    LevelClickListener levelClickListener;

    public ViewPagerAdapter(ArrayList<ViewPagerItem> viewPagerList, LevelClickListener levelClickListener) {
        this.viewPagerList = viewPagerList;
        this.levelClickListener = levelClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_item, parent, false);
        return new ViewHolder(view, levelClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewPagerItem viewPagerItem = viewPagerList.get(position);
        holder.theme.setText(viewPagerItem.theme);
        holder.text1.setText(viewPagerItem.text1);
        holder.text2.setText(viewPagerItem.text2);
        holder.text3.setText(viewPagerItem.text3);
        holder.text4.setText(viewPagerItem.text4);
        holder.text5.setText(viewPagerItem.text5);
        holder.text6.setText(viewPagerItem.text6);
    }

    @Override
    public int getItemCount() {
        return viewPagerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView theme;
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;
        TextView text6;
        LevelClickListener levelClickListener;

        public ViewHolder(@NonNull View itemView, LevelClickListener levelClickListener) {
            super(itemView);
            theme = itemView.findViewById(R.id.theme);
            text1 = itemView.findViewById(R.id.text1);
            text2 = itemView.findViewById(R.id.text2);
            text3 = itemView.findViewById(R.id.text3);
            text4 = itemView.findViewById(R.id.text4);
            text5 = itemView.findViewById(R.id.text5);
            text6 = itemView.findViewById(R.id.text6);
            this.levelClickListener = levelClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            levelClickListener.onLevelClick(getAdapterPosition());
        }
    }
}
