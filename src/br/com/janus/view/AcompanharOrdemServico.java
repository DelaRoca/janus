package br.com.janus.view;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

public class AcompanharOrdemServico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNome;
	private JTextField textFieldCPF;
	private JTextField textFieldModelo;
	private JTextField textFieldAno;
	private JTextField textFieldPlaca;
	private JTextField textFieldData;
	private JTextField textField_1;
	private JTable tabelaProduto;
	private JTable tabelaServico;
	private JTextField textFieldTotal;
	private JTextField textField;

	public AcompanharOrdemServico() throws ParseException {
		setLayout(null);

		JLabel lblRegistroOrdemServico = new JLabel("Acompanhar Ordem de Servi\u00E7o");
		lblRegistroOrdemServico.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroOrdemServico.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblRegistroOrdemServico.setBounds(10, 11, 980, 48);
		add(lblRegistroOrdemServico);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(81, 203, 60, 25);
		add(lblNome);

		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		lblCpfcnpj.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCpfcnpj.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCpfcnpj.setBounds(81, 171, 60, 25);
		add(lblCpfcnpj);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModelo.setBounds(570, 203, 60, 25);
		add(lblModelo);

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAno.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAno.setBounds(570, 233, 60, 25);
		add(lblAno);

		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPlaca.setBounds(570, 171, 60, 25);
		add(lblPlaca);

		textFieldNome = new JTextField();
		textFieldNome.setEditable(false);
		textFieldNome.setBounds(151, 203, 304, 25);
		add(textFieldNome);
		textFieldNome.setColumns(10);

		textFieldCPF = new JTextField();
		textFieldCPF.setEditable(false);
		textFieldCPF.setColumns(10);
		textFieldCPF.setBounds(151, 171, 205, 25);
		add(textFieldCPF);

		textFieldModelo = new JTextField();
		textFieldModelo.setEditable(false);
		textFieldModelo.setColumns(10);
		textFieldModelo.setBounds(638, 203, 244, 25);
		add(textFieldModelo);

		textFieldAno = new JTextField();
		textFieldAno.setEditable(false);
		textFieldAno.setColumns(10);
		textFieldAno.setBounds(638, 233, 126, 25);
		add(textFieldAno);

		textFieldPlaca = new JTextField();
		textFieldPlaca.setEditable(false);
		textFieldPlaca.setColumns(10);
		textFieldPlaca.setBounds(638, 171, 131, 25);
		add(textFieldPlaca);

		JLabel lblData = new JLabel("Data de Registro:");
		lblData.setHorizontalAlignment(SwingConstants.RIGHT);
		lblData.setBounds(532, 70, 100, 25);
		add(lblData);

		textFieldData = new JFormattedTextField(new MaskFormatter("##/##/####"));
		textFieldData.setEditable(false);
		textFieldData.setBounds(644, 70, 100, 25);
		add(textFieldData);
		textFieldData.setColumns(10);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(46, 142, 46, 14);
		add(lblCliente);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setBounds(81, 234, 60, 25);
		add(lblTelefone);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(151, 234, 205, 25);
		add(textField_1);
		textField_1.setColumns(10);

		JLabel lblCaixaCliente = new JLabel("");
		lblCaixaCliente.setBounds(46, 160, 416, 108);
		lblCaixaCliente.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(lblCaixaCliente);

		JLabel lblVeiculo = new JLabel("Ve\u00EDculo:");
		lblVeiculo.setBounds(532, 142, 46, 14);
		add(lblVeiculo);

		JLabel label = new JLabel("");
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setBounds(532, 160, 416, 108);
		add(label);

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
		scrollP.setBounds(10, 315, 480, 175);
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
		scrollS.setBounds(510, 315, 480, 175);
		add(scrollS);

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(434, 515, 46, 25);
		add(lblTotal);

		JLabel lblServio = new JLabel("Servi\u00E7os:");
		lblServio.setBounds(509, 297, 67, 14);
		add(lblServio);

		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		textFieldTotal.setBounds(490, 515, 86, 25);
		add(textFieldTotal);
		textFieldTotal.setColumns(10);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatus.setBounds(20, 106, 58, 25);
		add(lblStatus);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(85, 106, 225, 25);
		add(comboBox);

		JLabel lblNDaOs = new JLabel("N\u00BA da OS:");
		lblNDaOs.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNDaOs.setBounds(20, 70, 60, 25);
		add(lblNDaOs);

		textField = new JTextField();
		textField.setBounds(85, 70, 126, 25);
		add(textField);
		textField.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(221, 70, 90, 25);
		add(btnBuscar);

		JLabel lblExpirado = new JLabel("Expirado!");
		lblExpirado.setForeground(Color.RED);
		lblExpirado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblExpirado.setBounds(325, 106, 106, 25);
		add(lblExpirado);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(541, 551, 89, 23);
		add(btnCancelar);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			//TODO implementar
		});
		btnSalvar.setBounds(384, 551, 89, 23);
		add(btnSalvar);
	}
}
