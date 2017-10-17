public class CQubit {

	//Constructor
	public CQubit( double ReA, double ImA, double ReB, double ImB ){
		
		SpinX = 0;
		SpinY = 0;
		
		//Normalization
		double Norm = Math.sqrt(ReA*ReA+ImA*ImA+ReB*ReB+ImB*ImB);
		ReA = ReA / Norm;
		ImA = ImA / Norm;
		ReB = ReB / Norm;
		ImB = ImB / Norm;
			
		// Update the qubit
		SetQubitValue(ReA, ImA, ReB, ImB);		
	}
		
	// A simple constructor - builds a |0> or |1> qubit (input 0 or 1 in bQubit)
	public CQubit(int bQubit){
		SpinX=0;
		SpinY=0;
		
		if (bQubit==0){
			
			SpinZ = 1;
			ReAlpha = 1;
			ImAlpha = 0;
			ReBeta = 0;
			ImBeta = 0;
		}
		else
		if (bQubit==1){
			SpinZ = -1;
			ReAlpha = 0;
			ReBeta = 1;
			ImAlpha = 0;
			ImBeta = 0;
		}
		else
		{
			System.out.println("Error in Qubit init - Qubit constructed with illegal parameters");
			System.exit(0);
		}
	}

	
	// Used to initalize a qubit to the general form: Alpha|0> + Beta|1>
	public void SetQubitValue(double ReA, double ImA, double ReB, double ImB){
		ReAlpha = ReA;
		ReBeta = ReB;
		ImAlpha = ImA;
		ImBeta = ImB;
		
		if (( ReBeta == 0 )&& ( ImBeta == 0)) 
			{
			SpinZ = 1;
			}
		else {
			if (( ReAlpha == 0 )&& ( ImAlpha == 0)) 
			SpinZ = -1;
			else SpinZ = 0;
		}
		
		
	}
	
	// Print the qubit in the form [ (a+ib)|0>+(c+id)|1> ]
	public void PrintQubit(){
		
		if ((ReAlpha!=0.0)&&(ImAlpha!=0.0)) 
			{
			System.out.print("("+ReAlpha+PrintSign(ImAlpha)+Math.abs(ImAlpha)+"i)|0>");
			}
		else{
			if (ReAlpha!=0.0) System.out.print(ReAlpha+"|0>");
			else if (ImAlpha!=0.0) System.out.print(ImAlpha+"i|0>");
		}
		
		if ((ReBeta!=0.0)&&(ImBeta!=0.0)) System.out.print("+("+ReBeta+PrintSign(ImBeta)+Math.abs(ImBeta)+"i)|1>");
		else{
			if (ReBeta!=0.0) 
				{
				if ((PrintSign(ReBeta)=="+")&&(ReAlpha!=0)||(ImAlpha!=0))
				System.out.print(PrintSign(ReBeta)+ReBeta+"|1>");
				else System.out.print(ReBeta+"|1>");
				}
			else if (ImBeta!=0.0)System.out.print(PrintSign(ImBeta)+Math.abs(ImBeta)+"i|1>");
		}	
	}
	
	//A simple routine used by PrintQubit
	public String PrintSign(double d){
		String sign;
		if (d/Math.abs(d) > 0) sign = "+";
		else sign = "-";
		return sign;	
	}
	
	//Prints the probabilities to get |0> and |1> in a measurement of SpinZ
	public void PrintMeasureProbabilities(){
		System.out.print("Measure probabilities for qubit ");
		PrintQubit();
		System.out.println();
		System.out.println("Probability to get |0> is "+ (ReAlpha*ReAlpha+ImAlpha*ImAlpha));
		System.out.println("Probability to get |1> is "+ (ReBeta*ReBeta+ImBeta*ImBeta));
	}
	
	// A destroying measurement of a qubit in the SpinZ basis. Returns either |0> or |1>
	public CQubit Measure(){
		double dice = Math.random();
		if (dice<(ReAlpha*ReAlpha+ImAlpha*ImAlpha)){
			CQubit tempQubit = new CQubit(0);
			return tempQubit;
		}
		else{
			CQubit tempQubit = new CQubit(1);
			return tempQubit;
		}	
	}
	
	//Braket multiplication of <thisQubit|KetQubit>
	public double Braket(CQubit KetQubit)
	{
		CQubit BraQubit = this;
		double result = Math.pow(BraQubit.ReAlpha*KetQubit.ReAlpha,2)+
						Math.pow(BraQubit.ImAlpha*KetQubit.ImAlpha,2)+
						2*(BraQubit.ReAlpha*KetQubit.ReAlpha*BraQubit.ImAlpha*KetQubit.ImAlpha)+
						Math.pow(BraQubit.ReBeta*KetQubit.ReBeta,2)+
						Math.pow(BraQubit.ImBeta*KetQubit.ImBeta,2)+
						2*(BraQubit.ReBeta*KetQubit.ReBeta*BraQubit.ImBeta*KetQubit.ImBeta);			
		return result;
	}
	
	
	
	//Properties
	double SpinX;
	double SpinY;
	double SpinZ;
	
	double ReAlpha; // Real Coefficient of |0>
	double ReBeta;  // Real Coefficient of |1>
	double ImAlpha; // Imaginary Coefficient of |0>
	double ImBeta;  // Imaginary Coefficient of |1>
}
