package br.com.janus.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.InputMismatchException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.janus.controller.ClienteController;
import br.com.janus.controller.EnderecoController;
import br.com.janus.model.Cliente;
import br.com.janus.model.Endereco;

public class ValidaCliente implements ActionListener {

	private JTextField textFieldCPF;
	private JTextField textFieldCNPJ;
	
	public ValidaCliente(JTextField textFieldCPF, JTextField textFieldCNPJ) {
		this.textFieldCPF = textFieldCPF;
		this.textFieldCNPJ = textFieldCNPJ;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cpf = this.textFieldCPF.getText();
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");

		String cnpj = this.textFieldCNPJ.getText();
		cnpj = cnpj.replace(".", "");
		cnpj = cnpj.replace("/", "");
		cnpj = cnpj.replace("-", "");

		if (!cpf.isEmpty()) {
			try {
				cpfValido(cpf);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (!cnpj.isEmpty()) {
			cnpjValid(cnpj);
		}
	}

	public void cpfValido(String cpf) throws ParseException {
		if (validaCPF(cpf)) {
			System.out.println("cpf valido");
			try {
				Cliente cliente = new ClienteController().buscaDadosClienteCpf(cpf);
				Endereco endereco = new Endereco();
				if (cliente != null){
					endereco = new EnderecoController().buscaEndereco(cliente.getEndereco());
				}
				//CadastroCliente cadastro = new CadastroCliente();
				//cadastro.preencheDadosCliente(cliente,endereco);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			dadosInvalidos();
		}
	}

	public static boolean cnpjValid(String cnpj) {
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

	private void dadosInvalidos() {
		JOptionPane.showMessageDialog(null, "CPF/CNPJ inválido! Por favor, tente novamente.");
	}

	private static boolean validaCPF(String cpf) {
		if (cpf == null || cpf.length() != 11 || isCPFPadrao(cpf))
			return false;

		try {
			Long.parseLong(cpf);
		} catch (NumberFormatException e) { // CPF não possui somente números
			return false;
		}

		return calcDigVerif(cpf.substring(0, 9)).equals(cpf.substring(9, 11));
	}

	/**
	 *
	 * @param cpf
	 *            String valor a ser testado
	 * @return boolean indicando se o usuário entrou com um CPF padrão
	 */
	private static boolean isCPFPadrao(String cpf) {
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") || cpf.equals("33333333333")
				|| cpf.equals("44444444444") || cpf.equals("55555555555") || cpf.equals("66666666666")
				|| cpf.equals("77777777777") || cpf.equals("88888888888") || cpf.equals("99999999999")) {

			return true;
		}

		return false;
	}

	private static String calcDigVerif(String num) {
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
}
