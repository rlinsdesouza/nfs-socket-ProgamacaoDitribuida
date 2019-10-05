package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class TratadorCliente extends Thread {

    private Socket cliente;
    private Repositorio repos;
    
    public TratadorCliente(Socket cliente) {
        this.cliente = cliente;
        this.repos = new Repositorio();
    }

    public void run() {
    	
    	while(true) {
            try { 
            	DataInputStream entrada = null;
                entrada = new DataInputStream(cliente.getInputStream());
                String mensagem = entrada.readUTF(); //aqui o codigo pausa
                System.out.println("o cliente falou " + mensagem);
                
                DataOutputStream saida = null;
                saida = new DataOutputStream(cliente.getOutputStream());
                saida.writeUTF("Servidor diz: recebi o comando: "+mensagem);
                
                
                String[] msgSplitada = mensagem.split(" ");
                String comando = msgSplitada[0];
                String parametro = "";                
                
                
                if(msgSplitada.length<2) {
                	saida = null;
                    saida = new DataOutputStream(cliente.getOutputStream());
                	saida.writeUTF("Querendo quebrar o servidor? Favor verificar a sintaxe dos comandos");
                
                }else {
                	Topico topico = repos.getTopico(msgSplitada[1]);
                    for (int i = 1; i < msgSplitada.length; i++) {
                    	parametro = parametro+msgSplitada[i];
    				}
                	switch (comando) {
        			case "listTopicos":
        				String topicos = repos.readTopicos();
        				if (topicos != null) {
    						saida.writeUTF(topicos);					
        				}else {
        					saida.writeUTF("Topicos inexistentes!");	
        				}
        				break;

        			case "criar":
        				Topico novo = new Topico(parametro);
    					saida.writeUTF("Topico criado com sucesso!");					
        				break;

        			case "addMensagem":
        				for (int i = 2; i < msgSplitada.length; i++) {
                        	parametro = parametro+msgSplitada[i];
        				}
        				if (topico!=null) {
        					topico.addMensagens(parametro);
        					List<Socket> seguidores = topico.getSeguidores();
        					for (Socket seguidor : seguidores) {
        						DataOutputStream saidaToSeguidor = new DataOutputStream(seguidor.getOutputStream());				
        						saidaToSeguidor.writeUTF(parametro);
        					}
        					saida.writeUTF("Mensagem adicionada com sucesso!");
        				}
    					break;
        				
        			case "seguir":
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