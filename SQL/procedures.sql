--this is tested
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

-- this is tested
-- procedure for updating room status (available or unavailable)
CREATE or ALTER PROCEDURE updateRoomStatus
	@building_name varchar(20),
	@room_number int,
	@availability varchar(20)
AS
BEGIN
UPDATE rooms
SET rooms.status = @availability
FROM rooms INNER JOIN buildings ON (rooms.building_id = buildings.building_id)
WHERE (
	buildings.name = @building_name AND
	rooms.room_number = @room_number
)
END;

-- example exec statement below
-- EXEC updateRoomStatus
--	@building_name = 'Taft',
--	@room_number = 100,
--	@availability = 'unavailable';


-- this is tested
-- procedure for adding a new amenity
CREATE OR ALTER PROCEDURE addAmenity
    @amenity_name varchar(100),
    @category varchar(100),
    @start_time datetime,
    @end_time datetime,
    @description varchar(50),
    @cost int,
    @building_name varchar(100),
    @amenity_count int
AS
BEGIN
    DECLARE @building_id int;
    DECLARE @amenity_id int;

    BEGIN TRANSACTION;

    INSERT INTO amenity (name, category, start_time, end_time, description, cost)
    VALUES (@amenity_name, @category, @start_time, @end_time, @description, @cost);

	SET @amenity_id = SCOPE_IDENTITY();

    COMMIT TRANSACTION;

    BEGIN TRANSACTION;

    SET @building_id = (SELECT building_id FROM buildings WHERE name = @building_name);

    INSERT INTO building_amenity (building_id, amenity_id, amenity_count)
    VALUES (@building_id, @amenity_id, @amenity_count);

    COMMIT TRANSACTION;

END

-- example exec statement below
-- EXEC addAmenity
--	@amenity_name = 'Water station',
--	@category = 'indoor',
--	@start_time = '2024-04-18 08:00:00.000',
--	@end_time = '2024-04-18 08:00:00.000',
--	@description = 'Water station for drinking',
--	@cost = 0,
--	@building_name = 'Taft',
--	@amenity_count = 3

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

-- Crystal's stored procedures:
-- creating a procedure to submit a maintenance request that is valid

-- on first submission, the request is 'submitted' and not yet 'in progress'
CREATE or ALTER PROCEDURE insertValidRequest
	@request_id int OUTPUT,
	@student_id as int,
	@staff_id as int,
	@building_id as int,
	@room_number as int,
	@amenity_id as int,
	@status as varchar(20),
	@date_submitted as date,
	@date_completed as date
AS
BEGIN
INSERT INTO maintenance_request (student_id, staff_id, building_id, 
room_number, amenity_id, status, date_submitted, date_completed)
VALUES(@student_id, @staff_id, @building_id, @room_number,
@amenity_id, @status, @date_submitted, @date_completed)
SELECT @request_id = SCOPE_IDENTITY();
END;

grant execute on insertValidRequest to dbuser;

-- This checks the insert properly functioned. It also provides more use for the use case of this for students
-- because they can make sure they aren't submitting duplicate maintenance requests for shared amenities and can contact staff assigned.

CREATE OR ALTER PROCEDURE GetRequestsAmenity
    @building_id INT,
    @amenity_id INT = NULL

AS
BEGIN
    SET NOCOUNT ON;

    SELECT MR.request_id,
           ISNULL(stud.first_name + ' ' + stud.last_name, 'N/A') AS student_name,
           ISNULL(staff.first_name + ' ' + staff.last_name, 'N/A') AS staff_name,
		   ISNULL(staff.staff_id, 'No staff has been assigned to this request yet') AS staff_id,
		   ISNULL(staff.email, 'N/A') AS staff_email,
           ISNULL(staff.phone_number, 'N/A') AS staff_phone,
           MR.status,
           MR.date_submitted
    FROM maintenance_request MR
    LEFT JOIN student stud ON MR.student_id = stud.student_id
	LEFT JOIN staff staff ON MR.staff_id = staff.staff_id
    WHERE MR.building_id = @building_id
      AND MR.status IN ('submitted', 'in progress')
      AND MR.amenity_id = @amenity_id 
   
END;


CREATE PROCEDURE InsertGroup
    @groupSize int,
    @coed bit,
    @groupId int OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    
    INSERT INTO groups (groupSize, coed)
    VALUES (@groupSize, @coed);

    SET @groupId = SCOPE_IDENTITY(); -- Retrieve the newly inserted group ID
END;

-- DECLARE @newGroupId int; -- Declare a variable to hold the group ID
-- EXEC InsertGroup @groupSize = 5, @coed = 1, @groupId = @newGroupId OUTPUT; -- Call the stored procedure
-- SELECT @newGroupId AS NewGroupID; -- Display the newly inserted group ID
