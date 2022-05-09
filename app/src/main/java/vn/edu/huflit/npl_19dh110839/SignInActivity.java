package vn.edu.huflit.npl_19dh110839;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    Button signIn, signUp;
    TextInputLayout userName, passWord;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);

        signUp = findViewById(R.id.btnSignUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signIn = findViewById(R.id.btnSignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usename = userName.getEditText().getText().toString();
                String password = passWord.getEditText().getText().toString();

                if (TextUtils.isEmpty(usename)) {
                    userName.setError("Username required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passWord.setError("Password required");
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(usename, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Snackbar.make(view, "Successful Login", Snackbar.LENGTH_LONG).show();
                            Intent intent = new Intent(view.getContext(), MainPageActivity.class);
                            startActivity(intent);
                            return;
                        }
                    }
                });
                firebaseAuth.signInWithEmailAndPassword(usename, password).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}