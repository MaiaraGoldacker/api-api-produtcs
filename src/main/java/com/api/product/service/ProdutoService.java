package com.api.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.api.product.entity.Produto;
import com.api.product.exception.ProdutoEmUsoException;
import com.api.product.exception.ProdutoNaoEncontradoException;
import com.api.product.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto salvar(Produto produto) {		
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public void excluir(Long produtoId) {
		try {
			produtoRepository.deleteById(produtoId);
			produtoRepository.flush();
		} catch (EmptyResultDataAccessException ex){	
			throw new ProdutoNaoEncontradoException(produtoId);
		} catch(DataIntegrityViolationException ex) {
			throw new ProdutoEmUsoException(produtoId);
		}
	}
	
	public Produto buscarOuFalhar(Long produtoId) {
		return produtoRepository.findById(produtoId).orElseThrow(
				() -> new ProdutoNaoEncontradoException(produtoId));
	}

}
