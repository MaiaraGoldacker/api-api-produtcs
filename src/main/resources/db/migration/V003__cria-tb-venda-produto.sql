create table produto_venda (
	id bigint not null auto_increment,
	venda_id bigint not null,
    produto_id bigint not null,
	quantidade bigint not null,
	valor_venda decimal(10,2) not null,
	primary key (id),
    constraint fk_venda_produtovenda foreign key (venda_id) references venda (id),
    constraint fk_produto_produtovenda foreign key (produto_id) references produto (id)
) engine=InnoDB default charset=utf8;