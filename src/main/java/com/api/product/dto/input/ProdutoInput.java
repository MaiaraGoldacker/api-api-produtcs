package com.api.product.dto.input;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoInput {

	@NotBlank
	private String nome;
	
	private String descricao;
	
	@NotNull
	private BigDecimal preco;	
	
	@NotNull
	private Integer quantidade;
	
	@NotNull
	private Boolean ativo;
}
