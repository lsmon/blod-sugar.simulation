# blood-sugar.simulation

**A Simulator for daily blood sugar.**

In general, eating food raises blood sugar and exercise lowers blood sugar.
This project just simulates what can happen hypothetically with the sugar levels on the blood after eating and exercising.

__Food:__
In our model, eating food will increase blood sugar linearly for two hours.  The rate of increase depends on the food as defined in a database that we will provide.  See the glycemic index column. (NOTE this is only loosely based on science)


__Exercise:__
Exercise decreases blood sugar linearly for one hour.


__Normalization:__
Blood sugar starts at 80 at the beginning of the day. If neither food nor exercise is affecting your blood sugar (it has been more than 1 or 2 hours), it will approach 80 linearly at a rate of 1 per minute.


__Glycation:__
For every minute your blood sugar stays above 150, increment “glycation” by 1.  This is a measure of how much crystallized sugar is accumulating in your blood stream which increases heart disease risk.
