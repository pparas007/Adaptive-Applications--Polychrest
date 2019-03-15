package griup.polychrest;

public class Main {
	public static void main(String[] args) throws Exception{
		OntologyFactory ontology=new OntologyFactory();
		ontology.intializeOntology();
		ontology.createOntology();
		ontology.createInstances();
		ontology.createRecommendationInstances();
		ontology.writeOntology();
	}
}
