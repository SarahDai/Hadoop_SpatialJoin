# Hadoop_SpatialJoin

Spatial join is a common type of joins in many applications that manage multi-dimensional data. A typical example of spatial join is to have two datasets: **Dataset P** (set of points in two dimensional space) as shown in Figure 1a, and **Dataset R** (set of rectangles in two dimensional space) as shown in Figure 1b. The spatial join operation is to join these two datasets and report any pair (rectangle r, point p) where p is contained in r (or even in the border of r).

![SpatialJoin](http://farm9.staticflickr.com/8119/8691360812_ff10a44097.jpg)

For example, the join between the two datasets shown in Figure 1, will result in.
   - \<r1, (3,15)\> 
   - \<r2, (1,2)\> 
   - \<r2, (2,4)\> 
   - \<r3, (2,4)\> 
   - \<r3, (4,3)\> 
   - \<r5, (6,2)\> 
   - \<r5, (7,7)\> 
   - \<r6, (10,4)\> 

Step 1 (Create Datasets)
---

- Your task in this step is to create the two datasets **P** (set of 2D points) and **R** (set of 2D rectangles). Assume the space extends from 1...10,000 in the both the X and Y axis. Each line will contain one object.
- Scale each dataset P or R to be at least 100MB.
- Choose the appropriate random function (of your choice) to create the points. For the rectangles, you
will need to also select a point at random (say the top-left corner), and then select two random variables that define the height and width of the rectangle. For example, the height random variable can be uniform between [1,20] and the width is also uniform between [1,5].

Step 2 (MapReduce job for Spatial Join)
---

In this step, you will write a java map-reduce job that implements the spatial join operation between the two datasets P and R based on the following requirements:

- The program takes an optional input parameter W(x1, y1, x2, y2) that indicate a spatial window
(rectangle) of interest within which we want to report the joined objects. If W is omitted, then the entire two sets should be joined.
o Example, referring to Figure 1, if the window parameter is W(1, 3, 3, 20), then the reported joined objects should be:
   - \<r1, (3,15)\> 
   - \<r2, (2,4)\> 
   - \<r3, (2,4)\>
- You should have a single map-reduce job to implement the spatial join operation.
