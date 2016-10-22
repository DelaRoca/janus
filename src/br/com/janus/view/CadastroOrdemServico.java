package br.com.janus.view;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

public class CadastroOrdemServico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNome;
	private JTextField textFieldCPF;
	private JTextField textFieldCNPJ;
	private JTextField textFieldModelo;
	private JTextField textFieldAno;
	private JTextField textFieldPlaca;
	private JTextField textFieldData;
	private JTextField textField_1;
	private JTable tabelaProduto;
	private JTable tabelaServico;
	private JTextField textFieldTotal;

	public CadastroOrdemServico() throws ParseException {

		setLayout(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(70, 197, 60, 25);
		add(lblNome);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModelo.setBounds(550, 183, 60, 25);
		add(lblModelo);

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAno.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAno.setBounds(550, 215, 60, 25);
		add(lblAno);

		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPlaca.setBounds(550, 150, 60, 25);
		add(lblPlaca);

		textFieldNome = new JTextField();
		textFieldNome.setEditable(false);
		textFieldNome.setBounds(141, 197, 300, 25);
		add(textFieldNome);
		textFieldNome.setColumns(10);

		textFieldModelo = new JTextField();
		textFieldModelo.setEditable(false);
		textFieldModelo.setColumns(10);
		textFieldModelo.setBounds(617, 183, 244, 25);
		add(textFieldModelo);

		textFieldAno = new JTextField();
		textFieldAno.setEditable(false);
		textFieldAno.setColumns(10);
		textFieldAno.setBounds(617, 215, 126, 25);
		add(textFieldAno);

		textFieldPlaca = new JFormattedTextField(new MaskFormatter("UUU-####"));
		textFieldPlaca.setColumns(10);
		textFieldPlaca.setBounds(617, 150, 130, 25);
		add(textFieldPlaca);

		JLabel lblData = new JLabel("Data:");
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblData.setBounds(46, 65, 45, 25);
		add(lblData);

		textFieldData = new JFormattedTextField(new MaskFormatter("##/##/####"));
		textFieldData.setEditable(false);
		textFieldData.setBounds(101, 65, 85, 25);
		add(textFieldData);
		textFieldData.setColumns(10);
		
				JLabel lblRegistroOrdemServico = new JLabel("Registro de Ordem de Serviço");
				lblRegistroOrdemServico.setHorizontalAlignment(SwingConstants.CENTER);
				lblRegistroOrdemServico.setFont(new Font("Tahoma", Font.BOLD, 22));
				lblRegistroOrdemServico.setBounds(10, 11, 980, 48);
				add(lblRegistroOrdemServico);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(46, 101, 46, 14);
		add(lblCliente);

		JButton btnBuscar_1 = new JButton("Buscar");
		btnBuscar_1.setBounds(329, 149, 90, 25);
		add(btnBuscar_1);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setBounds(70, 232, 60, 25);
		add(lblTelefone);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(141, 232, 205, 25);
		add(textField_1);
		textField_1.setColumns(10);

		JLabel lblCaixaCliente = new JLabel("");
		lblCaixaCliente.setBounds(46, 118, 415, 150);
		lblCaixaCliente.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(lblCaixaCliente);

		JLabel lblVeculo = new JLabel("Ve\u00EDculo:");
		lblVeculo.setBounds(532, 101, 46, 14);
		add(lblVeculo);

		JLabel label = new JLabel("");
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setBounds(532, 118, 415, 150);
		add(label);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			//TODO implementar
		});
		btnSalvar.setBounds(373, 560, 89, 23);
		add(btnSalvar);

		JLabel lblProduto = new JLabel("Produtos:");
		lblProduto.setBounds(10, 297, 67, 14);
		add(lblProduto);

		String[] colunasP = { "", "Nome", "Valor", "Quantidade", "Valor Total" };
		Object[][] dadosP = { { "", "", "", "", "" } };
		tabelaProduto = new JTable(dadosP, colunasP);
		tabelaProduto.getTableHeader().setReorderingAllowed(false);
		tabelaProduto.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabelaProduto.getColumnModel().getColumn(1).setPreferredWidth(165); 
		tabelaProduto.getColumnModel().getColumn(2).setPreferredWidth(60);
		tabelaProduto.getColumnModel().getColumn(3).setPreferredWidth(140);
		tabelaProduto.getColumnModel().getColumn(3).setPreferredWidth(75);

		JScrollPane scrollP = new JScrollPane(tabelaProduto);
		scrollP.setBounds(10, 315, 480, 196);
		add(scrollP);

		String[] colunasS = { "", "Nome", "Valor/h", "Quantidade/h", "Valor Total" };
		Object[][] dadosS = { { "", "", "", "", "" } };
		tabelaServico = new JTable(dadosS, colunasS);
		tabelaServico.getTableHeader().setReorderingAllowed(false);
		tabelaServico.getColumnModel().getColumn(0).setPreferredWidth(40);
		tabelaServico.getColumnModel().getColumn(1).setPreferredWidth(165); 
		tabelaServico.getColumnModel().getColumn(2).setPreferredWidth(60);
		tabelaServico.getColumnModel().getColumn(3).setPreferredWidth(140);
		tabelaServico.getColumnModel().getColumn(3).setPreferredWidth(75);

		JScrollPane scrollS = new JScrollPane(tabelaServico);
		scrollS.setBounds(510, 315, 480, 196);
		add(scrollS);

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(422, 525, 46, 25);
		add(lblTotal);

		JLabel lblServico = new JLabel("Servi\u00E7os:");
		lblServico.setBounds(509, 297, 67, 14);
		add(lblServico);

		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setBounds(478, 525, 86, 25);
		add(textFieldTotal);
		textFieldTotal.setColumns(10);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(541, 560, 89, 23);
		add(btnCancelar);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBuscar.setBounds(774, 150, 88, 25);
		add(btnBuscar);
		
		textFieldCNPJ = new JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
		textFieldCNPJ.setBounds(140, 165, 179, 25);
		add(textFieldCNPJ);
		textFieldCNPJ.setColumns(10);

		textFieldCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		textFieldCPF.setColumns(10);
		textFieldCPF.setBounds(139, 131, 180, 25);
		add(textFieldCPF);
		
		JRadioButton rdbtnCpf = new JRadioButton("CPF:");
		rdbtnCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnCpf.setBounds(65, 131, 68, 23);
		add(rdbtnCpf);

		JRadioButton rdbtnCnpj = new JRadioButton("CNPJ:");
		rdbtnCnpj.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnCnpj.setBounds(56, 165, 78, 23);
		add(rdbtnCnpj);
	}
}
