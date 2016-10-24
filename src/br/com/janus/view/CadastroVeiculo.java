package br.com.janus.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.com.janus.controller.ClienteController;
import br.com.janus.controller.EnderecoController;

public class CadastroVeiculo extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldMarca;
	private JTextField textFieldModelo;
	private JTextField textFieldAno;
	private JFormattedTextField textFieldPlaca;

	public CadastroVeiculo() throws ParseException {
		setLayout(null);
		
		JLabel lblCadastroVeiculo = new JLabel("Cadastro de Ve\u00EDculo");
		lblCadastroVeiculo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroVeiculo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblCadastroVeiculo.setBounds(10, 11, 980, 48);
		add(lblCadastroVeiculo);
		
		JLabel lblFabricante = new JLabel("Fabricante:");
		lblFabricante.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFabricante.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFabricante.setBounds(173, 253, 138, 30);
		add(lblFabricante);
		
		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblModelo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblModelo.setBounds(209, 294, 100, 30);
		add(lblModelo);
		
		JLabel lblAno = new JLabel("Ano:");
		lblAno.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAno.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAno.setBounds(209, 335, 100, 30);
		add(lblAno);
		
		JLabel lblPlaca = new JLabel("Placa:");
		lblPlaca.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPlaca.setBounds(211, 212, 100, 30);
		add(lblPlaca);
		
		textFieldMarca = new JTextField();
		textFieldMarca.setColumns(10);
		textFieldMarca.setBounds(319, 259, 468, 25);
		add(textFieldMarca);
		
		textFieldModelo = new JTextField();
		textFieldModelo.setColumns(10);
		textFieldModelo.setBounds(319, 300, 468, 25);
		add(textFieldModelo);
		
		textFieldAno = new JFormattedTextField(new MaskFormatter("####"));
		textFieldAno.setColumns(10);
		textFieldAno.setBounds(319, 340, 108, 25);
		add(textFieldAno);
		
		textFieldPlaca = new JFormattedTextField(new MaskFormatter("UUU-####"));
		textFieldPlaca.setColumns(10);
		textFieldPlaca.setBounds(319, 216, 108, 25);
		add(textFieldPlaca);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			//TODO implementar
		});
		btnSalvar.setBounds(411, 484, 89, 23);
		add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(573, 484, 89, 23);
		add(btnCancelar);
	
		JButton btnBuscar = new JButton("Verificar");
		btnBuscar.addActionListener(a -> {
			
			String placa = this.textFieldPlaca.getText();
			placa = placa.replace("-", "");

			System.out.println("Placa: " + placa);
			System.out.println("Placa.isEmpty: " + placa.isEmpty());
			System.out.println("Placa.equals: " + placa.equals(""));
			System.out.println("Placa == null " + placa == null);

			try{
				Long.parseLong(placa);
			}catch (Exception e) {
				placa = "";
			}
			if (!placa.isEmpty()) {
				try {
					veiculoAtual = new VeiculoController().buscaDadosveiculo(placa);
					if (veiculoAtual != null) {
						this.preencheDadosVeiculo(veiculoAtual);
						edicao = true;
					} else {
						limpaCampos();
						edicao = false;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				dadosInvalidos();
			}
			
		});
		btnBuscar.setBounds(448, 217, 89, 23);
		add(btnBuscar);
		
		JLabel label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(430, 215, 23, 25);
		add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(792, 260, 23, 25);
		add(label_1);
		
		JLabel label_2 = new JLabel("*");
		label_2.setVerticalAlignment(SwingConstants.TOP);
		label_2.setBounds(792, 300, 23, 25);
		add(label_2);
		
		JLabel label_3 = new JLabel("*");
		label_3.setVerticalAlignment(SwingConstants.TOP);
		label_3.setBounds(430, 340, 23, 25);
		add(label_3);
	}
}
