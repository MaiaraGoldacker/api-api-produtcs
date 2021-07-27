package com.api.product.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Builder
public class ProdutoDTO {

	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;	
	private Boolean ativo;
	private Integer quantidade;
}
