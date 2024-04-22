-- Triggers for University Housing

-- Trigger One: This "resets" all maintenance request assigned to a certain employee
CREATE TRIGGER request_reset
ON staff
AFTER UPDATE
AS
BEGIN
    -- Check if the 'status' column has been updated
    IF UPDATE(status)
    BEGIN
        -- Declare variable to store the staff ID
        DECLARE @staff_id INT;

        -- Get the updated status and staff ID
        SELECT @staff_id = staff_id
        FROM inserted;

        -- Check if the status is changed to inactive
        IF EXISTS (SELECT 1 FROM inserted WHERE status = 'inactive')
        BEGIN
            -- Reset the maintenance requests assigned to the inactive staff
            UPDATE maintenance_request
            SET staff_id = NULL,
                status = 'submitted'
            WHERE staff_id = @staff_id and status = 'in progress';
        END
    END
END;

