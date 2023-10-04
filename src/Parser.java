import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Parser {
	
	public static ArrayList<FunctionalChain> CHAINS = new ArrayList<>();
	public static Hashtable<String, StorageMibField> HASHTABLE = new Hashtable<>();
	
	/**
	 * Main method whose behavior is:
	 * - Parse of the original xml file thanks to the DOM library.
	 * Generates the files requested: methods GenerateXCFiles and GenerateROCFiles
	 * <p>
	 * Note: This method calls provides in stderr the following errors information:
	 * <p>
	 * Avertissement : la validation a �t� activ�e mais aucun �l�ment org.xml.sax.ErrorHandler n'a �t� d�fini, ce qui devrait probablement �tre le cas. L'analyseur utilisera un gestionnaire d'erreurs par d�faut pour imprimer les 0 premi�res erreurs. Appelez la m�thode 'setErrorHandler' pour r�soudre ce probl�me.
	 * Error: URI=file:/C:/Users/Public/Metamodel_Capella/Workspace/Parser/Capella%20Light%20Metamodel.capella Line=20: L'�l�ment racine de document "org.polarsys.capella.core.data.capellamodeller:Project" doit correspondre � la racine DOCTYPE "null".
	 * Error: URI=file:/C:/Users/Public/Metamodel_Capella/Workspace/Parser/Capella%20Light%20Metamodel.capella Line=20: Le document nest pas valide : aucune grammaire d�tect�e.
	 * This is due to the fact that coding has been done without predefined grammar rules, coded by hands in the file.
	 * This might be done as future work.
	 * <p>
	 * Method to be used in case of generation of command-line application.
	 *
	 * @param args:
	 */
//  static OwnedPhysicalFunctions physicalFunction = null;
	public static void main(String[] args) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		
		try {
			factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			// input xml file */
//			File fileXML = new File("src/resources/TP_modelling.capella");
			File fileXML = new File("src/resources/Communication.capella");
			//File fileXML = new File("Cours_ENSIBS.capella");
			
			/* DOM library element used to store elements of xml files (only syntactic) */
			Document xml;
			try {
				// DOM method to parse the xml File */
				xml = builder.parse(fileXML);
				/* root DOM structure storing the XML files elements */
				Element root = xml.getDocumentElement();
				
				// HashTable to store all the parsed elements
				//key Id : identifier
				//Element: storage
				Hashtable<String, StorageMibField> mibFieldTable = new Hashtable<String, StorageMibField>();
				
				// Models Files generation*/
				GenerateModelExtraction(root, "", mibFieldTable);
//           getTheFunctionalChains(mibFieldTable);
			
			} catch (SAXParseException e) {
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @param root
	 * @param genDirectory
	 * @param table
	 */
	public static void GenerateModelExtraction(Element root, String genDirectory, Hashtable<String, StorageMibField> table) {
		ArrayList<StorageMibField> storageMibFieldsList = new ArrayList<StorageMibField>(0);
		/*added*/
		/* used to keep the link between classes and properties related*/
		/* to build back the mib path of an OID */
		Hashtable<String, StorageMibField> mibFieldTable = new Hashtable<String, StorageMibField>();
		Hashtable<String, ArrayList<String>> oidToOpTable = new Hashtable<String, ArrayList<String>>();
		ParseAndStore(root, "Logical Architecture", storageMibFieldsList, mibFieldTable, oidToOpTable, table);
		
		/* Après le parsing on effectue le linkage, car la table est complétement remplie */
		constructLinks(storageMibFieldsList, table);
		
		/* Après le linkage on ajoute à nos FunctionalChain les FunctionalChainInvolvements associés */
		ArrayList<FunctionalChain> chains = (ArrayList<FunctionalChain>) constructChains(table);
		
		for (FunctionalChain c : chains) {
			System.out.println(c.getName() + " of size: " + c.getChainInvolvements().size());
		}
		
		/* Variables globales pour après */
		CHAINS = chains;
		HASHTABLE = table;
		
		System.out.println("==== End of parsing ====");
	}
	
	/**
	 * Method which Parse and Store in the storageMibFieldsList of the xml file part related to the package pkgName
	 *
	 * @param root
	 * @param pkgName
	 * @param storageMibFieldsList
	 */
	public static void ParseAndStore(Node root, String pkgName, ArrayList<StorageMibField> storageMibFieldsList, Hashtable<String, StorageMibField> operationTable, Hashtable oidToOpTable, Hashtable<String, StorageMibField> table) {
		NodeList list = getNode(root, pkgName, storageMibFieldsList, operationTable, oidToOpTable, pkgName, table);
		if (list != null) {
			for (int i = 0; i < list.getLength(); i++) {
				Node n2 = list.item(i);
				//si le n�ud enfant est un Element, nous le traitons
				if (n2 instanceof Element) {
					//appel r�cursif � la m�thode pour le traitement du n�ud et de ses enfants
					n2.getNodeValue();
					description(n2, "", storageMibFieldsList, operationTable, oidToOpTable, pkgName, table);
				}
			}
		}
	}
	
	private static List<FunctionalChain> constructChains(Hashtable<String, StorageMibField> table) {
		ArrayList<FunctionalChain> chains = new ArrayList<>();
		for (StorageMibField field : table.values()) {
			if (field instanceof FunctionalChain functionalChain) {
				for (String id : functionalChain.getChainInvolvementsIds()) {
					StorageMibField chainInvolvementField = table.get(id);
					if (chainInvolvementField instanceof FunctionalChainInvolvements_function involvementsFunction) {
						functionalChain.addChainInvolvements(involvementsFunction);
					}
					if (chainInvolvementField instanceof FunctionalChainInvolvements_exchange involvementsExchange) {
						functionalChain.addChainInvolvements(involvementsExchange);
					}
				}
				chains.add(functionalChain);
			}
		}
		return chains;
	}
	
	private static void constructLinks(ArrayList<StorageMibField> storageMibFields, Hashtable<String, StorageMibField> table) {
		for (StorageMibField field : storageMibFields) { //TODO peut-etre foreach direct sur la table
//			if (field instanceof Function) {
//				Function function = ((Function) field);
//
//				//TODO peut-etre mettre input output ici
//			}
			/* LINKING FUNCTIONAL EXCHANGE */
			if (field instanceof FunctionalExchange functionalExchange) {
				/* Link output */
				StorageMibField output = table.get(functionalExchange.getOutputId());
				if (output instanceof Output) {
					functionalExchange.setOutput((Output) output);
				}
				
				/* Link input */
				StorageMibField input = table.get(functionalExchange.getInputId());
				if (input instanceof Input) {
					functionalExchange.setInput((Input) input);
				}
				
				/* Remplace l'élément complet */
				table.replace(functionalExchange.GetId(), functionalExchange);
			}
			/* LINKING FUNCTIONAL CHAIN INVOLVEMENTS */
			if (field instanceof AFunctionalChainInvolvements functionalChainInvolvements) {
				/* Involved pointe vers "Function" ou vers "FunctionalExchange" */
				StorageMibField involvedField = table.get(functionalChainInvolvements.getInvolvedId());
				
				/* CASE FUNCTION */
				if (involvedField instanceof Function function) {
					FunctionalChainInvolvements_function functionalChainInvolvements_function = (FunctionalChainInvolvements_function) functionalChainInvolvements;
					functionalChainInvolvements_function.setFunction(function);
					
					/* Remplace l'élément complet */
					table.replace(functionalChainInvolvements_function.GetId(), functionalChainInvolvements_function);
				}
				/* CASE FUNCTIONAL EXCHANGE */
				if (involvedField instanceof FunctionalExchange functionalExchange) {
					FunctionalChainInvolvements_exchange functionalChainInvolvements_exchange = (FunctionalChainInvolvements_exchange) functionalChainInvolvements;
					functionalChainInvolvements_exchange.setExchange(functionalExchange);
					
					/* Set source field */
					StorageMibField sourceField = table.get(functionalChainInvolvements_exchange.getSourceId());
					if (sourceField instanceof FunctionalChainInvolvements_function previous) {
						functionalChainInvolvements_exchange.setSource(previous);
					}
					
					/* Set target field */
					StorageMibField targetField = table.get(functionalChainInvolvements_exchange.getTargetId());
					if (targetField instanceof FunctionalChainInvolvements_function next) {
						functionalChainInvolvements_exchange.setTarget(next);
					}
					
					/* Remplace l'élément complet */
					table.replace(functionalChainInvolvements_exchange.GetId(), functionalChainInvolvements_exchange);
				}
			}
		}
	}
	
	/**
	 * M�thode qui va parser le contenu d'un n�ud.
	 * Adapt� d'un exemple d'utilisation de la librairie DOM.
	 * L'appel � ExtractFields sert � la singularisation des balises et r�cup�ration
	 * A faire: voir comment extraire l'appel � ExtractFieldshors de la m�thode.
	 * Used in ParseAndStore
	 *
	 * @param n
	 * @param tab
	 * @return
	 */
	
	public static String description(Node n, String tab, ArrayList storageMibFieldsList, Hashtable<String, StorageMibField> operationTable, Hashtable oidToOpTable, String pkgName, Hashtable<String, StorageMibField> table) {
		String str = new String();
		//Nous nous assurons que le n�ud pass� en param�tre est une instance d'Element
		//juste au cas o� il s'agisse d'un texte ou d'un espace, etc.
		if (n instanceof Element) {
			
			//Nous sommes donc bien sur un �l�ment de notre document
			//Nous castons l'objet de type Node en type Element
			Element element = (Element) n;
			
			//Nous pouvons r�cup�rer le nom du n�ud actuellement parcouru
			//gr�ce � cette m�thode, nous ouvrons donc notre balise
			str += "<" + n.getNodeName();
			//System.out.println(str);
			
			//nous contr�lons la liste des attributs pr�sents
			if (n.getAttributes() != null && n.getAttributes().getLength() > 0) {
				
				//nous pouvons r�cup�rer la liste des attributs d'un �l�ment
				NamedNodeMap att = n.getAttributes();
				int nbAtt = att.getLength();
				
				//nous parcourons tous les n�uds pour les afficher
				for (int j = 0; j < nbAtt; j++) {
					Node noeud = att.item(j);
					//On r�cup�re le nom de l'attribut et sa valeur gr�ce � ces deux m�thodes
					str += " " + noeud.getNodeName() + "=\"" + noeud.getNodeValue() + "\" ";
					//Extraction des champs
				}
			}
			
			//nous refermons notre balise car nous avons trait� les diff�rents attributs
			str += ">";
			// ExtractFields(str, storageMibFieldsList);
			
			//La m�thode getChildNodes retournant le contenu du n�ud + les n�uds enfants
			
			//Nous r�cup�rons le contenu texte uniquement lorsqu'il n'y a que du texte, donc un seul enfant
			
			if (n.getChildNodes().getLength() == 1) str += n.getTextContent();
			
			//Nous allons maintenant traiter les n�uds enfants du n�ud en cours de traitement
			int nbChild = n.getChildNodes().getLength();
			
			//Nous r�cup�rons la liste des n�uds enfants
			NodeList list = n.getChildNodes();
			
			String tab2 = tab + "\t";
			
			//nous parcourons la liste des n�uds
			
			//reorder modification
			for (int i = 0; i < nbChild; i++) {
				//       for(int i = nbChild -1; i == 0 ; i--){
				Node n2 = list.item(i);
				
				//si le n�ud enfant est un Element, nous le traitons
				if (n2 instanceof Element) {
					//appel r�cursif � la m�thode pour le traitement du n�ud et de ses enfants
					str += "\n " + tab2 + description(n2, tab2, storageMibFieldsList, operationTable, oidToOpTable, pkgName, table);
				}
			}
			
			//Nous fermons maintenant la balise
			
			if (n.getChildNodes().getLength() < 2) {
				str += "</" + n.getNodeName() + ">";
//             System.out.println(str);
			} else {
				str += "\n" + tab + "</" + n.getNodeName() + ">";
//            System.out.println(str);
			}
		}
		
		ExtractFields(str, pkgName, storageMibFieldsList, operationTable, oidToOpTable, table);
		return str;
		
	}
	
	/**
	 * M�thode qui remonte sous la forme d'une liste de noeud l'arbre syntaxique xml � partir du noeud n
	 * dont la racine a comme valeur strId.
	 * storageMibFieldsList used only in a call to the "description" method.
	 *
	 * @param n
	 * @param storageMibFieldsList
	 * @return
	 */
	
	public static NodeList getNode(Node n, String strId, ArrayList storageMibFieldsList, Hashtable operationTable, Hashtable oidToOpTable, String pkgName, Hashtable<String, StorageMibField> table) {
		NodeList n_return = null;
		Boolean reached = false;
		
		//Nous nous assurons que le n�ud pass� en param�tre est une instance d'Element
		//juste au cas o� il s'agisse d'un texte ou d'un espace, etc.
		if (n instanceof Element) {
			
			//Nous sommes donc bien sur un �l�ment de notre document
			//Nous castons l'objet de type Node en type Element
			Element element = (Element) n;
			
			//nous contr�lons la liste des attributs pr�sents
			if (n.getAttributes() != null && n.getAttributes().getLength() > 0) {
				
				//nous pouvons r�cup�rer la liste des attributs d'un �l�ment
				NamedNodeMap att = n.getAttributes();
				int nbAtt = att.getLength();
				
				//nous parcourons tous les n�uds pour les afficher
				for (int j = 0; j < nbAtt; j++) {
					Node noeud = att.item(j);
					//On r�cup�re le nom de l'attribut et sa valeur gr�ce � ces deux m�thodes
					if (noeud.getNodeValue().equals(strId)) {
						n_return = n.getChildNodes();
						reached = true;
//            	   System.out.println("nbAtt=" + nbAtt);
					}
//               System.out.println(noeud.getNodeValue());
				}
			}
			
			//Nous allons maintenant traiter les n�uds enfants du n�ud en cours de traitement
			if (reached == false) {
				int nbChild = n.getChildNodes().getLength();
				
				//Nous r�cup�rons la liste des n�uds enfants
				
				NodeList list = n.getChildNodes();
				//nous parcourons la liste des n�uds
				//        for(int i = 0; i < nbChild; i++){
				//reorder
				for (int i = nbChild - 1; i > 0; i--) {
					Node n2 = list.item(i);
					//si le n�ud enfant est un Element, nous le traitons
					
					if (n2 instanceof Element) {
						//appel r�cursif � la m�thode pour le traitement du n�ud et de ses enfants
						n_return = getNode(n2, strId, storageMibFieldsList, operationTable, oidToOpTable, pkgName, table);
					}
				}
			}
		}
		
		if (reached == true) {
			String str = "";
			String tab = "";
			int nbChild = n.getChildNodes().getLength();
			
			//Nous r�cup�rons la liste des n�uds enfants
			
			NodeList list = n.getChildNodes();
			String tab2 = tab + "\t";
			
			//nous parcourons la liste des n�uds
			
			for (int i = 0; i < nbChild; i++) {
				Node n2 = list.item(i);
				
				//si le n�ud enfant est un Element, nous le traitons
				if (n2 instanceof Element) {
					//appel r�cursif � la m�thode pour le traitement du n�ud et de ses enfants
					str += "\n " + tab2 + description(n2, tab2, storageMibFieldsList, operationTable, oidToOpTable, pkgName, table);
				}
			}
		}
		return n_return;
	}
	
	/**
	 * Method which, for a particular xml code related to a beacon parse this string (str) to store it as a subclass instance of StorageMibField.
	 * This instance may have as attibutes previous parsed xml parts stored in the StorageMibField list.
	 *
	 * @param str
	 * @param storageMibFieldsList
	 */
	
	public static void ExtractFields(String str, String pkgName, ArrayList<StorageMibField> storageMibFieldsList, Hashtable<String, StorageMibField> operationTable, Hashtable oidToOpTable, Hashtable<String, StorageMibField> table) {
		// Get the Beacon Type
		int cnt;
		StorageMibField.BeaconType beacon = StorageMibField.BeaconType.NOBEACON;
		
		if (str.startsWith("<ownedAssociations")) beacon = StorageMibField.BeaconType.OWNEDASSOCIATION;
		if (str.startsWith("<ownedMembers")) beacon = StorageMibField.BeaconType.OWNEDMEMBER;
		if (str.startsWith("<ownedMinCard")) beacon = StorageMibField.BeaconType.OWNEDCARD;
		if (str.startsWith("<ownedMaxCard")) beacon = StorageMibField.BeaconType.OWNEDCARD;
		if (str.startsWith("<ownedClasses")) beacon = StorageMibField.BeaconType.OWNEDCLASS;
		if (str.startsWith("<ownedExtensions")) beacon = StorageMibField.BeaconType.OWNEDEXTENSION;
		if (str.startsWith("<containedGenericTraces")) beacon = StorageMibField.BeaconType.CONTAINEDGENERICTRACE;
		if (str.startsWith("<containedProperties")) beacon = StorageMibField.BeaconType.CONTAINEDPROPERTY;
		if (str.startsWith("<ownedDataTypes")) beacon = StorageMibField.BeaconType.OWNEDDATATYPE;
		if (str.startsWith("<ownedPropertyValues")) beacon = StorageMibField.BeaconType.OWNEDPROPERTYVALUE;
		if (str.startsWith("<domainValue")) beacon = StorageMibField.BeaconType.DOMAINVALUE;
		if (str.startsWith("<ownedLiterals")) beacon = StorageMibField.BeaconType.OWNEDLITERAL;
		if (str.startsWith("<ownedVariabilityFeatures")) beacon = StorageMibField.BeaconType.VARIABILITYFEATURES;
		//Change of name from ownedVariabilityFeatures to ownedFilteringCriteria in case of orchestra 5.8.1
		if (str.startsWith("<ownedFilteringCriteria")) beacon = StorageMibField.BeaconType.VARIABILITYFEATURES;
		if (str.startsWith("<ownedExchangeItem")) beacon = StorageMibField.BeaconType.OWNEDEXCHANGEITEM;
		if (str.startsWith("<ownedElements")) beacon = StorageMibField.BeaconType.OWNEDELEMENT;
		if (str.startsWith("<ownedInterfaces")) beacon = StorageMibField.BeaconType.OWNEDINTERFACES;
		if (str.startsWith("<ownedExchangeItemAllocations")) beacon = StorageMibField.BeaconType.OWNEDEXCHANGEITEMALLOCATIONS;
		if (str.startsWith("<ownedInterfacePkgs")) beacon = StorageMibField.BeaconType.OWNEDINTERFACEPKGS;
		if (str.startsWith("<ownedMessages")) beacon = StorageMibField.BeaconType.OWNEDMESSAGES;
		
		//added in the switch to comply with .melodymodeler files beacons
		if (str.startsWith("<ownedGeneralizations")) beacon = StorageMibField.BeaconType.OWNEDGENERALIZATIONS;
		if (str.startsWith("<ownedFeatures")) beacon = StorageMibField.BeaconType.OWNEDFEATURES;
		if (str.startsWith("<ownedTraces")) beacon = StorageMibField.BeaconType.OWNEDTRACES;
		if (str.startsWith("<ownedInformationRealizations")) beacon = StorageMibField.BeaconType.OWNEDINFORMATIONREALIZATIONS;
		if (str.startsWith("<ownedDefaultValue")) beacon = StorageMibField.BeaconType.OWNEDDEFAULTVALUE;
		if (str.startsWith("<ownedUnits")) beacon = StorageMibField.BeaconType.OWNEDUNITS;
		if (str.startsWith("<ownedMinValue")) beacon = StorageMibField.BeaconType.OWNEDMINVALUE;
		if (str.startsWith("<ownedMaxValue")) beacon = StorageMibField.BeaconType.OWNEDMAXVALUE;
		if (str.startsWith("<ownedMinLength")) beacon = StorageMibField.BeaconType.OWNEDMINLENGTH;
		if (str.startsWith("<ownedMaxLength")) beacon = StorageMibField.BeaconType.OWNEDMAXLENGTH;
		if (str.startsWith("<ownedInstanceRoles")) beacon = StorageMibField.BeaconType.OWNEDINSTANCEROLES;
		if (str.startsWith("<ownedInteractionFragments")) beacon = StorageMibField.BeaconType.OWNEDINTERACTIONFRAGMENTS;
		if (str.startsWith("<ownedTimeLapses")) beacon = StorageMibField.BeaconType.OWNEDTIMELAPSES;
		if (str.startsWith("<ownedEvents")) beacon = StorageMibField.BeaconType.OWNEDEVENTS;
		
		if (str.startsWith("<ownedScenarios")) beacon = StorageMibField.BeaconType.OWNEDSCENARIOS;
		if (str.startsWith("<ownedSystemCapabilityInvolvement")) beacon = StorageMibField.BeaconType.OWNEDSYSTEMCAPABILITYINVOLVMENT;
		if (str.startsWith("<ownedSpecification")) beacon = StorageMibField.BeaconType.OWNEDSPECIFICATION;
		if (str.startsWith("<ownedConstraints")) beacon = StorageMibField.BeaconType.OWNEDCONSTRAINTS;
		if (str.startsWith("<ownedActorCapabilityInvolvements")) beacon = StorageMibField.BeaconType.OWNEDACTORCAPABILITYINVOLVMENTS;
		
		if (str.startsWith("<ownedCapabilities")) beacon = StorageMibField.BeaconType.OWNEDCAPABILITIES;
		if (str.startsWith("<ownedCapabilityPkgs")) beacon = StorageMibField.BeaconType.OWNEDCAPABILITYPKG;
		
		//added from physical part parsing
		if (str.startsWith("<ownedFunctionalChainInvolvements")) beacon = StorageMibField.BeaconType.OWNEDFUNCTIONALCHAININVOLVMENTS;
		if (str.startsWith("<ownedFunctionalChainRealizations")) beacon = StorageMibField.BeaconType.OWNEDFUNCTIONALCHAINREALIZATIONS;
		if (str.startsWith("<ownedPhysicalComponents")) beacon = StorageMibField.BeaconType.OWNEDPHYSICALCOMPONENTS;
		if (str.startsWith("<ownedPhysicalComponentPkg")) beacon = StorageMibField.BeaconType.OWNEDPHYSICALCOMPONENTPKG;
		if (str.startsWith("<ownedLogicalArchitectureRealizations")) beacon = StorageMibField.BeaconType.OWNEDLOGICALARCHITECTUREREALIZATIONS;
		if (str.startsWith("<ownedFunctions")) beacon = StorageMibField.BeaconType.OWNEDFUNCTIONS;
		if (str.startsWith("<ownedFunctionalChains")) beacon = StorageMibField.BeaconType.OWNEDFUNCTIONALCHAINS;
		if (str.startsWith("<ownedPortRealizations")) beacon = StorageMibField.BeaconType.OWNEDPORTREALIZATIONS;
		if (str.startsWith("<ownedFunctionRealizations")) beacon = StorageMibField.BeaconType.OWNEDFUNCTIONREALIZATIONS;
		if (str.startsWith("<outputs")) beacon = StorageMibField.BeaconType.OUTPUTS;
		if (str.startsWith("<inputs")) beacon = StorageMibField.BeaconType.INPUTS;
		if (str.startsWith("<ownedFunctionalExchangeRealizations")) beacon = StorageMibField.BeaconType.OWNEDFUNCTIONALEXCHANGEREALIZATIONS;
		if (str.startsWith("<ownedFunctionalExchanges")) beacon = StorageMibField.BeaconType.OWNEDFUNCTIONALEXCHANGES;
		if (str.startsWith("<ownedPhysicalFunctions")) beacon = StorageMibField.BeaconType.OWNEDPHYSICALFUNCTIONS;
		if (str.startsWith("<ownedFunctionPkg")) beacon = StorageMibField.BeaconType.OWNEDFUNCTIONALPKG;
		if (str.startsWith("<ownedCapabilityRealizationInvolvements")) beacon = StorageMibField.BeaconType.OWNEDCAPABILITYREALIZATIONINVOLVMENTS;
		if (str.startsWith("<ownedCapabilityRealizations")) beacon = StorageMibField.BeaconType.OWNEDCAPABILITYREALIZATIONS;
		if (str.startsWith("<ownedAbstractCapabilityPkg")) beacon = StorageMibField.BeaconType.OWNEDABSTRACTCAPABILITYPKG;
		if (str.startsWith("<ownedInterfacePkg")) beacon = StorageMibField.BeaconType.OWNEDINTERFACEPKG;
		if (str.startsWith("<ownedDataPkg")) beacon = StorageMibField.BeaconType.OWNEDDATAPKG;
		if (str.startsWith("<ownedParts")) beacon = StorageMibField.BeaconType.OWNEDPARTS;
		if (str.startsWith("<ownedPhysicalLinks")) beacon = StorageMibField.BeaconType.OWNEDPHYSICALLINKS;
		if (str.startsWith("<ownedComponentExchanges")) beacon = StorageMibField.BeaconType.OWNEDCOMPONENTEXCHANGES;
		if (str.startsWith("<ownedDeploymentLinks")) beacon = StorageMibField.BeaconType.OWNEDDEPLOYMENTLINKS;
		if (str.startsWith("<ownedComponentRealizations")) beacon = StorageMibField.BeaconType.OWNEDCOMPONENTREALIZATIONS;
		if (str.startsWith("<ownedFunctionalAllocation")) beacon = StorageMibField.BeaconType.OWNEDFUNCTIONALALLOCATIONS;
		if (str.startsWith("<ownedPortAllocations")) beacon = StorageMibField.BeaconType.OWNEDPORTALLOCATIONS;
		if (str.startsWith("<ownedComponentPortAllocations")) beacon = StorageMibField.BeaconType.OWNEDCOMPONENTPORTSALLOCATIONS;
		if (str.startsWith("<<ownedFeatures")) beacon = StorageMibField.BeaconType.OWNEDFEATURES;
		
		//wrt the type fill a StorageMibField object
		switch (beacon) {
			case OWNEDFUNCTIONALCHAINS:
				FunctionalChain functionalChain = extractFunctionalChain(str);
//				System.out.println("FunctionalChain: " + functionalChain.GetId());
				storageMibFieldsList.add(functionalChain);
				table.put(functionalChain.GetId(), functionalChain);
				break;
			case OWNEDFUNCTIONALCHAININVOLVMENTS:
				AFunctionalChainInvolvements functionalChainInvolvements = extractFunctionalChainInvolvements(str);
//				System.out.println("FunctionalChainInvolvements: " + functionalChainInvolvements.GetId());
				storageMibFieldsList.add(functionalChainInvolvements);
				table.put(functionalChainInvolvements.GetId(), functionalChainInvolvements);
				break;
			case OWNEDFUNCTIONS:
				Function function = extractFunction(str, table);
//				System.out.println("Function: " + function.GetId() + "/" + function.getName());
				storageMibFieldsList.add(function);
				table.put(function.GetId(), function);
				break;
			case OWNEDFUNCTIONALEXCHANGES:
				FunctionalExchange functionalExchange = extractFunctionalExchange(str);
//				System.out.println("FunctionalExchange: " + functionalExchange.GetId() + "/" + functionalExchange.getName());
				storageMibFieldsList.add(functionalExchange);
				table.put(functionalExchange.GetId(), functionalExchange);
				break;
			case INPUTS:
				Input input = extractInput(str);
//				System.out.println("Input: " + input.GetId() + "/" + input.getName());
				storageMibFieldsList.add(input);
				table.put(input.GetId(), input);
				break;
			case OUTPUTS:
				Output output = extractOutput(str);
//				System.out.println("Output: " + output.GetId() + "/" + output.getName());
				storageMibFieldsList.add(output);
				table.put(output.GetId(), output);
				break;
			default:
		}
	}
	
	private static FunctionalChain extractFunctionalChain(String str) {
		FunctionalChain functionalChain;
		
		int indexId = str.indexOf("id=");
		String functionalChainId = (String) str.subSequence(indexId + 4, indexId + 40);
		
		int indexName = str.indexOf("name=");
		int indexNameEnd = str.indexOf("\"", indexName + 6);
		String functionalChainName = (String) str.subSequence(indexName + 6, indexNameEnd);
		
		int indexSummary = str.indexOf("summary=");
		int indexSummaryEnd = str.indexOf("\"", indexSummary + 9);
		String functionalChainSummary = (String) str.subSequence(indexSummary + 9, indexSummaryEnd);
		
		functionalChain = new FunctionalChain(functionalChainId, functionalChainName, functionalChainSummary);
		
		// Recherche des éléments <ownedFunctionalChainInvolvements>
		int startIndex = str.indexOf("<ownedFunctionalChainInvolvements");
		while (startIndex != -1) {
			int endIndex = str.indexOf("</ownedFunctionalChainInvolvements>", startIndex);
			String subStr = str.substring(startIndex, endIndex);
			String involvementId = extractInvolvementId(subStr);
			functionalChain.addChainInvolvementsId(involvementId);
			startIndex = str.indexOf("<ownedFunctionalChainInvolvements", endIndex);
		}
		
		return functionalChain;
	}
	
	// Fonction pour extraire l'ID de FunctionalChainInvolvement (utilisée à l'intérieur de la fonction extractFunctionalChain)
	private static String extractInvolvementId(String str) {
		int indexId = str.indexOf("id=");
		String involvementId = (String) str.subSequence(indexId + 4, indexId + 40);
		return involvementId;
	}
	
	
	private static AFunctionalChainInvolvements extractFunctionalChainInvolvements(String str) {
		AFunctionalChainInvolvements functionalChainInvolvements;
		
		int indexId = str.indexOf("id=");
		String id = (String) str.subSequence(indexId + 4, indexId + 40);
		
		/* Rechercher l'attribut involved */
		String involvedId = null;
		int indexInvolved = str.indexOf("involved=");
		if (indexInvolved != -1) {
			int endIndex = str.indexOf("\"", indexInvolved + 10); // Recherche de l'indice de fin de la valeur de l'attribut
			involvedId = (String) str.subSequence(indexInvolved + 10, endIndex);
			involvedId = involvedId.replace("#", "");
		}
		
		/* Rechercher l'attribut source */
		String sourceId = null;
		int indexSource = str.indexOf("source=");
		if (indexSource != -1) {
			int endIndex = str.indexOf("\"", indexSource + 8); // Recherche de l'indice de fin de la valeur de l'attribut
			sourceId = (String) str.subSequence(indexSource + 8, endIndex);
			sourceId = sourceId.replace("#", "");
		}
		
		/* Rechercher l'attribut target */
		String targetId = null;
		int indexTarget = str.indexOf("target=");
		if (indexTarget != -1) {
			int endIndex = str.indexOf("\"", indexTarget + 8); // Recherche de l'indice de fin de la valeur de l'attribut
			targetId = (String) str.subSequence(indexTarget + 8, endIndex);
			targetId = targetId.replace("#", "");
		}
		
		if (sourceId != null && targetId != null) {
			functionalChainInvolvements = new FunctionalChainInvolvements_exchange(id, involvedId, sourceId, targetId);
		} else {
			functionalChainInvolvements = new FunctionalChainInvolvements_function(id, involvedId);
		}
		
		return functionalChainInvolvements;
	}
	
	
	private static FunctionalExchange extractFunctionalExchange(String str) {
		FunctionalExchange functionalExchange;
		
		int indexId = str.indexOf("id=");
		String functionalExchangeId = (String) str.subSequence(indexId + 4, indexId + 40);
		
		indexId = str.indexOf("name=");
		int indexNameEnd = str.indexOf("\"", indexId + 6);
		String functionalExchangeName = (String) str.subSequence(indexId + 6, indexNameEnd);
		
		/* Rechercher l'attribut source */
		String sourceId = null;
		int indexSource = str.indexOf("source=");
		if (indexSource != -1) {
			int endIndex = str.indexOf("\"", indexSource + 8);
			sourceId = (String) str.subSequence(indexSource + 8, endIndex);
			sourceId = sourceId.replace("#", "");
		}
		
		/* Rechercher l'attribut target */
		String targetId = null;
		int indexTarget = str.indexOf("target=");
		if (indexTarget != -1) {
			int endIndex = str.indexOf("\"", indexTarget + 8);
			targetId = (String) str.subSequence(indexTarget + 8, endIndex);
			targetId = targetId.replace("#", "");
		}
		
		functionalExchange = new FunctionalExchange(functionalExchangeId, functionalExchangeName, sourceId, targetId);
		
		return functionalExchange;
	}
	
	private static Input extractInput(String str) {
		Input input = null;
		
		int indexId = str.indexOf("id=");
		String inputId = (String) str.subSequence(indexId + 4, indexId + 40);
		
		indexId = str.indexOf("name=");
		int indexNameEnd = str.indexOf("\"", indexId + 6);
		String inputName = (String) str.subSequence(indexId + 6, indexNameEnd);
		
		input = new Input(inputId, inputName);
		return input;
	}
	
	private static Output extractOutput(String str) {
		Output output = null;
		
		int indexId = str.indexOf("id=");
		String outputId = (String) str.subSequence(indexId + 4, indexId + 40);
		
		indexId = str.indexOf("name=");
		int indexNameEnd = str.indexOf("\"", indexId + 6);
		String outputName = (String) str.subSequence(indexId + 6, indexNameEnd);
		
		output = new Output(outputId, outputName);
		return output;
	}
	
	private static Function extractFunction(String str, Hashtable<String, StorageMibField> table) {
		Function function = null;
		
		int indexId = str.indexOf("id=");
		String functionId = (String) str.subSequence(indexId + 4, indexId + 40);
		
		indexId = str.indexOf("name=");
		int indexNameEnd = str.indexOf("\"", indexId + 6);
		String functionName = (String) str.subSequence(indexId + 6, indexNameEnd);
		
		function = new Function(functionId, functionName);
		
		int nextIdIndex = -1;
		String id = "";
		
		do {
			nextIdIndex = str.indexOf("id=", nextIdIndex + 1);
			
			if (nextIdIndex != -1) {
				id = (String) str.subSequence(nextIdIndex + 4, nextIdIndex + 40);
				StorageMibField field = table.get(id);
				
				if (field != null) {
					if (field instanceof Output) {
						function.addOutput((Output) field);
					}
					if (field instanceof Input) {
						function.addInput((Input) field);
					}
				}
			}
		} while (nextIdIndex != -1);
		
		return function;
	}
}