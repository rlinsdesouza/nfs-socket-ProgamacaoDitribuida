package br.edu.ifpb.gugawag.so.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
		
    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");
        
        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        
        
        // laço infinito do servidor
        while (true) {
            Socket cliente = serverSocket.accept();
            TratadorCliente tratadorCliente = new TratadorCliente(cliente);
            tratadorCliente.start();                                        
        }   
    }
}
