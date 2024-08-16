--Types
--Open Ended
--Single Selection
--Multiple choice

--Subjects
--Math
--History
--Science
--Geography

--Difficuly
--Easy
--Moderate
--Hard

TRUNCATE TABLE type, question, answer, difficulty,question_exam, question_answer RESTART IDENTITY
insert into Type (type) values ('Open Ended'), ('Single Selection'), ('Multiple choice');
select * from type;

insert into Subject (subject) values ('Math'), ('History'), ('Science'), ('Geography');
select * from subject;

--Geography
--What is the name of the biggest technology company in South Korea? Difficulty.Easy
-- What is the name of the largest desert in the world? Difficulty.Easy
--What is the name of the current president of the United States? Difficulty.Easy

-- What is the capital of France? Paris Difficulty.Easy
-- What is the name of the largest ocean in the world? Pacific Ocean Difficulty.Easy
-- What is the name of the highest mountain in the world? Mount Everest, Nepal Difficulty.Moderate
-- What is the name of the largest country in the world by area? Russia Difficulty.Moderate
-- What is the name of the largest country in the world by population? India Difficulty.Moderate

select * from (question natural join subject) natural join type
where subject = 'Geography'

insert into Question (text, typeid, sid) values 
('What is the name of the biggest technology company in South Korea?', 3, 4)
,('What is the name of the largest desert in the world?', 3, 4)
,('What is the name of the current president of the United States?', 3, 4);

--insert into difficulty (qid, difficulty) values
--(1, 'Easy'),
--(2, 'Easy')


insert into Question (text, typeid, sid) values 
('what is the capital of France?', 1, 4)
,(' What is the name of the largest ocean in the world?', 1, 4)
,('What is the name of the highest mountain in the world?', 1, 4)
,('What is the name of the largest country in the world by area?', 1, 4)
,('What is the name of the largest country in the world by population?', 1, 4);``

--History
insert into question(text,typeid,sid) values ('What year did the Titanic sink in the Atlantic Ocean on 15 April, on its maiden voyage from Southampton?',3,2);
-- diffuclty - M
-- Answer 1 - "1908",false
-- Answer 2 - "1912",true
-- Answer 3 - "1914",false
-- Answer 4 - "1920",false
insert into question(text,typeid,sid) values ('Who was Prime Minister of Great Britain from 1841 to 1846?',3,2);
-- diffuclty - M
-- Answer 1 - "William Gladstone",false
-- Answer 2 - "Benjamin Disraeli",false
-- Answer 3 - "Robert Peel",true
-- Answer 4 - "Lord Palmerston",false
insert into question(text,typeid,sid) values ('Who was the first person in space',3,2);
-- diffuclty - E
-- Answer 1 - "Yuri Gagarin",false
-- Answer 2 - "Neil Armstrong",false
-- Answer 3 - "John Glenn",false
-- Answer 4 - "Alan Shepard",false
insert into question(text,typeid,sid) values ('What is the name of the first human to fly',1,2);
-- diffuclty - E
-- Answer 1 - "Orville Wright, United States" - Easy
insert into question(text,typeid,sid) values ('What is the name of the first man on the moon?',1,2);
-- diffuclty - E
-- Answer 1 - "Neil Armstrong, United States" - Easy
insert into question(text,typeid,sid) values ('Which metal was discovered by Hans Christian Oersted in 1825?',3,2);
-- diffuclty - H
-- Answer 1 - "Copper",false
-- Answer 2 - "Zinc",false
-- Answer 3 - "Nickel",false
-- Answer 4 - "Aluminium",true

--Math
insert into question(text,typeid,sid) values ('What is the equation for the area of a circle?',1,1);
-- diffuclty - M
-- Answer 1 - "A = πr²" - Moderate
insert into question(text,typeid,sid) values ('What is the equation for the volume of a sphere?',1,1);
-- diffuclty - M
-- Answer 1 - "V = 4/3πr³" - Moderate
insert into question(text,typeid,sid) values ('What is the equation for the slope of a line?',1,1);
-- diffuclty - M
-- Answer 1 - "m = (y₂ - y₁)/(x₂ - x₁)" - Moderate
insert into question(text,typeid,sid) values ('What is the equation for the Pythagorean Theorem?',1,1);
-- diffuclty - M
-- Answer 1 - "a² + b² = c²" - Moderate
insert into question(text,typeid,sid) values ('Which of the following are prime numbers?',3,1);
-- diffuclty - E
-- Answer 1 - "2",true
-- Answer 2 - "1",false
-- Answer 3 - "21",false
-- Answer 4 - "4",false
-- Answer 5 - "1",false





