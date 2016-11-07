# Blood Sugar Simulation

This is an assignment from Health IQ to simulate the blood sugar levels of person during a day.

## Getting Started
cold run:
```
mvn install
```

or
```
mvn clean install
```

execute a simple random test
```
$ cd target
$ java -jar blood.sugar.simulator-1.0-SNAPSHOT-jar-with-dependencies.jar random
```

if you have an input file, be sure that the file follows the this format
timestamp (yyyy-MM-dd HH:mm:ss), index_number, type, type_id, type_name
```
for example
2016-11-07 07:00:00,80.0,normalize,0,Normalize
2016-11-07 07:10:00,30.0,food,21,Wheat tortilla
...
2016-11-07 22:15:00,40.0,exercise,3,Running
```

###For reference:
to access the exercise or food database execute this command
```
$ cd target
$ java -jar blood.sugar.simulator-1.0-SNAPSHOT-jar-with-dependencies.jar list
```

if you want to have this information dump into a csv file, execute this command
```
$ cd target
$ java -jar blood.sugar.simulator-1.0-SNAPSHOT-jar-with-dependencies.jar list_to_csv
```


### Prerequisites

This project works using Java so it is essential have it installed.
also it is build on maven so it is indispensable have it installed.

## Running the tests

execute a simple random test
```
$ cd target
$ java -jar blood.sugar.simulator-1.0-SNAPSHOT-jar-with-dependencies.jar random
```

execute it with a input file
```
$ cd target
$ java -jar blood.sugar.simulator-1.0-SNAPSHOT-jar-with-dependencies.jar _<path_to_input_data>_
```

## Authors

* **Salomon Lee**
