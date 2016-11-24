package br.com.janus;

public enum StatusENUM {
	ABERTO("Em aberto",0),
	APROVADO("Aprovado",1),
	EXECUCAO("Em Execucao",2),
	FINALIZADO("Finalizado",3),
	CANCELADO("Cancelado",4),
	EXPIRADO("Expirado",5);
	
	private Integer valor;
	private String nome;
	
	private StatusENUM(String nome, Integer valor){
		this.nome = nome;
		this.valor = valor;
	}
	
	
	public Integer getValor(){
		return this.valor;
	}
	public String getNome(){
		return this.nome;
	}

}
