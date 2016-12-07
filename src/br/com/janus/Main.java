package br.com.janus;

import br.com.janus.view.GerenciadorDeInterface;

public class Main {

	public static void main(String[] args) {
		Conecta.conecta();
		GerenciadorDeInterface.run();
	}

}
