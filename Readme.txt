A Service to Create Orders for Perishable goods (Milk,Fruits,Eggs,Meat). Also should be able to retrieve the total orders placed on any given week

Business condition:
1. 100 units of items in the Perishable goods are stored in the local inventory every morning before 6 AM. The customers can buy the items within that limit till 12 pm.

2. The stock gets refreshed at 12 pm by maximum of another 100 units. But if we have not exhausted our morning stock completely, for example: (only 'x' amount of items from the 100 units loaded in the morning gets sold till 12 pm) and (that x value is greater than 0) then only x amount of items gets loaded at 12 pm.

Example:
100 eggs loaded to inventory morning at 6 am.
Customers bought 85 eggs till 12 pm.
We have only 15 eggs remaining at the time of 12 pm.
Another 85 eggs gets added to the inventory at 12 pm, which makes the total quantity to 100 again.

3. This happens 5 working days per week. The item sold per product is stored for each day.


List of functions:
1. Write a function to create an order for any of the Perishable product.
2. Write a function to retrieve the details of the number of products sold(day wise) for the last week.

Sample output:

Monday: [Milk, 112],[Fruits, 90], [Eggs, 198], [Meat, 59]
Tuesday: [Milk, 112],[Fruits, 90], [Eggs, 198], [Meat, 59]
Wednesday: [Milk, 112],[Fruits, 90], [Eggs, 198], [Meat, 59]
Thursday: [Milk, 112],[Fruits, 90], [Eggs, 198], [Meat, 59]
Friday: [Milk, 112],[Fruits, 90], [Eggs, 198], [Meat, 59]