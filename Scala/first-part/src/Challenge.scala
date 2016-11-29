object Challenge {
  def main(args: Array[String]){
    
    var filename = args(0)
    
    // read file and load graph info
    var graph = new Graph(filename)

    // calculate closeness centrality and rank
    graph.computeCentrality()

    println("vertice, centrality")
    for(c <- graph.centrality){
      println(c.toString)
    }
  }
}