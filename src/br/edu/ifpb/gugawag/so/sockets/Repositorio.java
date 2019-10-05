package br.edu.ifpb.gugawag.so.sockets;

import java.util.HashMap;

public class Repositorio {
	private static HashMap<String,Topico> topicos = new HashMap<String,Topico>();

	public static HashMap<String, Topico> getTopicos() {
		return Repositorio.topicos;
	}

	public static void setTopicos(HashMap<String, Topico> topicos) {
		Repositorio.topicos = topicos;
	}
	
	public static Topico addTopic (String nome) {
		Topico novo = new Topico (nome);
		Repositorio.topicos.put(nome, novo);
		return novo;
	}
    
	public static String readTopicos () {
		if (Repositorio.topicos.isEmpty()) {
			return null;
		}
		return Repositorio.topicos.keySet().toString();
	}
	
	public static Topico getTopico(String topico) {
		return Repositorio.topicos.get(topico);
	}

}
