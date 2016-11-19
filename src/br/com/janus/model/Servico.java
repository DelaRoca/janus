package br.com.janus.model;

public class Servico {

	private Integer idServico;
	private String descricao;
	private String nome;
	private Boolean porHora;
	private String valor;
	
	
	public Integer getIdServico() {
		return idServico;
	}
	public void setIdServico(Integer idServico) {
		this.idServico = idServico;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getPorHora() {
		return porHora;
	}
	public void setPorHora(Boolean porHora) {
		this.porHora = porHora;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}


}
