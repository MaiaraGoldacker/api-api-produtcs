package com.api.product.exception;

public class ProdutoEmUsoException extends RuntimeException{

private static final long serialVersionUID = 1L;
	
	public ProdutoEmUsoException(Long idProduto) {
		super("Produto id " + idProduto + " já foi utilizado para uma compra e não pode ser excluído.");
	}
	
}
