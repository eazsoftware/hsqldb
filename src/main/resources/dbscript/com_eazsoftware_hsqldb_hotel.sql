--
-- com_eazsoftware_hsqldb_hotel
--

DROP TABLE IF EXISTS "com_eazsoftware_hsqldb_Building";
CREATE TABLE "com_eazsoftware_hsqldb_Building" (
	"ID" bigint NOT NULL,
	area_ bigint,
	"name_" character varying(255),
	"address_" character varying(255)
);
ALTER TABLE "com_eazsoftware_hsqldb_Building" ADD PRIMARY KEY (ID);

DROP TABLE IF EXISTS "com_eazsoftware_hsqldb_Room";
CREATE TABLE "com_eazsoftware_hsqldb_Room" (
	"ID" bigint NOT NULL,
	building_ bigint,
	"name_" character varying(255),
	status_ bigint
);
ALTER TABLE "com_eazsoftware_hsqldb_Room" ADD PRIMARY KEY (ID);

DROP TABLE IF EXISTS "com_eaz_software_hsqldb_Area";
CREATE TABLE "com_eaz_software_hsqldb_Area" (
	"ID" bigint NOT NULL,
	"name_" character varying(255)
);
ALTER TABLE "com_eaz_software_hsqldb_Area" ADD PRIMARY KEY (ID);

DROP TABLE IF EXISTS "com_pexsoftware_enterprise_Booking$To$Guest";
CREATE TABLE "com_pexsoftware_enterprise_Booking$To$Guest" (
    "BookingID" bigint NOT NULL,
    "GuestID" bigint NOT NULL
);

DROP TABLE IF EXISTS "com_eaz_software_hsqldb_Guest";
CREATE TABLE "com_eaz_software_hsqldb_Guest" (
	"ID" bigint NOT NULL,
	"name_" character varying(255)
);
ALTER TABLE "com_eaz_software_hsqldb_Guest" ADD PRIMARY KEY (ID);
ALTER TABLE "com_pexsoftware_enterprise_Booking$To$Guest" ADD CONSTRAINT "fk_Booking$To$Guest_GuestID" FOREIGN KEY ("GuestID") REFERENCES "com_eaz_software_hsqldb_Guest"("ID");

DROP TABLE IF EXISTS "com_eaz_software_hsqldb_RoomStatus";
CREATE TABLE "com_eaz_software_hsqldb_RoomStatus" (
	"ID" bigint NOT NULL,
	"name_" character varying(255)
);
ALTER TABLE "com_eaz_software_hsqldb_RoomStatus" ADD PRIMARY KEY (ID);

DROP TABLE IF EXISTS "com_eaz_software_hsqldb_BookingStatus";
CREATE TABLE "com_eaz_software_hsqldb_BookingStatus" (
	"ID" bigint NOT NULL,
	"name_" character varying(255)
);
ALTER TABLE "com_eaz_software_hsqldb_BookingStatus" ADD PRIMARY KEY (ID);

DROP TABLE IF EXISTS "com_eaz_software_hsqldb_Booking";
CREATE TABLE "com_eaz_software_hsqldb_Booking" (
	"ID" bigint NOT NULL,
	room_ bigint,
	guest_ bigint,
	status_ bigint,
	date_ bigint
);
ALTER TABLE "com_eaz_software_hsqldb_Booking" ADD PRIMARY KEY (ID);
ALTER TABLE "com_pexsoftware_enterprise_Booking$To$Guest" ADD CONSTRAINT "fk_Booking$To$Guest_BookingID" FOREIGN KEY ("BookingID") REFERENCES "com_eaz_software_hsqldb_Booking"("ID");

