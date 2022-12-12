package edu.sunyulster.quizlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudySetsAdapter extends RecyclerView.Adapter<StudySetsAdapter.ViewHolder> {

    private List<StudySetInfo> infoList; // cached study set info objs
    private OnItemClickedListener listener;
    
    public interface OnItemClickedListener {
        public void onItemClicked(long setId);
    }

    public StudySetsAdapter(OnItemClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.study_card_layout, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (infoList != null) {
            StudySetInfo current = infoList.get(position);
            holder.name.setText(current.getName());
            holder.topic.setText(current.getTopic());
            String desc = current.getDesc();
            if (desc.isEmpty())
                holder.desc.setVisibility(View.GONE);
            else {
                holder.desc.setText(current.getDesc());
                holder.desc.setVisibility(View.VISIBLE);
            }
            holder.date.setText(current.getDate());
            holder.cardCount.setText(String.valueOf(current.getNumberOfCards()));
        } else {
            // Covers the case of data not being ready yet.
            holder.name.setText("No Study Sets...");
        }
    }

    @Override
    public int getItemCount() {
        if (infoList != null)
            return infoList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView topic;
        TextView desc;
        TextView date;
        TextView cardCount;
        StudySetsAdapter.OnItemClickedListener listener;
        

        private ViewHolder(View itemView, StudySetsAdapter.OnItemClickedListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            topic = itemView.findViewById(R.id.topic);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            cardCount = itemView.findViewById(R.id.cardCount);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }
        
        @Override
        public void onClick(View view) {
            listener.onItemClicked(infoList.get(getAdapterPosition()));
        }
    }

    public void setData(List<StudySetInfo> data) {
        infoList = data;
        notifyDataSetChanged();
    }
}
