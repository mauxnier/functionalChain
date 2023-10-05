import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class Main {
	public static void main(String[] args) {
		
		/* Parse le fichier XML */
		Parser.main(args);
		ArrayList<FunctionalChain> chains = Parser.CHAINS;
		Hashtable<String, StorageMibField> table = Parser.HASHTABLE;
		
		
		FunctionalChainInvolvements_function initialFunctionInvolvements = null;
		ArrayList<Function> contaminatedFunctions = new ArrayList<>();
		String flag = "THREAT";
		
		/* Recherche du premier élément contaminé */
		for (FunctionalChain chain : chains) {
			for (AFunctionalChainInvolvements involvements : chain.getChainInvolvements()) {
				if (involvements instanceof FunctionalChainInvolvements_function involvementsFunction) {
					if (Objects.equals(involvementsFunction.getSummary(), flag)) {
						initialFunctionInvolvements = involvementsFunction;
						System.out.println("Chaine " + chain.getName() + " contaminée.");
						Function initialFunction = involvementsFunction.getFunction();
						if (!contaminatedFunctions.contains(initialFunction)) contaminatedFunctions.add(initialFunction);
					}
				}
			}
		}
		
		/* Algorithme pour savoir la contamination */
		FunctionalChainInvolvements_exchange exchange = getFunctionalExchangeFromFunctionalFunction(initialFunctionInvolvements.GetId(), table);
		processExchange(exchange.getExchange(), table, contaminatedFunctions);
		
		/* On affiche les fonctions contaminées */
		System.out.println("Les fonctions contaminées sont : ");
		for (Function function : contaminatedFunctions) {
			System.out.println("- " + function.getName());
		}
	}
	
	public static FunctionalChainInvolvements_exchange getFunctionalExchangeFromFunctionalFunction(String idOfSource, Hashtable<String, StorageMibField> table) {
		for (StorageMibField field : table.values()) {
			if (field instanceof FunctionalChainInvolvements_exchange exchange) {
				if (exchange.getSource().GetId().equals(idOfSource)) {
					return exchange;
				}
			}
		}
		return null; // Retourne null si aucune correspondance n'est trouvée.
	}
	
	public static void processExchange(FunctionalExchange exchange, Hashtable<String, StorageMibField> table, ArrayList<Function> contaminatedFunctions) {
		Function fi = getFunctionByInputId(exchange.getInputId(), table);
//		printArray(contaminatedFunctions);
		
		/* Vérifiez que la fonction n'est pas déjà dans la liste contaminatedFunctions */
		if (!contaminatedFunctions.contains(fi)) {
			/* Ajoutez la fonction à la liste contaminatedFunctions */
			contaminatedFunctions.add(fi);
			
			/* Parcourez les sorties de la fonction d'entrée */
			for (Output o : fi.getOutputs()) {
				/* Recherchez si les sorties sont utilisées comme entrées dans d'autres fonctions */
				FunctionalExchange nextExchange = getFunctionalExchangeFromOutputId(o.GetId(), table);
				if (nextExchange != null && !Objects.equals(nextExchange.GetId(), exchange.GetId())) {
					processExchange(nextExchange, table, contaminatedFunctions);
				}
			}
		}
		/* Sinon, la fonction est déjà marquée comme contaminée, donc ne faites rien de plus */
	}
	
	public static void printArray(List<Function> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).getName());
			if (i < list.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		System.out.println(sb.toString());
	}
	
	public static FunctionalExchange getFunctionalExchangeFromOutputId(String outputId, Hashtable<String, StorageMibField> table) {
		for (StorageMibField field : table.values()) {
			if (field instanceof FunctionalExchange exchange) {
				if (exchange.getOutputId().equals(outputId)) {
					return exchange;
				}
			}
		}
		return null; // Retourne null si aucune correspondance n'est trouvée.
	}
	
	public static Function getFunctionByInputId(String inputId, Hashtable<String, StorageMibField> table) {
		for (StorageMibField field : table.values()) {
			if (field instanceof Function function)
				for (Input input : function.getInputs()) {
					if (input.GetId().equals(inputId)) {
						return function;
					}
				}
		}
		return null; // Retourne null si aucune fonction ne correspond à l'inputId.
	}
	
	public static Function getFunctionByOutputId(String outputId, Hashtable<String, StorageMibField> table) {
		for (StorageMibField field : table.values()) {
			if (field instanceof Function function) {
				for (Output output : function.getOutputs()) {
					if (output.GetId().equals(outputId)) {
						return function;
					}
				}
			}
		}
		return null; // Retourne null si aucune fonction ne correspond à l'outputId.
	}
}