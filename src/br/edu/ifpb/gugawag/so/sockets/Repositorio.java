package br.edu.ifpb.gugawag.so.sockets;

import java.util.HashMap;

public class Repositorio {
	private HashMap<String,Topico> topicos = new HashMap<String,Topico>();

	public HashMap<String, Topico> getTopicos() {
		return topicos;
	}

	public void setTopicos(HashMap<String, Topico> topicos) {
		this.topicos = topicos;
	}
    
	public String readTopicos () {
		return this.topicos.keySet().toArray().toString();
	}
	
	public Topico getTopico(String topico) {
		return this.topicos.get(topico);
	}

}
