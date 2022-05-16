package vn.edu.huflit.npl_19dh110839;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.navigation.NavDestination;
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
    FirebaseDatabase fDatabase;
    FirebaseAuth fAuth;
    TextView tvFullName, tvEmail;
    CartRepository cartRepository;
    Menu mMenu;
    int mCartItemCount = 0;
    TextView textCartItemCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        fDatabase = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();

        cartRepository = new CartRepository(getApplication());
        toolbar = findViewById(R.id.toolbarDrawer);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.orderFragment, R.id.profileFragment).setDrawerLayout(drawer).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.profileFragment) {
                    mMenu.findItem(R.id.mnuCart).setVisible(false);
                } else if (mMenu != null) {
                    mMenu.findItem(R.id.mnuCart).setVisible(true);
                }
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
                    tvFullName.setText(user.getLastName() + ' ' + user.getFirstName());
                    tvEmail.setText(user.getEmail());

                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mCartItemCount = cartRepository.getCountCart();
            setupBadge();
            Log.d("ABC", mCartItemCount + "");
        } catch (ExecutionException e) {
            Log.d("ABC", e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d("ABC", e.getMessage());
            e.printStackTrace();
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menucart, menu);
        mMenu = menu;


        View actionView = mMenu.findItem(R.id.mnuCart).getActionView();
        textCartItemCount = actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(mMenu.findItem(R.id.mnuCart));
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuCart) {
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }

        }
    }
}
