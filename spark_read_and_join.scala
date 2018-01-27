
//Launching spark shell
spark-shell --master yarn --num-executors 2 --executor-memory 2G --conf spark.ui.port=12673

// Read orders and order_items
val orders = sc.textFile("/public/retail_db/orders")
val orderItems = sc.textFile("/public/retail_db/order_items")

orders.first
res0: String = 1,2013-07-25 00:00:00.0,11599,CLOSED

mysql> desc orders;
+-------------------+-------------+------+-----+---------+----------------+
| Field             | Type        | Null | Key | Default | Extra          |
+-------------------+-------------+------+-----+---------+----------------+
| order_id          | int(11)     | NO   | PRI | NULL    | auto_increment |
| order_date        | datetime    | NO   |     | NULL    |                |
| order_customer_id | int(11)     | NO   |     | NULL    |                |
| order_status      | varchar(45) | NO   |     | NULL    |                |
+-------------------+-------------+------+-----+---------+----------------+

orderItems.first
res0: String = 1,1,957,1,299.98,299.98

mysql> desc order_items;
+--------------------------+------------+------+-----+---------+----------------+
| Field                    | Type       | Null | Key | Default | Extra          |
+--------------------------+------------+------+-----+---------+----------------+
| order_item_id            | int(11)    | NO   | PRI | NULL    | auto_increment |
| order_item_order_id      | int(11)    | NO   |     | NULL    |                |
| order_item_product_id    | int(11)    | NO   |     | NULL    |                |
| order_item_quantity      | tinyint(4) | NO   |     | NULL    |                |
| order_item_subtotal      | float      | NO   |     | NULL    |                |
| order_item_product_price | float      | NO   |     | NULL    |                |
+--------------------------+------------+------+-----+---------+----------------+



orders.take(10).foreach(println)
orderItems.take(10).foreach(println)

// Filter for completed or closed orders
val ordersFiltered = orders.filter(order => (order.split(",")(3) == "CLOSED" || order.split(",")(3) == "COMPLETE"))

1,2013-07-25 00:00:00.0,11599,CLOSED
3,2013-07-25 00:00:00.0,12111,COMPLETE
4,2013-07-25 00:00:00.0,8827,CLOSED
5,2013-07-25 00:00:00.0,11318,COMPLETE
6,2013-07-25 00:00:00.0,7130,COMPLETE
7,2013-07-25 00:00:00.0,4530,COMPLETE
12,2013-07-25 00:00:00.0,1837,CLOSED
15,2013-07-25 00:00:00.0,2568,COMPLETE
17,2013-07-25 00:00:00.0,2667,COMPLETE
18,2013-07-25 00:00:00.0,1205,CLOSED

val ordersFiltered = orders.
  filter(order => order.split(",")(3) == "COMPLETE" || order.split(",")(3) == "CLOSED")

// Convert filtered orders to key value pair <orderId, orderDate>
val ordersMap = ordersFiltered.
  map(order => (order.split(",")(0).toInt, order.split(",")(1)))

ordersMap.take(10).foreach(println)
(1,2013-07-25 00:00:00.0)
(3,2013-07-25 00:00:00.0)
(4,2013-07-25 00:00:00.0)
(5,2013-07-25 00:00:00.0)
(6,2013-07-25 00:00:00.0)
(7,2013-07-25 00:00:00.0)
(12,2013-07-25 00:00:00.0)
(15,2013-07-25 00:00:00.0)
(17,2013-07-25 00:00:00.0)
(18,2013-07-25 00:00:00.0)
 
// Convert order_items to key value pairs <order_item_order_id, (order_item_product_id, total)> 
val orderItemsMap = orderItems.
  map(oi => (oi.split(",")(1).toInt,(oi.split(",")(2).toInt, oi.split(",")(4).toFloat)))

ordersMap.take(10).foreach(println)
orderItemsMap.take(10).foreach(println)
(1,(957,299.98))
(2,(1073,199.99))
(2,(502,250.0))
(2,(403,129.99))
(4,(897,49.98))
(4,(365,299.95))
(4,(502,150.0))
(4,(1014,199.92))
(5,(957,299.98))
(5,(365,299.95))

// Join the two data sets
val ordersJoin = ordersMap.join(orderItemsMap)
ordersJoin.take(10).foreach(println)
ordersJoin.count
