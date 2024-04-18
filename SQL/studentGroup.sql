-- students, groups, preference
--haven't tested yet! 


--initialize student
CREATE TABLE student (
  student_id int identity(1000, 1),
  first_name varchar(20),
  last_name varchar(20),
  group_id int NULL REFERENCES group(group_id) ON DELETE SET NULL,
  year int,
  is_ra bit,
  email varchar(50),
  phone_number varchar(20),
  PRIMARY KEY (student_id), 
  CHECK (year BETWEEN 1 AND 5)
);

--initialize group
CREATE TABLE group (
  group_id int identity(100, 1) 
  groupSize int, 
  coed bit, 
  PRIMARY KEY (group_id)
);

--initialize preference
CREATE TABLE preference (
  student_id int,
  smoke bit,
  music varchar(20),
  space varchar(20),
  sleep_time int,
  living_style varchar(20),
  roommate_count smallint,
  PRIMARY KEY (student_id), 
  CHECK (music in ('hip hop', 'classical', 'pop', 'rock', 'jazz')), 
  CHECK (space in ('studying', 'socializing', 'relaxing')), 
  CHECK (living_style in ('messy', 'clean', 'lived-in')), 
  CHECK (roommate_count < 10), 
  CHECK (roommate_count > 0)
);


//sample data for demonstration/
INSERT INTO student (first_name, last_name, year, is_ra, email, phone_number)
VALUES
('Crystal', 'Zhu', 2, 0, 'cxz@example.com', '123-456-7890'),
('Jane', 'Smith', 2, 1, 'jane.smith@example.com', '987-654-3210'),
('Isabelle', 'Zhang', 3, 0, 'ixz@example.com', '555-555-5555'),
('Emily', 'Brown', 1, 0, 'emily.brown@example.com', '111-222-3333'),
('David', 'Taylor', 1, 0, 'david.taylor@example.com', '444-444-4444'),
('Alice', 'Johnson', 3, 0, 'alice.johnson@example.com', '111-111-1111'),
('Bob', 'Smith', 4, 0, 'bob.smith@example.com', '222-222-2222'),
('Charlie', 'Brown', 3, 1, 'charlie.brown@example.com', '333-333-3333'),
('Diana', 'Taylor', 4, 1, 'diana.taylor@example.com', '666-444-5555'),
('Eva', 'Lee', 3, 0, 'eva.lee@example.com', '555-666-7777'), 
('Frank', 'Adams', 1, 0, 'frank.adams@example.com', '666-666-6666'),
('Grace', 'Clark', 1, 0, 'grace.clark@example.com', '777-777-7777'),
('Henry', 'Evans', 1, 0, 'henry.evans@example.com', '888-888-8888'),
('Ivy', 'Garcia', 1, 0, 'ivy.garcia@example.com', '999-999-9999'),
('Jack', 'Harris', 1, 0, 'jack.harris@example.com', '000-000-0000');

INSERT INTO preference (student_id, smoke, music, space, sleep_time, living_style, roommate_count)
VALUES
(1000, 0, 'pop', 'socializing', 8, 'clean', '2'),
(1001, 1, 'rock', 'studying', 6, 'messy', '1'),
(1002, 0, 'jazz', 'socializing', 8, 'lived-in', '3'),
(1003, 0, 'classical', 'socializing', 7, 'clean', '2'),
(1004, 1, 'hip hop', 'socializing', 7, 'lived-in', '1'),
(1005, 0, 'rock', 'studying', 7, 'clean', '1'),
(1006, 0, 'pop', 'socializing', 8, 'lived-in', '2'),
(1007, 1, 'jazz', 'relaxing', 6, 'messy', '1'),
(1008, 0, 'classical', 'socializing', 7, 'lived-in', '2'),
(1009, 0, 'hip hop', 'studying', 7, 'clean', '1'),
(1010, 0, 'pop', 'socializing', 7, 'clean', '2'),
(1011, 0, 'rock', 'studying', 8, 'lived-in', '3'),
(1012, 1, 'jazz', 'socializing', 7, 'messy', '3'),
(1013, 0, 'classical', 'socializing', 7, 'clean', '2'),
(1014, 1, 'hip hop', 'relaxing', 6, 'messy', '2');

--insert group here

--procedure for insert student
CREATE or ALTER PROCEDURE insertStudent
  @student_id int output, 
  @first_name varchar(20),
  @last_name varchar(20),
  @year int,
  @is_ra bit,
  @email varchar(50),
  @phone_number varchar(20)
as
begin
insert into student (first_name, last_name, year, is_ra, email, phone_number)
values (@first_name, @last_name, @year, @is_ra, @email, @phone_number)
SET @student_id = SCOPE_IDENTITY(); --return student id, students are expected to remember it
END; 

--procedure for insert Group
CREATE or ALTER PROCEDURE insertGroups
  @group_id int output, 
  @groupSize int, 
  @coed bit
as
begin
insert into group (groupSize, coed)
values (@groupSize, @coed, @year)
SET @student_id = SCOPE_IDENTITY();
END; 


--procedure for preference 
CREATE or ALTER PROCEDURE insertPreference
    @student_id int,
    @smoke bit,
    @music varchar(20),
    @space varchar(20),
    @sleep_time int,
    @living_style varchar(20),
    @roommate_count smallint,
as
begin
insert into student (student_id, smoke, music, space, sleep_time, living_style, roommate_count)
values (@student_id, @smoke, @music, @space, @sleep_time, @living_style, @roommate_count)
END; 

