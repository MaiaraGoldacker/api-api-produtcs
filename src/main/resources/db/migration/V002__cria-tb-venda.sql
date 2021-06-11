create table venda (
	id bigint not null auto_increment,
	data_venda datetime,
	forma_pagamento bigint not null,
	primary key (id)
) engine=InnoDB default charset=utf8;