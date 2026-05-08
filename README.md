[README.md](https://github.com/user-attachments/files/27539893/README.md)

# **Personal Budgeting App**

\-This app is for manage and record your expenses, save money and track your transactions which help you to know for what you spend money and how to save your income effectively.



### **Table of Content**

* ##### [About](#about)
* ##### [Features](#features)
* ##### [Tech Stack](#tech-stack)
* ##### [Architecture](#Architecture)
* ##### [Project Structure](#project-structure)
* ##### [Getting Started](#getting-started)
* ##### [Contribution Guidelines](#contribution-guidlines)
* ##### [What's Next](#what's-next)
* ##### [Author](#author)

#### 

#### 

#### **About:**

the app provide to user a well-organized system to navigate his expenses and know his total income in a month and total expenses and how to benefit from the saving amount. Users have the ability to categorize their expenses, create budget to specified category and monitor its progress over time. It helps the user to manage his spends and how to save money to specific goal. how to spend money at specific budget and Category. It also helps the user to reduce unnecessary expenses. By using this system, users will be able to develop better financial habits and improve their overall financial stability.





#### **Features:**

* **Add Transaction**

&#x20;  **-** this feature is responsible about specify the type of the transaction, transaction amount and category(if expense). It helps the user to save all his income and expenses which make        

&#x20;    tracking his spends easier.

* **Track Budget Usage** 

&#x20;  **-** this feature is very useful if you have to many things to spend your many about (e.g. Food, Bills or Education).  it useful to organize your spends and stay remember how much you 

&#x20;    spend at each Budget

* **Generate Reports**

&#x20;  **-**  After each month , you can see how much you spent and saved and the percentage of each budget in the expenses amount.

* **Dashboard**

&#x20;  **-**  the Dashboard shows your recent transactions, your balance, total income and total expenses. It is very useful to quickly remember your expenses without needing to overthink when you   

&#x20;     spend this money and for what.





#### **Tech Stack:**

* **Programming Language:** 

&#x20;  **-** Java: used to implement the core logic for the System.

&#x20;  - SQL: for Writing queries.

* **Libraries/ tools :**

&#x20;  **-** Swing :

&#x20;     It is a GUI Library for implementing desktop app with Java.

&#x20;  - JDBC :

&#x20;     It is an API library for connect database with our system.

&#x20;  - SQL Server Management:

&#x20;     It is an engine to execute queries and create or edit the database.





#### **Architecture:**

* &#x20;**Frontend:**

&#x20;    **-** we use Swing (Java library) to implement a simple and easy-to-use interface.

* &#x20;**Backend:**

&#x20;    - we didn't frameworks, we only used Java and JDBC to implement a clear logic which handles all cases.

* &#x20;**Database**

&#x20;    **-** we use SQL server Management as an engine create the database and its entities.





#### **Project Structure:**



**/Personal\_Budgetng**

**|**

**|\_\_\_/src**

**|   |\_\_/main**

**|      |\_\_/java**

**|         |\_\_\_ Account**              #handles user account and balance

**|         |\_\_\_ Authentication**       #handles sign-in, sign-up and validate inputs

**|         |\_\_\_ Budget**               #handles adding budgets  with specified month              

**|         |\_\_\_ Budget\_Item**          #handles adding limit to the budget and specify a category 

**|         |\_\_\_ Category**             #handles creating categories to add expenses by it 

**|         |\_\_\_ Transaction**          #handles how transactions saved in Database and get some values from Database

**|         |\_\_\_ income**               #handles the transactions which its type is income     

**|         |\_\_\_ Expense**              #handles the transactions which its type is Expense 

**|         |\_\_\_ Goal**                 #handles adding Goal and save money to achieve it 

**|         |\_\_\_ Regular\_User**         #handles the action users  wants to take (e.g. add Transaction)

**|         |\_\_\_ Report**               #generates reports to a specified period of time 

**|         |\_\_\_ User**                 #creates users or get users' credentials

**|         |\_\_\_ UI**                   #handles user interface for different pages 

**|         |\_\_\_ Database**             #offers a connection to database and functions to easily get/add information from/in database  

**|         |\_\_\_ DashboardScreen**      #creates a GUI for Dashboard page

**|         |\_\_\_ ErrorScreen**          #if there is an error this class display the error screen with a message

**|         |\_\_\_ LoginScreen**          #creates a GUI for log in page

**|         |\_\_\_ SignupScreen**         #creates a GUI for sign up Screen

**|**

**|**

**|\_\_\_/Database Queries**

**|   |\_\_\_ SQLQuery1**                  #creates tables Transactions and Users

**|   |\_\_\_ SQLQuery2**                  #inserts default values into the previous tables

**|   |\_\_\_ SQLQuery3**                  #creates tables Goals , Budgets , Budget\_Items and Categories

**|**

**|**

**|**

**└── README.md**





#### **Getting Started:**

* **if you want to try the app or use it to track your spends follow this steps:**

  1. first clone the repository into your computer
  2. execute files in directory: Database Queries (by that you will create the database)    
  3. connect this database : Personal\_Budgetting with your IDE 
  4. now we finish! you can create now account and sign in after that by this credentials



#### **Contribution Guidelines:**

* **We will be happy if you want to contribute to add some features in our app**

  1. first fork the repository  to create your own copy of the repository
  2. clone your fork
  3. create a feature Branch to keep your changes organized
  4. set up the environment (go to Getting Started)



#### **What's Next:**

&#x20;**-** we eager to add more features and **enhance the GUI to give the user the best UX**



#### **Authors:**

**if you want any help you can contact us by this E-mails:**

* **Aya Yasser El-Metwally: 20240106@stud.fci-cu.edu.eg**
* **Fayrouza Hossam Moanes: 20240412@stud.fci-cu.edu.eg**
* **Hoda Hany Hassan: 20240647@stud.fci-cu.edu.eg**
* **Hager AbdElnaeem Zaki: 20242383@stud.fci-cu.edu.eg**



