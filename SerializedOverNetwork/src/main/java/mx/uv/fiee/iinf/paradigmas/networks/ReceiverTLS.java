package mx.uv.fiee.iinf.paradigmas.networks;

import mx.uv.fiee.iinf.paradigmas.networks.models.Persona;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReceiverTLS {

    public static void main(String[] args) {
        // Establecer el truststore para el cliente (puede ser el mismo keystore en pruebas locales)
        System.setProperty("javax.net.ssl.trustStore", "servidor.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 19000);
            socket.startHandshake();
            System.out.println("Conexión TLS establecida con el servidor.");
            SSLSession session = socket.getSession();
            System.out.println("--- Conexión TLS establecida con el servidor ----");
            System.out.println("--- Protocolo y versión:  " + session.getProtocol());
            System.out.println("--- Cipher suite:         " + session.getCipherSuite());


            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            while (true) {
                try {
                    Persona p = (Persona) ois.readObject();
                    System.out.println("Recibido UUID: " + p.getUuid());
                } catch (ClassNotFoundException e) {
                    System.err.println("Clase no encontrada: " + e.getMessage());
                    break;
                } catch (IOException e) {
                    System.err.println("Error de conexión: " + e.getMessage());
                    break;
                }
            }

            ois.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

