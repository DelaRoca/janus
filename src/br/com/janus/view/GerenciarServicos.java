package br.com.janus.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class GerenciarServicos extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tabelaServico;

	public GerenciarServicos() {
		setLayout(null);
		
		JLabel lblTitulo = new JLabel("Gerenciar Servi\u00E7os");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 980, 58);
		add(lblTitulo);
		
		String[] colunasS = { "Habilitado", "Nome", "Valor/h", "Descrição" };
		Object[][] dadosS = { { "", "", "", "" } };
		tabelaServico = new JTable(dadosS, colunasS);
		tabelaServico.getTableHeader().setReorderingAllowed(false);
		tabelaServico.getColumnModel().getColumn(0).setPreferredWidth(75);
		tabelaServico.getColumnModel().getColumn(1).setPreferredWidth(225); 
		tabelaServico.getColumnModel().getColumn(2).setPreferredWidth(50);
		tabelaServico.getColumnModel().getColumn(3).setPreferredWidth(250);

		JScrollPane scrollS = new JScrollPane(tabelaServico);
		scrollS.setBounds(175, 126, 650, 243);
		add(scrollS);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			// TODO implementar
		});
		btnSalvar.setBounds(411, 484, 89, 23);
		add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(573, 484, 89, 23);
		add(btnCancelar);

	}
}
