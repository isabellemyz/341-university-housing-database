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
);



CREATE TABLE group (
  group_id int identity(0, 1) 
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
  CHECK (roommate_count < 10)
);

INSERT INTO student (first_name, last_name, year, is_ra, email, phone_number)
VALUES
('Crystal', 'Zhu', 2, false, 'cxz@example.com', '123-456-7890'),
('Jane', 'Smith', 2, true, 'jane.smith@example.com', '987-654-3210'),
('Isabelle', 'Zhang', 3, false, 'ixz@example.com', '555-555-5555'),
('Emily', 'Brown', 1, false, 'emily.brown@example.com', '111-222-3333'),
('David', 'Taylor', 1, false, 'david.taylor@example.com', '444-444-4444');

INSERT INTO preference (student_id, smoke, music, space, sleep_time, living_style, roommate_count)
VALUES
(1000, false, 'pop', 'social', 8, 'clean', '2'),
(1001, true, 'rock', 'study', 6, 'messy', '1'),
(1002, false, 'jazz', 'social', 8, 'lived-in', '3'),
(1003, false, 'classical', 'social', 7, 'clean', '2'),
(1004, true, 'hip hop', 'social', 7, 'lived-in', '1');

