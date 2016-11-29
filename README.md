# Semantix Challenge

## Proposal

In this challenge, suppose we are looking to do social network
analysis for prospective customers. We want to extract from
their social network a metric called "closeness centrality".

Centrality metrics try to approximate a measure of influence
of an individual within a social network. The distance between
any two vertices is their shortest path. The *farness*
of a given vertex *v* is the sum of all distances from each vertex
to *v*. Finally, the *closeness* of a vertex *v* is the inverse
of the *farness*.

### First part

The first part of the challenge is to rank the vertices in a given
graph by their *closeness*. The graph is provided in the attached
file; each line of the file consists of two vertex names separated by
a single space, representing an edge between those two nodes.

### Second part

The second part of the challenge is to create a RESTful web server
with endpoints to register edges and display the centrality of the graph.

## Solutions

In the respective folders, it is possible to find solutions using two different languages:
*R* and *SCALA*.

### Algorithm

To calculate the distances in the graph, the Breadth-First Search algorithm was used.
Given the edges have no weight, it can provide a successful solution in `O(n^2)` time.

### Strategy

It was expected that the method to display the centrality of the graph will be called
much more often than the method to register a new edge.
Therefore, to optimize the time, the graph's centrality is recomputed every time a new
edge is added.

By doing this, the time to show the graph's centrality remains constant.
On the other hand, the time to add a new edge is increased due to the complexity to
recompute the distances in the graph.