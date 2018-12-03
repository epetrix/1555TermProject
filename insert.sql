INSERT INTO ourSysDATE VALUES(SYSDATE);

INSERT INTO Customer VALUES('adk67', 'jjj', 'Alex', '300 Road St', 'alex@pitt.edu');
INSERT INTO Customer VALUES('xyz123', 'password', 'Xavier', '100 Benedum St', 'xavier@pitt.edu');
INSERT INTO Customer VALUES('abc456', 'dogs', 'Alice', '200 Cathy Blvd', 'alice@pitt.edu');

INSERT INTO Administrator VALUES('admin', 'root', 'Bob', '123 Sennott St', 'bob@pitt.edu');

INSERT INTO Product VALUES(1, 'toaster', 'toasts bread', 'adk67', '21-JUN-2014', 20, 10, 'sold', 'abc456', SYSDATE, 1276);
INSERT INTO Product VALUES(2, 'Macbook Pro 2018', 'brand new Apple computer', 'abc456', SYSDATE, 1500, 30, 'not sold', null, null, null);
INSERT INTO Product VALUES(3, 'Bose QC30 Headphones', 'sound cancelling headphones', 'adk67', SYSDATE, 300, 15, 'not sold', null, null, null);
INSERT INTO Product VALUES(4, 'IPhone XR', 'brand new IPhone', 'xyz123', '01-JAN-2019', 300, 15, 'sold', 'abc456', SYSDATE, 1000);
INSERT INTO Product VALUES(5, 'Hydro Flask', 'new water bottle', 'xyz123', SYSDATE, 30, 4, 'not sold', 'adk67', SYSDATE, 3);

INSERT INTO Bidlog VALUES(1, 1, 'abc456', SYSDATE, 20);
INSERT INTO Bidlog VALUES(2, 1, 'xyz123', SYSDATE, 25);
INSERT INTO Bidlog VALUES(3, 1, 'abc456', SYSDATE, 30);
INSERT INTO Bidlog VALUES(4, 2, 'adk67', SYSDATE, 1500);
INSERT INTO Bidlog VALUES(5, 2, 'xyz123', SYSDATE, 1550);
INSERT INTO Bidlog VALUES(6, 3, 'abc456', SYSDATE, 300);
INSERT INTO Bidlog VALUES(7, 4, 'adk67', SYSDATE, 1000);
INSERT INTO Bidlog VALUES(8, 4, 'abc456', SYSDATE, 1020);
INSERT INTO Bidlog VALUES(9, 4, 'adk67', SYSDATE, 1100);
INSERT INTO Bidlog VALUES(10, 5,'abc456', SYSDATE, 40);
INSERT INTO Bidlog VALUES(11, 1,'abc456', '21-JUN-2014', 1276);

INSERT INTO Category VALUES('Tech', null);
INSERT INTO Category VALUES('Kitchen', null);
INSERT INTO Category VALUES('Outdoor Gear', null);
INSERT INTO Category VALUES('Phones', 'Tech');
INSERT INTO Category VALUES('Computers', 'Tech');
INSERT INTO Category VALUES('Cooking Supplies', 'Kitchen');
INSERT INTO Category VALUES('Headphones', 'Tech');
INSERT INTO Category VALUES('Water Bottles', 'Outdoor Gear');

INSERT INTO BelongsTo VALUES(1, 'Cooking Supplies');
INSERT INTO BelongsTo VALUES(2, 'Computers');
INSERT INTO BelongsTo VALUES(3, 'Headphones');
INSERT INTO BelongsTo VALUES(4, 'Phones');
INSERT INTO BelongsTo VALUES(5, 'Water Bottles');

commit;
