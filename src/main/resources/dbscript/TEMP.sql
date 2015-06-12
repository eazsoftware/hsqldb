CREATE SCHEMA PUBLIC AUTHORIZATION DBA
CREATE MEMORY TABLE "com_eaz_software_hsqldb_Guest"("ID" BIGINT NOT NULL PRIMARY KEY,"name_" VARCHAR(255))
CREATE MEMORY TABLE "com_eaz_software_hsqldb_RoomStatus"("ID" BIGINT NOT NULL PRIMARY KEY,"name_" VARCHAR(255))
CREATE MEMORY TABLE "com_eaz_software_hsqldb_BookingStatus"("ID" BIGINT NOT NULL PRIMARY KEY,"name_" VARCHAR(255))
CREATE MEMORY TABLE "com_eaz_software_hsqldb_Booking"("ID" BIGINT NOT NULL PRIMARY KEY,ROOM_ BIGINT,GUEST_ BIGINT,STATUS_ BIGINT,DATE_ BIGINT)
CREATE MEMORY TABLE "com_eaz_software_Booking$To$Guest"("BookingID" BIGINT NOT NULL,"GuestID" BIGINT NOT NULL,CONSTRAINT "fk_Booking$To$Guest_BookingID" FOREIGN KEY("BookingID") REFERENCES "com_eaz_software_hsqldb_Booking"("ID"),CONSTRAINT "fk_Booking$To$Guest_GuestID" FOREIGN KEY("GuestID") REFERENCES "com_eaz_software_hsqldb_Guest"("ID"))
CREATE MEMORY TABLE "com_eazsoftware_hsqldb_Building"("ID" BIGINT NOT NULL PRIMARY KEY,AREA_ BIGINT,"name_" VARCHAR(255),"address_" VARCHAR(255))
CREATE MEMORY TABLE "com_eazsoftware_hsqldb_Room"("ID" BIGINT NOT NULL PRIMARY KEY,BUILDING_ BIGINT,"name_" VARCHAR(255),STATUS_ BIGINT)
CREATE MEMORY TABLE "com_eaz_software_hsqldb_Area"("ID" BIGINT NOT NULL PRIMARY KEY,"name_" VARCHAR(255))
CREATE USER SA PASSWORD ""
GRANT DBA TO SA
SET WRITE_DELAY 10
