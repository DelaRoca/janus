package br.com.janus.view;

import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.com.janus.StatusENUM;
import br.com.janus.controller.ClienteController;
import br.com.janus.controller.OrdemServicoController;
import br.com.janus.controller.ServicoController;
import br.com.janus.model.Cliente;
import br.com.janus.model.OrdemServico;
import br.com.janus.model.OsServicos;
import br.com.janus.model.Servico;

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
		
		criaTabelaExecucao();
		populaTabelaExecucao();
		JScrollPane scrollE = new JScrollPane(tabelaExecucao);
		scrollE.setBounds(564, 178, 373, 291);
		add(scrollE);
		
		criaTabelaAprovados();
		populaTabelaAprovados();
		JScrollPane scrollA = new JScrollPane(tabelaAprovados);
		scrollA.setBounds(66, 178, 373, 291);
		add(scrollA);
				
		JLabel lblAprovados = new JLabel("Aprovados");
		lblAprovados.setHorizontalAlignment(SwingConstants.CENTER);
		lblAprovados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAprovados.setBounds(66, 143, 373, 25);
		add(lblAprovados);
		
		JLabel lblExecucao = new JLabel("Em Execução");
		lblExecucao.setHorizontalAlignment(SwingConstants.CENTER);
		lblExecucao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExecucao.setBounds(564, 143, 373, 25);
		add(lblExecucao);
		
		JLabel lblGerenciarOrdemDe = new JLabel("Gerenciar Ordem de Serviço");
		lblGerenciarOrdemDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblGerenciarOrdemDe.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblGerenciarOrdemDe.setBounds(10, 11, 980, 50);
		add(lblGerenciarOrdemDe);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(450, 550, 100, 30);
		add(btnCancelar);
		
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
		
		JButton btnFinalizarOSExecucao = new JButton("Finalizar");
		btnFinalizarOSExecucao.addActionListener(a -> {
			finalizaOrdemServico();
		});
		btnFinalizarOSExecucao.setBounds(851, 480, 86, 25);
		add(btnFinalizarOSExecucao);
		
		JButton btnCancelarOsExecucao = new JButton("Cancelar OS");
		btnCancelarOsExecucao.addActionListener(a -> {
			cancelaOSExecucao();
		});
		btnCancelarOsExecucao.setBounds(740, 480, 105, 25);
		add(btnCancelarOsExecucao);
	}

	private void executaOrdemServico() {
		int linhaSelecionada = tabelaAprovados.getSelectedRow();
		if(linhaSelecionada >= 0){
			System.out.println("linhaSelecionada " +linhaSelecionada);
			Integer idOrdem = (Integer) tabelaAprovados.getValueAt(linhaSelecionada, 0);
			System.out.println("idOrdem" + idOrdem);
			boolean executou = new OrdemServicoController().executaOrdem();
			if(executou){
				tabelaModeloAprovados.removeRow(linhaSelecionada);
				populaTabelaExecucao();
				populaTabelaAprovados();
				JOptionPane.showMessageDialog(null, "Ordem finalizada "+idOrdem+" ... ( PENSAR MENSAGEM");
			}else{
				JOptionPane.showMessageDialog(null, "Não foi possível ( PENSAR MENSAGEM)");
			}
		}
	}

	private void finalizaOrdemServico() {
		int linhaSelecionada = tabelaAprovados.getSelectedRow();
		if(linhaSelecionada >= 0){
			Integer idOrdem = (Integer) tabelaAprovados.getValueAt(linhaSelecionada, 0);
			System.out.println("idOrdem" + idOrdem);
			boolean finalizou = new OrdemServicoController().finalizaOrdem();
			if(finalizou){
				tabelaModeloAprovados.removeRow(linhaSelecionada);
				JOptionPane.showMessageDialog(null, "Ordem finalizada "+idOrdem+" ... ( PENSAR MENSAGEM");
			}else{
				JOptionPane.showMessageDialog(null, "Não foi possível ( PENSAR MENSAGEM)");
			}
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
				JOptionPane.showMessageDialog(null, "Ordem cancelada "+idOrdem+" ... ( PENSAR MENSAGEM");
			}else{
				JOptionPane.showMessageDialog(null, "Não foi possível ( PENSAR MENSAGEM");
			}
		}
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
	
	private void cancelaOSExecucao() {
		int linhaSelecionada = tabelaExecucao.getSelectedRow();
			if(linhaSelecionada >= 0){
			Integer idOrdem = (Integer) tabelaExecucao.getValueAt(linhaSelecionada, 0);
			System.out.println("idOrdem" + idOrdem);
			boolean cancelouOrdem = new OrdemServicoController().cancelaOrdemServico(idOrdem);
			if(cancelouOrdem){
				tabelaModeloExecucao.removeRow(linhaSelecionada);
			}else{
				JOptionPane.showMessageDialog(null, "Não foi possível ( PENSAR MENSAGEM");
			}
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
	
	private void populaTabelaExecucao(){
		while (tabelaModeloAprovados.getRowCount() > 0) {
			tabelaModeloAprovados.removeRow(0);
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
}