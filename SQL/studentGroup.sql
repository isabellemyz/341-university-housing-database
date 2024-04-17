-- students, groups, preference

CREATE TABLE student (
  student_id int identity(1000, 1),
  first_name varchar(20),
  last_name varchar(20),
  group_id int NULL REFERENCES group(group_id) ON DELETE SET NULL,
  year int,
  is_ra boolean,
  email varchar(50),
  phone_number varchar(20),
  PRIMARY KEY (student_id), 
  CHECK (year BETWEEN 1 AND 5)
);

CREATE TABLE group (
  group_id int identity(100, 1) 
  size int, 
  coed boolean, 
  PRIMARY KEY (group_id)
);

CREATE TABLE preference (
  student_id int,
  smoke boolean,
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
('Crystal', 'Zhu', 2, false, 'cxz@example.com', '123-456-7890'),
('Jane', 'Smith', 2, true, 'jane.smith@example.com', '987-654-3210'),
('Isabelle', 'Zhang', 3, false, 'ixz@example.com', '555-555-5555'),
('Emily', 'Brown', 1, false, 'emily.brown@example.com', '111-222-3333'),
('David', 'Taylor', 1, false, 'david.taylor@example.com', '444-444-4444'),
('Alice', 'Johnson', 3, false, 'alice.johnson@example.com', '111-111-1111'),
('Bob', 'Smith', 4, false, 'bob.smith@example.com', '222-222-2222'),
('Charlie', 'Brown', 3, true, 'charlie.brown@example.com', '333-333-3333'),
('Diana', 'Taylor', 4, true, 'diana.taylor@example.com', '666-444-5555'),
('Eva', 'Lee', 3, false, 'eva.lee@example.com', '555-666-7777'), 
('Frank', 'Adams', 1, false, 'frank.adams@example.com', '666-666-6666'),
('Grace', 'Clark', 1, false, 'grace.clark@example.com', '777-777-7777'),
('Henry', 'Evans', 1, false, 'henry.evans@example.com', '888-888-8888'),
('Ivy', 'Garcia', 1, false, 'ivy.garcia@example.com', '999-999-9999'),
('Jack', 'Harris', 1, false, 'jack.harris@example.com', '000-000-0000');

INSERT INTO preference (student_id, smoke, music, space, sleep_time, living_style, roommate_count)
VALUES
(1000, false, 'pop', 'social', 8, 'clean', '2'),
(1001, true, 'rock', 'study', 6, 'messy', '1'),
(1002, false, 'jazz', 'social', 8, 'lived-in', '3'),
(1003, false, 'classical', 'social', 7, 'clean', '2'),
(1004, true, 'hip hop', 'social', 7, 'lived-in', '1'),
(1005, false, 'rock', 'study', 7, 'clean', '1'),
(1006, false, 'pop', 'social', 8, 'lived-in', '2'),
(1007, true, 'jazz', 'study', 6, 'messy', '1'),
(1008, false, 'classical', 'social', 7, 'lived-in', '2'),
(1009, false, 'hip hop', 'study', 7, 'clean', '1'),
(1010, false, 'pop', 'social', 7, 'clean', '2'),
(1011, false, 'rock', 'study', 8, 'lived-in', '1'),
(1012, true, 'jazz', 'social', 7, 'messy', '3'),
(1013, false, 'classical', 'social', 7, 'clean', '2'),
(1014, true, 'hip hop', 'social', 6, 'messy', '1');

CREATE or ALTER PROCEDURE insertStudent
  @student_id int output, 
  @first_name varchar(20),
  @last_name varchar(20),
  @year int,
  @is_ra boolean,
  @email varchar(50),
  @phone_number varchar(20),
as
begin
insert into student (first_name, last_name, year, is_ra, email, phone_number)
values (@first_name, @last_name, @year, @is_ra, @email, @phone_number)
SET @student_id = SCOPE_IDENTITY();
END

CREATE or ALTER PROCEDURE insertStudent
  @student_id int output, 
  @first_name varchar(20),
  @last_name varchar(20),
  @year int,
  @is_ra boolean,
  @email varchar(50),
  @phone_number varchar(20),
as
begin
insert into student (first_name, last_name, year, is_ra, email, phone_number)
values (@first_name, @last_name, @year, @is_ra, @email, @phone_number)
SET @student_id = SCOPE_IDENTITY();
END

