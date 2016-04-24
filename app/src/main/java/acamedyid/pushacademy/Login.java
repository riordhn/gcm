package acamedyid.pushacademy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Function function = new Function(Login.this);

        if (!SaveSharedPreference.getUser(Login.this).equals("")) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        findViewById(R.id.email_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((TextView)findViewById(R.id.email)).getText().toString();
                String password = ((TextView)findViewById(R.id.password)).getText().toString();

                function.login(email, password);
            }
        });

    }
}
