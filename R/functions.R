# Breadth-First Search in the graph to return shortest paths, given every edge has length = 1
bfs <- function(vertex, vertices, edges){
        
        response <- data.frame(rep(Inf, length(vertices)), row.names = vertices)
        
        # vertices to visit and distances
        queue <- c(vertex)
        queue.distance <- c(0)
        
        while(length(queue) > 0){
                
                # next vertex to visit
                next.v <- queue[1]
                next.distance <- queue.distance[1]
                
                # remove first from queues
                queue <- queue[-1]
                queue.distance <- queue.distance[-1]
                
                # if this vertex has not been visited, go ahead
                if(is.infinite(response[as.character(next.v), 1])){
                        response[as.character(next.v), 1] <- next.distance
                        
                        #append its neighbors to the queue
                        queue <- c(queue, edges[[as.character(next.v)]])
                        queue.distance <- c(queue.distance, rep(next.distance + 1, length(edges[[as.character(next.v)]])))
                }
        }
        return(response)
}


# this function will add a new edge to the list of edges in memory
add.edge <- function(vertex.a, vertex.b, all.vertices = vertices, all.edges = edges, global = TRUE){
        # if vertex A is new, add
        if(! (vertex.a %in% all.vertices)){
                all.vertices <- c(all.vertices, vertex.a)
                all.edges[[as.character(vertex.a)]] <- c(vertex.b)
        }else{ # otherwise, just append B to its list of neighbors
                all.edges[[as.character(vertex.a)]] <- unique(c(all.edges[[as.character(vertex.a)]], vertex.b))
        }
        
        # if vertex B is new, add
        if(! (vertex.b %in% vertices)){
                all.vertices <- c(all.vertices, vertex.b)
                all.edges[[as.character(vertex.b)]] <- c(vertex.a)
        }else{ # otherwise, just append B to its list of neighbors
                all.edges[[as.character(vertex.b)]] <- unique(c(all.edges[[as.character(vertex.b)]], vertex.a))
        }
        
        if(global){
                vertices <<- all.vertices
                edges <<- all.edges
        }
        
        return (list("vertices" = all.vertices, "edges" = all.edges))
}

# function to read the graph information
read.graph.info <- function(filename = "edges.dat"){
        file.edges <- read.table(filename, header = F)
        
        # first, register all vertices
        vertices <- unique(c(file.edges[,1], file.edges[,2]))
        edges <- list()
        
        # second, register all edges
        for(i in 1:nrow(file.edges)){
                v1 <- file.edges[i,1]
                v2 <- file.edges[i,2]
                
                l <- add.edge(v1, v2, vertices, edges, FALSE)
                vertices <- l[["vertices"]]
                edges <- l[["edges"]]
        }
        
        return(list("vertices" = vertices, "edges" = edges))
}

# calculate the shortest paths
calculate.shortest.paths <- function(vertices, edges){
        # for each vertex, run BFS's algorithm to annotate the shortest distance to the other vertices
        n.vertices <- length(vertices)
        # initialize a matrix with "Infinite" for the shortest distances
        all.shortest.paths <- data.frame(row.names = vertices)
        
        for(v in vertices){
                # run BFS from "v" -- return distances
                df.tmp <- bfs(v, vertices, edges)
                all.shortest.paths <- cbind(all.shortest.paths, df.tmp)
        }
        #name each column properly
        names(all.shortest.paths) <- vertices
        
        return(all.shortest.paths)
}

#function to calculate closeness centrality and return the ranked list of vertices
# higher centralities are showed first
calculate.centrality <- function(paths = shortest.paths){
        # calculate centrality for each vertex
        centrality <- 1 / colSums(paths)
        centrality.df <- data.frame(centrality = centrality, row.names = vertices)
        
        #return sorted data frame with smaller centrality first
        sorted.df <- data.frame(rank = 1:nrow(paths),
                                vertex = row.names(centrality.df)[order(centrality.df, decreasing = TRUE)],
                                centrality = centrality.df[order(centrality.df, decreasing = TRUE),])
        
        return(sorted.df)
}


# this API will add an edge and update information about centrality and shortest paths
#* @post /addEdge
api.add.edge <- function(a, b){
        add.edge(a, b, global = T)
        
        shortest.paths <<- calculate.shortest.paths(vertices, edges)
        
        # calculate closeness centrality and rank
        centrality <<- calculate.centrality(shortest.paths)
        
        #success
        return(TRUE)
}

# this API returns the rank of the vertices in a given graph by their "closeness"
#* @get /centrality
api.centrality <- function(){
        return (centrality)
}

#######
# The following lines will load the minimum information to start
######

# load graph info
l <- read.graph.info("edges.dat")

vertices <<- l[["vertices"]]
edges <<- l[["edges"]]

# calculate the shortest distances
shortest.paths <<- calculate.shortest.paths(vertices, edges)

# calculate closeness centrality and rank
centrality <<- calculate.centrality(shortest.paths)
