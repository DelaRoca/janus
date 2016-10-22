package br.com.janus.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CadastroProduto extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNome;
	private JTextField textField;
	private JTextField textField_1;

	public CadastroProduto() {

		setLayout(null);
		
		JLabel lblCadastrarProduto = new JLabel("Cadastro de Produto");
		lblCadastrarProduto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastrarProduto.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblCadastrarProduto.setBounds(10, 11, 980, 56);
		add(lblCadastrarProduto);
		
		JLabel lblNomeDoProduto = new JLabel("Nome do produto:");
		lblNomeDoProduto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomeDoProduto.setBounds(318, 244, 120, 25);
		add(lblNomeDoProduto);
		
		textFieldNome = new JTextField();
		textFieldNome.setBounds(448, 244, 254, 25);
		add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JLabel lblPreco = new JLabel("Pre\u00E7o:");
		lblPreco.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPreco.setBounds(318, 280, 120, 25);
		add(lblPreco);
		
		textField = new JTextField();
		textField.setBounds(448, 280, 254, 25);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o:");
		lblDescricao.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescricao.setBounds(318, 318, 120, 25);
		add(lblDescricao);
		
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
		
		textField_1 = new JTextField();
		textField_1.setBounds(448, 318, 254, 25);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(705, 244, 23, 25);
		add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(705, 280, 23, 25);
		add(label_1);

	}
}
