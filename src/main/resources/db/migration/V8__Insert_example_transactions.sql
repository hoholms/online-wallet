INSERT INTO transactions (profile_id, category_id, is_income, amount, message, transaction_date)
VALUES (1, 1, true, 100.00, 'Received salary', now() - interval '30 days'),
       (1, 2, false, 50.00, 'Bought groceries', now() - interval '25 days'),
       (1, 3, true, 200.00, 'Received bonus', now() - interval '20 days'),
       (1, 4, false, 25.00, 'Paid for transportation', now() - interval '15 days'),
       (1, 5, true, 50.00, 'Received gift', now() - interval '10 days'),
       (1, 6, false, 10.00, 'Paid for monthly subscription', now() - interval '5 days'),
       (1, 7, false, 150.00, 'Paid for online course', now()),
       (1, 8, false, 75.00, 'Bought new clothes', now() + interval '5 days'),
       (1, 9, false, 20.00, 'Went to the movies', now() + interval '10 days'),
       (1, 10, false, 30.00, 'Paid for bus fare', now() + interval '15 days');
