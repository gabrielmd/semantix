import scala.io.Source

class Graph (f: String) {

	/* methods of this class */

	// add a new edge to the graph
	def addEdge(a:Integer, b:Integer){
		if(!listVertices.contains(a)){
			listVertices = (a :: listVertices)
					mapEdges += (a -> List())
		}

		if(!listVertices.contains(b)){
			listVertices = (b :: listVertices)
					mapEdges += (b -> List())
		}

		// finally, update the adjacency lists
		mapEdges += (a -> (b :: mapEdges(a)))
				mapEdges += (b -> (a :: mapEdges(b)))
	}

	// will read the file and add new edges
	def loadGraphFromFile(){

		for (line <- Source.fromFile(this.filename).getLines()) {
			var vertices = line.split(" ")
					addEdge(vertices(0).toInt, vertices(1).toInt)
		}
	}

	// this method sum the shortest paths to every other vertex
	def sumOfShortestPaths(start : Integer): Float = {
			var sum = 0

					var visited = List[Integer]()
					var queue = List(start)
					var queueDistances = List(0)

					// check the whole component
					while(queue.length > 0){
						var next = queue.head
								queue = queue.takeRight(queue.length-1)

								var nextDistance: Integer = queueDistances.head
								queueDistances = queueDistances.takeRight(queueDistances.length - 1)

								// only visit new nodes
								if(!visited.contains(next)){
								  visited = (visited :+ next)
								  
									sum += nextDistance

											// append its neighbors to the queue
											for(neighbor <- mapEdges(next)){
												queue = (queue :+ neighbor)
												queueDistances = (queueDistances :+ (nextDistance + 1))
											}
								}
					}

			return sum
	}

	// compute centrality based on the sum of the shortest paths
	def computeCentrality() {
		var response = List[Centrality]()

				for(vertex <- listVertices){
					var sumDistances = sumOfShortestPaths(vertex)
							response = (response :+ new Centrality(vertex, (1/sumDistances)))
				}

		centrality = response.sorted
	}


	/* constructor */

	val filename = f

	// a graph will be represented by its adjacency list
	var listVertices: List[Integer] = List[Integer]()
	var mapEdges: Map[Integer, List[Integer]] = Map[Integer, List[Integer]]()

	var centrality: List[Centrality] = List[Centrality]()

	this.loadGraphFromFile()

}