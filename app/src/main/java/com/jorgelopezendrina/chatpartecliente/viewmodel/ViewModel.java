package com.jorgelopezendrina.chatpartecliente.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jorgelopezendrina.chatpartecliente.pojo.Cliente;

public class ViewModel  extends AndroidViewModel {

    private Cliente cliente;
    private String nombre;

    public ViewModel(@NonNull Application application) {
        super(application);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
