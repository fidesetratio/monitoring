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

insert into search_category(category_name) values ('NORMAL');
insert into search_engine(query,label,category_id,active) values ('select reg_spaj from eka.mst_policy where rownum = 1','NORMAL SELECT',1,1);
