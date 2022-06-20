package com.example.targertchat.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.targertchat.R;
import com.example.targertchat.data.repositories.UsersRepository;
import com.example.targertchat.data.utils.NotificationToken;
import com.example.targertchat.data.utils.PostRegisterUser;
import com.example.targertchat.ui.contacts.ContactsActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText userNameEdt, passwordEdt, verifyPasswordEdt, displayNameEdt;
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView tv = findViewById(R.id.to_login);
        tv.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        userNameEdt = findViewById(R.id.uesrname_text);
        passwordEdt = findViewById(R.id.password_text);
        verifyPasswordEdt = findViewById(R.id.verifyPassword_text);
        displayNameEdt = findViewById(R.id.displayName_text);
        Button registerBtn = findViewById(R.id.registerBtn);

        UserViewModel userViewModel = new ViewModelProvider
                (this, new UserViewModelFactory()).get(UserViewModel.class);


        imgBtnInit();

        registerBtn.setOnClickListener(v -> {
                // on below line we are getting data from our edit text.
                String userName = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String verifyPassword = verifyPasswordEdt.getText().toString();
                String displayName = displayNameEdt.getText().toString();

                // checking if the entered text is empty or not.
                if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.equals(password, verifyPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords are not matching!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(password);
                if(!matcher.matches()) {
                    Toast.makeText(RegisterActivity.this, "Password must contain: at least one number, at least one lowercase character, " +
                            "at least one uppercase character and should be in the length of between 8 to 20" +
                            "Password can not contain whitespaces!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(displayName)) {
                    Toast.makeText(RegisterActivity.this, "Display Name is required!", Toast.LENGTH_SHORT).show();
                }

                if (img == null) {
                    Toast.makeText(RegisterActivity.this, "Image is required!", Toast.LENGTH_SHORT).show();
                }

                PostRegisterUser registerUser = new PostRegisterUser(userName, password, displayName, img);

                userViewModel.register(registerUser);
                userViewModel.isLoggedIn().observe(this, answerBoolean -> {
                if (answerBoolean) {
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(RegisterActivity.this, instanceIdResult -> {
                        String token = instanceIdResult.getToken();
                        NotificationToken notificationToken = new NotificationToken(token);
                        userViewModel.notifyToken(notificationToken);
                    });

                    Intent intent = new Intent(this, ContactsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Either user exists or server is not responsive.", Toast.LENGTH_SHORT).show();
                }
            });
            });
        }

        private void imgBtnInit() {
            ActivityResultLauncher<Intent> imgLauncher =
                    registerForActivityResult(
                            new ActivityResultContracts.StartActivityForResult(), result -> {
                                if (result.getResultCode() == RESULT_OK && result.getData()!= null) {
                                    Intent data = result.getData();
                                    if(data.getData() != null) {
                                        Uri uri = (Uri) data.getData();
                                        Bitmap bitmap;
                                        try {
                                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                            byte[] b = byteArrayOutputStream.toByteArray();
                                            this.img = Base64.encodeToString(b, Base64.DEFAULT);
                                        }
                                        catch(IOException e) {
                                            Toast.makeText(this,"Error in uploading image", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }
                            });

            Button addImageBtn = findViewById(R.id.addImage);
            addImageBtn.setOnClickListener( view -> {
                        Intent imageBrowser = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        imageBrowser.setType("image/*");
                        imgLauncher.launch(imageBrowser);
                    }
            );
        }
    }