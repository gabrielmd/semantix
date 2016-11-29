# load graph info
l <- read.graph.info("edges.dat")

vertices <- l[["vertices"]]
edges <- l[["edges"]]

# calculate the shortest distances
shortest.paths <- calculate.shortest.paths(vertices, edges)

# calculate closeness centrality and rank
ans <- calculate.centrality(shortest.paths)

# print answer
print(ans)