
var diameter = 600;
var margin = {
    top: 10,
    bottom: 70,
    left: 70,
    right: 20
};

var svg = d3.select("#BubbleChart").append("svg")
  .attr("width", diameter)
  .attr("height", diameter)
  .attr("class", "bubble")
  .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');
  
  
  

var pack = d3.pack()
            .size([diameter, diameter])
            .padding(1.5);

var tooltip = d3.select("#BubbleChart").append("div")	
    .attr("class", "tooltip")				
    .style("opacity", 0);

function update(year) {
	 
	var t = d3.transition().duration(1500);
d3.csv(year +'.csv', function(d){
	
	
	
    d.value = +d["User_Interest"];
    d.City = d["Food"]
    
    
    return d;
}, function(error,data){
    
    if (error) throw error;
    
    var color = d3.scaleOrdinal(d3.schemeCategory10);
	// var color = d3.scaleOrdinal(d3.scaleRainbow());
    
    var root = d3.hierarchy({children: data})
      .sum(function(d) { return d.value; })
    
    var node = svg.selectAll(".node")
    .data(pack(root).leaves())
    .enter().append("g")
      .attr("class", "node")
      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
 
    node.append("circle")
    .attr("id", function(d) { return d.id; })
    .attr("r", function(d) { return d.r; })
    .style("fill", function(d) { return color(d.data.City); });
    
    node.append("title")
            .text(function(d) {
                return "Food : " + d.data.City + "\nWeight : " + d.value ;
            });

    

    
    
    
  node.append("text")
    .attr("text-anchor", "middle")
    .text(function(d) {
    if (d.data.value > 0.7){
    return d.data.City;
    }
    return "";});

	
	//exit 
	
	node.exit().remove();
	
	//enter 
	var new_node = node.enter().data(pack(root).leaves())
					.attr("class", "node")
					.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
 
    node.select("circle")
    .attr("id", function(d) { return d.id; })
    .attr("r", function(d) { return d.r; })
    .style("fill", function(d) { return color(d.data.City); });
    
    node.append("title")
            .text(function(d) {
                return "Food : " + d.data.City + "\nWeight : " + d.value;
            });

    

    
    
    
  node.append("text")
    .attr("text-anchor", "middle")
    .text(function(d) {
    if (d.data.value > 0.7){
    return d.data.City;
    }
    return "";});
	
	
	//update
	new_node.merge(node)
			.transition(t)
					.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
 
    new_node.append("circle")
    .attr("id", function(d) { return d.id; })
    .attr("r", function(d) { return d.r; })
    .style("fill", function(d) { return color(d.data.City); });
    
    new_node.append("title")
            .text(function(d) {
                return "Food : " + d.data.City + "\nWeight : " + d.value;
            });

    

    
    
    
  new_node.append("text")
    .attr("text-anchor", "middle")
    .text(function(d) {
    if (d.data.value > 0.7){
    return d.data.City;
    }
    return "";});
			
	//eikhane shesh	
});
}




	var select =d3.select('year');
	select.on('change',function() {
		console.log(this.value);
		update(this.value);
	})
	
	var e = document.getElementById("year");
	var stre = e.options[e.selectedIndex].value;
	update(stre);
	//document.getElementById("form").onchange.submit();
   // document.location.reload(true);
    
