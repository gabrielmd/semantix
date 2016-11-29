

class Centrality(vertexRepresentation: Integer, valCentrality: Float) extends Ordered[Centrality] {
  var vertex = vertexRepresentation
  var value = valCentrality
 
  override def compare(that: Centrality): Int = {
    if(this.value > that.value){
      return -1
    }else if(this.value < that.value){
      return 1
    }
    return 0  
  }
  
  override def toString : String = {
    return (this.vertex).toString() + ", " + (this.value).toString()
  }
  
}