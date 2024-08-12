create or replace function set_creation_date()
   returns TRIGGER 
   language 'plpgsql'
  as
$set_creation_date$
	
begin
	update question
	set creationDate = CURRENT_DATE;
	return NEW;
end;
$set_creation_date$;

create trigger creationdate_insert_trig
after insert 
on question
for each row 
execute procedure set_creation_date();