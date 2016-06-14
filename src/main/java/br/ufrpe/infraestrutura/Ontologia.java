package br.ufrpe.infraestrutura;

import java.io.File;

import javax.swing.JOptionPane;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class Ontologia {

	private String base = "http://www.semanticweb.org/owlapi/ontologies/ontology#";
	private OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	private OWLDataFactory factory = manager.getOWLDataFactory();
	private IRI ontologyIRI = IRI.create("http://www.co-ode.org/ontologies/ontology/ontologia.owl");
	private OWLOntology ontologia;
	private File file = new File("/home/romulo/Área de Trabalho/ontologia.owl");


	public Ontologia() {		

		try {
			ontologia = manager.createOntology(ontologyIRI);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	


		try {
			manager.saveOntology(ontologia, IRI.create(file.toURI()));
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("ontology: " + ontologia);

	}



	public void criarSuperClasse(String classeOne, String classTwo) {

		OWLClass classeA = factory.getOWLClass(IRI.create(base  + classeOne));

		OWLClass classeB = factory.getOWLClass(IRI.create(base + classTwo));

		OWLAxiom axiom = factory.getOWLSubClassOfAxiom(classeA, classeB);
		AddAxiom addAxiom = new AddAxiom(ontologia, axiom);
		// Use the manager to apply the change
		manager.applyChange(addAxiom);

		saveOntology();

	}

	public void criarClasse(String classeOne) {

		PrefixManager pm = new DefaultPrefixManager(base);

		OWLClass classeA = factory.getOWLClass(":" + classeOne, pm);
		OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(classeA);

		manager.addAxiom(ontologia, declarationAxiom);

		saveOntology();


	}

	public void saveOntology() {
		try {
			manager.saveOntology(ontologia, IRI.create(file.toURI()));			
		} catch (OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	//Criação de restrição
	public void criaRestricao(String prop, String restricao, String ligacao, String classe1, String classe2, String classe3) {
	
		OWLObjectProperty propriedade = factory.getOWLObjectProperty(IRI.create(base + prop));
		OWLClass classeA = factory.getOWLClass(IRI.create(base + classe1));
		OWLClass classeB = factory.getOWLClass(IRI.create(base + classe2));
		OWLClass classeC = factory.getOWLClass(IRI.create(base + classe3));
                
                          

		OWLClassExpression someClasses = null;
		OWLClassExpression allClasses = null;
		OWLObjectIntersectionOf inter = null;
		OWLObjectUnionOf uni = null;
		OWLSubClassOfAxiom ax = null;
		
		
		if(restricao.equals("some")) {
			someClasses = factory.getOWLObjectSomeValuesFrom(propriedade, classeC);
			if(ligacao.equals("and")) {
				inter = factory.getOWLObjectIntersectionOf(classeA, classeB);
				ax = factory.getOWLSubClassOfAxiom(inter, someClasses);
			}
			else if(ligacao.equals("or")){
				uni = factory.getOWLObjectUnionOf(classeA, classeB);
				ax = factory.getOWLSubClassOfAxiom(uni, someClasses);
			}
		} else if(restricao.equals("all")) {
			allClasses = factory.getOWLObjectAllValuesFrom(propriedade, classeC);	
			if(ligacao.equals("and")) {
				inter = factory.getOWLObjectIntersectionOf(classeA, classeB);
				ax = factory.getOWLSubClassOfAxiom(inter, allClasses);
			}
			else if(ligacao.equals("or")){
				uni = factory.getOWLObjectUnionOf(classeA, classeB);
				ax = factory.getOWLSubClassOfAxiom(uni, allClasses);
			}
                        else if(ligacao.equals("not")){
                            //uni = factory.getOWLObjectComplementOf(allClasses);
                            ax = factory.getOWLSubClassOfAxiom(uni, allClasses);
                        }
		}
		

		// Add the axiom to our ontology		
		AddAxiom addAx = new AddAxiom(ontologia, ax);
		manager.applyChange(addAx);


		saveOntology();

	}
	
	public void criarClasseDisjunta(String classe1, String classe2) {
		
		OWLClass classeA = factory.getOWLClass(IRI.create(base + classe1));
		OWLClass classeB = factory.getOWLClass(IRI.create(base + classe2));
		
		OWLDisjointClassesAxiom declarationAxiom = factory.getOWLDisjointClassesAxiom(classeA, classeB);
		
		manager.addAxiom(ontologia, declarationAxiom);
		saveOntology();
		
	}
        
        public void criaRestricaoSplit(String text){
            
           /* String textArrayand[] = text.split("AND");
            String textArrayor[] = text.split("OR");
            String textArrayeat[] = text.split("eat");
            String textArraylife[] = text.split("life");
            String textArraynot[] = text.split("NOT");
           
           
         
            
            int somaStrings = textArrayand.length + textArrayeat.length
                    +textArraylife.length +textArraynot.length + textArrayor.length;
            
            for(int i = 0 ; i < somaStrings ; i++ ){
                if(textArrayand[0].equals("all")){
                    
                } */
           
           String string[] = text.split(" ");
           
           for(int i = 0 ; i < string.length;i++){
               if(string[i].equals("AND")){
               OWLClass classeA = factory.getOWLClass(IRI.create(base + string[i -1]));
               OWLClass classeB = factory.getOWLClass(IRI.create(base + string[i +i]));
                    if(string[i].equals("eat") || string[i].equals("life")){
                        
               }
           }
           }
            }
            
            
            
            
            
            
        }
