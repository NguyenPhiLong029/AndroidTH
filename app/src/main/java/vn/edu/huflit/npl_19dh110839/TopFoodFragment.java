package vn.edu.huflit.npl_19dh110839;

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
import java.util.Collections;

import vn.edu.huflit.npl_19dh110839.adapter.FoodAdapter;
import vn.edu.huflit.npl_19dh110839.models.Food;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopFoodFragment extends Fragment implements FoodAdapter.OnFoodItemClickListener {
    RecyclerView rvFood;
    FoodAdapter foodAdapter;
    ArrayList<Food> foods;
    FirebaseDatabase fDatabase;
    DatabaseReference dFood;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopFoodFragment newInstance(String param1, String param2) {
        TopFoodFragment fragment = new TopFoodFragment();
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
        foods = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFood = view.findViewById(R.id.rvFood);
        foodAdapter = new FoodAdapter(foods, this);
        rvFood.setAdapter(foodAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvFood.setLayoutManager(layoutManager);
        rvFood.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        fDatabase = FirebaseDatabase.getInstance();
        dFood = fDatabase.getReference();
        Query qFood = dFood.child("foods").orderByChild("rate");
        qFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foods.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    foods.add(food);
                }
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_food, container, false);
    }


    @Override
    public void onFoodItemClick(Food food) {

    }
}