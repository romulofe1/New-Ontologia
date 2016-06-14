package br.ufrpe.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.ufrpe.infraestrutura.Ontologia;
import br.ufrpe.infraestrutura.StanfordClassPLN;
import edu.stanford.nlp.trees.Tree;

public class JanelaPrincipal extends JFrame implements ActionListener{

	private JLabel lbTitulo, lbImagem; 
	private JButton btSalvar, btLimpar;
	private JTextField tfcampo1, tfcampo2, tfcampo3;
	private ImageIcon img;
	private JComboBox jcbOpc0, jcbOpc1, jcbOpc2;
	private JCheckBox ativarCampo;
	private Ontologia ont;
	private static StanfordClassPLN PLN;

	public JanelaPrincipal() {
		super("OWL - Web Ontology Language");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(400, 150);
		setSize(750, 400);
		setResizable(false);

		//Classes de negocio
		ont = new Ontologia();
		PLN = new StanfordClassPLN();
		

		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		lbTitulo = new JLabel("Inteligencia Artificial - OWL");
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 15));
		btLimpar = new JButton("Limpar");
		btSalvar = new JButton("Salvar");
		ativarCampo = new JCheckBox("Ativar este campo");

		tfcampo1 = new JTextField("", 20);
		tfcampo1.requestFocus();
		tfcampo2 = new JTextField("", 20);
		tfcampo3 = new JTextField("", 20);
		tfcampo3.setEditable(false);
		img = new ImageIcon("C:\\Users\\Felipe\\Desktop\\owl-teste\\coruja.jpg");
		lbImagem = new JLabel(img);

		jcbOpc0 = new JComboBox();
		jcbOpc0.addItem("");	
		jcbOpc0.addItem("all");				
		jcbOpc0.addItem("some");

		jcbOpc1 = new JComboBox();
		jcbOpc1.addItem("is a");
		jcbOpc1.addItem("is not a");
		jcbOpc1.addItem("are");		
		jcbOpc1.addItem("not");
		jcbOpc1.addItem("or");
		jcbOpc1.addItem("and");
		jcbOpc1.addItem("possui");
		jcbOpc1.addItem("vive");
		jcbOpc1.addItem("come");

		jcbOpc2 = new JComboBox();
		jcbOpc2.addItem("is a");
		jcbOpc2.addItem("is not a");
		jcbOpc2.addItem("are");		
		jcbOpc2.addItem("not");
		jcbOpc2.addItem("or");
		jcbOpc2.addItem("and");	
		jcbOpc2.addItem("possui");
		jcbOpc2.addItem("vive");
		jcbOpc2.addItem("come");


		getContentPane().add(lbTitulo);
		getContentPane().add(lbImagem);
		getContentPane().add(tfcampo3);
		getContentPane().add(tfcampo2);
		getContentPane().add(tfcampo1);
		getContentPane().add(btLimpar);
		getContentPane().add(btSalvar);
		getContentPane().add(jcbOpc0);
		getContentPane().add(jcbOpc1);
		getContentPane().add(jcbOpc2);
		getContentPane().add(ativarCampo);


		//Pos_Coluna, Pos_Linha, Comprim_Linha, Alt_Linha
		lbTitulo.setBounds(50, 65, 200, 100);
		lbImagem.setBounds(250, 25, 300, 180);
		tfcampo1.setBounds(95, 220, 130, 20);
		tfcampo2.setBounds(335, 220, 130, 20);
		tfcampo3.setBounds(575, 220, 130, 20);
		btLimpar.setBounds(575, 300, 130, 25);
		btSalvar.setBounds(425, 300, 130, 25);
		jcbOpc0.setBounds(5, 220, 80, 20);
		jcbOpc1.setBounds(240, 220, 80, 20);
		jcbOpc2.setBounds(480, 220, 80, 20);
		ativarCampo.setBounds(575, 250, 130, 20);

		//Eventos
		btLimpar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				tfcampo1.setText("");
				tfcampo2.setText("");
				tfcampo3.setText("");
				jcbOpc0.setSelectedIndex(0);
			}
		});

		ativarCampo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(ativarCampo.isSelected())
					tfcampo3.setEditable(true);
				else
					tfcampo3.setEditable(false);
			}
		});

		btSalvar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String item = (String) jcbOpc1.getSelectedItem();

				if(item.equals("is a") || item.equals("are")) {
					ont.criarSuperClasse((String) tfcampo1.getText(), (String) tfcampo2.getText());

					if(ativarCampo.isSelected()) {
						item = (String) jcbOpc2.getSelectedItem();
						if(item.equals("is a") || item.equals("are")) {
							ont.criarSuperClasse((String) tfcampo2.getText(), (String) tfcampo3.getText());
						}
					}
					//ont.saveOntology();
					//JOptionPane.showMessageDialog(null, "Axioma salvo");	
				} else if(item.equals("is not a")) {
					
					ont.criarClasseDisjunta((String) tfcampo1.getText(), (String) tfcampo2.getText());
										
				} else if(item.equals("and") || item.equals("or")){
					if(ativarCampo.isSelected()) {
						item = (String) jcbOpc2.getSelectedItem();
						if(item.equals("possui") || item.equals("come") || item.equals("vive")) {
							String prop = (String) jcbOpc2.getSelectedItem();
							System.out.println("testeeeeeeeee");
							if((String.valueOf(jcbOpc0.getSelectedItem()).equals("some"))){
								ont.criaRestricao(prop, "some", String.valueOf(jcbOpc1.getSelectedItem()),
										(String) tfcampo1.getText(), (String) tfcampo2.getText(), (String) tfcampo3.getText());	
								System.out.println("xxxxxx");
							} else if(String.valueOf(jcbOpc0.getSelectedItem()).equals("all")) {
								ont.criaRestricao(prop, "all", String.valueOf(jcbOpc1.getSelectedItem()),
										(String) tfcampo1.getText(), (String) tfcampo2.getText(), (String) tfcampo3.getText());									
							} else {
								ont.criaRestricao(prop, null, String.valueOf(jcbOpc1.getSelectedItem()),
										(String) tfcampo1.getText(), (String) tfcampo2.getText(), (String) tfcampo3.getText());								
							}

						}
					}

				} else {
					
				}
				
				ont.saveOntology();
				JOptionPane.showMessageDialog(null, "Axioma salvo");

			}
		});




	}

	public static void main(String[] args) throws IOException{
		//new JanelaPrincipal().setVisible(true);;
		for(Tree t : PLN.Teste("cat eats meat and fish")) {
			for(Tree t2 : PLN.buscaElementoPos(t, "NN"))				
				System.out.println(t2.children()[0].value());
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
