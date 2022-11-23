create
    extension if not exists pgcrypto;

update online_wallet.public.users
set password = crypt(online_wallet.public.users.password, gen_salt('bf'));