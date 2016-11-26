package br.com.janus.model;

public class ClienteFisico extends Cliente {
	
	private String cpf;
	private String dataNascimento;
	
	public ClienteFisico(){
		super();
	}

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
}
