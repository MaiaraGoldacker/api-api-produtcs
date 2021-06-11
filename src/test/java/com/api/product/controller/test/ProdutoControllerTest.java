package com.api.product.controller.test;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import com.api.product.assembler.ProdutoDTOAssembler;
import com.api.product.assembler.ProdutoDTODisassembler;
import com.api.product.controller.ProdutoController;
import com.api.product.dto.ProdutoDTO;
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

	@org.junit.jupiter.api.Test
	public void deveCriarProduto() throws Exception {
		
		ProdutoDTO dto = criaNovoProduto();
		
		Produto produtoSalvo = new Produto();
		
		produtoSalvo.setId(101L);
		produtoSalvo.setDescricao("Mesa 6 cadeiras - madeira maciça");
		produtoSalvo.setNome("Mesa Kapensberg");
		produtoSalvo.setPreco(BigDecimal.valueOf(4390.35));
		produtoSalvo.setQuantidade(20);		
				
		
		BDDMockito.given(service.salvar(Mockito.any(Produto.class))).willReturn(produtoSalvo); 
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_PRODUCT)
							  .contentType(MediaType.APPLICATION_JSON)
							  .accept(MediaType.APPLICATION_JSON)
							  .content(json);
		
		mvc.perform(request)
		.andExpect(MockMvcResultMatchers.status().isCreated()) ;
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
	
	private ProdutoDTO criaNovoProduto() {
		
		ProdutoDTO produtoDTO = new ProdutoDTO();
		produtoDTO.setDescricao("Mesa 6 cadeiras - madeira maciça");
		produtoDTO.setNome("Mesa Kapensberg");
		produtoDTO.setPreco(BigDecimal.valueOf(4390.35));
		produtoDTO.setQuantidade(20);
		
		return produtoDTO;
	}

}
