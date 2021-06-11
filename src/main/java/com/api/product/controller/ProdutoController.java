package com.api.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.product.assembler.ProdutoDTOAssembler;
import com.api.product.assembler.ProdutoDTODisassembler;
import com.api.product.dto.ProdutoDTO;
import com.api.product.dto.input.ProdutoInput;
import com.api.product.repository.ProdutoRepository;
import com.api.product.service.ProdutoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoDTOAssembler produtoDtoAssembler;
	
	@Autowired
	private ProdutoDTODisassembler produtoDtoDisassembler; 
	
	@GetMapping
	public List<ProdutoDTO> Listar(){
		return produtoDtoAssembler.toCollectionModel(produtoRepository.findAll());
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long produtoId) {
		return   produtoDtoAssembler.toModel(produtoService.buscarOuFalhar(produtoId));
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput){
		var produtoAtual = produtoService.buscarOuFalhar(produtoId);		
		produtoDtoDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		
		return  produtoDtoAssembler.toModel(produtoService.salvar(produtoAtual));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@RequestBody @Valid ProdutoInput produtoInput) {		
			ProdutoDTO p = produtoDtoAssembler.toModel(
						produtoService.salvar( produtoDtoDisassembler.toDomainModel(produtoInput)));
			return p;
	}
	
	@DeleteMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long produtoId){
		produtoService.excluir(produtoId);	
	}
	
}
