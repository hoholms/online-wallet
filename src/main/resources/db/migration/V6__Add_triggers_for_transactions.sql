create or replace function balance_trigger_func()
    returns trigger
    language plpgsql as
$$
begin
    update profiles
    set balance = calc_balance(new.profile_id)
    where id = new.profile_id;
    return new;
end;
$$;

create trigger balance_insert_trigger
    after insert
    on transactions
    for each row
execute procedure balance_trigger_func();

create trigger balance_update_trigger
    after update
    on transactions
    for each row
execute procedure balance_trigger_func();

create trigger balance_delete_trigger
    after delete
    on transactions
    for each row
execute procedure balance_trigger_func();