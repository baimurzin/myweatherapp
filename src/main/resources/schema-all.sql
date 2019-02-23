drop table if exists city_registry;
create table if not exists city_registry (
  city_id bigint not null primary key,
  city_name varchar2(50) not null,
  country varchar2(5) not null,
  lat double,
  lon double
);

create index city_name_idx on city_registry(city_name);
