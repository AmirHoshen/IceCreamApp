package userlogina.example.mylastapplication;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{


    String data1[], data2[];
    int images[];
    Context context;

   public RecyclerViewAdapter(Context ct, String s1[], String s2[], int img[]){
       context = ct;
       data1 = s1;
       data2 = s2;
       images = img;
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
        holder.iceCreamTastes.setText(data1[position]);
        holder.iceCreamDescription.setText(data2[position]);
        holder.myImageView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView iceCreamTastes, iceCreamDescription;
       ImageView myImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iceCreamTastes = itemView.findViewById(R.id.iceCreamTastes);
            iceCreamDescription = itemView.findViewById(R.id.iceCreamDescription);
            myImageView = itemView.findViewById(R.id.myImageView);
        }
    }
}
