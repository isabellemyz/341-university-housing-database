--this is tested
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

--exec statement 
-- DECLARE @newStudentId int;
-- EXEC insertStudent  @newStudentId OUTPUT, 'Olivia', 'Martinez', 2, 0, 'olivia.martinez@example.com', '321-123-1234';
-- SELECT @newStudentId;



CREATE or ALTER PROCEDURE insertPreference
    @student_id int,
    @smoke bit,
    @music varchar(20),
    @space varchar(20),
    @sleep_time int,
    @living_style varchar(20),
    @roommate_count smallint
as
begin
insert into preference (student_id, smoke, music, space, sleep_time, living_style, roommate_count)
values (@student_id, @smoke, @music, @space, @sleep_time, @living_style, @roommate_count)
END; 


--exec statement
-- EXEC insertPreference  1015, false, 'pop', 'relaxing', 7, 'clean', '2';
-- SELECT *
-- FROM student s
-- INNER JOIN preference p ON s.student_id = p.student_id
-- WHERE s.student_id = 1015; 
