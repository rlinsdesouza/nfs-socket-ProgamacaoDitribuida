package br.edu.ifpb.gugawag.so.sockets;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Topico {
	
	private String nome;
	private List<String> mensagens;
	private List<Socket> seguidores;
	
	public Topico(String nome) {
		super();
		this.nome = nome;
		this.mensagens = new ArrayList<String>();
		this.seguidores = new ArrayList<Socket>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getMensagens() {
		return mensagens;
	}

	public void addMensagens(String mensagen) {
		this.mensagens.add(mensagen);
	}

	public List<Socket> getSeguidores() {
		return seguidores;
	}

	public void addSeguidor(Socket seguidor) {
		this.seguidores.add(seguidor);
	}

	@Override
	public String toString() {
		return "Topico [nome=" + nome + "]";
	}

}
