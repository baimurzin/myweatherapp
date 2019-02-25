----------------------------------------------
--  CITY_REGISTRY TABLE
----------------------------------------------
drop table if exists city_registry;
create table if not exists city_registry (
  city_id bigint not null primary key,
  city_name varchar2(120) not null,
  country varchar2(5) not null,
  lat double,
  lon double
);

create index cr_city_name_idx on city_registry(city_name);

----------------------------------------------
--  CITIES TABLE
----------------------------------------------
drop table if exists cities;
CREATE TABLE if not exists CITIES (
--   id bigint auto_increment primary key,
  city_id bigint not null,
  city_name varchar2(120) not null
);

create index cities_city_name_idx on CITIES(city_name);
create index cities_city_id_idx on CITIES(city_id);

