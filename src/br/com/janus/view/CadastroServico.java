package br.com.janus.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class CadastroServico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldValor;
	private JTextField textFieldNome;
	private JTextField textField;
	
	public CadastroServico() {
		setLayout(null);
		
		JLabel lblTitulo = new JLabel("Cadastro de Servi\u00E7o");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitulo.setBounds(10, 10, 980, 45);
		add(lblTitulo);
		
		JLabel lblNomeDoServio = new JLabel("Nome do Servi\u00E7o:");
		lblNomeDoServio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeDoServio.setBounds(290, 238, 120, 25);
		add(lblNomeDoServio);
		
		JLabel lblDescrio = new JLabel("Descri\u00E7\u00E3o:");
		lblDescrio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescrio.setBounds(290, 309, 120, 25);
		add(lblDescrio);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValor.setBounds(290, 273, 120, 25);
		add(lblValor);
		
		JCheckBox chckbxPorHora = new JCheckBox("por hora");
		chckbxPorHora.setBounds(532, 273, 95, 25);
		add(chckbxPorHora);
		
		textFieldValor = new JTextField();
		textFieldValor.setBounds(420, 273, 86, 25);
		add(textFieldValor);
		textFieldValor.setColumns(10);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(420, 238, 285, 25);
		add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			// TODO implementar
		});
		btnSalvar.setBounds(375, 522, 89, 23);
		add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(575, 522, 89, 23);
		add(btnCancelar);
		
		textField = new JTextField();
		textField.setBounds(420, 306, 285, 25);
		add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(710, 238, 23, 25);
		add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(513, 273, 23, 25);
		add(label_1);
	}
}
