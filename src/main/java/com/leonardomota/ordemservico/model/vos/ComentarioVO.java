package com.leonardomota.ordemservico.model.vos;

import java.time.OffsetDateTime;

public class ComentarioVO {

	private Integer id;
	private String descricao;
	private OffsetDateTime dataPublicacao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public OffsetDateTime getDataPublicacao() {
		return dataPublicacao;
	}
	public void setDataPublicacao(OffsetDateTime dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
	
}
