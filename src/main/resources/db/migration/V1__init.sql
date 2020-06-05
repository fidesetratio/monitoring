DROP TABLE IF EXISTS `search_category`;

CREATE TABLE `search_category` (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  category_name varchar(200) not null default '',
  PRIMARY KEY (`id`)
) ENGINE=INNODB;


CREATE TABLE `search_engine` (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  query text,
  category_id int not null default 0,
  label varchar(200) not null default '',
  active int not null default 0,
  PRIMARY KEY (`id`)
) ENGINE=INNODB;

create table column_group(
   cgid int not null auto_increment,
   group_name varchar(200) not null default '',
   primary key(cgid)
)ENGINE=INNODB;

create table column_variable(
 cid int not null auto_increment,
 cgid int not null default 0,
 variable_name varchar(200) not null default '',
 query text,
 column_name varchar(200) not null default '',
 flag_active int not null default 1,
 primary key(cid)
 ) ENGINE=INNODB;
 

create table product_searching_category(
	cat_id int not null auto_increment,
	category_name varchar(200) not null default '',
	category_description varchar(300) not null default '',
	primary key(cat_id)
) ENGINE=INNODB;

create table product_searching(
	id int not null auto_increment,
	lsbs_id int not null default 0,
	lsdbs_number int not null default 0,
	cat_id int not null default 0,
	primary key(id)
) ENGINE=INNODB;


insert into search_category(category_name) values ('NORMAL');
insert into search_engine(query,label,category_id,active) values ('select reg_spaj from eka.mst_policy where rownum = 1','NORMAL SELECT',1,1);

insert product_searching_category(category_name, category_description) values('SMILE EXTRA','SMILE EXTRA');