-- user wsp, pw wsp1SpencerH!
USE `wsp`;
DROP procedure IF EXISTS `registrationPassword`;
DROP table IF EXISTS "registration";

CREATE TABLE "registration" (
  "registrationid" int(11) NOT NULL AUTO_INCREMENT,
  "lastName" nvarchar(32) NOT NULL,
  "firstName" nvarchar(32) NOT NULL,
  "email" varchar(64) NOT NULL,
  "binPass" binary(32) DEFAULT NULL,
  "phone" int(11) unsigned NOT NULL,
  "male" tinyint(2) unsigned NOT NULL,
  "langCPlusPlus" tinyint(2) unsigned NOT NULL DEFAULT '0',
  "langJava" tinyint(2) unsigned NOT NULL DEFAULT '0',
  "langCSharp" tinyint(2) unsigned NOT NULL DEFAULT '0',
  "langSwift" tinyint(2) unsigned NOT NULL DEFAULT '0',
  "langPython" tinyint(2) unsigned NOT NULL DEFAULT '0',
  "hometown" nvarchar(32) NOT NULL,
  PRIMARY KEY ("registrationid")
);

DELIMITER $$
USE `wsp`$$
CREATE DEFINER="root"@"localhost" PROCEDURE "registrationPassword"(IN id INT, IN pass NVARCHAR(255))
BEGIN
	UPDATE registration SET binPass=UNHEX(SHA2(CONCAT(pass, CHAR(id)), 0))
		WHERE registrationid = id;
END$$

DELIMITER ;
