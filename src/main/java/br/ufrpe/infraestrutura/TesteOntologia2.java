package br.ufrpe.infraestrutura;

import java.io.File;

import javax.swing.JOptionPane;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class TesteOntologia2 {

	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException {
		
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		IRI ontologyIRI = IRI.create("http://www.co-ode.org/ontologies/ontology/fauna.owl");
		OWLOntology ontologia = manager.createOntology(ontologyIRI);		
		//System.out.println("Loaded ontology: " + pizzaOntology);

		File file = new File("C:\\Users\\Rômulo Ferreira\\Desktop\\fauna.owl");		
		manager.saveOntology(ontologia, IRI.create(file.toURI()));
		
		
		OWLDataFactory factory = manager.getOWLDataFactory();
		
		/*
		OWLClass clsA = factory.getOWLClass(IRI.create(ontologyIRI + "#A"));
		OWLClass clsB = factory.getOWLClass(IRI.create(ontologyIRI + "#B"));
		
		OWLAxiom axiom = factory.getOWLSubClassOfAxiom(clsA, clsB);		
		AddAxiom addAxiom = new AddAxiom(ontologia, axiom);
		
		manager.applyChange(addAxiom);
		

		for (OWLClass cls : ontologia.getClassesInSignature()) {
			System.out.println("Referenced class: " + cls);
		}
		 */
		
		String animal = JOptionPane.showInputDialog("Digite um animal: ");
		String tipo = JOptionPane.showInputDialog("Digite um tipo do animal: ");
		String base = "http://www.semanticweb.org/owlapi/ontologies/ontology#";
		
		PrefixManager pm = new DefaultPrefixManager(base);
		
		OWLClass clsAMethodA = factory.getOWLClass(":" + animal, pm);
		OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(clsAMethodA);
		
		manager.addAxiom(ontologia, declarationAxiom);
		
		OWLClass clsAMethodB = factory.getOWLClass(":" + tipo, pm);
		declarationAxiom = factory.getOWLDeclarationAxiom(clsAMethodB);
		
		manager.addAxiom(ontologia, declarationAxiom);
		
		
		manager.saveOntology(ontologia, IRI.create(file.toURI()));
		//manager.saveOntology(ontologia, new SystemOutDocumentTarget());
		
		
		
		
		
		animal = JOptionPane.showInputDialog("Digite outro animal: ");
		tipo = JOptionPane.showInputDialog("Digite um tipo do animal: ");
                
                
		
		
		clsAMethodA = factory.getOWLClass(IRI.create(base  + animal));
		//declarationAxiom = factory.getOWLDeclarationAxiom(clsAMethodA);
		
		//manager.addAxiom(ontologia, declarationAxiom);
		
		clsAMethodB = factory.getOWLClass(IRI.create(base + tipo));
		//declarationAxiom = factory.getOWLDeclarationAxiom(clsAMethodB);
		
		//manager.addAxiom(ontologia, declarationAxiom);
		
		
		OWLAxiom axiom = factory.getOWLSubClassOfAxiom(clsAMethodB, clsAMethodA);
		AddAxiom addAxiom = new AddAxiom(ontologia, axiom);
		// Use the manager to apply the change
		manager.applyChange(addAxiom);
		
		
		manager.saveOntology(ontologia, IRI.create(file.toURI()));
		manager.saveOntology(ontologia, new SystemOutDocumentTarget());
		
		
		
		
		

	}
}
