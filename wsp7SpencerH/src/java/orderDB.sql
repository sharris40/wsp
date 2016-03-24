-- user wsp, pw wsp1SpencerH!
USE `wsp`;
DROP table IF EXISTS "orders";

CREATE TABLE "orders" (
  "orderid" int(11) NOT NULL,
  "bookid" int(11) NOT NULL,
  "quantity" int(11) unsigned NOT NULL,
  "time" timestamp NOT NULL DEFAULT NOW(),
  PRIMARY KEY ("orderid", "bookid"),
  FOREIGN KEY ("bookid") REFERENCES "books"("bookid")
);
