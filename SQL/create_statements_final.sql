USE UniversityHousing;

--initialize groups
CREATE TABLE groups (
  group_id int identity(100, 1) ,
  groupSize int, 
  coed bit, -- here 1 represents 'yes' to coed and 0 is 'no'
  PRIMARY KEY (group_id)
);

-- initialize student
CREATE TABLE student (
  student_id int identity(1000, 1),
  first_name varchar(20),
  last_name varchar(20),
  group_id int NULL REFERENCES groups (group_id) ON DELETE SET NULL,
  year int,
  is_ra bit,
  email varchar(50),
  phone_number varchar(20),
  PRIMARY KEY (student_id), 
  CHECK (year BETWEEN 1 AND 5)
);

--initialize preference
CREATE TABLE preference (
  student_id int,
  smoke bit, -- here 1 represents 'yes' to smoking and 0 is 'no'
  music varchar(20),
  space varchar(20),
  sleep_time int,
  living_style varchar(20),
  roommate_count smallint,
  PRIMARY KEY (student_id), 
  CHECK (music in ('hip hop', 'classical', 'pop', 'rock', 'jazz')), 
  CHECK (space in ('studying', 'socializing', 'relaxing')), 
  CHECK (living_style in ('messy', 'clean', 'lived-in')), 
  CHECK (roommate_count < 10), 
  CHECK (roommate_count > 0)
);

-- initialize staff
CREATE TABLE staff (
  staff_id int identity(3000, 1),
  first_name varchar(20),
  last_name varchar(20),
  position varchar(20) ,
  status varchar(20),
  email varchar(50),
  phone_number varchar(20),
  PRIMARY KEY (staff_id),
  CHECK (position in ('supervisor', 'plumber', 'electrician', 'custodian', 'technician')),
  CHECK (status in ('active', 'inactive'))
);


-- initialize amenity
CREATE TABLE amenity (
  amenity_id int identity (0,1),
  name varchar(20),
  category varchar(20),
  start_time datetime,
  end_time datetime,
  description varchar(50),
  cost int,
  PRIMARY KEY (amenity_id),
  CHECK (category in ('indoor', 'outdoor'))
);

-- initialize buildings
CREATE TABLE buildings (
	building_id int identity(1000, 1),
	name varchar(20),
	num_rooms int,
	num_floors int,
	PRIMARY KEY (building_id)
);

-- initialize rooms
CREATE TABLE rooms (
	room_number int,
	building_id int,
	floors int,
	capacity int,
	price int,
	status varchar(20),
	PRIMARY KEY (room_number, building_id),
	FOREIGN KEY (building_id) REFERENCES buildings (building_id),
	CHECK (status in ('available', 'unavailable'))
);

-- initialize building_amenity
CREATE TABLE building_amenity (
	building_id int,
	amenity_id int,
	amenity_count int,
	PRIMARY KEY (building_id, amenity_id),
	FOREIGN KEY (building_id) REFERENCES buildings (building_id),
	FOREIGN KEY (amenity_id) REFERENCES amenity (amenity_id)
);

-- initialize table maintenance_request
CREATE TABLE maintenance_request (
  request_id int identity(2000, 1),
  student_id int,
  staff_id int,
  building_id int,
  room_number int,
  amenity_id int,
  status varchar(20),
  date_submitted date,
  date_completed date,
  PRIMARY KEY (request_id),
  FOREIGN KEY (student_id) REFERENCES student(student_id),
  FOREIGN KEY (staff_id) REFERENCES staff (staff_id),
  FOREIGN KEY (room_number, building_id) REFERENCES rooms (room_number, building_id),
  FOREIGN KEY (building_id, amenity_id) REFERENCES building_amenity (building_id, amenity_id),
  CHECK (status in ('submitted', 'in progress', 'completed')),
  CHECK (date_completed >= date_submitted)
);

-- initialize table assignment
CREATE TABLE assignment (
  group_id int,
  room_number int,
  building_id int,
  PRIMARY KEY (group_id),
  FOREIGN KEY (room_number, building_id) REFERENCES rooms (room_number, building_id),
);
