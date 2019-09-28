package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

	//Arquivos em memoria
    List<String> arquivos = new ArrayList<String>();

	

    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");
        
        Servidor server = new Servidor();

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String mensagem = dis.readUTF();
            System.out.println(mensagem);
            String[] msgSplitada = mensagem.split(" ");
            
            switch (msgSplitada[0]) {
			case "readdir":
            	dos.writeUTF("Arquivos:" + server.readdir(msgSplitada[1]));				
				break;

			case "rename":
            	server.rename(msgSplitada[1], msgSplitada[2]);				
            	dos.writeUTF("");				
            	break;

			case "create":
            	server.create(msgSplitada[1]);
            	dos.writeUTF("");				
            	break;
				
			case "remove":
            	server.remove(msgSplitada[1]);
            	dos.writeUTF("");				
            	break;
			default:
				dos.writeUTF("Li sua mensagem: " + mensagem + " mas nenhum comando reconhecido!");
				break;
			}    
            
        }
        /*
         * Observe o while acima. Perceba que primeiro se lê a mensagem vinda do cliente (linha 29, depois se escreve
         * (linha 32) no canal de saída do socket. Isso ocorre da forma inversa do que ocorre no while do Cliente2,
         * pois, de outra forma, daria deadlock (se ambos quiserem ler da entrada ao mesmo tempo, por exemplo,
         * ninguém evoluiria, já que todos estariam aguardando.
         */
    }
    
    public String readdir (String diretorio) {
    	return this.arquivos.toString();
    }
    
    public void rename (String arquivo, String novoNome) {
    	for (int i = 0; i < this.arquivos.size(); i++) {
			if (this.arquivos.get(i).equals(arquivo)) {
				this.arquivos.set(i, novoNome);
			}
		} 
    }
    
    public void create (String nomeArquivo) {
    	this.arquivos.add(nomeArquivo);
    }
    
    public void remove (String nomeArquivo) {
    	for (int i = 0; i < this.arquivos.size(); i++) {
			if (this.arquivos.get(i).equals(nomeArquivo)) {
				this.arquivos.remove(i);
			}
		} 
    }
}
