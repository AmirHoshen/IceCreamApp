package userlogina.example.mylastapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import userlogina.example.mylastapplication.Orders.Upload;

public class HelperAdapter extends RecyclerView.Adapter{

    List<Upload> fetchDishList;

    public HelperAdapter(List<Upload> fetchDish){
        this.fetchDishList = fetchDish;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);


        return viewHolderClass;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        Upload fetchDish = fetchDishList.get(position);
        viewHolderClass.dishTitle.setText(fetchDish.getFalvor());
        viewHolderClass.dishDescription.setText(fetchDish.getDescription());
        viewHolderClass.dishPrice.setText(""+fetchDish.getPrice());



    }

    @Override
    public int getItemCount() {
        return fetchDishList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView dishTitle, dishDescription, dishPrice;
        ImageView image;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            dishTitle = itemView.findViewById(R.id.iceCreamTastes);
            dishDescription = itemView.findViewById(R.id.iceCreamDescription);
            dishPrice = itemView.findViewById(R.id.IceCreamPrice);
            image = itemView.findViewById(R.id.myImageView);
        }
    }
}
