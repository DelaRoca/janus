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

import br.com.janus.StatusENUM; //TODO REMOVER - implementar a segunda forma de controlar estados.
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
		
		JLabel lblTitulo = new JLabel("Gerenciar Ordem de Servi�o");
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
		
		JLabel lblExecucao = new JLabel("Em Execu��o");
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
        tabelaModeloAprovados.addColumn("N�mero da OS");
        tabelaModeloAprovados.addColumn("Cliente");
        tabelaModeloAprovados.setNumRows(0);
		tabelaAprovados.getTableHeader().setReorderingAllowed(false);
		tabelaAprovados.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabelaAprovados.getColumnModel().getColumn(1).setPreferredWidth(273);
	}
	
	private void populaTabelaAprovados() {
		ordensAprovadas.clear();
		ordensAprovadas = new OrdemServicoController().buscaOrdensServico(StatusENUM.APROVADO.getValor());
		for (OrdemServico ordemServico : ordensAprovadas) {
			Cliente cliente = new Cliente();
			try {
				cliente = new ClienteController().buscaDadosclienteId(ordemServico.getIdCliente());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			tabelaModeloAprovados.addRow(new Object[]{ordemServico.getIdOrdemServico(), cliente.getNome()});
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
		tabelaModeloExecucao.addColumn("N�mero da OS");
		tabelaModeloExecucao.addColumn("Cliente");
		tabelaModeloExecucao.setNumRows(0);
		tabelaExecucao.getTableHeader().setReorderingAllowed(false);
		tabelaExecucao.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabelaExecucao.getColumnModel().getColumn(1).setPreferredWidth(273);
	}
	
	private void populaTabelaExecucao(){
		while (tabelaModeloExecucao.getRowCount() > 0) {
			tabelaModeloExecucao.removeRow(0);
		}
		ordensExecutadas.clear();	
		ordensExecutadas = new OrdemServicoController().buscaOrdensServico(StatusENUM.EXECUCAO.getValor());
		for (OrdemServico ordemServico : ordensExecutadas) {
			Cliente cliente = new Cliente();
			try {
				cliente = new ClienteController().buscaDadosclienteId(ordemServico.getIdCliente());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			tabelaModeloExecucao.addRow(new Object[]{ordemServico.getIdOrdemServico(), cliente.getNome()});
		}
	}

	private void cancelaOSAprovada() {
		int linhaSelecionada = tabelaAprovados.getSelectedRow();
		if(linhaSelecionada >= 0){
			Integer idOrdem = (Integer) tabelaAprovados.getValueAt(linhaSelecionada, 0);
			System.out.println("idOrdem" + idOrdem);
			boolean cancelouOrdem = new OrdemServicoController().cancelaOrdemServico(idOrdem);
			if(cancelouOrdem){
				tabelaModeloAprovados.removeRow(linhaSelecionada);
				JOptionPane.showMessageDialog(null, "Ordem de Servi�o " + idOrdem + " cancelado com sucesso");
			}else{
				JOptionPane.showMessageDialog(null, "Erro de dados??? (pensar msg) ");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma ordem de servi�o");
		}
	}

	private void executaOrdemServico() {
		int linhaSelecionada = tabelaAprovados.getSelectedRow();
		if(linhaSelecionada >= 0){
			String dataExecucao = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
			System.out.println(dataExecucao);
			System.out.println("linhaSelecionada " +linhaSelecionada);
			Integer idOrdem = (Integer) tabelaAprovados.getValueAt(linhaSelecionada, 0);
			System.out.println("idOrdem" + idOrdem);
			boolean executou = new OrdemServicoController().executaOrdem(idOrdem, dataExecucao);
			if(executou){
				tabelaModeloAprovados.removeRow(linhaSelecionada);
				populaTabelaExecucao();
//				populaTabelaAprovados(); // TODO  PQ popular aprovados se � s� remover a �nica linha nele?? usando a linha tava dando problema!
				JOptionPane.showMessageDialog(null, "Ordem de servi�o " + idOrdem + " executando com sucesso");
			}else{
				JOptionPane.showMessageDialog(null, "Erro de dados??? (pensar msg) ");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma ordem de servi�o");
		}
	}
	
	private void cancelaOSExecucao() {
		int linhaSelecionada = tabelaExecucao.getSelectedRow();
			if(linhaSelecionada >= 0){
			Integer idOrdem = (Integer) tabelaExecucao.getValueAt(linhaSelecionada, 0);
			System.out.println("idOrdem" + idOrdem);
			boolean cancelouOrdem = new OrdemServicoController().cancelaOrdemServico(idOrdem);
			if(cancelouOrdem){
				tabelaModeloExecucao.removeRow(linhaSelecionada);
				JOptionPane.showMessageDialog(null, "Ordem de Servi�o " + idOrdem + " cancelado com sucesso");
			}else{
				JOptionPane.showMessageDialog(null, "Erro de dados??? (pensar msg) ");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma ordem de servi�o");
		}
	}

	private void finalizaOrdemServico() {
		int linhaSelecionada = tabelaExecucao.getSelectedRow();
		if(linhaSelecionada >= 0){
			Integer idOrdem = (Integer) tabelaExecucao.getValueAt(linhaSelecionada, 0);
			System.out.println("idOrdem" + idOrdem);
			boolean finalizou = new OrdemServicoController().finalizaOrdem();
			if(finalizou){
				tabelaModeloExecucao.removeRow(linhaSelecionada);
				JOptionPane.showMessageDialog(null, "Ordem de servi�o " + idOrdem + " finalizado com sucesso");
			}else{
				JOptionPane.showMessageDialog(null, "Erro de dados??? (pensar msg) ");
			}
		}else{
			JOptionPane.showMessageDialog(null, "Selecione uma ordem de servi�o");
		}
	}
}