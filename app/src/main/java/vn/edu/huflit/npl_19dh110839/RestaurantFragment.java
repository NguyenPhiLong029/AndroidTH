package vn.edu.huflit.npl_19dh110839;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vn.edu.huflit.npl_19dh110839.adapter.RestaurantAdapter;
import vn.edu.huflit.npl_19dh110839.models.Restaurant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment implements RestaurantAdapter.Listener {

    RecyclerView rvRestaurants;
    RestaurantAdapter restaurantAdapter;
    ArrayList<Restaurant> restaurants;
    FirebaseDatabase fDatabase;
    DatabaseReference dRestaurant;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        restaurants = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRestaurants = view.findViewById(R.id.rvRestaurants);
        restaurantAdapter = new RestaurantAdapter(restaurants, this, 1);
        rvRestaurants.setAdapter(restaurantAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvRestaurants.setLayoutManager(layoutManager);
        rvRestaurants.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        fDatabase = FirebaseDatabase.getInstance();
        dRestaurant = fDatabase.getReference();

        Query qRestaurant = dRestaurant.child("restaurants");
        qRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurants.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                    restaurants.add(restaurant);
                }
                restaurantAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onClick(Restaurant restaurantItem) {
        Bundle bundl=new Bundle();
        Intent intent=new Intent(getContext(), DetailRestaurantActivity.class);
        bundl.putSerializable("restaurant",restaurantItem);
        intent.putExtras(bundl);
        startActivity(intent);
    }
}