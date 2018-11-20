-- functions

CREATE OR REPLACE FUNCTION func_productCount(c IN VARCHAR2, x IN NUMBER)
  RETURN NUMBER IS
  total NUMBER;

  BEGIN
    SELECT count(*) INTO total
      FROM Product P NATURAL JOIN BelongsTo B CROSS JOIN ourSysDATE O
      WHERE P.status = 'sold'
        AND B.category = c
        AND MONTHS_BETWEEN(O.c_date, P.sell_date) <= x;

    RETURN total;
  END;
/

CREATE OR REPLACE FUNCTION func_bidCount(u IN VARCHAR2, x IN NUMBER)
  RETURN NUMBER IS
  total NUMBER;

  BEGIN
    SELECT count(*) INTO total
      FROM Bidlog B CROSS JOIN ourSysDATE O
      WHERE B.bid_time IS NOT NULL
        AND B.bidder = u
        AND MONTHS_BETWEEN(O.c_date, B.bid_time) <= x;

    RETURN total;
  END;
/

CREATE OR REPLACE FUNCTION func_buyingAmount(u IN VARCHAR2, x IN NUMBER)
  RETURN NUMBER IS
  total NUMBER;

  BEGIN
    SELECT sum(P.amount) INTO total
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
    SET amount = :NEW.amount;
END;
/

CREATE OR REPLACE TRIGGER trig_closeAuctions
AFTER UPDATE ON ourSysDATE
FOR EACH ROW
BEGIN
    FOR id IN (
        SELECT auction_id AS id
        FROM Product
        WHERE :NEW.c_date - start_date >= number_of_days)
    LOOP
        UPDATE Product
        SET status = 'closed'
        WHERE auction_id = id;
    END LOOP;
END;
/
show errors;

-- procedures

CREATE OR REPLACE PROCEDURE proc_putProduct(
  name IN varchar2,
  descr IN varchar2,
  seller IN varchar2,
  minPrice IN number,
  days IN number,
  cat IN varchar2,
  id OUT number )
IS
  currTime date;
BEGIN
  SELECT c_date INTO currTime FROM ourSysDATE;
  SELECT max(auction_id) INTO id FROM Product;
  id := id + 1;
  INSERT INTO Product VALUES(id, name, descr, seller, currTime, minPrice, days, 'not sold', null, null, null);
  INSERT INTO BelongsTo VALUES(id, cat);
END;
/

commit;
