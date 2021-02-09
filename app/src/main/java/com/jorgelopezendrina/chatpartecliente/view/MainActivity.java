package com.jorgelopezendrina.chatpartecliente.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jorgelopezendrina.chatpartecliente.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void muestraListaUsuarios(String listaLimpia) {
        Dialog dialogLista = new Dialog(MainActivity.this);
        dialogLista.setContentView(R.layout.lista_dialogo);
        ImageButton btAceptarLista = dialogLista.findViewById(R.id.btAceptarLista);
        TextView tvListaClientes = dialogLista.findViewById(R.id.tvListaClientes);
        tvListaClientes.setText(listaLimpia);
        btAceptarLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLista.dismiss();
            }
        });
        dialogLista.show();
    }
}