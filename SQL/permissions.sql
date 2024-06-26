-- granting permissions for tables
USE UniversityHousing;
GRANT SELECT ON amenity TO dbuser;
GRANT INSERT ON amenity TO dbuser;
GRANT UPDATE ON amenity TO dbuser;
GRANT DELETE ON amenity TO dbuser;

GRANT SELECT ON assignment TO dbuser;
GRANT INSERT ON assignment TO dbuser;
GRANT UPDATE ON assignment TO dbuser;
GRANT DELETE ON assignment TO dbuser;

GRANT SELECT ON building_amenity TO dbuser;
GRANT INSERT ON building_amenity TO dbuser;
GRANT UPDATE ON building_amenity TO dbuser;
GRANT DELETE ON building_amenity TO dbuser;

GRANT SELECT ON buildings TO dbuser;
GRANT INSERT ON buildings TO dbuser;
GRANT UPDATE ON buildings TO dbuser;
GRANT DELETE ON buildings TO dbuser;

GRANT SELECT ON groups TO dbuser;
GRANT INSERT ON groups TO dbuser;
GRANT UPDATE ON groups TO dbuser;
GRANT DELETE ON groups TO dbuser;

GRANT SELECT ON maintenance_request TO dbuser;
GRANT INSERT ON maintenance_request TO dbuser;
GRANT UPDATE ON maintenance_request TO dbuser;
GRANT DELETE ON maintenance_request TO dbuser;

GRANT SELECT ON preference TO dbuser;
GRANT INSERT ON preference TO dbuser;
GRANT UPDATE ON preference TO dbuser;
GRANT DELETE ON preference TO dbuser;

GRANT SELECT ON rooms TO dbuser;
GRANT INSERT ON rooms TO dbuser;
GRANT UPDATE ON rooms TO dbuser;
GRANT DELETE ON rooms TO dbuser;

GRANT SELECT ON staff TO dbuser;
GRANT INSERT ON staff TO dbuser;
GRANT UPDATE ON staff TO dbuser;
GRANT DELETE ON staff TO dbuser;

GRANT SELECT ON student TO dbuser;
GRANT INSERT ON student TO dbuser;
GRANT UPDATE ON student TO dbuser;
GRANT DELETE ON student TO dbuser;

-- granting permissions for stored procedures
use UniversityHousing;
grant execute on addAmenity to dbuser;
grant execute on addAmenityToBuilding to dbuser;
grant execute on insertStudent to dbuser;
grant execute on insertPreference to dbuser;
grant execute on AnalyzeStudentPreferences to dbuser; 
grant execute on insertValidRequest to dbuser;
grant execute on InsertGroup to dbuser; 
grant execute on assignGroup to dbuser; 
grant execute on ViewAvailableRoom to dbuser; 
grant execute on viewAllStudents to dbuser;
grant execute on deleteAmenityFromBuilding to dbuser;
grant execute on getGroupCountAndSize to dbuser;
grant execute on viewAllStudentGroups to dbuser;
grant execute on deleteAmenity to dbuser;
grant execute on deleteAmenityFromBuilding to dbuser;
grant execute on getAmenityNameFromID to dbuser;
grant execute on getBuildingNameFromID to dbuser;
grant execute on insertValidRequest to dbuser;
grant execute on getRequestsAmenity to dbuser;
grant execute on getSubmittedRequests to dbuser;
grant execute on assignStaffToRequest to dbuser;
grant execute on changeRequestStatusToCompleted to dbuser;
grant execute on getStaffMaintenanceRequests to dbuser;
grant execute on updateRoomStatus to dbuser;
