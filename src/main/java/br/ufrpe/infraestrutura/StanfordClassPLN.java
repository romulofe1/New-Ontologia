package br.ufrpe.infraestrutura;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap; 


public class StanfordClassPLN {

	
	public static List<Tree> Teste(String texto){
		
		List<Tree> lista = new LinkedList<Tree>();
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		Annotation document = new Annotation(texto);
		
		pipeline.annotate(document);
		
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		for(CoreMap sentence: sentences){
			Tree tree = sentence.get(TreeAnnotation.class);
			tree.pennPrint();
			lista.add(tree);
			
		}
		return lista;
	}
	
	public static List<Tree> buscaElementoPos(Tree tree, String tag){
		List<Tree> lista = new LinkedList<Tree>();
		
		for(Tree t: tree.children()){
			if(t.value().equalsIgnoreCase(tag)){
				lista.add(t);
			}
			else{
				lista.addAll(buscaElementoPos(t, tag));
			}
		}
		return lista;
	}
}
