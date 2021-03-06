package intro.multiecras.miguel_barros_android.Offline;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import intro.multiecras.miguel_barros_android.R;

import static intro.multiecras.miguel_barros_android.R.*;

public class MainActivity extends AppCompatActivity {
    public String accept = "none";
    private NotaViewModel mNotaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        mNotaViewModel = ViewModelProviders.of(this).get(NotaViewModel.class);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();
            mNotaViewModel.deleteAll();
            // Delete the existing data

            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.changeToEn) {

            return true;
        }
        if (id == R.id.changeToPt) {

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
