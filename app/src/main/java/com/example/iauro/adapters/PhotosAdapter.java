package com.example.iauro.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iauro.R;
import com.example.iauro.model.PhotoData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {
    private final List<PhotoData> photosList;
    private final Context mContext;

    public PhotosAdapter(Context context, List<PhotoData> photoDataList) {
        this.mContext = context;
        this.photosList = photoDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        PhotoData data = photosList.get(position);
        // bind data
        if (data.getUrl() != null && !data.getUrl().trim().isEmpty()){
            Picasso.get().load(data.getUrl())
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(myViewHolder.iv_myImage);
        } else {
            myViewHolder.iv_myImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_menu_gallery));
        }

        myViewHolder.tv_myText.setText(data.getTitle());
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_myImage;
        private TextView tv_myText;

        public MyViewHolder(View view) {
            super(view);

            iv_myImage = view.findViewById(R.id.iv_myImage);
            tv_myText = view.findViewById(R.id.tv_myText);
        }
    }
}
