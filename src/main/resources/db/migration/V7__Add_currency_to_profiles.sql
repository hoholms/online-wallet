alter table profiles
    add column currency varchar(3) default 'USD' not null check (length(currency) = 3);