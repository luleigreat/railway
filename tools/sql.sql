
create table t_user_info(
  username varchar(100),
  password varchar(100),
  password_salt varchar(100),
  section_name varchar(100),
  constraint pk_users primary key(username)
) charset=utf8 ENGINE=InnoDB;;

create table t_admin_info(
  username varchar(100),
  password varchar(100),
  password_salt varchar(100),
  constraint pk_users2 primary key(username)
) charset=utf8 ENGINE=InnoDB;;
