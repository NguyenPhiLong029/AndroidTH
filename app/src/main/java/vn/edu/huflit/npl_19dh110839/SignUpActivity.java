package vn.edu.huflit.npl_19dh110839;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.toolbar);
        NavController navController = Navigation.findNavController(this, R.id.nav_signup);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.fullNameFragment, R.id.addressFragment, R.id.usernamePasswordFragment).build();

        NavigationUI.setupWithNavController(toolbar, navController);
    }
}