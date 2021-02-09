package com.jorgelopezendrina.chatpartecliente.view;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.google.android.material.textfield.TextInputEditText;
import com.jorgelopezendrina.chatpartecliente.R;
import com.jorgelopezendrina.chatpartecliente.pojo.Cliente;
import com.jorgelopezendrina.chatpartecliente.viewmodel.ViewModel;


public class NombreUser extends Fragment {

    private ViewModel viewModel;
    private Cliente cliente;


    public NombreUser() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_nombre_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        final NavController navC = Navigation.findNavController(view);
        botonSalir(view);
        botonAceptar(view, navC);
    }

    private void botonAceptar(@NonNull View view, NavController navC) {
        ImageButton btAceptarNombre = view.findViewById(R.id.btAceptarNombre);
        TextInputEditText etNombreUsuario = view.findViewById(R.id.etNombreUsuario);
        btAceptarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (etNombreUsuario.getText().length()>15 || etNombreUsuario.getText().length()==0){
                   etNombreUsuario.setError("Longitud excedida o campo vacío");
               }else{
                   viewModel.setNombre(etNombreUsuario.getText().toString());
                   navC.navigate(R.id.ac_nombreUser_to_chatCliente);
               }
            }
        });
    }

    private void botonSalir(@NonNull View view) {
        ImageButton btSalir = view.findViewById(R.id.btCancelarNombre);
        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getActivity().finishAndRemoveTask();
                } else {
                    getActivity().finish();
                    System.exit(0);
                }
            }
        });
    }
}