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

-- Trigger: As soon as a maintenance is set to complete, it inputs the completion date

CREATE TRIGGER update_date_completed
ON maintenance_request
AFTER UPDATE
AS
BEGIN
    -- Check if the 'status' column has been updated
    IF UPDATE(status)
    BEGIN
        -- Declare variables to store the old and new status values
        DECLARE @old_status VARCHAR(20), @new_status VARCHAR(20);

        -- Get the old and new values of 'status'
        SELECT @old_status = deleted.status, @new_status = inserted.status
        FROM deleted
        INNER JOIN inserted ON deleted.request_id = inserted.request_id;

        -- Check if the status is changed to 'completed'
        IF @old_status <> 'completed' AND @new_status = 'completed'
        BEGIN
            -- Update the date_completed to the current timestamp
            UPDATE maintenance_request
            SET date_completed = GETDATE()
            WHERE request_id IN (SELECT request_id FROM inserted);
        END
    END
END;

