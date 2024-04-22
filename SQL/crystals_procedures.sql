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

CREATE OR ALTER PROCEDURE getRequestsAmenity
    @building_id INT,
    @amenity_id INT = NULL

AS
BEGIN
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

CREATE OR ALTER PROCEDURE getSubmittedRequests
AS
BEGIN
    SELECT MR.request_id,
           ISNULL(S.first_name + ' ' + S.last_name, 'N/A') AS student_name,
           ISNULL(ST.first_name + ' ' + ST.last_name, 'N/A') AS staff_name,
		   MR.building_id,
		   ISNULL(MR.room_number, 'N/A') AS room_number,
		   ISNULL(MR.amenity_id, 'N/A') AS amenity_id,
           MR.status,
           MR.date_submitted
    FROM maintenance_request MR
    LEFT JOIN student S ON MR.student_id = S.student_id
    LEFT JOIN staff ST ON MR.staff_id = ST.staff_id
    WHERE MR.status = 'submitted';
END;


CREATE OR ALTER PROCEDURE assignStaffToRequest
    @request_id INT,
    @staff_id INT
AS
BEGIN
    SET NOCOUNT ON;

    -- Check if the request is in the available requests list (status = 'submitted')
    IF EXISTS (SELECT 1 FROM maintenance_request WHERE request_id = @request_id AND status = 'submitted')
    BEGIN
        -- Update the maintenance request to assign the staff and change status to 'in progress'
        UPDATE maintenance_request
        SET staff_id = @staff_id,
            status = 'in progress'
        WHERE request_id = @request_id;
    END
    ELSE
    BEGIN
        -- Request is not available (status is not 'submitted')
        RAISERROR('The specified request is not available.', 16, 1);
    END
END;

CREATE OR ALTER PROCEDURE changeRequestStatusToCompleted
    @request_id INT
AS
BEGIN
    -- Check if the request exists and its current status is 'in progress'
    IF EXISTS (SELECT 1 FROM maintenance_request WHERE request_id = @request_id AND status = 'in progress' )
    BEGIN
        -- Update the status of the maintenance request to 'completed'
        UPDATE maintenance_request
        SET status = 'completed'
		-- I have a trigger that changes date so that is unnecessary
        WHERE request_id = @request_id;
    END
    ELSE
    BEGIN
        -- Request does not exist or its status is not 'in progress'
        RAISERROR('The specified request either does not exist or its status is not ''in progress''.', 16, 1);
    END
END;

CREATE OR ALTER PROCEDURE getStaffMaintenanceRequests
    @staff_id INT
AS
BEGIN
    SELECT MR.request_id,
           ISNULL(S.first_name + ' ' + S.last_name, 'N/A') AS student_name,
           ISNULL(MR.building_id, 'N/A') AS building_id,
           ISNULL(MR.room_number, 'N/A') AS room_number,
           ISNULL(MR.amenity_id, 'N/A') AS amenity_id,
           MR.status,
           MR.date_submitted,
		   MR.date_completed
    FROM maintenance_request MR
    LEFT JOIN student S ON MR.student_id = S.student_id
    WHERE MR.staff_id = @staff_id;
END;
