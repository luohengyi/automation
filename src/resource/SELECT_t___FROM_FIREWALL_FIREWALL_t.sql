create table FIREWALL
(
    ID       int generated always as identity constraint FIREWALL primary key,
    NAME     VARCHAR(50) default 'NULL' not null,
    IP       VARCHAR(20),
    PORT     INTEGER     default 0,
    USERNAME VARCHAR(255),
    PASSWORD VARCHAR(255)
)