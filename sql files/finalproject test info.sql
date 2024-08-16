insert into type (name) values ('Long Answer');
select * from type;

insert into answer (typeid, text) values (1, 'The quick brown fox jumps over the lazy dog')
select * from answer;

select text from answer where aid = 1;