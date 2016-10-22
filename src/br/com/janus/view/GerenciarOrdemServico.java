package br.com.janus.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class GerenciarOrdemServico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tabelaAprovados;
	private JTable tabelaExecucao;
 
	public GerenciarOrdemServico(){
		setLayout(null);
		
		String[] colunas = { "Número da OS", "Cliente"};
		Object[][] dados = { { "010", "Ciclano da Silva" }, { "013", "Beltrano da Silva" } };
		tabelaAprovados = new JTable(dados, colunas);
		tabelaAprovados.getTableHeader().setReorderingAllowed(false);
		tabelaAprovados.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabelaAprovados.getColumnModel().getColumn(1).setPreferredWidth(273); 
		
		JScrollPane scroll = new JScrollPane(tabelaAprovados);
		scroll.setBounds(564, 178, 373, 291);
		add(scroll);
		
		String[] colunasS ={ "Número da OS", "Cliente"};
		Object[][] dadosS = { { "003", "Josefano Dela Roca" }, { "002", "Josefina Crocetta"} };
		tabelaExecucao = new JTable(dadosS, colunasS);
		tabelaExecucao.getTableHeader().setReorderingAllowed(false);
		tabelaExecucao.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabelaExecucao.getColumnModel().getColumn(1).setPreferredWidth(273); 

		JScrollPane scrollS = new JScrollPane(tabelaExecucao);
		scrollS.setBounds(66, 178, 373, 291);
		add(scrollS);
				
		JLabel lblAprovados = new JLabel("Aprovados");
		lblAprovados.setHorizontalAlignment(SwingConstants.CENTER);
		lblAprovados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAprovados.setBounds(66, 143, 373, 25);
		add(lblAprovados);
		
		JLabel lblExecucao = new JLabel("Em Execu\u00E7\u00E3o");
		lblExecucao.setHorizontalAlignment(SwingConstants.CENTER);
		lblExecucao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExecucao.setBounds(564, 143, 373, 25);
		add(lblExecucao);
		
		JLabel lblGerenciarOrdemDe = new JLabel("Gerenciar Ordem de Servi\u00E7o");
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
		//TODO Implementar
		});
		btnCancelarOSAprovados.setBounds(242, 480, 105, 25);
		add(btnCancelarOSAprovados);
		
		JButton btnExecutarOSAprovados = new JButton("Executar");
		btnExecutarOSAprovados.addActionListener(a -> {
		//TODO Implementar
		});
		btnExecutarOSAprovados.setBounds(353, 480, 86, 25);
		add(btnExecutarOSAprovados);
		
		JButton btnFinalizarOSExecucao = new JButton("Finalizar");
		btnFinalizarOSExecucao.addActionListener(a -> {
		//TODO Implementar
		});
		btnFinalizarOSExecucao.setBounds(851, 480, 86, 25);
		add(btnFinalizarOSExecucao);
		
		JButton btnCancelarOsExecucao = new JButton("Cancelar OS");
		btnCancelarOsExecucao.addActionListener(a -> {
		//TODO Implementar
		});
		btnCancelarOsExecucao.setBounds(740, 480, 105, 25);
		add(btnCancelarOsExecucao);
	}
}
