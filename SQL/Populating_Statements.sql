USE UniversityHousing;

-- Populating data for the staff table
INSERT INTO staff (first_name, last_name, position, status, email, phone_number) VALUES
('John', 'Doe', 'supervisor', 'active', 'john.doe@example.com', '123-456-7890'),
('Jane', 'Smith', 'plumber', 'active', 'jane.smith@example.com', '234-567-8901'),
('Michael', 'Johnson', 'electrician', 'active', 'michael.johnson@example.com', '345-678-9012'),
('Emily', 'Williams', 'custodian', 'active', 'emily.williams@example.com', '456-789-0123'),
('David', 'Brown', 'technician', 'inactive', 'david.brown@example.com', '567-890-1234'),
('Sarah', 'Jones', 'supervisor', 'active', 'sarah.jones@example.com', '678-901-2345'),
('Christopher', 'Davis', 'plumber', 'active', 'christopher.davis@example.com', '789-012-3456'),
('Amanda', 'Miller', 'electrician', 'inactive', 'amanda.miller@example.com', '890-123-4567'),
('James', 'Wilson', 'custodian', 'active', 'james.wilson@example.com', '901-234-5678'),
('Jessica', 'Taylor', 'plumber', 'active', 'jessica.taylor@example.com', '012-345-6789'),
('Robert', 'Martinez', 'custodian', 'active', 'robert.martinez@example.com', '123-456-7890'),
('Jennifer', 'Anderson', 'plumber', 'inactive', 'jennifer.anderson@example.com', '234-567-8901'),
('Matthew', 'Thomas', 'electrician', 'active', 'matthew.thomas@example.com', '345-678-9012'),
('Laura', 'Hernandez', 'custodian', 'active', 'laura.hernandez@example.com', '456-789-0123'),
('Daniel', 'Young', 'technician', 'active', 'daniel.young@example.com', '567-890-1234');

-- Populating data for the amenity table

INSERT INTO amenity (name, category, start_time, end_time, description, cost) VALUES
('Swimming Pool', 'outdoor', '2024-04-18 08:00:00', '2024-04-18 20:00:00', 'Swimming pool; deepest 12 feet', 10),
('Gym', 'indoor', '2024-04-18 05:00:00', '2024-04-18 22:00:00', 'Fully equipped gym with weights', 30),
('Tennis Court', 'outdoor', '2024-04-18 08:00:00', '2024-04-18 20:00:00', 'Outdoor tennis court', 0),
('Sauna', 'indoor', '2024-04-18 10:00:00', '2024-04-18 20:00:00', 'Indoor sauna', 20),
('Basketball Court', 'outdoor', '2024-04-18 08:00:00', '2024-04-18 20:00:00', 'Outdoor basketball court', 0),
('Spa', 'indoor', '2024-04-18 12:00:00', '2024-04-18 18:00:00', 'Indoor spa', 25),
('Yoga Studio', 'indoor', '2024-04-18 07:00:00', '2024-04-18 19:00:00', 'Indoor yoga studio', 10),
('Mini Golf', 'outdoor', '2024-04-18 09:00:00', '2024-04-18 21:00:00', 'Outdoor mini golf course', 8),
('Squash Court', 'indoor', '2024-04-18 08:00:00', '2024-04-18 20:00:00', 'Indoor squash court', 12),
('Volleyball Court', 'outdoor', '2024-04-18 10:00:00', '2024-04-18 18:00:00', 'Outdoor volleyball court', 7);
-- add more amenities
