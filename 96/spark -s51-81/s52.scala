Problem Scenario 52 : You have been given below code snippet. 
val anRDD = sc.parallelize(List(1,2,3,4,5,6,7,8,2,4,2,1,1,1,1,1))
Operation_xyz 

Write a correct code snippet for Operation _xyz which will produce below output
Scala.collection.Map[Int,Long]= Map(5->1,8->1,3->1,6->1,1->6,2->3,4->2,7->1) 

==================================================================================
Solution : 

1. val anRDD = sc.parallelize(List(1,2,3,4,5,6,7,8,2,4,2,1,1,1,1,1))

2. val anRDDMap = anRDD.groupBy(k).mapValues(_.size)

3. val anRDDMap = anRDD.groupBy(identity).mapValues(_.size)
(8,1)
(1,6)
(2,3)
(3,1)
(4,2)
(5,1)
(6,1)
(7,1)
4. val sortedRDD = anRDDMap.sortByKey()

(1,6)
(2,3)
(3,1)
(4,2)
(5,1)
(6,1)
(7,1)
(8,1)

b.countByValue 
val anRDDMap2 = anRDD.countByValue 
res4: scala.collection.Map[Int,Long] = Map(5 -> 1, 1 -> 6, 6 -> 1, 2 -> 3, 7 -> 1, 3 -> 1, 8 -> 1, 4 -> 2)
scala> anRDDMap2.take(10).foreach(println)
(5,1)
(1,6)
(6,1)
(2,3)
(7,1)
(3,1)
(8,1)
(4,2)


Returns a map that contains all unique values of the RDD and their respective occurrence counts. (Warning: This operation will finally aggregate the information in a single reducer.)

Listing Variants 

def countByValue(): Map[T, Long]
