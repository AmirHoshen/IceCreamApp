package userlogina.example.mylastapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.service.autofill.AutofillService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import userlogina.example.mylastapplication.Orders.Dish;

public class BusinessRecyclerAdapter extends RecyclerView.Adapter<userlogina.example.mylastapplication.RecyclerViewAdapter.MyViewHolder> {

    ArrayList<String> _name = new ArrayList<>();
    ArrayList<String> _description = new ArrayList<>();
    ArrayList<Double> _price = new ArrayList<>();
    Context context;

    public BusinessRecyclerAdapter(Context ct, ArrayList s1, ArrayList s2, ArrayList s3) {
        context = ct;
        _name = s1;
        _description = s2;
        _price = s3;
    }

    public BusinessRecyclerAdapter(List<Dish> fetchDish) {
    }

    @NonNull
    @Override
    public userlogina.example.mylastapplication.RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new userlogina.example.mylastapplication.RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userlogina.example.mylastapplication.RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.iceCreamTastes.setText(_name.get(position));
        holder.iceCreamDescription.setText(_description.get(position));
        holder.iceCreamPrice.setText(_price.get(position).toString());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivityOrderBenAndJerry.class);
                intent.putExtra("name", _name.get(position));
                intent.putExtra("description", _description.get(position));
                intent.putExtra("price", _price.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return _description.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView iceCreamTastes, iceCreamDescription, iceCreamPrice;
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

