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
