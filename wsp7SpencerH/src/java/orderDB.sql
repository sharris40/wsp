-- user wsp, pw wsp1SpencerH!
USE `wsp`;
DROP table IF EXISTS "books";

CREATE TABLE "books" (
  "bookid" int(11) NOT NULL AUTO_INCREMENT,
  "title" nvarchar(32) NOT NULL,
  "author" nvarchar(32) NOT NULL,
  "price" int(6) unsigned DEFAULT NULL,
  "publicationYear" int(5) unsigned NOT NULL,
  PRIMARY KEY ("bookid")
);

INSERT INTO books (title, author, price, publicationYear)
    VALUES ('A Good Book', 'Great Author', 20000, 2002);
INSERT INTO books (title, author, price, publicationYear)
    VALUES ('A Mediocre Book', 'Okay Author', 3500, 2013);
INSERT INTO books (title, author, price, publicationYear)
    VALUES ('A Bad Book', 'Terrible Author', 500, 2005);
