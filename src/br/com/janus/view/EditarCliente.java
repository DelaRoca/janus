package br.com.janus.view;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.InputMismatchException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import br.com.janus.controller.ClienteController;
import br.com.janus.controller.EnderecoController;
import br.com.janus.model.Cliente;
import br.com.janus.model.Endereco;

public class EditarCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNome;
	private JTextField textFieldCPF;
	private JTextField textFieldDtNasc;
	private JTextField textFieldEmail;
	private JTextField textFieldTelefone;
	private JTextField textFieldCelular;
	private JTextField textFieldRua;
	private JTextField textFieldBairro;
	private JTextField textFieldCidade;
	private JTextField textFieldEstado;
	private JTextField textFieldCNPJ;
	private JRadioButton rdbtnCpf;
	private JRadioButton rdbtnCnpj;
	public boolean edicao= false;
	private JButton btnVerificar;
	
	private Cliente clienteAtual;
	private Endereco enderecoAtual;

	public EditarCliente() throws ParseException {
		setLayout(null);

		textFieldCNPJ = new JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
		textFieldCNPJ.setBounds(316, 124, 179, 25);
		add(textFieldCNPJ);
		textFieldCNPJ.setColumns(10);

		textFieldCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		textFieldCPF.setColumns(10);
		textFieldCPF.setBounds(315, 90, 180, 25);
		add(textFieldCPF);

		JLabel lblTitulo = new JLabel("Editar Cadastro de Cliente");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitulo.setBounds(10, 23, 980, 48);
		add(lblTitulo);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(205, 171, 100, 30);
		add(lblNome);

		JLabel lblDtNasc = new JLabel("Data de Nascimento:");
		lblDtNasc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDtNasc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDtNasc.setBounds(167, 212, 138, 30);
		add(lblDtNasc);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(205, 251, 100, 30);
		add(lblEmail);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTelefone.setBounds(205, 292, 100, 30);
		add(lblTelefone);

		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCelular.setBounds(205, 333, 100, 30);
		add(lblCelular);

		JLabel lblRua = new JLabel("Rua:");
		lblRua.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRua.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRua.setBounds(205, 374, 100, 30);
		add(lblRua);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBairro.setBounds(205, 415, 100, 30);
		add(lblBairro);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCidade.setBounds(205, 456, 100, 30);
		add(lblCidade);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEstado.setBounds(205, 497, 100, 30);
		add(lblEstado);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(316, 175, 468, 25);
		add(textFieldNome);
		textFieldNome.setColumns(10);

		textFieldDtNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
		textFieldDtNasc.setEditable(false);
		textFieldDtNasc.setColumns(10);
		textFieldDtNasc.setBounds(315, 216, 150, 25);
		add(textFieldDtNasc);

		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(315, 257, 468, 25);
		add(textFieldEmail);

		textFieldTelefone = new JFormattedTextField(new MaskFormatter("(##) ####-####"));
		textFieldTelefone.setColumns(10);
		textFieldTelefone.setBounds(315, 298, 468, 25);
		add(textFieldTelefone);

		textFieldRua = new JTextField();
		textFieldRua.setColumns(10);
		textFieldRua.setBounds(315, 380, 468, 25);
		add(textFieldRua);

		textFieldBairro = new JTextField();
		textFieldBairro.setColumns(10);
		textFieldBairro.setBounds(315, 421, 468, 25);
		add(textFieldBairro);

		textFieldCidade = new JTextField();
		textFieldCidade.setColumns(10);
		textFieldCidade.setBounds(315, 462, 468, 25);
		add(textFieldCidade);

		JLabel label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(787, 171, 23, 25);
		add(label);

		JLabel label_2 = new JLabel("");
		label_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_2.setBounds(242, 82, 270, 78);
		add(label_2);

		JLabel label_3 = new JLabel("*");
		label_3.setVerticalAlignment(SwingConstants.TOP);
		label_3.setBounds(787, 297, 23, 25);
		add(label_3);

		textFieldEstado = new JFormattedTextField(new MaskFormatter("UU"));
		textFieldEstado.setBounds(315, 503, 68, 25);
		add(textFieldEstado);
		textFieldEstado.setColumns(10);

		textFieldCelular = new JFormattedTextField(new MaskFormatter("(##) ####-####"));
		textFieldCelular.setColumns(10);
		textFieldCelular.setBounds(315, 339, 468, 25);
		add(textFieldCelular);

		rdbtnCpf = new JRadioButton("CPF:");
		rdbtnCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnCpf.setBounds(244, 81, 68, 23);
		rdbtnCpf.addActionListener(a -> {
			rdbtnCnpj.setSelected(false);
			textFieldCNPJ.setText("");
			//textFieldCNPJ.setValue("");
			textFieldCNPJ.repaint();
			textFieldCNPJ.setEditable(false);			
			textFieldDtNasc.setEditable(true);
			textFieldCPF.setEditable(true);
		});
		add(rdbtnCpf);

		rdbtnCnpj = new JRadioButton("CNPJ:");
		rdbtnCnpj.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnCnpj.setBounds(235, 115, 78, 23);
		rdbtnCnpj.addActionListener(a -> {
			rdbtnCpf.setSelected(false);
			textFieldCPF.setText("");
			//textFieldCPF.setValue("");
			textFieldCPF.repaint();
			textFieldCPF.setEditable(false);
			textFieldDtNasc.setText("");
			//textFieldDtNasc.setValue("");
			textFieldDtNasc.repaint();
			textFieldDtNasc.setEditable(false);
			textFieldCNPJ.setEditable(true);
		});
		add(rdbtnCnpj);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			if (verificaCamposValidos()) {
				if(edicao){
					atualizaCliente();
				}else{
					salvaCliente();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Campos obrigatórios não preenchidos.");
			}
		});
		btnSalvar.setBounds(406, 555, 89, 23);
		add(btnSalvar);

		this.btnVerificar = new JButton("Verificar");
		btnVerificar.setBounds(552, 100, 89, 23);
		btnVerificar.addActionListener(a -> {
			String cpf = this.textFieldCPF.getText();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");

			String cnpj = this.textFieldCNPJ.getText();
			cnpj = cnpj.replace(".", "");
			cnpj = cnpj.replace("/", "");
			cnpj = cnpj.replace("-", "");
			if (!cpf.isEmpty()) {
				cpfValido(cpf);
			} else if (!cnpj.isEmpty()) {
				cnpjValid(cnpj);
			}
		});
		add(btnVerificar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(568, 555, 89, 23);
		add(btnCancelar);

		JLabel label_1 = new JLabel("*");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(516, 82, 23, 25);
		add(label_1);
	}
	
	private void atualizaCliente() {
		System.out.println("edicao endereco");
		System.out.println("idEndereco: "+ enderecoAtual);
		Endereco endereco = constroiEndereco();
		Integer idEndereco = new ClienteController().atualizaEndereco(endereco);
		System.out.println("idEnderec"+ idEndereco);
		if(idEndereco != 0 ){
			Cliente cliente = constroiCliente(idEndereco);
			new ClienteController().atualizaCliente(cliente,idEndereco);
		}
		
	}

	private void salvaCliente() {
		System.out.println("salvar cliente");
		Endereco endereco = constroiEndereco();
		String idEndereco = new ClienteController().salvaEndereco(endereco);
		System.out.println("idEnderec"+ idEndereco);
		if(idEndereco != "0" ){
			Cliente cliente = constroiCliente(Integer.parseInt(idEndereco));
			new ClienteController().salvaCliente(cliente, Integer.parseInt(idEndereco));
		}
	}

	private Endereco constroiEndereco() {
		if(enderecoAtual != null){
			enderecoAtual.setRua(this.textFieldRua.getText());
			enderecoAtual.setBairro(this.textFieldBairro.getText());
			enderecoAtual.setCidade(this.textFieldBairro.getText());
			enderecoAtual.setEstado(this.textFieldEstado.getText());
			return enderecoAtual;
		}
		Endereco endereco = new Endereco();
		endereco.setRua(this.textFieldRua.getText());
		endereco.setBairro(this.textFieldBairro.getText());
		endereco.setCidade(this.textFieldBairro.getText());
		endereco.setEstado(this.textFieldEstado.getText());
		endereco.setIdEndereco(0);
		return endereco;
	}

	private Cliente constroiCliente(Integer idEndereco) {
		String cpf = this.textFieldCPF.getText();
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");

		String cnpj = this.textFieldCNPJ.getText();
		cnpj = cnpj.replace(".", "");
		cnpj = cnpj.replace("/", "");
		cnpj = cnpj.replace("-", "");

		if(clienteAtual != null){
			clienteAtual.setCpf(cpf);
			clienteAtual.setCnpj(cnpj);
			clienteAtual.setNome(this.textFieldNome.getText());
			clienteAtual.setEmail(this.textFieldEmail.getText());
			clienteAtual.setDataNascimento(this.textFieldDtNasc.getText());
			clienteAtual.setTelefone(this.textFieldTelefone.getText());
			clienteAtual.setCelular(this.textFieldCelular.getText());
			clienteAtual.setEndereco(idEndereco);
			return clienteAtual;
		}
		Cliente cliente = new Cliente();
		cliente.setCpf(cpf);
		cliente.setCnpj(cnpj);
		cliente.setNome(this.textFieldNome.getText());
		cliente.setEmail(this.textFieldEmail.getText());
		cliente.setDataNascimento(this.textFieldDtNasc.getText());
		cliente.setTelefone(this.textFieldTelefone.getText());
		cliente.setCelular(this.textFieldCelular.getText());
		cliente.setEndereco(idEndereco);
		cliente.setIdCliente(0);
		return cliente;
	}

	private boolean verificaCamposValidos() {
		System.out.println("aqui nos verifica campos ");
		System.out.println("this.textFieldNome" + this.textFieldNome.getText().equals(""));
		System.out.println("this.textFieldTelefone " + this.textFieldTelefone.getText().equals(""));
		System.out.println("this.textFieldCPF.getText().equals" + this.textFieldCPF.getText().equals(""));

		if (this.textFieldNome.getText().equals("") || this.textFieldTelefone.getText().equals("")
				|| (this.textFieldCPF.getText().equals("") && this.textFieldCNPJ.getText().equals(""))) {
			return false;
		}
		return true;
	}

	private void cpfValido(String cpf) {
		if (validaCPF(cpf)) {
			System.out.println("cpf valido");
			try {
				clienteAtual = new ClienteController().buscaDadosClienteCpf(cpf);
				if (clienteAtual != null) {
					enderecoAtual= new EnderecoController().buscaEndereco(clienteAtual.getEndereco());
					
					this.preencheDadosCliente(clienteAtual, enderecoAtual);
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
	}

	private void limpaCampos() {
		textFieldRua.setText("");
		textFieldBairro.setText("");
		textFieldCidade.setText("");
		textFieldEstado.setText("");
		// textFieldCPF.setText("");
		// textFieldCNPJ.setText("");
		textFieldNome.setText("");
		textFieldEmail.setText("");
		textFieldCelular.setText("");
		textFieldTelefone.setText("");
		textFieldDtNasc.setText("");
	}

	private void dadosInvalidos() {
		JOptionPane.showMessageDialog(null, "CPF/CNPJ inválido! Por favor, tente novamente.");
	}

	private boolean validaCPF(String cpf) {
		if (cpf == null || cpf.length() != 11 || isCPFPadrao(cpf))
			return false;

		try {
			Long.parseLong(cpf);
		} catch (NumberFormatException e) { // CPF não possui somente números
			return false;
		}

		return calcDigVerif(cpf.substring(0, 9)).equals(cpf.substring(9, 11));
	}

	private boolean isCPFPadrao(String cpf) {
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999")) {

			return true;
		}

		return false;
	}

	private Object calcDigVerif(String num) {
		Integer primDig, segDig;
		int soma = 0, peso = 10;
		for (int i = 0; i < num.length(); i++)
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

		if (soma % 11 == 0 | soma % 11 == 1)
			primDig = new Integer(0);
		else
			primDig = new Integer(11 - (soma % 11));

		soma = 0;
		peso = 11;
		for (int i = 0; i < num.length(); i++)
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

		soma += primDig.intValue() * 2;
		if (soma % 11 == 0 | soma % 11 == 1)
			segDig = new Integer(0);
		else
			segDig = new Integer(11 - (soma % 11));

		return primDig.toString() + segDig.toString();
	}

	public void preencheDadosCliente(Cliente cliente, Endereco endereco) {
		System.out.println("Cliente : " + cliente.getIdCliente());
		if (cliente != null) {
			this.clienteAtual = cliente;
			textFieldCPF.setText(cliente.getCpf());
			textFieldCNPJ.setText(cliente.getCnpj());
			textFieldNome.setText(cliente.getNome());
			textFieldNome.repaint();
			textFieldEmail.setText(cliente.getEmail());
			textFieldCelular.setText(cliente.getCelular());
			textFieldTelefone.setText(cliente.getTelefone());
			textFieldDtNasc.setText(cliente.getDataNascimento());
		}
		System.out.println("Endereco : " + endereco.getIdEndereco());
		if (endereco != null) {
			this.enderecoAtual = endereco;
			textFieldRua.setText(endereco.getRua());
			textFieldBairro.setText(endereco.getBairro());
			textFieldCidade.setText(endereco.getCidade());
			textFieldEstado.setText(endereco.getEstado());

		}

	}

	private boolean cnpjValid(String cnpj) {
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (cnpj == null || cnpj.length() != 14
				|| cnpj.matches("^(0{14}|1{14}|2{14}|3{14}|4{14}|5{14}|6{14}|7{14}|8{14}|9{14})$"))
			return (false);
		char dig13, dig14;
		int sm, i, r, num, peso;
		// "try" - protege o código para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}
			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}
			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);
			// Verifica se os dígitos calculados conferem com os dígitos
			// informados.
			if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}
	
}
