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


INSERT INTO student (first_name, last_name, year, is_ra, email, phone_number)
VALUES
('Crystal', 'Zhu', 2, 0, 'cxz@example.com', '123-456-7890'),
('Jane', 'Smith', 2, 1, 'jane.smith@example.com', '987-654-3210'),
('Isabelle', 'Zhang', 3, 0, 'ixz@example.com', '555-555-5555'),
('Emily', 'Brown', 1, 0, 'emily.brown@example.com', '111-222-3333'),
('David', 'Taylor', 1, 0, 'david.taylor@example.com', '444-444-4444'),
('Alice', 'Johnson', 3, 0, 'alice.johnson@example.com', '111-111-1111'),
('Bob', 'Smith', 4, 0, 'bob.smith@example.com', '222-222-2222'),
('Charlie', 'Brown', 3, 1, 'charlie.brown@example.com', '333-333-3333'),
('Diana', 'Taylor', 4, 1, 'diana.taylor@example.com', '666-444-5555'),
('Eva', 'Lee', 3, 0, 'eva.lee@example.com', '555-666-7777'), 
('Frank', 'Adams', 1, 0, 'frank.adams@example.com', '666-666-6666'),
('Grace', 'Clark', 1, 0, 'grace.clark@example.com', '777-777-7777'),
('Henry', 'Evans', 1, 0, 'henry.evans@example.com', '888-888-8888'),
('Ivy', 'Garcia', 1, 0, 'ivy.garcia@example.com', '999-999-9999'),
('Jack', 'Harris', 1, 0, 'jack.harris@example.com', '000-000-0000');

INSERT INTO preference (student_id, smoke, music, space, sleep_time, living_style, roommate_count)
VALUES
(1000, 0, 'pop', 'socializing', 8, 'clean', '2'),
(1001, 1, 'rock', 'studying', 6, 'messy', '1'),
(1002, 0, 'jazz', 'socializing', 8, 'lived-in', '3'),
(1003, 0, 'classical', 'socializing', 7, 'clean', '2'),
(1004, 1, 'hip hop', 'socializing', 7, 'lived-in', '1'),
(1005, 0, 'rock', 'studying', 7, 'clean', '1'),
(1006, 0, 'pop', 'socializing', 8, 'lived-in', '2'),
(1007, 1, 'jazz', 'relaxing', 6, 'messy', '1'),
(1008, 0, 'classical', 'socializing', 7, 'lived-in', '2'),
(1009, 0, 'hip hop', 'studying', 7, 'clean', '1'),
(1010, 0, 'pop', 'socializing', 7, 'clean', '2'),
(1011, 0, 'rock', 'studying', 8, 'lived-in', '3'),
(1012, 1, 'jazz', 'socializing', 7, 'messy', '3'),
(1013, 0, 'classical', 'socializing', 7, 'clean', '2'),
(1014, 1, 'hip hop', 'relaxing', 6, 'messy', '2');

-- populating data for the buildings table
INSERT INTO buildings (name, num_rooms, num_floors) VALUES
('Taft', 6, 4),
('Taplin', 6, 4),
('Staley', 10, 6),
('Village House 3A', 7, 4);
