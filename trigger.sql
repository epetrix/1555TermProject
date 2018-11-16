-- functions
create or replace FUNCTION func_productCount(c IN varchar2, x IN number)
  RETURN number
  IS total number;

  BEGIN
    SELECT c_date - sell_date AS months, category, count(*) INTO total
      FROM Product NATURAL JOIN BelongsTo JOIN ourSysDATE
      WHERE sell_date IS NOT NULL AND c = category AND months <= x
      GROUP BY months, category; -- auction_id?

    RETURN(total);
  END;

CREATE OR REPLACE FUNCTION func_bidCount(u IN varchar2, x IN number)

CREATE OR REPLACE FUNCTION func_buyingAmount(u IN varchar2, x IN number)
