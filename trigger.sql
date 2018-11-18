-- functions
CREATE OR REPLACE FUNCTION func_productCount(c IN varchar2, x IN number)
  RETURN number IS
  total number;
  BEGIN
    SELECT count(*) INTO total
      FROM Product P NATURAL JOIN BelongsTo B CROSS JOIN ourSysDATE O
      WHERE P.sell_date IS NOT NULL
        AND B.category = c
        AND MONTHS_BETWEEN(O.c_date, P.sell_date) <= x;

    RETURN total;
  END;
/
-- CREATE OR REPLACE FUNCTION func_bidCount(u IN varchar2, x IN number)
-- /
-- CREATE OR REPLACE FUNCTION func_buyingAmount(u IN varchar2, x IN number)
-- /
commit;
