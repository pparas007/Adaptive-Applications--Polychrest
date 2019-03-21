package griup.polychrest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;

public class ReadOntology {

public static void query () throws FileNotFoundException {
    	
		queryResult(loadAllClassesOnt());
    }
	public static String link="http://polychrest/ontology";
	public static OntModel Ontologymodel = null;
	public static String localSource = "resource\\ontology_owl.owl";
	
	public static OntModel loadAllClassesOnt() throws FileNotFoundException {
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        m.read(new FileInputStream(localSource),null);
        return m;
	}
	
public static void queryResult (OntModel m) {
		
		String queryString = "PREFIX base:  <http://polychrest/ontology#>\r\n" + 
				"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\r\n" + 
				"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"\r\n" + 
				"select ?shopping where \r\n" + 
				"{\r\n" + 
				"?shopping a base:shopping\r\n" + 
				"}";
		
		Query query = QueryFactory.create(queryString);
		QueryExecution queryExecution = QueryExecutionFactory.create(query, m);
		ResultSet results = queryExecution.execSelect();

        String resultTxt = ResultSetFormatter.asText(results);
        System.out.println(resultTxt);
        queryExecution.close();
        
        
		
		
	}
}
