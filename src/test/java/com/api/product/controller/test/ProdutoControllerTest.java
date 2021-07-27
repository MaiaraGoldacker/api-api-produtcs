package com.api.product.controller.test;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.product.assembler.ProdutoDTOAssembler;
import com.api.product.assembler.ProdutoDTODisassembler;
import com.api.product.controller.ProdutoController;
import com.api.product.dto.ProdutoDTO;
import com.api.product.dto.input.ProdutoInput;
import com.api.product.entity.Produto;
import com.api.product.repository.ProdutoRepository;
import com.api.product.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {
	
	String API_PRODUCT = "/produtos";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	private ProdutoService service;
	
	@MockBean
	private ProdutoRepository repository;
	
	@MockBean
	private ProdutoDTOAssembler produtoDtoAssembler;
	
	@MockBean
	private ProdutoDTODisassembler produtoDtoDisassembler;
	
	@Autowired
	ProdutoController produtoController;

	@Test
	public void deveCriarProduto() throws Exception {
		
		ProdutoInput produtoInput = criaNovoProdutoInput();
		ProdutoDTO produtoDto = criaNovoProdutoDTO();
		Produto produtoSalvo = criaNovoProduto();				
		
		BDDMockito.given(service.salvar(Mockito.any(Produto.class))).willReturn(produtoSalvo); 
		String json = new ObjectMapper().writeValueAsString(produtoInput);
		
		BDDMockito.when(produtoDtoDisassembler
				.toDomainModel(Mockito.any(ProdutoInput.class))).thenReturn(produtoSalvo);
		
		BDDMockito.when(produtoDtoAssembler.toModel(Mockito.any(Produto.class))).thenReturn(produtoDto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_PRODUCT)
							  .contentType(MediaType.APPLICATION_JSON)
							  .accept(MediaType.APPLICATION_JSON)
							  .content(json);
		
		mvc.perform(request)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(jsonPath("id").isNotEmpty())
		.andExpect(jsonPath("ativo").value(produtoDto.getAtivo()))
		.andExpect(jsonPath("descricao").value(produtoDto.getDescricao()))
		.andExpect(jsonPath("preco").value(produtoDto.getPreco()))
		.andExpect(jsonPath("quantidade").value(produtoDto.getQuantidade()));
	}

	@Test	
	public void deveDeletarProduto() throws Exception {
		
		Produto produto = new Produto(); 
		produto.setId(11L);
		
		BDDMockito.given(service.buscarOuFalhar(Mockito.anyLong()))
					.willReturn(produto);
	

		MockHttpServletRequestBuilder request = 
		MockMvcRequestBuilders.delete(API_PRODUCT.concat("/" + 11L));
	
		mvc.perform(request)
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void deveAlterarProduto() throws Exception{
		
		Long id = 11L;
		String json = new ObjectMapper().writeValueAsString(criaNovoProdutoInput());

		Produto produto = criaNovoProduto();
		
		BDDMockito.given(service.buscarOuFalhar(id)).willReturn(produto);
		
		Produto produtoAlterado =  criaNovoProduto();
		produtoAlterado.setDescricao("Mesa branca");
		
		ProdutoDTO produtoAlteradoDTO =  criaNovoProdutoDTO();
		produtoAlteradoDTO.setDescricao("Mesa branca");
		
		BDDMockito.given(service.salvar(produto)).willReturn(produtoAlterado);
		
		BDDMockito.when(produtoDtoAssembler.toModel(Mockito.any(Produto.class))).thenReturn(produtoAlteradoDTO);
		
		MockHttpServletRequestBuilder request = 
				MockMvcRequestBuilders.put(API_PRODUCT.concat("/" + 11L))
									  .content(json)
									  .accept(MediaType.APPLICATION_JSON)
									  .contentType(MediaType.APPLICATION_JSON);

		mvc.perform(request)
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("id").isNotEmpty()) 
		   .andExpect(jsonPath("ativo").value(produtoAlterado.getAtivo()))
		   .andExpect(jsonPath("descricao").value(produtoAlterado.getDescricao()))
		   .andExpect(jsonPath("preco").value(produtoAlterado.getPreco()))
		   .andExpect(jsonPath("quantidade").value(produtoAlterado.getQuantidade()));
	}
	
	private ProdutoInput criaNovoProdutoInput() {
		
		return ProdutoInput.builder()
				 		 .descricao("Mesa 6 cadeiras - madeira maciça")
				 		 .nome("Mesa Kapensberg")
				 		 .ativo(true)
				 		 .preco(BigDecimal.valueOf(4390.35))
				 		 .quantidade(20).build();
	}

	private Produto criaNovoProduto() {
		
		return Produto.builder()
				.id(11L)
					  .descricao("Mesa 6 cadeiras - madeira")
					  .nome("Mesa Kapensberg")
					  .ativo(true)
					  .preco(BigDecimal.valueOf(4390.35))
					  .quantidade(20).build();
	}
	
	private ProdutoDTO criaNovoProdutoDTO() {
		
		return ProdutoDTO.builder()
						 .id(11L)
				 		 .descricao("Mesa 6 cadeiras - madeira maciça")
				 		 .nome("Mesa Kapensberg")
				 		 .ativo(true)
				 		 .preco(BigDecimal.valueOf(4390.35))
				 		 .quantidade(20).build();
	}

	
}
