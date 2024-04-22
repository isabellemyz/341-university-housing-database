USE UniversityHousing;

-- procedure for updating room status (available or unavailable)
CREATE or ALTER PROCEDURE updateRoomStatus
	@building_id int,
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
--	@building_id = 1000,
--	@room_number = 100,
--	@availability = 'unavailable';

-- procedure for adding a new amenity
CREATE OR ALTER PROCEDURE addAmenity
    @amenity_id int output,
    @amenity_name varchar(100),
    @category varchar(100),
    @start_time datetime,
    @end_time datetime,
    @description varchar(50),
    @cost int
AS
BEGIN
    INSERT INTO amenity (name, category, start_time, end_time, description, cost)
    VALUES (@amenity_name, @category, @start_time, @end_time, @description, @cost);
    
    SET @amenity_id = SCOPE_IDENTITY();
END;

-- example exec statement below
-- EXEC addAmenity
--	@amenity_name = 'Water station',
--	@category = 'indoor',
--	@start_time = '2024-04-18 08:00:00.000',
--	@end_time = '2024-04-18 08:00:00.000',
--	@description = 'Water station for drinking',
--	@cost = 0

-- stored procedure for adding amenity to a building
CREATE OR ALTER PROCEDURE addAmenityToBuilding
	@building_id int,
	@amenity_id int,
	@amenity_count int
AS
BEGIN
	IF EXISTS (SELECT 1 FROM buildings WHERE building_id = @building_id)
	BEGIN
		IF EXISTS (SELECT 1 FROM amenity WHERE amenity_id = @amenity_id)
		BEGIN
			INSERT INTO building_amenity (building_id, amenity_id, amenity_count)
			VALUES (@building_id, @amenity_id, @amenity_count);
		END
		ELSE
		BEGIN
			RAISERROR('Amenity ID is not valid', 16, 1);
		END
	END
	ELSE
	BEGIN
		RAISERROR('Building ID is not valid', 16, 1);
	END
END;

-- stored procedure for viewing all students
CREATE or ALTER PROCEDURE viewAllStudents
AS
BEGIN
	SELECT * FROM student;
END;

-- stored procedure for viewing student groups
CREATE or ALTER PROCEDURE viewAllStudentGroups
AS
BEGIN
	SELECT *
    FROM groups INNER JOIN assignment
        ON groups.group_id = assignment.group_id
END;

-- stored procedure for deleting an amenity from a building
CREATE or ALTER PROCEDURE deleteAmenityFromBuilding
	@amenity_id int,
	@building_id int
AS
BEGIN
	IF EXISTS (SELECT 1 FROM building_amenity WHERE building_id = @building_id)
    BEGIN
        IF EXISTS (SELECT 1 FROM building_amenity WHERE amenity_id = @amenity_id)
        BEGIN
            DELETE FROM building_amenity
            WHERE building_id = @building_id
                AND amenity_id = @amenity_id;
        END
        ELSE
        BEGIN
            RAISERROR('The specified amenity ID is not valid', 16, 1);
        END
    END
    ELSE
    BEGIN
            RAISERROR('The specified building ID is not valid', 16, 1);
    END
END;

-- stored procedure to delete an amenity entirely
CREATE or ALTER PROCEDURE deleteAmenity
	@amenity_id int
AS
BEGIN
	IF EXISTS (SELECT 1 FROM building_amenity WHERE amenity_id = @amenity_id)
	BEGIN
		RAISERROR('Amenity still in use at a building', 16, 1);
	END
	ELSE
	BEGIN
		DELETE FROM amenity
		WHERE amenity_id = @amenity_id
	END
END;

-- stored procedure for getting building name given its ID
CREATE or ALTER PROCEDURE getBuildingNameFromID
	@building_name varchar(20) output,
	@building_id int
AS
BEGIN
	SELECT @building_name = name
	FROM buildings
	WHERE building_id = @building_id
END;

-- exec statement:
-- Use UniversityHousing
-- DECLARE @building_name varchar(20)
-- EXEC getBuildingNameFromID
--	@building_name OUTPUT,
--	@building_id = 1000

-- stored procedure to get amenity name given its ID
CREATE or ALTER PROCEDURE getAmenityNameFromID
	@amenity_name varchar(20) output,
	@amenity_id int
AS
BEGIN
	SELECT @amenity_name = name
	FROM amenity
	WHERE amenity_id = @amenity_id
END;
