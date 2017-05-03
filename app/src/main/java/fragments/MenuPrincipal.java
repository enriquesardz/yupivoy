package fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ensardz.yupivoyenrique.FormulariosContainer;
import com.example.ensardz.yupivoyenrique.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuPrincipal extends Fragment {

    private Button hotelAvionButton;

    public MenuPrincipal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_principal, container, false);
        hotelAvionButton = (Button)view.findViewById(R.id.hotel_avion_button);
        hotelAvionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FormulariosContainer.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
