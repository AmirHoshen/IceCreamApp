package userlogina.example.mylastapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import userlogina.example.mylastapplication.Orders.Dish;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    ArrayList<Dish> _dish = new ArrayList<>();
    ArrayList<String> _name = new ArrayList<>();
    ArrayList<String> _description = new ArrayList<>();
    ArrayList<Double> _price = new ArrayList<>();
    ArrayList<Uri> _images = new ArrayList<>();
    Context context;


    public RecyclerViewAdapter(Context ct, ArrayList s1, ArrayList s2,ArrayList s3, ArrayList img){
        context = ct;
        _name = s1;
        _description = s2;
        _price = s3;
        _images = img;
    }

    public RecyclerViewAdapter(Context ct,ArrayList<Dish> fetchDish,ArrayList<Uri> Uri) {
        context = ct;
        synchronized (this){for(Dish d:fetchDish){
            _name.add(d.getFalvor());
            _description.add(d.getDescription());
            _price.add(d.getPrice());
            FirebaseStorage.getInstance().getReference().child(d.getImageUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public synchronized void onSuccess(Uri uri) {
                    System.out.println("Image Got");
                    _images.add(uri);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public synchronized void onFailure(@NonNull Exception exception) {

                }
            });
        }}

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.iceCreamTastes.setText(_name.get(position));
        holder.iceCreamDescription.setText(_description.get(position));
        holder.myImageView.setImageURI(_images.get(position));
        holder.iceCreamPrice.setText(_price.get(position).toString());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivityOrderBenAndJerry.class);
                intent.putExtra("name", _name.get(position));
                intent.putExtra("description", _description.get(position));
                intent.putExtra("image", _images.get(position));
                intent.putExtra("price", _price.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _name.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView iceCreamTastes, iceCreamDescription,iceCreamPrice;
        ImageView myImageView;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iceCreamTastes = itemView.findViewById(R.id.iceCreamTastes);
            iceCreamDescription = itemView.findViewById(R.id.iceCreamDescription);
            myImageView = itemView.findViewById(R.id.myImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            iceCreamPrice = itemView.findViewById(R.id.IceCreamPrice);

        }
    }
}