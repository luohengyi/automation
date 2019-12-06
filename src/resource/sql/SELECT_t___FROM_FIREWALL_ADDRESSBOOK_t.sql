create table ADDRESSBOOK
(
	id int generated always as identity
		constraint ADDRESSBOOK
			primary key,
	name varchar(255),
	firewallid int default 0 not null
)
