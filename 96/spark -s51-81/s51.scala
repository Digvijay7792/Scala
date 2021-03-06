Problem Scenario 51 : You have been given below code snippet. 
val a = sc.parallelize(List(1, 2, 1, 3), 1) 
val b = a.map((_, "b")) 
val c = a.map((_, "c")) 
Operation_xyz 

Write a correct code snippet for Operation_xyz which will produce below output. 

Output : 

Array[(lnt, (Iterable[String], Iterable[String]))] = Array( 
(2,(ArrayBuffer(b),ArrayBuffer(c))), 
(3,(ArrayBuffer(b),ArrayBuffer(c))), 
(1,(ArrayBuffer(b, b),ArrayBuffer(c, c))) 

================================================================================
Solution: 

val a = sc.parallelize(List(1, 2, 1, 3), 1) 
scala> a.take(10).foreach(println)
1
2
1
3

val b = a.map((_, "b")) 
scala> b.take(10).foreach(println)
(1,b)
(2,b)
(1,b)
(3,b)

val c = a.map((_, "c")) 
scala> c.take(10).foreach(println)
(1,c)
(2,c)
(1,c)
(3,c)

val d = b.map((_, "c"))
scala> d.take(10).foreach(println)
((1,b),c)
((2,b),c)
((1,b),c)
((3,b),c)

val e = d.map((_, "d"))
scala> e.take(10).foreach(println)
(((1,b),c),d)
(((2,b),c),d)
(((1,b),c),d)
(((3,b),c),d)



val arr = b.cogroup(c).collect 
scala> arr.foreach(println)
(1,(CompactBuffer(b, b),CompactBuffer(c, c)))
(3,(CompactBuffer(b),CompactBuffer(c)))
(2,(CompactBuffer(b),CompactBuffer(c)))


cogroup [Pair], groupWith [Pair] 
A very powerful set of functions that allow grouping up to 3 key-value RDDS together using their keys. 
Another example 

val x = sc.parallelize(List((1, "apple"), (2, "banana"), (3, "orange"), (4, "kiwi")), 2) 
val y = sc.parallelize(List((5, "computer"), (1, "laptop"), (1, "desktop"), (4, "iPad")), 2) 

x.cogroup(y).collect 

Array(Int,(Iterable[String], Iterable[String]))] = Array( 
(4,(ArrayBuffer(kiwi),ArrayBuffer(iPad))), 
(2, (ArrayBuffer(banana),ArrayBuffer())), 
(3,(ArrayBuffer(orange),ArrayBuffer())), 
(1 ,(ArrayBuffer(apple),ArrayBuffer(laptop, desktop))), 
(5, (ArrayBuffer(),ArrayBuffer(computer)))) 
