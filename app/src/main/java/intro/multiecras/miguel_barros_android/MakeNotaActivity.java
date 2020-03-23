package intro.multiecras.miguel_barros_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import intro.multiecras.miguel_barros_android.DB.Notas.Nota;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MakeNotaActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    private EditText mTitleView;
    private EditText mDescriptionView;
    private EditText mCidadeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);

        mTitleView = findViewById(R.id.input_title);
        mCidadeView = findViewById(R.id.input_cidade);
        mDescriptionView = findViewById(R.id.input_desc);
        final Button button = findViewById(R.id.btn_create);
        button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(mTitleView.getText()) && TextUtils.isEmpty(mCidadeView.getText()) && TextUtils.isEmpty(mDescriptionView.getText())){
                    setResult(RESULT_CANCELED, replyIntent);

                }else {
                    String titulo = mTitleView.getText().toString();
                    String cidade = mCidadeView.getText().toString();
                    String descricao = mDescriptionView.getText().toString();
                    String[] nota = {titulo,cidade,descricao};
                    replyIntent.putExtra(EXTRA_REPLY, nota);
                    setResult(RESULT_OK, replyIntent);


                }
                finish();


            }
        });
    }
}
