package userlogina.example.mylastapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.service.autofill.AutofillService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import userlogina.example.mylastapplication.Orders.Dish;
import userlogina.example.mylastapplication.Orders.Upload;

public class BusinessRecyclerAdapter extends RecyclerView.Adapter<BusinessRecyclerAdapter.ImageViewHolder> {



    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView description;
        public TextView price;
        public ImageView imgView;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.iceCreamTastes);
            description = itemView.findViewById(R.id.iceCreamDescription);
            price = itemView.findViewById(R.id.IceCreamPrice);
            imgView = itemView.findViewById(R.id.myImageView);
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
        Upload uploadCurrent = mUploads.get(position);
        holder.name.setText(uploadCurrent.getTaste());
        holder.description.setText("  " + uploadCurrent.getDescription());
        holder.price.setText(uploadCurrent.getPrice()+"");

//
        Picasso.with(context)
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

}

