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
			int linhaSelecionada = tabelaAprovados.getSelectedRow();
			if(linhaSelecionada >= 0){
				Integer idOrdemDeServico = (Integer) tabelaAprovados.getValueAt(linhaSelecionada, 0);
				String dataCancelado = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
				cancelaOrdemServico(idOrdemDeServico, dataCancelado);
				tabelaModeloAprovados.removeRow(linhaSelecionada);
			}
		});
		btnCancelarOSAprovados.setBounds(242, 480, 105, 25);
		add(btnCancelarOSAprovados);
		
		JButton btnExecutarOSAprovados = new JButton("Executar");
		btnExecutarOSAprovados.addActionListener(a -> {
			int linhaSelecionada = tabelaAprovados.getSelectedRow();
			if(linhaSelecionada >= 0){
				Integer idOrdemDeServico = (Integer) tabelaAprovados.getValueAt(linhaSelecionada, 0);
				String dataExecucao = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
				executaOrdemServico(idOrdemDeServico, dataExecucao);
				tabelaModeloAprovados.removeRow(linhaSelecionada);
			}
		});
		btnExecutarOSAprovados.setBounds(353, 480, 86, 25);
		add(btnExecutarOSAprovados);
		
		JButton btnCancelarOsExecucao = new JButton("Cancelar OS");
		btnCancelarOsExecucao.addActionListener(a -> {
			int linhaSelecionada = tabelaExecucao.getSelectedRow();
			if(linhaSelecionada >= 0) {
				Integer idOrdemDeServico = (Integer) tabelaExecucao.getValueAt(linhaSelecionada, 0);
				String dataCancelado = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
				cancelaOrdemServico(idOrdemDeServico, dataCancelado);
				tabelaModeloExecucao.removeRow(linhaSelecionada);
			}
		});
		btnCancelarOsExecucao.setBounds(740, 480, 105, 25);
		add(btnCancelarOsExecucao);
		
		JButton btnFinalizarOSExecucao = new JButton("Finalizar");
		btnFinalizarOSExecucao.addActionListener(a -> {
			int linhaSelecionada = tabelaExecucao.getSelectedRow();
			if(linhaSelecionada >= 0){
				Integer idOrdemDeServico = (Integer) tabelaExecucao.getValueAt(linhaSelecionada, 0);
				String dataFinalizado = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
				finalizaOrdemServico(idOrdemDeServico, dataFinalizado);
				tabelaModeloExecucao.removeRow(linhaSelecionada);
			}
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
	//
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
	//
	private void populaTabelaAprovados() {
		limpaTabelaAprovados();
		preencheTabelaAprovados();
	}
	//
	private void preencheTabelaAprovados() {
		ordensAprovadas = new OrdemServicoController().buscaOrdensServicoAprovadas();
		for (OrdemServico ordemServico : ordensAprovadas) {
			Cliente cliente = new Cliente();
			int idCliente = ordemServico.getIdCliente();
			try {
				cliente = new ClienteController().buscaDadosclienteId(idCliente);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int idOrdemDeServico = ordemServico.getIdOrdemDeServico();
			String nome = cliente.getNome();
			tabelaModeloAprovados.addRow(new Object[]{idOrdemDeServico, nome});
		}
	}
	//
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
	//
	private void limpaTabelaAprovados(){
		ordensAprovadas.clear();
	}
	//
	private void populaTabelaExecucao(){
		limpaTabelaExecucao();
		preencheTabelaExecucao();
	}
	//
	private void preencheTabelaExecucao() {
		ordensExecutadas = new OrdemServicoController().buscaOrdensServicoExecutadas();
		for (OrdemServico ordemServico : ordensExecutadas) {
			Cliente cliente = new Cliente();
			int idCliente = ordemServico.getIdCliente();
			try {
				cliente = new ClienteController().buscaDadosclienteId(idCliente);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int idOrdemDeServico = ordemServico.getIdOrdemDeServico();
			String nome = cliente.getNome();
			tabelaModeloExecucao.addRow(new Object[]{idOrdemDeServico, nome});
		}
	}
	//
	private void limpaTabelaExecucao(){
		ordensExecutadas.clear();
		while (tabelaModeloExecucao.getRowCount() > 0) {
			tabelaModeloExecucao.removeRow(0);
		}
	}
	//
	private void cancelaOrdemServico(Integer idOrdemDeServico, String dataCancelado) {
		boolean cancelouOrdem = new OrdemServicoController().cancelaOrdemServico(idOrdemDeServico, dataCancelado);
		if(cancelouOrdem){
			mostraMensagem("Ordem de Servi�o cancelada");
		}else{
			mostraMensagem("N�o foi poss�vel cancelar a Ordem de servi�o");
		}
	}
	//
	private void executaOrdemServico(Integer idOrdemDeServico, String dataExecucao) {
		boolean executou = new OrdemServicoController().executaOrdemServico(idOrdemDeServico, dataExecucao);
		if(executou){
			populaTabelaExecucao();
			mostraMensagem("Ordem de Servi�o executada");
		}else{
			mostraMensagem("N�o foi poss�vel executar a Ordem de servi�o");
		}
	}
	//
	private void finalizaOrdemServico(Integer idOrdemDeServico, String dataFinalizado) {
		boolean finalizou = new OrdemServicoController().finalizaOrdemServico(idOrdemDeServico, dataFinalizado);
		if(finalizou){
			mostraMensagem("Ordem de Servi�o finalizada");
		}else{
			mostraMensagem("N�o foi poss�vel finalizar a Ordem de servi�o");
		}
	}
	//
	private void  mostraMensagem(String mensagem) {
		JOptionPane.showMessageDialog(null, mensagem);
	}
}