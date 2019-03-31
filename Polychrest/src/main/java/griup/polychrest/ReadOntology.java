package griup.polychrest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb.TDB;
import org.apache.jena.update.GraphStore;
import org.apache.jena.update.GraphStoreFactory;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;

public class ReadOntology {
	public static String owlForm="resource\\ontology_owl.owl";
	
	public static String link="http://polychrest/ontology";
	public static OntModel Ontologymodel = null;
	public static String localSource = "resource\\ontology_owl.owl";

public static OntModel loadAllClassesOnt() throws FileNotFoundException {
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
	    m.read(new FileInputStream(localSource),null);
	    return m;
	}	
	

	
public static String query (String query) throws FileNotFoundException {
    	
	return	queryResult(loadAllClassesOnt(),query);
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

public static void insert (String query) throws FileNotFoundException {
	
	InsertData(loadAllClassesOnt(),query);
    }


public static void InsertData (OntModel m,String query123) {
	
	GraphStore graphStore = GraphStoreFactory.create(m) ;
	String polychrest_ontology="resource\\polychrest_ontology.ttl";
//	Query query = QueryFactory.create(query123);
	//QueryExecution queryExecution = QueryExecutionFactory.create(query, m);
	//ResultSet results = queryExecution.execSelect();
	UpdateRequest request = UpdateFactory.create(query123);
    //String resultTxt = ResultSetFormatter.asText(results);
    //System.out.println(resultTxt);
    //queryExecution.close();
	//UpdateAction.readExecute(query123, m) ;
	//UpdateAction.parseExecute(query123, m) ;
	UpdateAction.execute(request, graphStore) ;
	TDB.sync(graphStore);
    //return resultTxt;
	try {
		m.write(new FileWriter(polychrest_ontology,false), "TURTLE");
		  m.write(new FileOutputStream(new File(localSource)),"RDF/XML-ABBREV");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}
}
