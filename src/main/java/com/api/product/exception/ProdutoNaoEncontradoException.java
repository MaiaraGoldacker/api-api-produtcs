package com.api.product.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ProdutoNaoEncontradoException(Long idProduto) {
		super("Produto id " + idProduto + " não encontrado");
	}
	
}
