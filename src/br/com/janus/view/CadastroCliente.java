package br.com.janus.view;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.ParseException;
//import java.util.InputMismatchException;

import javax.swing.ButtonGroup;
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
import br.com.janus.model.ClienteFisico;
import br.com.janus.model.ClienteJuridico;
import br.com.janus.model.Endereco;

@SuppressWarnings("serial")
public class CadastroCliente extends JPanel {

	private JTextField textFieldNome;
	private JFormattedTextField textFieldCPF;
	private JFormattedTextField textFieldDtNasc;
	private JTextField textFieldEmail;
	private JFormattedTextField textFieldTelefone;
	private JFormattedTextField textFieldCelular;
	private JTextField textFieldRua;
	private JTextField textFieldBairro;
	private JTextField textFieldCidade;
	private JFormattedTextField textFieldEstado;
	private JFormattedTextField textFieldCNPJ;
	private JButton btnVerificar;
	private JButton btnSalvar;
	private JRadioButton rdbtnCpf;
	private JRadioButton rdbtnCnpj;
	
	JLabel lblCadastroDeClientes;
	
	public boolean edicao = false;
	private ClienteFisico clienteFisico;
	private ClienteJuridico clienteJuridico;
	private Endereco enderecoAtual;

	public CadastroCliente() throws ParseException {
		setLayout(null);

		textFieldCNPJ = new JFormattedTextField(new MaskFormatter("##.###.###/####-##"));
		textFieldCNPJ.setBounds(319, 115, 179, 25);
		textFieldCNPJ.setColumns(10);
		add(textFieldCNPJ);
		textFieldCNPJ.setEditable(false);

		textFieldCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		textFieldCPF.setColumns(10);
		textFieldCPF.setBounds(318, 81, 180, 25);
		add(textFieldCPF);
		textFieldCPF.setEditable(false);

		JLabel label_2 = new JLabel("");
		label_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_2.setBounds(254, 74, 277, 74);
		add(label_2);

		lblCadastroDeClientes = new JLabel("Cadastro de Clientes");
		lblCadastroDeClientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeClientes.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblCadastroDeClientes.setBounds(10, 11, 980, 48);
		add(lblCadastroDeClientes);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNome.setBounds(211, 150, 100, 30);
		add(lblNome);

		JLabel lblDtNasc = new JLabel("Data de Nascimento:");
		lblDtNasc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDtNasc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDtNasc.setBounds(173, 186, 138, 30);
		add(lblDtNasc);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(211, 225, 100, 30);
		add(lblEmail);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTelefone.setBounds(211, 266, 100, 30);
		add(lblTelefone);

		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCelular.setBounds(211, 307, 100, 30);
		add(lblCelular);

		JLabel lblRua = new JLabel("Rua:");
		lblRua.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRua.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRua.setBounds(211, 348, 100, 30);
		add(lblRua);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBairro.setBounds(211, 389, 100, 30);
		add(lblBairro);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCidade.setBounds(211, 430, 100, 30);
		add(lblCidade);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEstado.setBounds(211, 471, 100, 30);
		add(lblEstado);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(321, 154, 468, 25);
		add(textFieldNome);
		textFieldNome.setColumns(10);

		textFieldDtNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
		textFieldDtNasc.setColumns(10);
		textFieldDtNasc.setBounds(321, 190, 150, 25);
		add(textFieldDtNasc);

		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(321, 231, 468, 25);
		add(textFieldEmail);

		textFieldTelefone = new JFormattedTextField(new MaskFormatter("(##) ####-####"));
		textFieldTelefone.setColumns(10);
		textFieldTelefone.setBounds(321, 272, 468, 25);
		add(textFieldTelefone);

		textFieldCelular = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
		textFieldCelular.setColumns(10);
		textFieldCelular.setBounds(321, 313, 468, 25);
		add(textFieldCelular);

		textFieldRua = new JTextField();
		textFieldRua.setColumns(10);
		textFieldRua.setBounds(321, 354, 468, 25);
		add(textFieldRua);

		textFieldBairro = new JTextField();
		textFieldBairro.setColumns(10);
		textFieldBairro.setBounds(321, 395, 468, 25);
		add(textFieldBairro);

		textFieldCidade = new JTextField();
		textFieldCidade.setColumns(10);
		textFieldCidade.setBounds(321, 436, 468, 25);
		add(textFieldCidade);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(a -> {
			GerenciadorDeInterface.setPanel(new Principal());
		});
		btnCancelar.setBounds(574, 529, 89, 23);
		add(btnCancelar);

		JLabel label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setBounds(793, 155, 23, 25);
		add(label);

		JLabel label_1 = new JLabel("*");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(536, 76, 23, 25);
		add(label_1);

		JLabel label_3 = new JLabel("*");
		label_3.setVerticalAlignment(SwingConstants.TOP);
		label_3.setBounds(793, 271, 23, 25);
		add(label_3);

		textFieldEstado = new JFormattedTextField(new MaskFormatter("UU"));
		textFieldEstado.setBounds(321, 477, 68, 25);
		add(textFieldEstado);
		textFieldEstado.setColumns(10);

		rdbtnCpf = new JRadioButton("CPF:");
		rdbtnCpf.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnCpf.setBounds(244, 81, 68, 23);
		rdbtnCpf.addActionListener(a -> {
				textFieldCNPJ.setText("");
				textFieldCNPJ.setValue("");
				textFieldCNPJ.repaint();
				textFieldCNPJ.setEditable(false);
				textFieldDtNasc.setEditable(true);
				textFieldCPF.setEditable(true);
				btnSalvar.setEnabled(false);
				limpaCampos();
		});
		add(rdbtnCpf);

		rdbtnCnpj = new JRadioButton("CNPJ:");
		rdbtnCnpj.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnCnpj.setBounds(235, 115, 78, 23);
		rdbtnCnpj.addActionListener(a -> {
			textFieldCPF.setText("");
			textFieldCPF.setValue("");
			textFieldCPF.repaint();
			textFieldCNPJ.setEditable(true);
			textFieldDtNasc.setEditable(false);
			textFieldCPF.setEditable(false);
			btnSalvar.setEnabled(false);
			limpaCampos();
		});
		add(rdbtnCnpj);

	    ButtonGroup grupoRadios = new ButtonGroup();
	    grupoRadios.add(rdbtnCpf);
	    grupoRadios.add(rdbtnCnpj);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(a -> {
			if (verificaCamposValidos()) {
				if (edicao) {
					atualizaCliente();
				} else {
					salvaCliente();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Campos obrigatï¿½rios nï¿½o preenchidos.");
			}
		});
		btnSalvar.setBounds(412, 529, 89, 23);
		btnSalvar.setEnabled(false);
		add(btnSalvar);

		this.btnVerificar = new JButton("Verificar");
		btnVerificar.setBounds(552, 100, 89, 23);
		btnVerificar.addActionListener(a -> {
			String cpf = this.textFieldCPF.getText();
			String cnpj = this.textFieldCNPJ.getText();
			cpf = limpezaCaracteresNaoNumeraisCpfCnpj( cpf );
			cnpj = limpezaCaracteresNaoNumeraisCpfCnpj( cnpj );
			//TODO paraFabioDeFabio: reverificar e reescrever essa "lógica" -> funciona, mas tá mal organizado o código.			
			try{
				Long.parseLong(cpf);
			}catch (Exception e) {
				cpf = "";
			}
			if (!cpf.isEmpty()) {
				cpfValido(cpf);
				btnSalvar.setEnabled(true);
			} else if (!cnpj.isEmpty()) {
				if (novo(cnpj)) {
					btnSalvar.setEnabled(true);
					try {
						clienteJuridico = new ClienteController().buscaDadosClienteCnpj(cnpj);
						if (clienteJuridico != null) {
							enderecoAtual = new EnderecoController().buscaEndereco(clienteJuridico.getEndereco());
							this.preencheDadosCliente(clienteJuridico, enderecoAtual);
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
		});
		add(btnVerificar);
	}

	private boolean novo(String cnpj) {
		if (cnpj == null || cnpj.length() != 14)
			return false;

		try {
			Long.parseLong(cnpj);
		} catch (NumberFormatException e) { // CNPJ nï¿½o possui somente nï¿½meros
			return false;
		}

		int soma = 0;
		String cnpj_calc = cnpj.substring(0, 12);

		char chr_cnpj[] = cnpj.toCharArray();
		for (int i = 0; i < 4; i++)
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
				soma += (chr_cnpj[i] - 48) * (6 - (i + 1));

		for (int i = 0; i < 8; i++)
			if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
				soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));

		int dig = 11 - soma % 11;
		cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc)))
				.append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
		soma = 0;
		for (int i = 0; i < 5; i++)
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
				soma += (chr_cnpj[i] - 48) * (7 - (i + 1));

		for (int i = 0; i < 8; i++)
			if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
				soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));

		dig = 11 - soma % 11;
		cnpj_calc = (new StringBuilder(String.valueOf(cnpj_calc)))
				.append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();

		return cnpj.equals(cnpj_calc);
	}

	private void atualizaCliente() {
		Endereco endereco = constroiEndereco();
		Integer idEndereco = new ClienteController().atualizaEndereco(endereco);
		if (idEndereco != 0) {
			String cpf = this.textFieldCPF.getText();
			String cnpj = this.textFieldCNPJ.getText();
			cpf = limpezaCaracteresNaoNumeraisCpfCnpj( cpf );
			cnpj = limpezaCaracteresNaoNumeraisCpfCnpj( cnpj );
			
			if ( !cpf.equals("") ) {
				ClienteFisico cliente = constroiClienteFisico();
				new ClienteController().atualizaCliente(cliente);
			}else if( !cnpj.equals("") ){
				ClienteJuridico cliente = constroiClienteJuridico();
				new ClienteController().atualizaCliente(cliente);
			}
		}
	}

	private ClienteJuridico constroiClienteJuridico() {
		String cnpj = this.textFieldCNPJ.getText();
		cnpj = limpezaCaracteresNaoNumeraisCpfCnpj( cnpj );
		
		ClienteJuridico cliente = new ClienteJuridico();
		cliente.setCnpj(cnpj);
		cliente.setNome(this.textFieldNome.getText());
		cliente.setEmail(this.textFieldEmail.getText());
		cliente.setTelefone(this.textFieldTelefone.getText());
		cliente.setCelular(this.textFieldCelular.getText());
		return cliente;
	}
	
	private ClienteFisico constroiClienteFisico() {
//		if (clienteFisico != null) {
//			clienteFisico.setCpf(this.textFieldCPF.getText());           
//			clienteFisico.setNome(this.textFieldNome.getText());             
//			clienteFisico.setEmail(this.textFieldEmail.getText());           
//			clienteFisico.setDataNascimento(this.textFieldDtNasc.getText()); 
//			clienteFisico.setTelefone(this.textFieldTelefone.getText());     
//		    clienteFisico.setCelular(this.textFieldCelular.getText());
//		    return clienteFisico;
//		}
		String cpf = this.textFieldCPF.getText();
		cpf = limpezaCaracteresNaoNumeraisCpfCnpj( cpf );
		
		ClienteFisico cliente = new ClienteFisico();
		cliente.setCpf(cpf);
		cliente.setNome(this.textFieldNome.getText());
		cliente.setEmail(this.textFieldEmail.getText());
		cliente.setDataNascimento(this.textFieldDtNasc.getText());
		cliente.setTelefone(this.textFieldTelefone.getText());
		cliente.setCelular(this.textFieldCelular.getText());
		return cliente;
}

	private void salvaCliente() {
		Endereco endereco = constroiEndereco();
		String idEndereco = new ClienteController().salvaEndereco(endereco);
		if (idEndereco != "0") {
			novoCliente(Integer.parseInt(idEndereco));
		}
	}

	private String limpezaCaracteresNaoNumeraisCpfCnpj(String cpfCnpj) {
		cpfCnpj	 = cpfCnpj.replace(".", "");
		cpfCnpj	 = cpfCnpj.replace("/", "");
		cpfCnpj	 = cpfCnpj.replace("-", "");
		cpfCnpj	 = cpfCnpj.replace(" ", "");
		return cpfCnpj;
	}
	
	private void novoCliente(int idEndereco) {
		String cpf = this.textFieldCPF.getText();
		String cnpj = this.textFieldCNPJ.getText();
		cpf = limpezaCaracteresNaoNumeraisCpfCnpj( cpf );
		cnpj = limpezaCaracteresNaoNumeraisCpfCnpj( cnpj );
	
		if ( !cpf.equals("") ) {
			ClienteFisico cliente = new ClienteFisico();
			String nome = this.textFieldNome.getText();
			String email = this.textFieldEmail.getText();
			String dataNasc = this.textFieldDtNasc.getText(); 
			String telefone = this.textFieldTelefone.getText();
			String celular = this.textFieldCelular.getText();
			
			cliente.setCpf(cpf);
			cliente.setNome(nome);
			cliente.setEmail(email);
			cliente.setDataNascimento(dataNasc);
			cliente.setTelefone(telefone);
			cliente.setCelular(celular);
			cliente.setEndereco(idEndereco);
			new ClienteController().salvaCliente(cliente,idEndereco);
		} else if ( !cnpj.equals("") ) {
			ClienteJuridico cliente = new ClienteJuridico();
			String nome = this.textFieldNome.getText();
			String email = this.textFieldEmail.getText();
			String telefone = this.textFieldTelefone.getText();
			String celular = this.textFieldCelular.getText();
			
			cliente.setCnpj(cnpj);
			cliente.setNome(nome);
			cliente.setEmail(email);
			cliente.setTelefone(telefone);
			cliente.setCelular(celular);
			cliente.setEndereco(idEndereco);
			new ClienteController().salvaCliente(cliente,idEndereco);
		}
	}

	private Endereco constroiEndereco() {
		if (enderecoAtual != null) {
			enderecoAtual.setRua(this.textFieldRua.getText());
			enderecoAtual.setBairro(this.textFieldBairro.getText());
			enderecoAtual.setCidade(this.textFieldCidade.getText());
			enderecoAtual.setEstado(this.textFieldEstado.getText());
			return enderecoAtual;
		}
		Endereco endereco = new Endereco();
		endereco.setRua(this.textFieldRua.getText());
		endereco.setBairro(this.textFieldBairro.getText());
		endereco.setCidade(this.textFieldCidade.getText());
		endereco.setEstado(this.textFieldEstado.getText());
		endereco.setIdEndereco(0);
		return endereco;
	}

	private boolean verificaCamposValidos() {
		if (this.textFieldNome.getText().equals("") || this.textFieldTelefone.getText().equals("")
				|| (this.textFieldCPF.getText().equals("") && this.textFieldCNPJ.getText().equals(""))) {
			return false;
		}
		return true;
	}

	private void cpfValido(String cpf) {
		if (validaCPF(cpf)) {
			try {
				clienteFisico = new ClienteController().buscaDadosClienteCpf(cpf);
				if (clienteFisico != null) {
					enderecoAtual = new EnderecoController().buscaEndereco(clienteFisico.getEndereco());
					preencheDadosCliente(clienteFisico, enderecoAtual);
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
		textFieldRua.repaint();
		textFieldBairro.setText("");
		textFieldBairro.repaint();
		textFieldCidade.setText("");
		textFieldCidade.repaint();
		textFieldEstado.setText("");
		textFieldEstado.setValue("");
		textFieldEstado.repaint();
		textFieldNome.setText("");
		textFieldNome.repaint();
		textFieldEmail.setText("");
		textFieldEmail.repaint();
		textFieldCelular.setText("");
		textFieldCelular.setValue("");
		textFieldCelular.repaint();
		textFieldTelefone.setText("");
		textFieldTelefone.setValue("");
		textFieldTelefone.repaint();
		textFieldDtNasc.setText("");
		textFieldDtNasc.setValue("");
		textFieldDtNasc.repaint();
		lblCadastroDeClientes.setText("Cadastro de Clientes");
		lblCadastroDeClientes.repaint();
	}

	private void dadosInvalidos() {
		JOptionPane.showMessageDialog(null, "CPF/CNPJ invï¿½lido! Por favor, tente novamente.");
	}

	private boolean validaCPF(String cpf) {
		if (cpf == null || cpf.length() != 11 || isCPFPadrao(cpf))
			return false;
		try {
			Long.parseLong(cpf);
		} catch (NumberFormatException e) { // CPF nï¿½o possui somente nï¿½meros
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

	public void preencheDadosCliente(ClienteJuridico cliente, Endereco endereco) {
		if (cliente != null) {
			lblCadastroDeClientes.setText("Ediï¿½ï¿½o de Cliente");
			lblCadastroDeClientes.repaint();
			
			this.clienteJuridico = cliente;
			textFieldCNPJ.setText(cliente.getCnpj());
			textFieldNome.setText(cliente.getNome());
			textFieldNome.repaint();
			textFieldEmail.setText(cliente.getEmail());
			textFieldCelular.setText(cliente.getCelular());
			textFieldTelefone.setText(cliente.getTelefone());
		}
		if (endereco != null) {
			this.enderecoAtual = endereco;
			textFieldRua.setText(endereco.getRua());
			textFieldBairro.setText(endereco.getBairro());
			textFieldCidade.setText(endereco.getCidade());
			textFieldEstado.setText(endereco.getEstado());
		}
	}
	
	public void preencheDadosCliente(ClienteFisico cliente, Endereco endereco) {
		if (cliente != null) {
			lblCadastroDeClientes.setText("Ediï¿½ï¿½o de Cliente");
			lblCadastroDeClientes.repaint();
			
			this.clienteFisico = cliente;
			textFieldCPF.setText(cliente.getCpf());
			textFieldDtNasc.setText(cliente.getDataNascimento());
			textFieldNome.setText(cliente.getNome());
			textFieldNome.repaint();
			textFieldEmail.setText(cliente.getEmail());
			textFieldCelular.setText(cliente.getCelular());
			textFieldTelefone.setText(cliente.getTelefone());
		}
		if (endereco != null) {
			this.enderecoAtual = endereco;
			textFieldRua.setText(endereco.getRua());
			textFieldBairro.setText(endereco.getBairro());
			textFieldCidade.setText(endereco.getCidade());
			textFieldEstado.setText(endereco.getEstado());
		}
	}
	
	//TODO Criar dupla verificação na hora do Salvar (se o CPF gravado na variavel confere com o "atual" cpf escrito no campo!)
}
