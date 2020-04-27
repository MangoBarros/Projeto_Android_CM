package intro.multiecras.miguel_barros_android.Account;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import intro.multiecras.miguel_barros_android.API.GetData;
import intro.multiecras.miguel_barros_android.API.RetrofitClientInstance;
import intro.multiecras.miguel_barros_android.API.User;
import intro.multiecras.miguel_barros_android.Mapas.MapaActivity;
import intro.multiecras.miguel_barros_android.Offline.MainActivity;
import intro.multiecras.miguel_barros_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static intro.multiecras.miguel_barros_android.Account.LoginActivity.SHARED_PREFS;

public class NewAccountActivity extends AppCompatActivity {

    private TextView username;
    private TextView mail;
    private TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        username = findViewById(R.id.usernameIn);
        mail = findViewById(R.id.emailIn);
        password = findViewById(R.id.passwordIn);


    }

    public void createAccountA(View view) {
        if(password.getText().length()<8){
            Log.i("testPass", "Testar password");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("A Password precisa de ter 8 caracteres");
                    alertDialogBuilder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return;
        }
        User user = new User(mail.getText().toString(),password.getText().toString());
        user.setName(username.getText().toString());
        GetData service = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<User> call = service.postRegister(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.body() != null){
                    User user = response.body();
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", user.getApi_token());
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), MapaActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "Algo correu mal tente novamente", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("error",t.getMessage());
                Toast.makeText(getApplicationContext(), "Algo correu mal tente novamente", Toast.LENGTH_SHORT).show();
            }
        });

        }
    }

