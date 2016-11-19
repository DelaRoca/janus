package br.com.janus.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.com.janus.controller.ProdutoController;
import br.com.janus.model.Produto;


public class CadastroProduto extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNome;
	private JTextField textFieldValor;
	private JTextField textFieldDescricao;
		
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
		
		textFieldValor = new JTextField();
		textFieldValor.setBounds(448, 280, 254, 25);
		add(textFieldValor);
		textFieldValor.setColumns(10);
		
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
			if (verificaCamposValidos()) {
				salvaProduto();
			} else {
				JOptionPane.showMessageDialog(null, "Campos obrigatórios não preenchidos.");
			}
		});
		btnSalvar.setBounds(384, 551, 89, 23);
		add(btnSalvar);
		
		textFieldDescricao = new JTextField();
		textFieldDescricao.setBounds(448, 318, 254, 25);
		add(textFieldDescricao);
		textFieldDescricao.setColumns(10);
		
		JLabel label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(705, 244, 23, 25);
		add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(705, 280, 23, 25);
		add(label_1);

	}
	
	
	private void salvaProduto() {
		System.out.println("salvar produto");
		Produto produto = constroiProduto();
		new ProdutoController().salvaProduto(produto);
	}
	

	private Produto constroiProduto() {
		Produto produto = new Produto();
		produto.setNome(this.textFieldNome.getText());
		produto.setValor(this.textFieldValor.getText());
		produto.setDescricao(this.textFieldDescricao.getText());
		produto.setIdProduto(0);
		return produto;
	}

	private boolean verificaCamposValidos() {
		System.out.println("aqui nos verifica campos ");
		System.out.println("this.textFieldNome" + this.textFieldNome.getText().equals(""));
		System.out.println("this.textFieldValor " + this.textFieldValor.getText().equals(""));

		if (this.textFieldNome.getText().equals("") || this.textFieldValor.getText().equals("")) {
			return false;
		}
		return true;
	}

}
