package br.com.janus.model;

public class ClienteJuridico extends Cliente{
	
	private String cnpj;

	public ClienteJuridico(){
		super();
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	
}
