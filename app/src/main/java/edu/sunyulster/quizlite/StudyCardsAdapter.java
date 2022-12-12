package edu.sunyulster.quizlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudyCardsAdapter extends RecyclerView.Adapter<StudyCardsAdapter.ViewHolder> {

    private List<StudyContent> cards;
    private boolean[] showValue;
    private Context context;

    public StudyCardsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.study_card_layout, parent, false);
        return new ViewHolder(itemView, showValue);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (cards != null) {
            StudyContent current = cards.get(position);
            String data = "";
            if (showValue[position]) {
                // show value
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.medium));
                holder.text.setTextColor(context.getColor(R.color.light));
                data = current.getValue();
            } else {
                // show key
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.light));
                holder.text.setTextColor(context.getColor(R.color.medium));
                data = current.getKey();
            }
            holder.text.setText(data);
        } else {
            // Covers the case of data not being ready yet.
            holder.text.setText("No cards...");
        }
    }

    @Override
    public int getItemCount() {
        if (cards != null)
            return cards.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        CardView cardView;
        boolean[] showValue;

        private ViewHolder(View itemView, boolean[] showValue) {
            super(itemView);
            this.showValue = showValue;
            text = itemView.findViewById(R.id.cardContent);
            cardView = itemView.findViewById(R.id.card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // toggle show answer value
            int cardNumber = getAdapterPosition();
            if (showValue[cardNumber])
                // show key
                showValue[cardNumber] = false;
            else
                // show value
                showValue[cardNumber] = true;

            notifyDataSetChanged();

        }
    }

    public void setData(StudySet set) {
        cards = set.getContent();
        showValue = new boolean[cards.size()];
        notifyDataSetChanged();
    }

}
