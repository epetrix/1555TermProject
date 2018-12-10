-- functions

CREATE OR REPLACE FUNCTION func_productCount(c IN varchar2, x IN number)
  RETURN number IS
  total number;

  BEGIN
    SELECT count(*) INTO total
      FROM Product P NATURAL JOIN BelongsTo B CROSS JOIN ourSysDATE O
      WHERE P.status = 'sold'
        AND B.category = c
        AND MONTHS_BETWEEN(O.c_date, P.sell_date) <= x;

    RETURN total;
  END;
/

CREATE OR REPLACE FUNCTION func_bidCount(u IN varchar2, x IN number)
  RETURN number IS
  total number;

  BEGIN
    SELECT count(*) INTO total
      FROM Bidlog B CROSS JOIN ourSysDATE O
      WHERE B.bid_time IS NOT NULL
        AND B.bidder = u
        AND MONTHS_BETWEEN(O.c_date, B.bid_time) <= x;

    RETURN total;
  END;
/

CREATE OR REPLACE FUNCTION func_buyingAmount(u IN varchar2, x IN number)
  RETURN number IS
  total number;

  BEGIN
    SELECT NVL(sum(P.amount), 0) INTO total
      FROM Product P CROSS JOIN ourSysDATE O
      WHERE P.status = 'sold'
        AND P.buyer = u
        AND MONTHS_BETWEEN(O.c_date, P.sell_date) <= x;

    RETURN total;
  END;
/

-- triggers

CREATE OR REPLACE TRIGGER trig_bidTimeUpdate
BEFORE INSERT ON Bidlog
BEGIN
    UPDATE ourSysDATE
    SET c_date = c_date + INTERVAL '5' SECOND;
END;
/

CREATE OR REPLACE TRIGGER trig_UpdateHighBid
AFTER INSERT ON Bidlog
FOR EACH ROW
BEGIN
    UPDATE Product
    SET amount = :NEW.amount, buyer = :NEW.bidder
    WHERE :NEW.auction_id = auction_id
      AND :NEW.amount > amount;
END;
/

CREATE OR REPLACE TRIGGER trig_closeAuctions
AFTER UPDATE ON ourSysDATE
FOR EACH ROW
BEGIN
    UPDATE Product
    SET status = 'closed'
    WHERE :NEW.c_date - start_date >= number_of_days
      AND status = 'under auction';
END;
/

-- procedures

CREATE OR REPLACE PROCEDURE proc_putProduct(
  name IN varchar2,
  descr IN varchar2,
  seller IN varchar2,
  days IN number,
  cats IN categories_t,
  id OUT number )
IS
  currTime date;
BEGIN
  SELECT c_date INTO currTime FROM ourSysDATE;
  SELECT NVL(max(auction_id), 0) INTO id FROM Product;
  id := id + 1;
  INSERT INTO Product VALUES(id, name, descr, seller, currTime, 0, days, 'under auction', null, null, null);
  FOR i in 1..cats.count LOOP
    INSERT INTO BelongsTo VALUES(id, cats(i));
  END LOOP;
END;
/

commit;
