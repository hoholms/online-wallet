create or replace function calc_balance(current_profile bigint)
    returns numeric(19, 2)
    language plpgsql
    immutable as
$$
begin
    return
            coalesce((select sum(amount)
                      from transactions
                      where profile_id = current_profile
                        and is_income = true
                        and transaction_date <= CURRENT_DATE), 0)
            -
            coalesce((select sum(amount)
                      from transactions
                      where profile_id = current_profile
                        and is_income = false
                        and transaction_date <= CURRENT_DATE), 0);
end;
$$;

alter table profiles
    alter column balance set default 0;