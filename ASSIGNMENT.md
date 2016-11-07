This exercise is designed to be straightforward and to take less than 10 hours.  If you would like to be creative, you are welcome to spend more time on it, but this is not required.


Please create a git repository on github.com and share it with me (https://github.com/vishl).  Please commit early and often so I can see how your code evolves.


Feel free to email, call, or text me with any questions.


#1)Design a simulator for daily blood sugar.


The purpose of this exercise is to evaluate your ability to write clear, modular, maintainable code.  One of the challenges of a startup is that we have to deal with constantly changing features, and the code needs to adapt quickly.  Well written code can be changed easily.


In general, eating food raises blood sugar and exercise lowers blood sugar.  We have created a simplified model of how these factors affect blood sugar.  Write a simulator that accepts several inputs of two types, food and exercise, each with a timestamp.  The output should be a “graph” of blood sugar over the course of the day, and a graph of the amount of “glycation” that occurred during the day.  


**Food:**
In our model, eating food will increase blood sugar linearly for two hours.  The rate of increase depends on the food as defined in a database that we will provide.  See the glycemic index column. (NOTE this is only loosely based on science)


**Exercise:**
Exercise decreases blood sugar linearly for one hour.


**Normalization:**
Blood sugar starts at 80 at the beginning of the day. If neither food nor exercise is affecting your blood sugar (it has been more than 1 or 2 hours), it will approach 80 linearly at a rate of 1 per minute.


**Glycation:**
For every minute your blood sugar stays above 150, increment “glycation” by 1.  This is a measure of how much crystallized sugar is accumulating in your blood stream which increases heart disease risk.


Interface and language
The interface and language can be whatever you want.  If you want to make a gui executable, go for it.  If you want to make a command line program that just outputs an array of integers, that is ok.  I will be evaluating the structure and modularity of your code.



