package br.com.janus.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel panel;

	public Frame() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1000, 21);

		JMenu menuClientes = new JMenu("Clientes");
		JMenu menuVeiculos = new JMenu("Veículos");
		JMenu menuProdutos = new JMenu("Produtos");
		JMenu menuServicos = new JMenu("Serviços");
		JMenu menuOrdemServico = new JMenu("Ordem de Serviço");

		JMenuItem cadastroCliente = new JMenuItem("Cadastro Cliente");
		cadastroCliente.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new CadastroCliente());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		JMenuItem cadastroVeiculo = new JMenuItem("Cadastro Veículo");
		cadastroVeiculo.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new CadastroVeiculo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		JMenuItem cadastroProduto = new JMenuItem("Cadastro Produto");
		cadastroProduto.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new CadastroProduto());
		});
		JMenuItem cadastroServico = new JMenuItem("Cadastro Serviço");
		cadastroServico.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new CadastroServico());
		});
		JMenuItem desabilitarServico = new JMenuItem("Gerenciar Serviços");
		desabilitarServico.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new GerenciarServicos());
		});
		JMenuItem registrarOrdemServico = new JMenuItem("Registrar Ordem de Serviço");
		registrarOrdemServico.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new CadastroOrdemServico());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		JMenuItem acompanharOrdemServico = new JMenuItem("Acompanhar Ordem de Serviço");
		acompanharOrdemServico.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new AcompanharOrdemServico());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		JMenuItem gerenciarOrdemServico = new JMenuItem("Gerenciar Ordem de Serviço");
		gerenciarOrdemServico.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new GerenciarOrdemServico());
		});

		menuClientes.add(cadastroCliente);
		menuVeiculos.add(cadastroVeiculo);
		menuProdutos.add(cadastroProduto);
		menuServicos.add(cadastroServico);
		menuServicos.add(desabilitarServico);
		menuOrdemServico.add(registrarOrdemServico);
		menuOrdemServico.add(acompanharOrdemServico);
		menuOrdemServico.add(gerenciarOrdemServico);

		menuBar.add(menuClientes);
		menuBar.add(menuVeiculos);
		menuBar.add(menuProdutos);
		menuBar.add(menuServicos);
		menuBar.add(menuOrdemServico);
		setJMenuBar(menuBar);

		panel = new JPanel(null, true);
		this.getContentPane().add(panel);
	}

	public JPanel getPanel() {
		return panel;
	}

}
