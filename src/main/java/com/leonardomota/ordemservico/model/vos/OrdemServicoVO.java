package com.leonardomota.ordemservico.model.vos;

import java.time.OffsetDateTime;

public class OrdemServicoVO {

	private Long id;
	private ClienteVO cliente;
	private String descricao;
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataModificacao;
	private OffsetDateTime dataFechou;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ClienteVO getCliente() {
		return cliente;
	}
	public void setCliente(ClienteVO cliente) {
		this.cliente = cliente;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public OffsetDateTime getDataModificacao() {
		return dataModificacao;
	}
	public void setDataModificacao(OffsetDateTime dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	public OffsetDateTime getDataFechou() {
		return dataFechou;
	}
	public void setDataFechou(OffsetDateTime dataFechou) {
		this.dataFechou = dataFechou;
	}
		
}
