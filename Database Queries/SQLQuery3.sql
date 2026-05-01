
-- File: SQLQuery3.sql
-- Author: Hoda Hany
-- Description: Creating tables for Budget, Budget_Item, Goal, Category


USE Personal_Budgeting;
GO
IF EXISTS (SELECT * FROM sys.objects WHERE name = 'Budget_Items') DROP TABLE Budget_Items;
IF EXISTS (SELECT * FROM sys.objects WHERE name = 'Budgets') DROP TABLE Budgets;
IF EXISTS (SELECT * FROM sys.objects WHERE name = 'Goals') DROP TABLE Goals;
IF EXISTS (SELECT * FROM sys.objects WHERE name = 'Categories') DROP TABLE Categories;
GO
-- 1. Categories Table
CREATE TABLE Categories (
                            category_id INT IDENTITY(1,1) PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            is_default INT NOT NULL DEFAULT 0
);
GO

-- 2. Budgets Table
CREATE TABLE Budgets (
                         budget_id INT IDENTITY(1,1) PRIMARY KEY,
                         month VARCHAR(7) NOT NULL,
                         user_id INT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES Users(ID)
);
GO

-- 3. Budget_Items Table
CREATE TABLE Budget_Items (
                              id INT IDENTITY(1,1) PRIMARY KEY,
                              budget_id INT NOT NULL,
                              category_id INT NOT NULL,
                              limit_amount FLOAT NOT NULL,
                              spent_amount FLOAT NOT NULL DEFAULT 0,
                              FOREIGN KEY (budget_id) REFERENCES Budgets(budget_id),
                              FOREIGN KEY (category_id) REFERENCES Categories(category_id)
);
GO

-- 4. Goals Table
CREATE TABLE Goals (
                       goal_id INT IDENTITY(1,1) PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       target_amount FLOAT NOT NULL,
                       current_amount FLOAT NOT NULL DEFAULT 0,
                       category_id INT NULL,
                       user_id INT NOT NULL,
                       FOREIGN KEY (category_id) REFERENCES Categories(category_id),
                       FOREIGN KEY (user_id) REFERENCES Users(ID)
);
GO

-- Insert default categories
INSERT INTO Categories (name, is_default) VALUES ('Food', 1);
INSERT INTO Categories (name, is_default) VALUES ('Transportation', 1);
INSERT INTO Categories (name, is_default) VALUES ('Shopping', 1);
INSERT INTO Categories (name, is_default) VALUES ('Entertainment', 1);
INSERT INTO Categories (name, is_default) VALUES ('Bills', 1);
INSERT INTO Categories (name, is_default) VALUES ('Health', 1);
INSERT INTO Categories (name, is_default) VALUES ('Education', 1);
INSERT INTO Categories (name, is_default) VALUES ('Savings', 1);
GO

-- Verification queries
SELECT 'Categories Table', COUNT(*) FROM Categories
UNION ALL
SELECT 'Budgets Table', COUNT(*) FROM Budgets
UNION ALL
SELECT 'Budget_Items Table', COUNT(*) FROM Budget_Items
UNION ALL
SELECT 'Goals Table', COUNT(*) FROM Goals;
GO

USE Personal_Budgeting;
GO

