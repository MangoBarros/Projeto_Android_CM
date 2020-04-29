package intro.multiecras.miguel_barros_android.Mapas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import intro.multiecras.miguel_barros_android.API.Nota;
import intro.multiecras.miguel_barros_android.R;

public class SeeNota extends AppCompatActivity {

    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_nota);

        Intent i = getIntent();
        id = Integer.valueOf(i.getStringExtra("id"));

        Toast.makeText(getApplicationContext(),String.valueOf(id), Toast.LENGTH_SHORT).show();
    }
}
