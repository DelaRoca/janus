package br.com.janus;

import br.com.janus.controller.OrdemServicoController;
import br.com.janus.view.GerenciadorDeInterface;

public class Main {

	public static void main(String[] args) {
		Conecta.conecta();
		System.out.println("Status con : "+ Conecta.status);
		GerenciadorDeInterface.run();
		
		new OrdemServicoController().expiraOrdemServico();
	}

}
