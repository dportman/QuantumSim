//  a wrapper for the gates. 
public class CGate {
	
	//Hadamard Gate	
	public CQubit GateHadamard(CQubit Qubit){		
		CQubit tempQubit = new CQubit(Qubit.ReAlpha+Qubit.ReBeta,Qubit.ImAlpha+Qubit.ImBeta,Qubit.ReAlpha-Qubit.ReBeta,Qubit.ImAlpha-Qubit.ImBeta);
		return tempQubit;
	}
		
	//Phase Gate	
	public CQubit GatePhase(CQubit Qubit, double Phase){		
		CQubit tempQubit = new CQubit(Qubit.ReAlpha,Qubit.ImAlpha,Math.sin(Phase)*Qubit.ReBeta,Math.cos(Phase)*Qubit.ImBeta);
		return tempQubit;
	}	
}

