package userlogina.example.mylastapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import userlogina.example.mylastapplication.Orders.Upload;

public class BusinessRecyclerAdapter extends RecyclerView.Adapter<BusinessRecyclerAdapter.ImageViewHolder> {



    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView description;
        public TextView price;
        public ImageView imgView;
        public ConstraintLayout mainLayout;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.iceCreamTastes);
            description = itemView.findViewById(R.id.iceCreamDescription);
            price = itemView.findViewById(R.id.IceCreamPrice);
            imgView = itemView.findViewById(R.id.myImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    private Context context;
    private List<Upload> mUploads;


    public BusinessRecyclerAdapter(Context ct, List<Upload> uploads) {
        context = ct;
        mUploads = uploads;

    }


    public BusinessRecyclerAdapter() {
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.name.setText(mUploads.get(position).getFalvor());
        holder.description.setText(mUploads.get(position).getDescription());
        holder.price.setText(" "+ mUploads.get(position).getPrice());

        Picasso.with(context)
                .load(mUploads.get(position).getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imgView);
        holder.imgView.setTag(mUploads.get(position).getImageUrl());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivityOrderBenAndJerry.class);
                intent.putExtra("name", mUploads.get(position).getFalvor());
                intent.putExtra("description", mUploads.get(position).getDescription());
                intent.putExtra("image", mUploads.get(position).getImageUrl());
                intent.putExtra("price", mUploads.get(position).getPrice());
                intent.putExtra("tag",mUploads.get(position).getTag());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

}

