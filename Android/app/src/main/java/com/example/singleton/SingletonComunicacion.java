package com.example.singleton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class SingletonComunicacion extends Observable implements Runnable{

    private static SingletonComunicacion ref;

    private Socket s;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private boolean conectado;

    private SingletonComunicacion(){
        conectado = false;
    }

    public static SingletonComunicacion getInstance() {

        // Solo deja que corra una instancia de Singleton
        if( ref == null ){
            ref = new SingletonComunicacion();
            new Thread(ref).start();
            System.out.println("INICIADO");
        }
        return ref;
    }

    @Override
    public void run() {

        // Conectarse al Host
        if(!conectado){
            try {
                InetAddress ip = InetAddress.getByName("10.0.2.2");
                s = new Socket(ip, 5000);
                entrada = new DataInputStream(s.getInputStream());
                salida = new DataOutputStream(s.getOutputStream());
                conectado = true;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while(true){
            try {

                //Recibe lo que manda el host
                recibir();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void recibir() throws IOException {
        if(conectado){
            String mensajeEntrada = entrada.readUTF();
            setChanged();
            notifyObservers(mensajeEntrada);
            clearChanged();
        }
    }

    public void enviar(final String mensaje){

        //Manda el mensaje con el string de las coordenadas y 
        if(conectado) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        salida.writeUTF(mensaje);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
