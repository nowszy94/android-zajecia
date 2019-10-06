package com.example.prostykod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NotepadActivity extends AppCompatActivity {

    private AuthenticationService authenticationService = AuthenticationService.getInstance();
    private NotepadService notepadService = NotepadService.getInstance();
    private EditText notepadField;
    private EditText changePasswordField;
    private Button saveNotepadButton;
    private Button changePasswordButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        initFields();
    }

    private void initFields() {
        this.notepadField = findViewById(R.id.notepad);
        this.changePasswordField = findViewById(R.id.changePassword);
        this.saveNotepadButton = findViewById(R.id.notepadButton);
        this.changePasswordButton = findViewById(R.id.changePasswordButton);
        this.logoutButton = findViewById(R.id.logoutButton);

        this.notepadField.setText(notepadService.getContent());

        this.saveNotepadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notepadService.setContent(notepadField.getText().toString());
            }
        });

        this.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticationService.changePassword(changePasswordField.getText().toString());
            }
        });

        this.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticationService.logout();
                startActivity(new Intent(NotepadActivity.this, MainActivity.class));
            }
        });
    }
}
