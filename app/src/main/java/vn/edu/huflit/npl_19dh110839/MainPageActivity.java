package vn.edu.huflit.npl_19dh110839;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;

import vn.edu.huflit.npl_19dh110839.models.App;
import vn.edu.huflit.npl_19dh110839.models.CartRepository;
import vn.edu.huflit.npl_19dh110839.models.Users;

public class MainPageActivity extends AppCompatActivity {
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    NavigationView navigationView;
    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    TextView textCartItemCount;
    int mCartItemCount = 0;
    App app;
    Menu mMenu;
    boolean flag = true;
    CartRepository cartRepository;

    FirebaseDatabase fDatabase;
    FirebaseAuth fAuth;
    TextView tvFullName, tvEmail;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnucart, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        fDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbarDrawer);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.open, R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.orderFragment, R.id.profileFragment).setDrawerLayout(drawer).build();

        navController=Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            MenuItem item= toolbar.getMenu().getItem(0);
            if (navDestination.getId() == R.id.profileFragment) {
                item.setVisible(false);
            } else {
                item.setVisible(true);
            }
        });

        View view = navigationView.getHeaderView(0);
        tvFullName = view.findViewById(R.id.name_header);
        tvEmail = view.findViewById(R.id.email_header);

        String userID = fAuth.getCurrentUser().getUid();

        fDatabase.getReference().child("users").child(userID).get()
                .addOnSuccessListener(dataSnapshot -> {
                    Users user = dataSnapshot.getValue(Users.class);
                    user.setUserID(userID);
                    tvFullName.setText(user.getFirstName()+' '+user.getLastName());
                    tvEmail.setText(user.getEmail());

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainPageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuCart) {
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        try {
//            mCartItemCount = cartRepository.getCountCart();
//            setupBadge();
//            Log.d("ABC", mCartItemCount+"");
//        } catch (ExecutionException e) {
//            Log.d("ABC", e.getMessage());
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            Log.d("ABC", e.getMessage());
//            e.printStackTrace();
//        }
//
//    }

//    private void setupBadge() {
//        if (textCartItemCount != null) {
//            if (mCartItemCount == 0) {
//                if (textCartItemCount.getVisibility() != View.GONE) {
//                    textCartItemCount.setVisibility(View.GONE);
//                }
//            } else {
//                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
//                if (textCartItemCount.getVisibility() != View.VISIBLE) {
//                    textCartItemCount.setVisibility(View.VISIBLE);
//                }
//            }
//
//        }
//    }

}
