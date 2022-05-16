package vn.edu.huflit.npl_19dh110839.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.edu.huflit.npl_19dh110839.R;
import vn.edu.huflit.npl_19dh110839.models.OrderFinished;
import vn.edu.huflit.npl_19dh110839.models.Restaurant;
import vn.edu.huflit.npl_19dh110839.models.Users;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Restaurant> restaurants;
    private ArrayList<OrderFinished> orderFinisheds;
    private OnOrderItemListener onOrderItemListener;


    public interface OnOrderItemListener {
        void onOrderItemListener(OrderFinished orderFinished);
    }

    public OrderAdapter(ArrayList<OrderFinished> orderFinisheds, ArrayList<Restaurant> restaurants, OnOrderItemListener onOrderItemListener) {
        this.orderFinisheds = orderFinisheds;
        this.restaurants = restaurants;
        this.onOrderItemListener = onOrderItemListener;
    }

    public OrderAdapter(ArrayList<OrderFinished> orderFinisheds, OnOrderItemListener onOrderItemListener) {
        this.orderFinisheds = orderFinisheds;
        this.onOrderItemListener = onOrderItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_order_finished, parent, false);
        return new OrderAdapter.ViewHolderOrder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderFinished orderFinished = orderFinisheds.get(position);
        ViewHolderOrder viewHolderOrder = (ViewHolderOrder) holder;

        Restaurant restaurant = new Restaurant();
        for (Restaurant restaurant1 : restaurants) {
            if (orderFinished.getFoodBaskets().get(0).getResKey().equals(restaurant1.getResKey())) {
                restaurant = restaurant1;
                break;
            }
        }
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("restaurants/" + restaurant.getLogo());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(viewHolderOrder.ivImage);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                viewHolderOrder.tvUserName.setText(user.getFirstName() + " " + user.getLastName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewHolderOrder.tvName.setText(restaurant.getName());
        viewHolderOrder.tvAddress.setText(restaurant.getAddress());
        viewHolderOrder.tvID.setText(orderFinished.getOrderID());
        viewHolderOrder.tvDate.setText(orderFinished.getOrderDate());
        viewHolderOrder.tvSum.setText(orderFinished.getOrderSum());

        viewHolderOrder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrderItemListener.onOrderItemListener(orderFinished);
            }
        });
    }

    public class ViewHolderOrder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvSum, tvID, tvDate, tvUserName;
        ImageView ivImage;

        public ViewHolderOrder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvOrderName);
            ivImage = itemView.findViewById(R.id.ivOrderImage);
            tvAddress = itemView.findViewById(R.id.tvOrderAddress);
            tvSum = itemView.findViewById(R.id.tvOrderSum);
            tvID = itemView.findViewById(R.id.tvOrderID);
            tvDate = itemView.findViewById(R.id.tvOrderDate);
            tvUserName = itemView.findViewById(R.id.tvOrderUserName);
        }
    }


    @Override
    public int getItemCount() {
        return orderFinisheds.size();
    }
}
