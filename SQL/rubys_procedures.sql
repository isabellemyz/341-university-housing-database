-- stored procedure for inserting a new student
CREATE or ALTER PROCEDURE insertStudent
  @student_id int output, 
  @first_name varchar(20),
  @last_name varchar(20),
  @group_id int = NULL,
  @year int,
  @is_ra bit,
  @email varchar(50),
  @phone_number varchar(20)
AS
BEGIN
INSERT INTO student (first_name, last_name, group_id, year, is_ra, email, phone_number)
VALUES (@first_name, @last_name, @group_id, @year, @is_ra, @email, @phone_number)
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
AS
BEGIN
INSERT INTO preference (student_id, smoke, music, space, sleep_time, living_style, roommate_count)
VALUES (@student_id, @smoke, @music, @space, @sleep_time, @living_style, @roommate_count)
END; 

--exec statement
-- EXEC insertPreference  1015, false, 'pop', 'relaxing', 7, 'clean', '2';
-- SELECT *
-- FROM student s
-- INNER JOIN preference p ON s.student_id = p.student_id
-- WHERE s.student_id = 1015; 

-- stored procedure for analyzing student preferences
CREATE PROCEDURE AnalyzeStudentPreferences
    @fixed_id INT
AS
BEGIN
SELECT TOP 10 p.student_id,     
CONCAT(
        COALESCE(s.first_name + ' ', ''),
        s.last_name
    ) AS full_name,
	s.phone_number, 
	s.email,
	s.year,
       (CASE WHEN p.smoke = fixed.smoke THEN 1 ELSE 0 END +
        CASE WHEN p.music = fixed.music THEN 1 ELSE 0 END +
        CASE WHEN p.space = fixed.space THEN 1 ELSE 0 END +
        CASE WHEN p.sleep_time = fixed.sleep_time THEN 1 ELSE 0 END +
        CASE WHEN p.living_style = fixed.living_style THEN 1 ELSE 0 END +
        CASE WHEN p.roommate_count = fixed.roommate_count THEN 1 ELSE 0 END) AS similarity_score
FROM preference p INNER JOIN student s ON p.student_id = s.student_id, (SELECT * FROM preference p WHERE p.student_id = @fixed_id) as fixed

WHERE p.student_id <> @fixed_id and s.group_id is null
ORDER BY similarity_score DESC; 
END; 


--test
-- EXEC AnalyzeStudentPreferences @fixed_id = 1002; -- Replace 1002 with the actual student ID

CREATE or ALTER PROCEDURE InsertGroup
    @groupId int OUTPUT, 
    @group_size int,
    @coed bit,

AS
BEGIN
    SET NOCOUNT ON;
    
    INSERT INTO groups (group_size, coed)
    VALUES (@group_size, @coed);

    SET @groupId = SCOPE_IDENTITY(); -- Retrieve the newly inserted group ID
END;

-- DECLARE @newGroupId int; -- Declare a variable to hold the group ID
-- EXEC InsertGroup @group_size = 5, @coed = 1, @groupId = @newGroupId OUTPUT; -- Call the stored procedure
-- SELECT @newGroupId AS NewGroupID; -- Display the newly inserted group ID

CREATE or ALTER PROCEDURE assignGroup
    @student_id int,
    @group_id int
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Check if the provided group_id exists in the groups table
    IF NOT EXISTS (SELECT 1 FROM groups WHERE group_id = @group_id)
    BEGIN
        PRINT 'Error: Group with the provided group_id does not exist.';
        RETURN; -- Exit the stored procedure
    END
    
    -- Update the group_id of the student
    UPDATE student
    SET group_id = @group_id
    WHERE student_id = @student_id;
END;

--exec
-- EXEC assignGroup @student_id = 1005, @group_id = 100;
-- SELECT * from student where student_id = 1005; 

--Students that has a group should be able to view available rooms, after selected a room by its building and room#, student can assign their group
-- to the room by inputting info into the stored procedure. After a room is being chosen by a student, it's should become 
-- occupied or unavailable. 
CREATE or ALTER PROCEDURE ViewAvailableRoom
    @group_id int
AS
BEGIN
    SET NOCOUNT ON;

    -- Declare variables
    DECLARE @groupSize int;

    -- Get the group size based on group_id
    SELECT @groupSize = groupSize
    FROM groups
    WHERE group_id = @group_id;

    -- Check if the group exists
    IF @groupSize IS NULL
    BEGIN
        PRINT 'Error: Group with the provided group_id does not exist.';
        RETURN;
    END;

    -- View available rooms
    SELECT r.room_number, r.building_id, r.floors, r.capacity, r.price, r.status
    FROM rooms r
    WHERE r.capacity = @groupSize
    AND r.status = 'available';
END;

--EXEC ViewAvailableRoom @group_id = 100;

-- stored procedure for getting group count and size
CREATE PROCEDURE getGroupCountAndSize
    @group_id INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT limit.group_count, g.groupSize
    FROM (
        SELECT COUNT(*) AS group_count, group_id
        FROM student
        WHERE group_id = @group_id
        GROUP BY group_id
    ) AS limit
    INNER JOIN groups g ON limit.group_id = g.group_id;
END;

--EXEC GetGroupCountAndSize @group_id = 101;
