# Solution using R

The functions designed to solve this challenge can be found in file *[functions.R](https://github.com/gabrielmd/semantix/tree/master/R/functions.R)*.

## First part

File `solve.R` can be executed. 

The output is the rank of vertices based on their closeness.
It is not necessary to install any external packages.

Example of output:

``` 
      rank vertex  centrality
1      1     44 0.005988024
2      2     88 0.005917160
3      3     33 0.005882353
4      4     74 0.005847953
5      5     51 0.005813953
```

## Second part

To start the WebServer, the code in the file `solve.R` can be executed. 
There are two APIs that will run at the port $8080$: 

 * `GET /centrality` -- there is no extra parameter. It returns the rank of vertices based on their closeness.
 * `POST /addEdge` -- there are two parameters (`a` and `b`) to identify the vertices. It will add the new edge and recompute the closeness of the graph. The API returns `TRUE` when the procedure is over.

For the second part, it is necessary to install two external packages: `devtools` and `plumber`.
If needed, the installation is made when file `server.R` is sourced.

Examples of outputs:

 * `GET /centrality`
 
```
 [
  {
    "rank": 1,
    "vertex": "44",
    "centrality": 0.006
  },
  {
    "rank": 2,
    "vertex": "88",
    "centrality": 0.0059
  },
  {
    "rank": 3,
    "vertex": "33",
    "centrality": 0.0059
  },
  {
    "rank": 4,
    "vertex": "74",
    "centrality": 0.0058
  },
  {
    "rank": 5,
    "vertex": "51",
    "centrality": 0.0058
  }
]
```

 * `POST /addEdge`
 
 ```
[
  true
]
 ```
