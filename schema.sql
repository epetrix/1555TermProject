DROP TABLE ourSysDATE CASCADE CONSTRAINTS;
DROP TABLE Customer CASCADE CONSTRAINTS;
DROP TABLE Administrator CASCADE CONSTRAINTS;
DROP TABLE Product CASCADE CONSTRAINTS;
DROP TABLE Bidlog CASCADE CONSTRAINTS;
DROP TABLE Category CASCADE CONSTRAINTS;
DROP TABLE BelongsTo CASCADE CONSTRAINTS;

commit;

CREATE TABLE ourSysDATE
(
    c_date date,
    CONSTRAINT ourSysDATE_PK PRIMARY KEY (c_date)
);

CREATE TABLE Customer
(
    login       varchar2(10),
    password    varchar2(10),
    name        varchar2(20),
    address     varchar2(30),
    email       varchar2(20),
    CONSTRAINT Customer_PK PRIMARY KEY (login)
);

CREATE TABLE Administrator
(
    login       varchar2(10),
    password    varchar2(10),
    name        varchar2(20),
    address     varchar2(30),
    email       varchar2(20),
    CONSTRAINT Administrator_PK PRIMARY KEY (login)
);

CREATE TABLE Product
(
    auction_id      int,
    name            varchar2(20),
    description     varchar2(30),
    seller          varchar2(10),
    start_date      date,
    min_price       int,
    number_of_days  int,
    status          varchar2(15) NOT NULL,
    buyer           varchar2(10),
    sell_date       date,
    amount          int,
    CONSTRAINT Product_PK PRIMARY KEY (auction_id),
    CONSTRAINT Product_FK_seller FOREIGN KEY (seller) REFERENCES Customer(login),
    CONSTRAINT Product_FK_buyer FOREIGN KEY (buyer) REFERENCES Customer(login)
);

CREATE TABLE Bidlog
(
    bidsn       int,
    auction_id  int,
    bidder      varchar2(10),
    bid_time    date,
    amount      int,
    CONSTRAINT Bidlog_PK PRIMARY KEY (bidsn),
    CONSTRAINT Bidlog_FK_auction_id FOREIGN KEY (auction_id) REFERENCES Product(auction_id),
    CONSTRAINT Bidlog_FK_bidder FOREIGN KEY (bidder) REFERENCES Customer(login)
);

CREATE TABLE Category
(
    name                varchar2(20),
    parent_category     varchar2(20),
    CONSTRAINT Category_PK PRIMARY KEY (name),
    CONSTRAINT Category_FK FOREIGN KEY (parent_category) REFERENCES Category(name)
);

CREATE TABLE BelongsTo
(
    auction_id      int,
    category        varchar2(20),
    CONSTRAINT BelongsTo_PK PRIMARY KEY (auction_id, category),
    CONSTRAINT BelongsTo_FK_auction_id FOREIGN KEY (auction_id) REFERENCES Product(auction_id),
    CONSTRAINT BelongsTo_FK_category FOREIGN KEY (category) REFERENCES Category(name)
);

commit;
