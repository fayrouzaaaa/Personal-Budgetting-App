Personal Budgeting App
-This app is for manage and record your expenses, save money and track your transactions which help you to know for what you spend money and how to save your income effectively.

Table of Content
About
Features
Tech Stack
Architecture
Project Structure
Getting Started
Contribution Guidelines
What's Next
Author


About:
the app provide to user a well-organized system to navigate his expenses and know his total income in a month and total expenses and how to benefit from the saving amount. Users have the ability to categorize their expenses, create budget to specified category and monitor its progress over time. It helps the user to manage his spends and how to save money to specific goal. how to spend money at specific budget and Category. It also helps the user to reduce unnecessary expenses. By using this system, users will be able to develop better financial habits and improve their overall financial stability.


Features:
Add Transaction
   - this feature is responsible about specify the type of the transaction, transaction amount and category(if expense). It helps the user to save all his income and expenses which make
     tracking his spends easier.
Track Budget Usage
   - this feature is very useful if you have to many things to spend your many about (e.g. Food, Bills or Education).  it useful to organize your spends and stay remember how much you
     spend at each Budget
Generate Reports
   -  After each month , you can see how much you spent and saved and the percentage of each budget in the expenses amount.
Dashboard
   -  the Dashboard shows your recent transactions, your balance, total income and total expenses. It is very useful to quickly remember your expenses without needing to overthink when you
      spend this money and for what.


Tech Stack:
Programming Language:
   - Java: used to implement the core logic for the System.
   - SQL: for Writing queries.
Libraries/ tools :
   - Swing :
      It is a GUI Library for implementing desktop app with Java.
   - JDBC :
      It is an API library for connect database with our system.
   - SQL Server Management:
      It is an engine to execute queries and create or edit the database.


Architecture:
 Frontend:
     - we use Swing (Java library) to implement a simple and easy-to-use interface.
 Backend:
     - we didn't frameworks, we only used Java and JDBC to implement a clear logic which handles all cases.
 Database
     - we use SQL server Management as an engine create the database and its entities.


Project Structure:

/Personal_Budgetng
|
|___/src
|   |__/main
|      |__/java
|         |___ Account              #handles user account and balance
|         |___ Authentication       #handles sign-in, sign-up and validate inputs
|         |___ Budget               #handles adding budgets  with specified month
|         |___ Budget_Item          #handles adding limit to the budget and specify a category
|         |___ Category             #handles creating categories to add expenses by it
|         |___ Transaction          #handles how transactions saved in Database and get some values from Database
|         |___ income               #handles the transactions which its type is income
|         |___ Expense              #handles the transactions which its type is Expense
|         |___ Goal                 #handles adding Goal and save money to achieve it
|         |___ Regular_User         #handles the action users  wants to take (e.g. add Transaction)
|         |___ Report               #generates reports to a specified period of time
|         |___ User                 #creates users or get users' credentials
|         |___ UI                   #handles user interface for different pages
|         |___ Database             #offers a connection to database and functions to easily get/add information from/in database
|         |___ DashboardScreen      #creates a GUI for Dashboard page
|         |___ ErrorScreen          #if there is an error this class display the error screen with a message
|         |___ LoginScreen          #creates a GUI for log in page
|         |___ SignupScreen         #creates a GUI for sign up Screen
|
|
|___/Database Queries
|   |___ SQLQuery1                  #creates tables Transactions and Users
|   |___ SQLQuery2                  #inserts default values into the previous tables
|   |___ SQLQuery3                  #creates tables Goals , Budgets , Budget_Items and Categories
|
|
|
└── README.md


Getting Started:
if you want to try the app or use it to track your spends follow this steps:
first clone the repository into your computer
execute files in directory: Database Queries (by that you will create the database)
connect this database : Personal_Budgetting with your IDE
now we finish! you can create now account and sign in after that by this credentials

Contribution Guidelines:
We will be happy if you want to contribute to add some features in our app
first fork the repository  to create your own copy of the repository
clone your fork
create a feature Branch to keep your changes organized
set up the environment (go to Getting Started)

What's Next:
 - we eager to add more features and enhance the GUI to give the user the best UX

Authors:
if you want any help you can contact us by this E-mails:
Aya Yasser El-Metwally: 20240106@stud.fci-cu.edu.eg
Fayrouza Hossam Moanes: 20240412@stud.fci-cu.edu.eg
Hoda Hany Hassan: 20240647@stud.fci-cu.edu.eg
Hager AbdElnaeem Zaki: 20242383@stud.fci-cu.edu.eg
