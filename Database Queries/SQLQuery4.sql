Create trigger budget_months ON Users 
AFTER INSERT
AS
	declare @userID int
	select @userID = ID from inserted
	INSERT INTO BUDGETS VALUES ('JAN', @userID), ('FEB', @userID), ('MAR', @userID), 
							   ('APR', @userID), ('MAY', @userID), ('JUN', @userID), 
							   ('JUL', @userID), ('AUG', @userID), ('SEP', @userID), 
							   ('OCT', @userID), ('NOV', @userID), ('DEC', @userID)
