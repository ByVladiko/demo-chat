INSERT INTO POSTAL_OFFICE (ID, CREATE_TS, UPDATE_TS, NAME, LOC_NAME, DESCRIPTION, IS_DEFAULT_ROLE)
VALUES (gen_random_uuid(), current_timestamp, "USER", "User", "Default user role", true);

INSERT INTO POSTAL_OFFICE (ID, CREATE_TS, UPDATE_TS, NAME, LOC_NAME, DESCRIPTION, IS_DEFAULT_ROLE)
VALUES (gen_random_uuid(), current_timestamp, "ADMIN", "Admin", "Admin role", false);