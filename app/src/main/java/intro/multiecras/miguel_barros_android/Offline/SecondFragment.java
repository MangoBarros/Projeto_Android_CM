package intro.multiecras.miguel_barros_android.Offline;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import intro.multiecras.miguel_barros_android.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class SecondFragment extends Fragment {
    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    private EditText mTitleView;
    private EditText mDescriptionView;
    private EditText mCidadeView;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        final View createFragment= inflater.inflate(R.layout.fragment_second, container, false);
        mTitleView = createFragment.findViewById(R.id.input_title);
        mCidadeView = createFragment.findViewById(R.id.input_cidade);
        mDescriptionView = createFragment.findViewById(R.id.input_desc);

        final Button button = createFragment.findViewById(R.id.btn_create);
        button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(mTitleView.getText()) && TextUtils.isEmpty(mCidadeView.getText()) && TextUtils.isEmpty(mDescriptionView.getText())){
                    getActivity().setResult(RESULT_CANCELED, replyIntent);

                }else {
                    String titulo = mTitleView.getText().toString();
                    String cidade = mCidadeView.getText().toString();
                    String descricao = mDescriptionView.getText().toString();
                    getActivity().setResult(RESULT_OK, replyIntent);
                    NavHostFragment.findNavController( SecondFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);

                }


            }
        });
        // Inflate the layout for this fragment
        return createFragment;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_voltar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }
}
