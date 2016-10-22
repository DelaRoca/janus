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
		JMenu menuVeiculos = new JMenu("Ve�culos");
		JMenu menuProdutos = new JMenu("Produtos");
		JMenu menuServicos = new JMenu("Servi�os");
		JMenu menuOrdemServico = new JMenu("Ordem de Servi�o");

		JMenuItem cadastroCliente = new JMenuItem("Novo Cliente");
		cadastroCliente.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new CadastroCliente());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		JMenuItem editarCliente = new JMenuItem("Editar Cliente");
		editarCliente.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new EditarCliente());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		JMenuItem cadastroVeiculo = new JMenuItem("Novo Ve�culo");
		cadastroVeiculo.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new CadastroVeiculo());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		JMenuItem editarVeiculo = new JMenuItem("Editar Ve�culo");
		editarVeiculo.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new EditarVeiculo());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		JMenuItem cadastroProduto = new JMenuItem("Novo Produto");
		cadastroProduto.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new CadastroProduto());
		});
		JMenuItem cadastroServico = new JMenuItem("Novo Servi�o");
		cadastroServico.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new CadastroServico());
		});
		JMenuItem desabilitarServico = new JMenuItem("Gerenciar Servi�os");
		desabilitarServico.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new GerenciarServicos());
		});
		JMenuItem registrarOrdemServico = new JMenuItem("Registrar Ordem de Servi�o");
		registrarOrdemServico.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new CadastroOrdemServico());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		JMenuItem acompanharOrdemServico = new JMenuItem("Acompanhar Ordem de Servi�o");
		acompanharOrdemServico.addActionListener(a -> {
			try {
				GerenciadorDeInterface.setPanel(new AcompanharOrdemServico());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		JMenuItem gerenciarOrdemServico = new JMenuItem("Gerenciar Ordem de Servi�o");
		gerenciarOrdemServico.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new GerenciarOrdemServico());
		});

		menuClientes.add(cadastroCliente);
		menuClientes.add(editarCliente);
		menuVeiculos.add(cadastroVeiculo);
		menuVeiculos.add(editarVeiculo);
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
