package com.codingbjs.voicenotice.voicedata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingbjs.voicenotice.databinding.VoiceItemViewPagerItemBinding;

import java.util.ArrayList;

public class VoiceItemViewPagerAdapter extends RecyclerView.Adapter<VoiceItemViewPagerAdapter.ViewHolder>{

    private ArrayList<VoiceItem> voiceItems;

    private ModifyButtonClickListener modifyButtonClickListener;
    private DeleteButtonClickListener deleteButtonClickListener;

    public VoiceItemViewPagerAdapter(ArrayList<VoiceItem> voiceItems) {
        this.voiceItems = voiceItems;
    }

    public interface ModifyButtonClickListener{
        void modifyButtonClick(VoiceItemViewPagerItemBinding viewPagerItemBinding, int position);
    }

    public interface DeleteButtonClickListener{
        void deleteButtonClick(int position);
    }

    public void setModifyButtonClickListener(ModifyButtonClickListener modifyButtonClickListener) {
        this.modifyButtonClickListener = modifyButtonClickListener;
    }

    public void setDeleteButtonClickListener(DeleteButtonClickListener deleteButtonClickListener) {
        this.deleteButtonClickListener = deleteButtonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        VoiceItemViewPagerItemBinding viewPagerItemBinding = VoiceItemViewPagerItemBinding.
                                            inflate(layoutInflater, parent, false);
        return new ViewHolder(viewPagerItemBinding);
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        VoiceItemViewPagerItemBinding viewPagerItemBinding;

        public ViewHolder(@NonNull VoiceItemViewPagerItemBinding itemView) {
            super(itemView.getRoot());
            viewPagerItemBinding = itemView;

            viewPagerItemBinding.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        if(deleteButtonClickListener != null) {
                            deleteButtonClickListener.deleteButtonClick(position);
                        }
                    }
                }
            });

            viewPagerItemBinding.modifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        if(modifyButtonClickListener != null) {
                            modifyButtonClickListener.modifyButtonClick(viewPagerItemBinding, position);
                        }
                    }
                }
            });
        }

        public void setListItemBinding(VoiceItem voiceItem, int position) {
            viewPagerItemBinding.titleEditText.setText(voiceItem.getTitle());
            viewPagerItemBinding.voiceTextEditText.setText(voiceItem.getVoice());
        }
    }
}
