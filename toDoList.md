# TO DO LIST

## PHASE 1
Revisit in **PHASE 3**
- [x] schema
- [x] inserts
- [x] functions
- [x] procedures
- [x] triggers

## PHASE 2

- [x] login
- [x] admin interface
- [x] customer interface

## PHASE 3
- [ ] Incomplete schema definition and integrity constraints
- [ ] IC: A product needs to be in one of the four status
- [ ] sell_date is end of auction date at first and should be initialized when inserting a new product
- [ ] Proc_putProduct should be able to accept any number of categories
- [ ] The category of a new product cannot be a non-leaf category
- [ ] Incorrect implementation of trig_updateHighBid
- [ ] A new bid must be higher than all existing bids of the proudct
- [ ] trig_closeAuction should not close prodcuts that are not under auction
- [ ] func_productCount should not count products that were not "sold"
- [ ] Check leaf category using SQL Methods (1.c)
- [ ] Check bid amount using SQL methods to enforce consistency (1.d)
- [ ] Update sell_date when selling the product (1.e)
- [ ] Prevent SQL injection wherever user inputs are used 
- [ ] Find second highest bid uusing SQL methods (1.e)
- [ ] The highest bidder should be shown if the product is not sold (2.c)
- [ ] Incorrect query for task 1.f
