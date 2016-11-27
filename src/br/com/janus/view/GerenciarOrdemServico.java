package br.com.janus.view;

import java.awt.Font;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.com.janus.controller.ClienteController;
import br.com.janus.controller.OrdemServicoController;
import br.com.janus.model.Cliente;
import br.com.janus.model.OrdemServico;

public class GerenciarOrdemServico extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel tabelaModeloAprovados = new DefaultTableModel();
	private JTable tabelaAprovados;

	private DefaultTableModel tabelaModeloExecucao = new DefaultTableModel();
	private JTable tabelaExecucao;
	
	private ArrayList<OrdemServico> ordensAprovadas = new ArrayList<OrdemServico>();
	private ArrayList<OrdemServico> ordensExecutadas = new ArrayList<OrdemServico>();
 
	public GerenciarOrdemServico(){
		setLayout(null);
		
		JLabel lblTitulo = new JLabel("Gerenciar Ordem de Serviço");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitulo.setBounds(10, 11, 980, 50);
		add(lblTitulo);
		
		JLabel lblAprovados = new JLabel("Aprovados");
		lblAprovados.setHorizontalAlignment(SwingConstants.CENTER);
		lblAprovados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAprovados.setBounds(66, 143, 373, 25);
		add(lblAprovados);
		
		criaTabelaAprovados();
		populaTabelaAprovados();
		JScrollPane scrollA = new JScrollPane(tabelaAprovados);
		scrollA.setBounds(66, 178, 373, 291);
		add(scrollA);
		
		JLabel lblExecucao = new JLabel("Em Execução");
		lblExecucao.setHorizontalAlignment(SwingConstants.CENTER);
		lblExecucao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExecucao.setBounds(564, 143, 373, 25);
		add(lblExecucao);

		criaTabelaExecucao();
		populaTabelaExecucao();
		JScrollPane scrollE = new JScrollPane(tabelaExecucao);
		scrollE.setBounds(564, 178, 373, 291);
		add(scrollE);
		
		JButton btnCancelarOSAprovados = new JButton("Cancelar OS");
		btnCancelarOSAprovados.addActionListener(a -> {
			cancelaOSAprovada();
		});
		btnCancelarOSAprovados.setBounds(242, 480, 105, 25);
		add(btnCancelarOSAprovados);
		
		JButton btnExecutarOSAprovados = new JButton("Executar");
		btnExecutarOSAprovados.addActionListener(a -> {
			executaOrdemServico();
		});
		btnExecutarOSAprovados.setBounds(353, 480, 86, 25);
		add(btnExecutarOSAprovados);
		
		JButton btnCancelarOsExecucao = new JButton("Cancelar OS");
		btnCancelarOsExecucao.addActionListener(a -> {
			cancelaOSExecucao();
		});
		btnCancelarOsExecucao.setBounds(740, 480, 105, 25);
		add(btnCancelarOsExecucao);
		
		JButton btnFinalizarOSExecucao = new JButton("Finalizar");
		btnFinalizarOSExecucao.addActionListener(a -> {
			finalizaOrdemServico();
		});
		btnFinalizarOSExecucao.setBounds(851, 480, 86, 25);
		add(btnFinalizarOSExecucao);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(450, 550, 100, 30);
		add(btnCancelar);
	}

	private void criaTabelaAprovados(){
		tabelaAprovados = new JTable(tabelaModeloAprovados){
			private static final long serialVersionUID = 1L;

			@Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        tabelaModeloAprovados.addColumn("Número da OS");
        tabelaModeloAprovados.addColumn("Cliente");
        tabelaModeloAprovados.setNumRows(0);
		tabelaAprovados.getTableHeader().setReorderingAllowed(false);
		tabelaAprovados.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabelaAprovados.getColumnModel().getColumn(1).setPreferredWidth(273);
	}
	
	private void populaTabelaAprovados() {
		limpaTabelaAprovados();
		String buscaOrdens = "aprovado";
		ordensAprovadas = new OrdemServicoController().buscaOrdensServico(buscaOrdens);
		for (OrdemServico ordemServico : ordensAprovadas) {
			Cliente cliente = new Cliente();
			int idCliente = ordemServico.getIdCliente();
			try {
				cliente = new ClienteController().buscaDadosclienteId(idCliente);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			tabelaModeloAprovados.addRow(new Object[]{ordemServico.getIdOrdemDeServico(), cliente.getNome()});
		}
	}
	
	private void criaTabelaExecucao(){
		tabelaExecucao = new JTable(tabelaModeloExecucao){
			private static final long serialVersionUID = 1L;
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
				case 1:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		tabelaModeloExecucao.addColumn("Número da OS");
		tabelaModeloExecucao.addColumn("Cliente");
		tabelaModeloExecucao.setNumRows(0);
		tabelaExecucao.getTableHeader().setReorderingAllowed(false);
		tabelaExecucao.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabelaExecucao.getColumnModel().getColumn(1).setPreferredWidth(273);
	}
	
	private void limpaTabelaAprovados(){
		ordensAprovadas.clear();
	}
	
	private void populaTabelaExecucao(){
		limpaTabelaExecucao();
		String buscaOrdens = "execucao";
		ordensExecutadas = new OrdemServicoController().buscaOrdensServico(buscaOrdens);
		for (OrdemServico ordemServico : ordensExecutadas) {
			Cliente cliente = new Cliente();
			int idCliente = ordemServico.getIdCliente();
			try {
				cliente = new ClienteController().buscaDadosclienteId(idCliente);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			tabelaModeloExecucao.addRow(new Object[]{ordemServico.getIdOrdemDeServico(), cliente.getNome()});
		}
	}
	
	private void limpaTabelaExecucao(){
		ordensExecutadas.clear();
		while (tabelaModeloExecucao.getRowCount() > 0) {
			tabelaModeloExecucao.removeRow(0);
		}
	}

	private void cancelaOSAprovada() {
		int linhaSelecionada = tabelaAprovados.getSelectedRow();
		if(linhaSelecionada >= 0){
			Integer idOrdemDeServico = (Integer) tabelaAprovados.getValueAt(linhaSelecionada, 0);
			String dataCancelado = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
			boolean cancelouOrdem = new OrdemServicoController().cancelaOrdemServico(idOrdemDeServico, dataCancelado);
			if(cancelouOrdem){
				tabelaModeloAprovados.removeRow(linhaSelecionada);
				JOptionPane.showMessageDialog(null, "Ordem de Serviço " + idOrdemDeServico + " cancelado com sucesso");
			}else{
				JOptionPane.showMessageDialog(null, "Erro de dados??? (pensar msg) ");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma ordem de serviço");
		}
	}

	private void executaOrdemServico() {
		int linhaSelecionada = tabelaAprovados.getSelectedRow();
		if(linhaSelecionada >= 0){
			Integer idOrdemDeServico = (Integer) tabelaAprovados.getValueAt(linhaSelecionada, 0);
			String dataExecucao = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
			boolean executou = new OrdemServicoController().executaOrdem(idOrdemDeServico, dataExecucao);
			if(executou){
				tabelaModeloAprovados.removeRow(linhaSelecionada);
				populaTabelaExecucao();
				JOptionPane.showMessageDialog(null, "Ordem de serviço " + idOrdemDeServico + " executando com sucesso");
			}else{
				JOptionPane.showMessageDialog(null, "Erro de dados??? (pensar msg) ");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma ordem de serviço");
		}
	}
	
	private void cancelaOSExecucao() {
		int linhaSelecionada = tabelaExecucao.getSelectedRow();
			if(linhaSelecionada >= 0){
			Integer idOrdemDeServico = (Integer) tabelaExecucao.getValueAt(linhaSelecionada, 0);
			String dataCancelado = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
			boolean cancelouOrdem = new OrdemServicoController().cancelaOrdemServico(idOrdemDeServico, dataCancelado);
			if(cancelouOrdem){
				tabelaModeloExecucao.removeRow(linhaSelecionada);
				JOptionPane.showMessageDialog(null, "Ordem de Serviço " + idOrdemDeServico + " cancelado com sucesso");
			}else{
				JOptionPane.showMessageDialog(null, "Erro de dados??? (pensar msg) ");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma ordem de serviço");
		}
	}

	private void finalizaOrdemServico() {
		int linhaSelecionada = tabelaExecucao.getSelectedRow();
		if(linhaSelecionada >= 0){
			Integer idOrdemDeServico = (Integer) tabelaExecucao.getValueAt(linhaSelecionada, 0);
			String dataFinalizado = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
			boolean finalizou = new OrdemServicoController().finalizaOrdem(idOrdemDeServico, dataFinalizado);
			if(finalizou){
				tabelaModeloExecucao.removeRow(linhaSelecionada);
				JOptionPane.showMessageDialog(null, "Ordem de serviço " + idOrdemDeServico + " finalizado com sucesso");
			}else{
				JOptionPane.showMessageDialog(null, "Erro de dados??? (pensar msg) ");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma ordem de serviço");
		}
	}
}