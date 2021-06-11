create table produto (
	id bigint not null auto_increment,
	nome varchar(80) not null,
	descricao varchar(400) not null,
	preco decimal(10,2) not null,
	ativo tinyint(1) not null,
	quantidade decimal(10) not null,
	primary key (id)
) engine=InnoDB default charset=utf8;
