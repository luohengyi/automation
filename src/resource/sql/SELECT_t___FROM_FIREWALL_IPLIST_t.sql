create table IPLIST
(
    ID     int generated always as identity constraint IPLIST primary key,
    IP     VARCHAR(100),
    ADDRID INTEGER default 0 not null
)