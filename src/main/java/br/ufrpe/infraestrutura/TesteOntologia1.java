package br.ufrpe.infraestrutura;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

public class TesteOntologia1 {

	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException {

		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		String base = "http://www.co-ode.org/ontologies/pizza/pizza.owl";

		IRI ontologyIRI = IRI.create(base);
		OWLOntology ontologia = manager.createOntology(ontologyIRI);		
		//System.out.println("Loaded ontology: " + pizzaOntology);

		File file = new File("C:\\Users\\Rômulo Ferreira\\Desktop\\local.owl");		
		manager.saveOntology(ontologia, IRI.create(file.toURI()));		



		// Now create the ontology - we use the ontology IRI (not the physical URI)
		//OWLOntology ontology = manager.createOntology(ontologyIRI);
		// Specify that A is a subclass of B. Add a subclass axiom.
		OWLDataFactory factory = manager.getOWLDataFactory();
		// Get hold of references to class A and class B.
		OWLClass clsA = factory.getOWLClass(IRI.create(ontologyIRI + "#A"));
		OWLClass clsB = factory.getOWLClass(IRI.create(ontologyIRI + "#B"));
		// Create the axiom
		OWLAxiom axiom = factory.getOWLSubClassOfAxiom(clsA, clsB);
		// Add the axiom to the ontology, so that the ontology states that A is a subclass of B.
		AddAxiom addAxiom = new AddAxiom(ontologia, axiom);
		// Use the manager to apply the change
		manager.applyChange(addAxiom);
		// The ontology will now contain references to class A and class B.


		/*
		for (OWLClass cls : ontology.getClassesInSignature()) {
		System.out.println("Referenced class: " + cls);
		}
		// We should also find that B is an ASSERTED superclass of A
		Set<OWLClassExpression> superClasses = clsA.getSuperClasses(ontology);
		System.out.println("Asserted superclasses of " + clsA + ":");
		for (OWLClassExpression desc : superClasses) {
		System.out.println(desc);
		}
		// Save the ontology to the location where we loaded it from, in the default ontology format
		 */
		for (OWLClass cls : ontologia.getClassesInSignature()) {
			System.out.println("Referenced class: " + cls);
		}

		manager.saveOntology(ontologia, IRI.create(file.toURI()));
		
		
		OWLObjectProperty hasPart = factory.getOWLObjectProperty(IRI.create(base + "#hasPart"));
		OWLClass nose = factory.getOWLClass(IRI.create(base + "#Nose"));
		
		OWLClassExpression hasPartSomeNose = factory.getOWLObjectSomeValuesFrom(hasPart, nose);
		
		//factory.getOWLObjectIntersectionOf(clsA, hasPartSomeNose);
		
		OWLClass head = factory.getOWLClass(IRI.create(base + "#Head"));
		// We now want to state that Head is a subclass of hasPart some Nose
		
		OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(head, hasPartSomeNose);
		// Add the axiom to our ontology
		AddAxiom addAx = new AddAxiom(ontologia, ax);
		manager.applyChange(addAx);
		
		
		manager.saveOntology(ontologia, IRI.create(file.toURI()));

		










		/*
		IRI iritwo = IRI.create("http://www.semanticweb.org/owlapi/ontologies/ontology#A");
		// Create the class
		OWLClass clsAMethodA = factory.getOWLClass(iritwo);

		OWLOntology ontology = manager.createOntology(IRI.create(
				"http://www.semanticweb.org/owlapi/ontologies/ontology"));
				// We can add a declaration axiom to the ontology, that essentially adds the class to the
				// signature of our ontology.
		OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(clsAMethodA);

		manager.addAxiom(ontology, declarationAxiom);
		 */
	}
}
