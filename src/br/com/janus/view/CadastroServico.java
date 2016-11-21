package br.com.janus.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import br.com.janus.controller.ServicoController;
import br.com.janus.model.Servico;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class CadastroServico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldValor;
	private JTextField textFieldNome;
	private JTextField textFieldDescricao;
	private JCheckBox chckbxPorHora;
	
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
		
		chckbxPorHora = new JCheckBox("por hora");
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
			if (verificaCamposValidos()) {
				salvaServico();
			} else {
				JOptionPane.showMessageDialog(null, "Campos obrigatórios não preenchidos.");
			}
		});
		btnSalvar.setBounds(375, 522, 89, 23);
		add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(575, 522, 89, 23);
		add(btnCancelar);
		
		textFieldDescricao = new JTextField();
		textFieldDescricao.setBounds(420, 306, 285, 25);
		add(textFieldDescricao);
		textFieldDescricao.setColumns(10);
		
		JLabel label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(710, 238, 23, 25);
		add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(513, 273, 23, 25);
		add(label_1);
	}
	
	
	private void salvaServico() {
		System.out.println("salvar serviço");
		Servico servico = constroiServico();
		new ServicoController().salvaServico(servico);
	}
	

	private Servico constroiServico() {
		Servico servico = new Servico();
		servico.setNome(this.textFieldNome.getText());
		servico.setValor(this.textFieldValor.getText());
		servico.setDescricao(this.textFieldDescricao.getText());
		servico.setPorHora(this.chckbxPorHora.isSelected());
		servico.setEstaAtivo(true);
		servico.setIdServico(0);
		return servico;
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
