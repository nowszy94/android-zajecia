package com.example.prostykod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AuthenticationService authenticationService;
    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private TextView errorMessageField;
    private TextView authenticatedMessageField;
    private boolean errorOnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFields();
    }

    private void initFields() {
        this.authenticationService = AuthenticationService.getInstance();
        this.usernameField = findViewById(R.id.username);
        this.passwordField = findViewById(R.id.password);
        this.loginButton = findViewById(R.id.loginButton);
        this.errorMessageField = findViewById(R.id.errorMessage);
        this.authenticatedMessageField = findViewById(R.id.authenticatedMessage);
        this.errorOnLogin = false;

        this.errorMessageField.setVisibility(View.INVISIBLE);
        this.authenticatedMessageField.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticationService.authenticate(usernameField.getText().toString(), passwordField.getText().toString());
                errorOnLogin = !authenticationService.getIsAuthenticated();

                errorMessageField.setVisibility(errorOnLogin ? View.VISIBLE : View.INVISIBLE);

                if (authenticationService.getIsAuthenticated()) {
                    startActivity(new Intent(MainActivity.this, NotepadActivity.class));
                }
            }
        });
    }
}
