package br.com.janus.view;

import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	JLabel lblValor;
	
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
		
		lblValor = new JLabel("Valor (R$):");
		lblValor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValor.setBounds(318, 279, 120, 30);
		add(lblValor);
		
		textFieldValor = new JTextField();
		textFieldValor.setBounds(448, 279, 254, 30);
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
			try{
				if (verificaCamposValidos()) {
					if (verificarCampoValor() ) {
						salvaProduto();
					} else {
						JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Campos obrigat�rios n�o preenchidos");
				}
			} catch( Exception e){
				 JOptionPane.showMessageDialog(null, "Dados inválidos, tente novamente");
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
	
	//
	private void salvaProduto() {
		System.out.println("salvar produto");
		Produto produto = constroiProduto();
		new ProdutoController().salvaProduto(produto);
	}
	
	//
	private Produto constroiProduto() {
		Produto produto = new Produto();
		produto.setNome(this.textFieldNome.getText());
		produto.setValor(this.textFieldValor.getText());
		produto.setDescricao(this.textFieldDescricao.getText());
		produto.setIdProduto(0);
		return produto;
	}
//
	private boolean verificaCamposValidos() {
		if (this.textFieldNome.getText().equals("") || this.textFieldValor.getText().equals("")) {
			return false;
		}
		return true;
	}
	 //
    private boolean verificarCampoValor() {
        String[] valoresPartidos = textFieldValor.getText().split(",", 2);
		try {
			Long.parseLong (valoresPartidos[0]);
			Long.parseLong (valoresPartidos[1]);
		} catch (NumberFormatException ex) {
			return false;
		}
		if (!(valoresPartidos[1].length() == 2) && !(valoresPartidos[1].length() == 1)){
			return false;
		}
		return true;
    }
}