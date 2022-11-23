insert into online_wallet.public.users (username, password, enabled)
values ('admin', 'admin', 'true');

insert into online_wallet.public.authorities (user_id, authority)
values (1, 'USER'),
       (1, 'ADMIN');

insert into online_wallet.public.profiles (balance, first_name, last_name, email, created_date, user_id,
                                           activation_code)
values (0, 'admin', 'admin', 'admin@admin.admin', now(), 1, null);