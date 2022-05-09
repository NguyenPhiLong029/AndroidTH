package vn.edu.huflit.npl_19dh110839;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.edu.huflit.npl_19dh110839.adapter.FoodBasketAdapter;
import vn.edu.huflit.npl_19dh110839.models.Basket;
import vn.edu.huflit.npl_19dh110839.models.Cart;
import vn.edu.huflit.npl_19dh110839.models.CartRepository;
import vn.edu.huflit.npl_19dh110839.models.Food;
import vn.edu.huflit.npl_19dh110839.models.FoodBasket;
import vn.edu.huflit.npl_19dh110839.models.OrderFinished;
import vn.edu.huflit.npl_19dh110839.models.Users;


public class OrderActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvTotal, tvName, tvAddress;
    private RecyclerView rvFoods;
    private Basket basket;
    private FoodBasketAdapter adapter;
    private Button btnPlaceOrder;
    CartRepository cartRepository;
    GoogleMap map;
    FirebaseAuth fAuth;
    FirebaseDatabase fDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        SupportMapFragment mapFragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.frgMaps);
        mapFragment.getMapAsync(this);

        tvAddress = findViewById(R.id.tvAddress);
        tvName = findViewById(R.id.tvName);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();

        String userID = fAuth.getCurrentUser().getUid();

        fDatabase.getReference().child("users").child(userID).get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        Users user = dataSnapshot.getValue(Users.class);
                        LatLng latLngUser = new LatLng(user.getLatitude(), user.getLongitude());
                        MarkerOptions options = new MarkerOptions().position(latLngUser);
                        options.icon(BitmapDescriptorFactory.fromBitmap(
                                BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker)));

                        map.addMarker(options);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngUser, 16));



                        tvName.setText("Name: " + user.getFirstName() + " " + user.getLastName());
                        tvAddress.setText("Address: " + user.getEmail());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


        cartRepository = new CartRepository(getApplication());


        Intent intent = getIntent();

        if( intent.getSerializableExtra("basket") != null ){
            basket = (Basket) intent.getSerializableExtra("basket");
        }else {
            try {

                basket = new Basket();
                List<Cart> carts = cartRepository.getAllCarts();
                for (Cart cart : carts){
                    basket.addFood(new FoodBasket(new Food(cart.getFoodName(),
                            cart.getFoodImage(),
                            cart.getFoodPrice(),
                            cart.getFoodRate(),
                            cart.getResKey(),
                            cart.getFoodKey()),
                            cart.getQuantity(),
                            cart.getSum()));
                }
                basket.calculateBasket();
                Log.d("ABC", basket.toString());

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        tvTotal = findViewById(R.id.tvTotal);
        tvTotal.setText(basket.getTotalPrice()+"");
        rvFoods = findViewById(R.id.rvFoods);
        adapter = new FoodBasketAdapter(new ArrayList<>(basket.foods.values()));
        rvFoods.setAdapter(adapter);
        rvFoods.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartRepository.deleteAll();
                String orderKey = fDatabase.getReference().child("orders").push().getKey();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String orderDate = sdf.format(System.currentTimeMillis());

                OrderFinished orderFinished = new OrderFinished(orderKey, orderDate, basket.getTotalPrice(), 1, userID, new ArrayList<FoodBasket>(basket.foods.values()));
                fDatabase.getReference().child("orders").child(userID).child(orderKey).setValue(orderFinished)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void aVoid) {


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


                finish();

            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (googleMap != null) {
            map = googleMap;

        }
    }
}
