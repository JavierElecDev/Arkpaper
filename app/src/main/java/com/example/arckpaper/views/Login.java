package com.example.arckpaper.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.arckpaper.MainActivity;
import com.example.arckpaper.R;
import com.example.arckpaper.models.User;
import com.example.arckpaper.views.interfaces.ILoginView;

public class Login extends AppCompatActivity {

    private LoginPresenter presenter;
    private EditText email, pass;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(new listenerPresenter(),this);

        email = findViewById(R.id.logetEmail);
        pass = findViewById(R.id.logetPassword);
        btnLogin = findViewById(R.id.logBtnLogin);
        btnRegister = findViewById(R.id.logBtnRegister);

    }

    @Override
    protected void onResume() {
        super.onResume();

        btnLogin.setOnClickListener(view -> {

            User user = new User(email.getText().toString(),pass.getText().toString());
            presenter.insertUser(user);
        });
    }

    private class listenerPresenter implements ILoginView{

        @Override
        public void showError(String msg) {
            Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void showInsertUser(int id) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }
}