package com.codingbjs.voicenotice.voicedata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingbjs.voicenotice.databinding.VoiceItemListItemBinding;

import java.util.ArrayList;

public class VoiceItemListAdapter extends RecyclerView.Adapter<VoiceItemListAdapter.ViewHolder>{

    private ArrayList<VoiceItem> voiceItems;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public VoiceItemListAdapter(ArrayList<VoiceItem> voiceItems) {
        this.voiceItems = voiceItems;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        VoiceItemListItemBinding voiceItemListItemBinding = VoiceItemListItemBinding.inflate(
                layoutInflater, parent, false);
        return new ViewHolder(voiceItemListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VoiceItem voiceItem = voiceItems.get(position);
        holder.setListItemBinding(voiceItem, position);
    }

    @Override
    public int getItemCount() {
        return voiceItems.size();
    }

    public VoiceItem getVoiceItem (int position) {
        return voiceItems.get(position);
    }


    public void setVoiceItems(ArrayList<VoiceItem> voiceItems) {
        this.voiceItems = voiceItems;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        VoiceItemListItemBinding voiceItemListItemBinding;

        public ViewHolder(@NonNull VoiceItemListItemBinding itemView) {
            super(itemView.getRoot());
            voiceItemListItemBinding = itemView;

            voiceItemListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        if(onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                }
            });

            voiceItemListItemBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        if(onItemLongClickListener != null) {
                            onItemLongClickListener.onItemLongClick(v, position);
                        }
                    }
                    return false;
                }
            });

        }

        public void setListItemBinding(VoiceItem voiceItem, int position) {
            voiceItemListItemBinding.listNum.setText(String.valueOf(position + 1));
            voiceItemListItemBinding.listTitle.setText(voiceItem.getTitle());
            voiceItemListItemBinding.listDate.setText(voiceItem.getDateText());
        }
    }
}
