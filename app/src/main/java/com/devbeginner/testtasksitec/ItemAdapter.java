package com.devbeginner.testtasksitec;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devbeginner.testtasksitec.model.db.ReceivedCodes;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<ReceivedCodes> values;

    public ItemAdapter (ArrayList<ReceivedCodes> values){
        this.values = values;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.textView.setText(Integer.toString(values.get(position).getCode()));
        holder.textView.setText(Integer.toString(values.get(position).getCode()));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_content);
        }
    }
}
