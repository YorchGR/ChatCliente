package com.jorgelopezendrina.chatpartecliente.pojo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente  {
    private int idCliente = -1;
    private String nombre;
    private Socket socket;
    private DataInputStream flujoE;
    private DataOutputStream flujoS;

    public Cliente() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getFlujoE() {
        return flujoE;
    }

    public void setFlujoE(DataInputStream flujoE) {
        this.flujoE = flujoE;
    }

    public DataOutputStream getFlujoS() {
        return flujoS;
    }

    public void setFlujoS(DataOutputStream flujoS) {
        this.flujoS = flujoS;
    }

    public void cerrarFlujos() {
        try {
            flujoE.close();
            flujoS.close();
            socket.close();
        } catch (IOException ex) {
        }
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + idCliente + ", Nombre=" + nombre + ", socket=" + socket + ", flujoE=" + flujoE + ", flujoS=" + flujoS + '}';
    }
}
