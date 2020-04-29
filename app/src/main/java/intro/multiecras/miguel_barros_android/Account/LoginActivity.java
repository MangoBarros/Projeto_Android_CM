package intro.multiecras.miguel_barros_android.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import intro.multiecras.miguel_barros_android.API.GetData;
import intro.multiecras.miguel_barros_android.API.RetrofitClientInstance;
import intro.multiecras.miguel_barros_android.API.User;
import intro.multiecras.miguel_barros_android.Offline.MainActivity;
import intro.multiecras.miguel_barros_android.Mapas.MapaActivity;
import intro.multiecras.miguel_barros_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    public static final String SHARED_PREFS = "token";
    private TextView mail;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.emailIn);
        password = findViewById(R.id.passwordIn);
    }

    public void notasPrivate(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void createAccount(View view) {
        Intent intent = new Intent(getApplicationContext(),NewAccountActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        User user = new User(mail.getText().toString(),password.getText().toString());
        GetData service = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<User> call = service.postLogin(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                assert response.body() != null;
                //String token = String.valueOf(response.body());
                User user = response.body();
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", user.getApi_token());
                editor.putInt("id", user.getId());
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MapaActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("error",t.getMessage());
                Toast.makeText(getApplicationContext(), "Algo correu mal tente novamente", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
