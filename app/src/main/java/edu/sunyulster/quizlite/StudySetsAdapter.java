package edu.sunyulster.quizlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudySetsAdapter extends RecyclerView.Adapter<StudySetsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private List<StudySetInfo> infoList; // Cached copy of words

    public StudySetsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.study_card_layout, parent, false);
        return new ViewHolder(itemView);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView topic;
        TextView desc;
        TextView date;
        TextView cardCount;

        private ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            topic = itemView.findViewById(R.id.topic);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            cardCount = itemView.findViewById(R.id.cardCount);
        }
    }

    public void setData(List<StudySetInfo> data) {
        infoList = data;
        notifyDataSetChanged();
    }
}