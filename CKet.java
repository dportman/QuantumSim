import java.io.*;

public class CKet {
	
	
	
	//The overloaded constructors
	
	//When initialized as CKet(), get a binary string from the user
	public CKet() throws IOException{
		BinaryKet = InputKet();	
		Length = BinaryKet.length;
	}
	
	//Building a ket from a binary string
	public CKet(String InputStr){
		if (InputStr.length()==0){
			BinaryKet = StrToKet("1111111111"); // the default input string
		}
		else BinaryKet = StrToKet(InputStr);
		Length = BinaryKet.length;
	}

	//Building a ket from two qubits
	public CKet (CQubit QubitA, CQubit QubitB){
		BinaryKet[0] = QubitA;
		BinaryKet[1] = QubitB;
		Length = 2;
	}
	
	//END OF CONSTRUCTORS -------------------------------------------------
	
	//Get a Ket state in the binary basis from the user
 	public CQubit[] InputKet() throws IOException{
 		System.out.println("Please enter the initial state as a binary string [Default 11111....11111] ");
 		
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);  
	    
	    boolean isStringOK = false; // used in input verification
	    String line ="";
	    line = br.readLine();

	    if(line.length()==0){
			return StrToKet("1111111111111111111111111111111111111111"); // the default input string (when the user only presses ENTER)
	    }
	    while (!isStringOK)	
	    {
	    for (int i=0;i<line.length();i++)
	    	if ((line.charAt(i)!='0') && (line.charAt(i)!='1')) 
	    	{
	    	System.out.println("Error - Only binary form is accepted! (00101 etc.)");
	    	line = br.readLine();
	    	if (line.length()==0){
				return StrToKet("1111111111"); // the default input string
		    }
	    	}
	    	else
	    	{
	    	isStringOK = true;	
	    	}
	    }   
	    return StrToKet(line);	
 	}
 	
	
	//Converts a string of zeros and ones to a Ket of qubits - Used by InputKet()
 	public CQubit[] StrToKet(String InputStr) {
 		
 		CQubit[] tempBinaryKet = new CQubit[InputStr.length()];
 		
 		for (int i=0;i<InputStr.length();i++)
 		{	
 				if (Integer.parseInt(InputStr.substring(i,i+1)) == 0)
 					tempBinaryKet[i] = new CQubit((byte)0);
 				else 
 					tempBinaryKet[i] = new CQubit((byte)1);
 		}
 		return tempBinaryKet;		
 	}
 	
 	// Prints the Ket in binary representaion to the console 
 	public void PrintBinaryKet(){ 		
 		for (int i = 0; i<BinaryKet.length;i++){
 			System.out.print("[ ");
 			BinaryKet[i].PrintQubit();
 			System.out.print(" ]");
 		}
 		System.out.println();
 	}
 	
 	//Perform a measurement of the Ket. The Ket collapses to the measured state
 	public void CollapseMeasureKet(){
 		for (int i=0; i<BinaryKet.length;i++)
 			BinaryKet[i] = BinaryKet[i].Measure();
 	}
 	
 	public CKet CollapseMeasureKetNoDestroy(){
 		CKet tempKet = this;
 		for (int i=0; i<BinaryKet.length;i++)
 			tempKet.BinaryKet[i] = BinaryKet[i].Measure();
 		return tempKet;
 	}
 	
 	
 	//Perform a measurement of the Ket. The Ket doesnt collapse to the measured state
 	public CKet NoCollapseMeasureKet(){
 		CKet tempKet = this;
 		for (int i=0; i<BinaryKet.length;i++)
 			tempKet.BinaryKet[i].Measure();
 		return tempKet;
 	}

 	public double FidelityWith(CKet Ket){
 		double result = 1;
 		for (int i = 0; i<Length;i++){
 			result = result*BinaryKet[i].Braket(Ket.BinaryKet[i]);
 		}
 		return 1-result;
 	}
 		
 	//Creates an AViz xyz file representing the ket`s spin configuration
 	public void Ket2AViz(String DirName, String FileName) throws IOException{
 		
 		File outFile = new File(DirName,FileName + ".xyz");
 		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
 		
 		writer.write(String.valueOf(Length));
 		writer.newLine();
 		writer.write("# Qubit Chain");
 		writer.newLine();
 		
 		for (int i = 0; i < Length; i++){	
 			
 			writer.write("Sp "+(i+1)+" 1 1 " + BinaryKet[i].SpinX+" "+BinaryKet[i].SpinY+" "+BinaryKet[i].Measure().SpinZ);
 			writer.newLine();
 		}	
 		writer.close();		
 	}

 	
	//Properties
 	CQubit[] BinaryKet;
 	int Length;


}
