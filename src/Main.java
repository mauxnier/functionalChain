import java.util.UUID;

public class Main {
	public static void main(String[] args) {
		Function f1 = new Function(UUID.randomUUID().toString(), "f1");
		Function f2 = new Function(UUID.randomUUID().toString(), "f2");
		Function f3 = new Function(UUID.randomUUID().toString(), "f3");
		Function f4 = new Function(UUID.randomUUID().toString(), "f4");
		Function f5 = new Function(UUID.randomUUID().toString(), "f5");
		
		FunctionalChainInvolvments_function fcif1 = new FunctionalChainInvolvments_function(UUID.randomUUID().toString(), "fcif1");
		fcif1.setFunction(f1);
		FunctionalChainInvolvments_function fcif2 = new FunctionalChainInvolvments_function(UUID.randomUUID().toString(), "fcif2");
		fcif2.setFunction(f2);
		FunctionalChainInvolvments_function fcif3 = new FunctionalChainInvolvments_function(UUID.randomUUID().toString(), "fcif3");
		fcif3.setFunction(f3);
		FunctionalChainInvolvments_function fcif4 = new FunctionalChainInvolvments_function(UUID.randomUUID().toString(), "fcif4");
		fcif4.setFunction(f4);
		FunctionalChainInvolvments_function fcif5 = new FunctionalChainInvolvments_function(UUID.randomUUID().toString(), "fcif5");
		fcif5.setFunction(f5);
		
		Output o1 = new Output(UUID.randomUUID().toString(), "o1");
		Output o2 = new Output(UUID.randomUUID().toString(), "o2");
		Output o3 = new Output(UUID.randomUUID().toString(), "o3");
		Output o4 = new Output(UUID.randomUUID().toString(), "o4");
		Output o5 = new Output(UUID.randomUUID().toString(), "o5");
		
		Input i1 = new Input(UUID.randomUUID().toString(), "i1");
		Input i2 = new Input(UUID.randomUUID().toString(), "i2");
		Input i3 = new Input(UUID.randomUUID().toString(), "i3");
		Input i4 = new Input(UUID.randomUUID().toString(), "i4");
		Input i5 = new Input(UUID.randomUUID().toString(), "i5");
		
		FunctionalExchange fe1 = new FunctionalExchange(UUID.randomUUID().toString(), "fe1");
		fe1.setOutput(o1);
		fe1.setInput(i1);
		FunctionalExchange fe2 = new FunctionalExchange(UUID.randomUUID().toString(), "fe2");
		fe2.setOutput(o2);
		fe2.setInput(i2);
		FunctionalExchange fe3 = new FunctionalExchange(UUID.randomUUID().toString(), "fe3");
		fe3.setOutput(o3);
		fe3.setInput(i3);
		FunctionalExchange fe4 = new FunctionalExchange(UUID.randomUUID().toString(), "fe4");
		fe4.setOutput(o4);
		fe4.setInput(i4);
		FunctionalExchange fe5 = new FunctionalExchange(UUID.randomUUID().toString(), "fe5");
		fe5.setOutput(o5);
		fe5.setInput(i5);
		
		System.out.println("First functional chain");
		FunctionalChain functionalChain = new FunctionalChain(UUID.randomUUID().toString(), "fc1", "FunctionalChain");
		
		FunctionalChainInvolvments_exchange fcie1 = functionalChain.addFunctionalExchange(fe1, fcif1, fcif2);
		FunctionalChainInvolvments_exchange fcie2 = functionalChain.addFunctionalExchange(fe2, fcif2, fcif3);
		FunctionalChainInvolvments_exchange fcie3 = functionalChain.addFunctionalExchange(fe3, fcif3, fcif5);
		FunctionalChainInvolvments_exchange fcie4 = functionalChain.addFunctionalExchange(fe4, fcif4, fcif5);
		FunctionalChainInvolvments_exchange fcie5 = functionalChain.addFunctionalExchange(fe5, fcif2, fcif4);
		functionalChain.start();
	}
}