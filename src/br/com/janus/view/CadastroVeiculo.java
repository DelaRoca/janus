package br.com.janus.view;

import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.com.janus.controller.VeiculoController;
import br.com.janus.model.Veiculo;

public class CadastroVeiculo extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldMarca;
	private JTextField textFieldModelo;
	private JTextField textFieldAno;
	private JFormattedTextField textFieldPlaca;
	public boolean edicao = false;
	private Veiculo veiculoAtual;
	private JLabel lblCadastroVeiculo;
	
	public CadastroVeiculo() throws ParseException {
		setLayout(null);
		
		lblCadastroVeiculo = new JLabel("Cadastro de Veículo");
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
			if (verificaCamposValidos()) {
				if (edicao) {
					atualizaVeiculo();
				} else {
					salvaVeiculo();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Campos obrigatórios não preenchidos");
			}
		});
		btnSalvar.setBounds(411, 484, 89, 23);
		add(btnSalvar);
		btnSalvar.setEnabled(false);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(573, 484, 89, 23);
		add(btnCancelar);
	
		JButton btnBuscar = new JButton("Verificar");
		btnBuscar.addActionListener(a -> {
			try {
				if (!(textFieldPlaca.getText().equals("   -    ")) ) {
					veiculoAtual = new VeiculoController().buscaDadosVeiculoPlaca(this.textFieldPlaca.getText());
					if (veiculoAtual != null) {
						this.preencheDadosVeiculo(veiculoAtual);
						edicao = true;
						btnSalvar.setEnabled(true);
	
					} else {
						limpaCampos();
						edicao = false;
						btnSalvar.setEnabled(true);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		btnBuscar.setBounds(448,217,89,23);
		add(btnBuscar);

	JLabel label = new JLabel("*");label.setVerticalAlignment(SwingConstants.TOP);label.setBounds(430,215,23,25);

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
	
	private void atualizaVeiculo() {
		Veiculo veiculo = constroiVeiculo();
		new VeiculoController().atualizaVeiculo(veiculo);
	}
	
	private void salvaVeiculo() {
		Veiculo veiculo = constroiVeiculo();
		new VeiculoController().salvaVeiculo(veiculo);
	}
	
	private Veiculo constroiVeiculo() {
		if (veiculoAtual != null) {
			veiculoAtual.setPlaca(this.textFieldPlaca.getText());
			veiculoAtual.setFabricante(this.textFieldMarca.getText());
			veiculoAtual.setModelo(this.textFieldModelo.getText());
			veiculoAtual.setAno(this.textFieldAno.getText());
			return veiculoAtual;
		}
		Veiculo veiculo = new Veiculo();
		veiculo.setPlaca(this.textFieldPlaca.getText());
		veiculo.setFabricante(this.textFieldMarca.getText());
		veiculo.setModelo(this.textFieldModelo.getText());
		veiculo.setAno(this.textFieldAno.getText());
		veiculo.setIdVeiculo(0);
		return veiculo;
	}
	
	private boolean verificaCamposValidos() {
		if (this.textFieldPlaca.getText().equals("") || this.textFieldPlaca.getText().equals("   -    ") || this.textFieldAno.getText().equals("    ") || this.textFieldModelo.getText().equals("") || this.textFieldMarca.getText().equals("")   ) {
			return false;
		}
		return true;
	}
	
	public void preencheDadosVeiculo(Veiculo veiculo) {
		if (veiculo != null) {
			lblCadastroVeiculo.setText("Edição de Veículo");
			lblCadastroVeiculo.repaint();
			
			this.veiculoAtual = veiculo;
			textFieldPlaca.setText(veiculo.getPlaca());
			textFieldMarca.setText(veiculo.getFabricante());
			textFieldModelo.setText(veiculo.getModelo());
			textFieldAno.setText(veiculo.getAno());
		}

	}
	
	private void limpaCampos() {
		textFieldMarca.setText("");
		textFieldMarca.repaint();
		textFieldModelo.setText("");
		textFieldModelo.repaint();
		textFieldAno.setText("");
		textFieldAno.repaint();
		lblCadastroVeiculo.setText("Cadastro de Veículo");
		lblCadastroVeiculo.repaint();
	}
	
}
