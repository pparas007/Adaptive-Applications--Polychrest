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

public static String query (String query) throws FileNotFoundException {
    	
	return	queryResult(loadAllClassesOnt(),query);
    }
	public static String link="http://polychrest/ontology";
	public static OntModel Ontologymodel = null;
	public static String localSource = "resource\\ontology_owl.owl";
	
	public static OntModel loadAllClassesOnt() throws FileNotFoundException {
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        m.read(new FileInputStream(localSource),null);
        return m;
	}
	
public static String queryResult (OntModel m,String query123) {
		
		
		
		Query query = QueryFactory.create(query123);
		QueryExecution queryExecution = QueryExecutionFactory.create(query, m);
		ResultSet results = queryExecution.execSelect();

        String resultTxt = ResultSetFormatter.asText(results);
        //System.out.println(resultTxt);
        queryExecution.close();
        
        return resultTxt;
		
		
	}
}
