package br.com.janus.model;

public class OrdemServico {

	public Integer idOrdemDeServico;
	public Integer idCliente;
	public Integer idVeiculo;
	public String total;
	public Boolean estaExpirado;
	public String dataCriacao;
	public String dataAprovado;
	public String dataExecucao;
	public String dataFinalizado;
	public String dataCancelado;
	
	public Integer getIdOrdemDeServico() {
		return idOrdemDeServico;
	}
	public void setIdOrdemDeServico(Integer idOrdemDeServico) {
		this.idOrdemDeServico = idOrdemDeServico;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Integer getIdVeiculo() {
		return idVeiculo;
	}
	public void setIdVeiculo(Integer idVeiculo) {
		this.idVeiculo = idVeiculo;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public Boolean getEstaExpirado() {
		return estaExpirado;
	}
	public void setEstaExpirado(Boolean estaExpirado) {
		this.estaExpirado = estaExpirado;
	}
	public String getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public String getDataAprovado() {
		return dataAprovado;
	}
	public void setDataAprovado(String dataAprovado) {
		this.dataAprovado = dataAprovado;
	}
	public String getDataExecucao() {
		return dataExecucao;
	}
	public void setDataExecucao(String dataExecucao) {
		this.dataExecucao = dataExecucao;
	}
	public String getDataFinalizado() {
		return dataFinalizado;
	}
	public void setDataFinalizado(String dataFinalizado) {
		this.dataFinalizado = dataFinalizado;
	}
	public String getDataCancelado() {
		return dataCancelado;
	}
	public void setDataCancelado(String dataCancelado) {
		this.dataCancelado = dataCancelado;
	}	
}
