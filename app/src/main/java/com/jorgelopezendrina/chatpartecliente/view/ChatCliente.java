package com.jorgelopezendrina.chatpartecliente.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.jorgelopezendrina.chatpartecliente.R;
import com.jorgelopezendrina.chatpartecliente.pojo.Cliente;
import com.jorgelopezendrina.chatpartecliente.util.UtilThread;
import com.jorgelopezendrina.chatpartecliente.viewmodel.ViewModel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatCliente extends Fragment {

    private boolean run = true;
    private ViewModel viewModel;
    private TextView nombreCliente;
    private TextView tvPanelChat;
    private EditText etTextoEnvio;
    private String text,listaLimpia="";
    private ImageButton btEnviar, btSalir,bt_privado;
    private String host = "10.0.2.2",lista="+";
    private int port = 5000;
    private Cliente cliente;

    public ChatCliente() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_chat_cliente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        startClient();
    }

    private void init(@NonNull View view) {
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        nombreCliente = view.findViewById(R.id.tvNombreCliente);
        nombreCliente.setText(viewModel.getNombre());
        tvPanelChat = view.findViewById(R.id.tvPanelChat);
        etTextoEnvio = view.findViewById(R.id.etTextoEnvio);
        btEnviar = view.findViewById(R.id.btEnviar);
        botonListaUsuarios(view);
        botonSalir(view);
    }


    private void startClient() {
        cliente = new Cliente();
        cliente.setNombre(viewModel.getNombre());
        UtilThread.threadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    cliente.setSocket(new Socket(host, port));
                    cliente.setFlujoE(new DataInputStream(cliente.getSocket().getInputStream()));
                    cliente.setFlujoS(new DataOutputStream(cliente.getSocket().getOutputStream()));
                    cliente.getFlujoS().writeUTF(cliente.getNombre());
                    cliente.setIdCliente(Integer.parseInt(cliente.getFlujoE().readUTF()));
                    lecturaCliente();
                    escribirCliente();
                } catch (IOException ex) {
                }
            }
        });
    }

    private void escribirCliente() {
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = etTextoEnvio.getText().toString();
                if (!mensaje.equals("")) {
                    mandaMensaje(mensaje);
                }
            }
        });
        etTextoEnvio.setText("");
    }

    private void mandaMensaje(String mensaje) {
        UtilThread.threadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    cliente.getFlujoS().writeUTF(mensaje);
                    if (mensaje.equals("*")){
                       cliente.cerrarFlujos();
                    }
                } catch (IOException ex) {
                    System.out.println("btEnviar " + ex.getLocalizedMessage());
                }
                etTextoEnvio.setText("");
            }
        });
    }

    private void lecturaCliente() {
        UtilThread.threadWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while (run) {
                    try {
                        text = cliente.getFlujoE().readUTF();
                        if (!isListaClientes()){
                            tvPanelChat.post(new Runnable() {
                                @Override
                                public void run() {
                                    tvPanelChat.append(text);
                                }
                            });
                        }else{
                            preparaListaClientes(text);
                        }
                    } catch (IOException ex) {
                        System.out.println("Lectura cliente" + ex.getLocalizedMessage());
                        run = false;
                    }
                }
            }
        });
    }

    private void preparaListaClientes(String text) {
        String[] listaCruda = text.split(":");
        for (String cad:listaCruda) {
            if (!cad.equals("")){
                listaLimpia += ("Usuario: "+cad+"\n");
            }
        }
    }

    private void muestraListaUsuarios() {
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(getContext());
        alertDialog.setTitle("Usuarios");
        alertDialog.setMessage(listaLimpia);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listaLimpia="";
            }
        });
        alertDialog.show();
    }

    private boolean isListaClientes() {
        return text.charAt(0)==':';
    }

    private void botonListaUsuarios(@NonNull View view) {
        bt_privado= view.findViewById(R.id.btPrivado);
        bt_privado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandaMensaje(lista);
                muestraListaUsuarios();
            }
        });
    }


    private void botonSalir(@NonNull View view) {
        btSalir = view.findViewById(R.id.btSalir);
        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliente.cerrarFlujos();
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