package com.api.product.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.api.product.dto.input.ProdutoInput;
import com.api.product.entity.Produto;

@Component
public class ProdutoDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainModel(ProdutoInput produtoInput) {
		return modelMapper.map(produtoInput, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
		modelMapper.map(produtoInput, produto);
	}
	
}
