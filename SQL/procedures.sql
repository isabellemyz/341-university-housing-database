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

DECLARE @newStudentId int;
EXEC insertStudent  @newStudentId OUTPUT, 'Olivia', 'Martinez', 2, 0, 'olivia.martinez@example.com', '321-123-1234';
SELECT @newStudentId;
