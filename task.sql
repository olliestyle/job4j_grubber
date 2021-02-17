-- 1. В одном запросе получить
-- - имена всех person, которые не состоят в компании с id = 5;
-- - название компании для каждого человека.
select person.name as person_name, company.name as company_name from person join company on person.company_id = company.id where person.company_id != 5;

-- 2. Необходимо выбрать название компании с максимальным количеством человек + количество человек в этой компании.
select c.name as company_name, count(c.name) as employee_count from person p join company c on p.company_id = c.id group by c.name order by employee_count desc limit 1;