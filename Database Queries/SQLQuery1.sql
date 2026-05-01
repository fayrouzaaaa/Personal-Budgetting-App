-- -----------------------------------------------------------------
-- This file is dedicated to creating the database as well as
-- its two most important objects/tables: Users and Transactions
-- -----------------------------------------------------------------

create database Personal_Budgetting 

-- -----------------------------------------------------------------
-- Table Users contains 5 attributes
-- ID: Auto-incremennted and serves as the primary key for User
-- Name: The user's name, can not be null 
-- Email: The user's email, can not be repeated or be null
-- Password: The password of the user's account
-- Balance: The balance the user currently has in their account
-- -----------------------------------------------------------------

create table Users (
ID int identity(1,1) primary key,
Name varchar(100) NOT NULL,
Email varchar(100) UNIQUE NOT NULL,
Password varchar(100) NOT NULL,
Balance real default 0.0
)

-- -----------------------------------------------------------------
-- Table Transactions has 5 attributes
-- ID: Auto-incremented and serves as the primary key for Transaction
-- Name: The name/description of the transaction (eg. Dinner at Restaurant)
-- Amount: The amount of money assigned to the transaction
-- User_ID: The ID of the user who made the transaction. This is used as the
--			foreign key that references user table.
-- Type: Type of transaction (Income or Expense).
-- Date: The date on which the transaction tookplace.
-- Category: Category of the transaction (eg. food, transport, etc.)
-- -----------------------------------------------------------------

create table Transactions (
ID int identity(1,1) primary key,
Name varchar(100) NOT NULL,
Amount real NOT NULL,
User_ID int NOT NULL,
Type varchar(100) NOT NULL,
Date date NOT NULL,
Category varchar(100)
)


-- -----------------------------------------------------------------
-- Adding constraint FK_1 which is setting the foreign key that ties 
-- table Users and table Transactions
-- -----------------------------------------------------------------

ALTER table Transactions
ADD CONSTRAINT FK_1 FOREIGN KEY (User_ID) references Users (ID)
