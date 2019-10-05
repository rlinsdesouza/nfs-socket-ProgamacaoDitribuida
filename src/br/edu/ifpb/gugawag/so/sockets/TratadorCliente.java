package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class TratadorCliente extends Thread {

    private Socket cliente;
    
    public TratadorCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public void run() {
    	
    	while(true) {
            try { 
            	DataInputStream entrada = null;
                entrada = new DataInputStream(cliente.getInputStream());
                String mensagem = entrada.readUTF(); //aqui o codigo pausa
                System.out.println("o cliente"+ this.cliente.getInetAddress() + "/" + this.cliente.getPort() + " falou " + mensagem);
                
                DataOutputStream saida = null;
                saida = new DataOutputStream(cliente.getOutputStream());
                saida.writeUTF("Servidor diz: recebi o comando: "+mensagem);
                
                
                String[] msgSplitada = mensagem.split(" ");
                String comando = msgSplitada[0];
                String[] parametros = mensagem.split("\"");
                
                if(msgSplitada.length<2) {
                	saida = null;
                    saida = new DataOutputStream(cliente.getOutputStream());
                	saida.writeUTF("Querendo quebrar o servidor? Favor verificar a sintaxe dos comandos");
                
                }else {
                	switch (comando) {
        			case "list":
        				String topicos = Repositorio.readTopicos();
        				if (topicos != null) {
    						saida.writeUTF(topicos);					
        				}else {
        					saida.writeUTF("Topicos inexistentes!");	
        				}
        				break;

        			case "criar":
        				Topico topico = Repositorio.addTopic(parametros[1]);
        				topico.addSeguidor(this.cliente);
        				saida.writeUTF("Topico criado com sucesso!");					
        				break;

        			case "addMsg":
        				topico = Repositorio.getTopico(parametros[1]);
        				if (topico!=null) {
        					topico.addMensagens(parametros[3]);
        					List<Socket> seguidores = topico.getSeguidores();
        					if (!seguidores.isEmpty()) {
        						for (Socket seguidor : seguidores) {
            						DataOutputStream saidaToSeguidor = new DataOutputStream(seguidor.getOutputStream());				
            						saidaToSeguidor.writeUTF("O topico: "+ topico.getNome() + " teve a msg '" + parametros[3] + "' adicionada recentemente!");
            					}	
        					}
        				}
    					break;
        				
        			case "seguir":
        				topico = Repositorio.getTopico(parametros[1]);
        				if (topico != null) {
    						topico.addSeguidor(this.cliente);
    						saida.writeUTF("Seguindo topico: "+topico);
        				}else {
        					saida.writeUTF("Topico nao existente!");	
        				}		
                    	break;
        			default:
        				saida.writeUTF(msgSplitada[0] + " nao eh um comando reconhecido!");
        				break;
        			}    	
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}