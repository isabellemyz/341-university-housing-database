-- granting permissions for tables
use UniversityHousing;
grant select on amenity to dbuser;
grant insert on amenity to dbuser;
grant update on amenity to dbuser;
grant delete on amenity to dbuser;

grant select on building_amenity to dbuser;
grant insert on building_amenity to dbuser;
grant update on building_amenity to dbuser;
grant delete on building_amenity to dbuser;


-- granting permissions for stored procedures
use UniversityHousing;
grant execute on addAmenity to dbuser;
