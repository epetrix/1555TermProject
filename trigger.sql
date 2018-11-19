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
    SELECT sum(P.amount) INTO total
      FROM Product P CROSS JOIN ourSysDATE O
      WHERE P.status = 'sold'
        AND P.buyer = u
        AND MONTHS_BETWEEN(O.c_date, P.sell_date) <= x;

    RETURN total;
  END;
/

-- triggers

commit;

CREATE OR REPLACE TRIGGER trig_bidTimeUpdate
BEFORE INSERT ON BidLog
FOR EACH ROW 
BEGIN
    :NEW.c_date := c_date + INTERVAL '5' SECOND;
END;
/

CREATE OR REPLACE TRIGGER trig_UpdateHighBid
AFTER INSERT ON BidLog 
DECLARE bidAmount NUMBER;
BEGIN
    SELECT amount INTO bidAmount FROM BidLog;
    UPDATE Product
    SET amount = bidAmount; 
END;
/

CREATE OR REPLACE TRIGGER trig_closeAuctions
AFTER UPDATE ON ourSysDATE
FOR EACH ROW 
DECLARE currentTime DATE;
DECLARE bidTime DATE;
BEGIN
    SELECT c_date iNTO currentTime FROM ourSysDATE;
    SELECT sell_date INTO bidTime FROM Product;
    IF bidTime < currentTime THEN
        UPDATE Product
        SET status = 'closed';
    END IF; 
END; 
/
        
    


    
    
    




