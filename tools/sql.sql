drop table if exists "t_user_info";
drop table if exists "t_admin_info";
drop table if exists "t_type_info";
drop table if exists "t_table_info";
drop table if exists "t_upload_info";

create table t_user_info(
  username varchar(100),
  password varchar(100),
  section_name varchar(100),
  constraint pk_users primary key(username)
) charset=utf8 ENGINE=InnoDB;

create table t_admin_info(
  username varchar(100),
  password varchar(100),
  constraint pk_users2 primary key(username)
) charset=utf8 ENGINE=InnoDB;

create table t_type_info(
  id int(4),
  type_name varchar(100),
  constraint pk_type_info primary key(id)
) charset=utf8 ENGINE=InnoDB;

create table t_table_info(
  id int(4),
  table_name varchar(100),
  table_type int(4),
  template_path varchar(100),
  constraint pk_table_name primary key(id)
) charset=utf8 ENGINE=InnoDB;

create table t_upload_info(
  user_name varchar(100),
  year varchar(100),
  table_id int(4),
  uploaded int(4),
  constraint pk_upload_info primary key(user_name,year)
) charset=utf8 ENGINE=InnoDB;

insert into t_type_info values(1,"教育部统计报表");
insert into t_type_info values(2,"铁总数据预统计");
insert into t_type_info values(3,"铁总职教统计表");

insert into t_table_info values(1,"企业人力资源基本情况统计表",1,"");
insert into t_table_info values(2,"职工继续教育相关情况统计表",1,"");
insert into t_table_info values(3,"岗位技能达标活动年度汇总表",2,"");
insert into t_table_info values(4,"岗位培训年度汇总表",2,"");
insert into t_table_info values(5,"高铁岗位培训年度汇总表",2,"");
insert into t_table_info values(6,"（总公司报表1）铁路操作技能人员岗位培训情况统计表",3,"");
insert into t_table_info values(7,"（总公司报表2）高速铁路主要行车工种岗位培训情况统计表",3,"");
insert into t_table_info values(8,"（总公司报表3）铁路操作技能人员学历教育情况统计表",3,"");
insert into t_table_info values(9,"（总公司报表4）铁路局培训能力情况统计表（铁路局汇总）",3,"");
insert into t_table_info values(10,"（总公司报表5）铁路局、培训基地及站段职教工作人员情况统计表",3,"");
insert into t_table_info values(11,"（总公司报表6）铁路局职教经费情况统计表",3,"");