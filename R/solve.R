#######
# The following lines will load the minimum information to start
######

source("functions.R")

# load graph info
l <- read.graph.info("aux/edges.dat")

vertices <<- l[["vertices"]]
edges <<- l[["edges"]]

# calculate the shortest distances
shortest.paths <<- calculate.shortest.paths(vertices, edges)

# calculate closeness centrality and rank
centrality <<- calculate.centrality(shortest.paths)

# print answer
print(centrality)