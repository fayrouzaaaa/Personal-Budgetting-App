-- -----------------------------------------------------------------
-- This file is dedicated to inserting some test values in the DB
-- tables.
-- -----------------------------------------------------------------

-- -----------------------------------------------------------------
-- Inserting into Users table
-- -----------------------------------------------------------------

INSERT INTO Users 
VALUES ('Jana', 'jana123@gmail.com', 'jana!budgets', 0.0) 


-- -----------------------------------------------------------------
-- Inserting into Transactions table
-- -----------------------------------------------------------------

INSERT INTO Transactions 
VALUES ('Driving Job', 100, 1, 'Income', '2026-02-12', 'Transportation')

INSERT INTO Transactions 
VALUES ('Lunch', 50, 1, 'Expense', '2026-03-01', 'Food')


-- -----------------------------------------------------------------
-- SELECT statements to view the tables after insertion
-- -----------------------------------------------------------------

SELECT * FROM Users
SELECT * FROM Transactions

-- -----------------------------------------------------------------
-- SELECT statement to test joining the two tables
-- -----------------------------------------------------------------

SELECT	Users.ID as ID, Users.Name as Name, Transactions.Name, Transactions.Type as Type FROM Users, Transactions 
WHERE Users.ID = Transactions.User_ID