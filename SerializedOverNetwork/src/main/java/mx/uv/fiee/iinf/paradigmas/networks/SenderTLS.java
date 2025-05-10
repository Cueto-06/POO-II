package mx.uv.fiee.iinf.paradigmas.networks;

import mx.uv.fiee.iinf.paradigmas.networks.models.Persona;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.UUID;

public class SenderTLS {
    static final int PORT = 19000;

    public static void main(String[] args) throws IOException {
        // Establecer el keystore para el servidor
        System.setProperty("javax.net.ssl.keyStore", "servidor.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(PORT);

        System.out.println("Esperando conexión TLS del cliente...");
        SSLSocket socket = (SSLSocket) serverSocket.accept();
        System.out.println("Conexión TLS establecida con el cliente.");
        SSLSession session = socket.getSession();
        System.out.println("--- Conexión TLS establecida con el cliente");
        System.out.println("--- Protocolo y versión:  " + session.getProtocol());
        System.out.println("--- Cipher suite:         " + session.getCipherSuite());



        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        Random random = new Random();

        while (true) {
            Persona p = new Persona();
            p.setFullname("Nombre Aleatorio " + random.nextInt(1000));
            p.setAge(random.nextInt(55));
            p.setUuid(UUID.randomUUID().toString());

            oos.writeObject(p);
            oos.flush();

            System.out.println("Enviado objeto con UUID: " + p.getUuid());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }

        oos.close();
        socket.close();
        serverSocket.close();
    }
}

