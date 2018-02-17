Problem Scenario 75 : You have been given MySQL DB with following details. 
user=retail_dba 
password=cloudera 
database=retail_db 
table=retail_db.order_items 
jdbc URL = jdbc:mysql://quickstart:3306/retail_db 

Please accomplish following activities. 

1. Copy "retail_db.order_items" table to hdfs in respective directory P90_order_items. 
2. Do the summation of entire revenue in this table using pyspark. 
3. Find the maximum and minimum revenue as well. 
4. Calculate average revenue 
Columns of order_items table : (order_item_id , order_item_order_id , order_item_product_id, order_item_quantity,order_item_subtotal,order_item_product_price) 

========================================================================= 

Solution : 

Step 1 : Import Single table . 

[paslechoix@gw03 ~]$ sqoop import -m=1 \
--connect=jdbc:mysql://ms.itversity.com/retail_db \
--username=retail_user \
--password=itversity \
--table=order_items 
--target-dir=p90_order_items


Step 2 : Read the data from one of the partition, created using above command. 

[paslechoix@gw03 ~]$ hdfs dfs -cat p90_order_items/*
152860,61113,957,1,299.98,299.98
152861,61114,1014,5,249.9,49.98
152862,61115,725,1,108.0,108.0
152863,61115,1073,1,199.99,199.99
152864,61115,191,4,399.96,99.99
152865,61115,1014,4,199.92,49.98

Step 3 : In pyspark, get the total revenue across all days and orders. 

order_items = sc.textFile("p90_order_items") 
#Cast string to float 

order_items_rev = order_items.map(lambda line: flatMap(line.split(",")([4])))
Step 4 : Verity extracted data 

order_items_rev = order_items.flatMap(lambda line: line.split(",")[4]).map
for revenue in order_items_rev.collect(): print(revenue) 

counts = text_file.flatMap(lambda line: line.split(" ")) \
             .map(lambda word: (word, 1)) \
             .reduceByKey(lambda a, b: a + b)

#use reduce function to sum a single column vale 
totalRevenue = extractedRevenueColumn.reduce(lambda a, b: a + b) 

Step 5 : Calculate the maximum revenue 
maximumRevenue = extractedRevenueColumn.reduce(lambda a, b: (a if a>=b else b) ) 

Step 6 : Calculate the minimum revenue 
minimumRevenue = extractedRevenueColumn.reduce(lambda a, b: (a if a<=b else b) ) 

Step 7 : Caclculate average revenue 

Count=extractedRevenuecolumn.count() 
averageRev=totalRevenue/count
