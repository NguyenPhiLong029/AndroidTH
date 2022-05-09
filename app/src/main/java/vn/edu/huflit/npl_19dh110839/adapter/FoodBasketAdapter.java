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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.edu.huflit.npl_19dh110839.R;
import vn.edu.huflit.npl_19dh110839.models.FoodBasket;

public class FoodBasketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public interface OnFoodBasketItemClickListener {
        void onFoodBasketItemListener(FoodBasket foodBasket);
    }
    ArrayList<FoodBasket> baskets;
    public FoodBasketAdapter(ArrayList<FoodBasket> baskets )
    {
        this.baskets = baskets;
    }
    public class ViewHolderFoodBasket extends RecyclerView.ViewHolder {
        TextView nameFood,qty,priceFood,intoMoney;
        ImageView ivImage;

        public ViewHolderFoodBasket(View itemView) {
            super(itemView);
            nameFood = itemView.findViewById(R.id.nameFood);
            qty = itemView.findViewById(R.id.qty);
            priceFood = itemView.findViewById(R.id.price);
            intoMoney = itemView.findViewById(R.id.intoMoney);
        }
    }

    //    private ArrayList<FoodBasket> foodBaskets;
    private OnFoodBasketItemClickListener onFoodBasketItemClickListener;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_food_basket, parent, false);
        return new ViewHolderFoodBasket(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodBasket foodBasket = baskets.get(position);
        FoodBasketAdapter.ViewHolderFoodBasket viewHolderFoodBasket = (FoodBasketAdapter.ViewHolderFoodBasket) holder;
        System.out.println("foodname");
        System.out.println(foodBasket.getName());
        viewHolderFoodBasket.nameFood.setText(foodBasket.getName()+"");
        viewHolderFoodBasket.priceFood.setText(foodBasket.getPrice()+"");
        viewHolderFoodBasket.qty.setText(foodBasket.getQuantity()+"");

        int qty = foodBasket.getQuantity();
        int price = foodBasket.getPrice();
        int priceFood = qty * price;
        viewHolderFoodBasket.intoMoney.setText(priceFood + " ");
        System.out.println(priceFood);

//        viewHolderFoodBasket.intoMoney.setText(priceFood);
    }

    @Override
    public int getItemCount() {
        return baskets.size();
    }
}
