package fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ensardz.yupivoyenrique.R;


public class FormularioAvion extends Fragment {

    private static final String LOG = FormularioAvion.class.getSimpleName();
    private static final int THRESHOLD = 3;

//    private EditText fechaEntradaEditText;
//    private EditText fechaSalidaEditText;
//    private TextView nochesTextView;

    private Context mContext;

    public FormularioAvion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_avion, container, false);
        mContext = getContext();


        return view;
    }
}
