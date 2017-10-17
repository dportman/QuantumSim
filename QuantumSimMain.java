import java.io.*;
import javax.swing.*;
public class QuantumSimMain {

	public static  void main(String[] args) throws IOException {
		
		System.out.println("GUI or text based version? (Choose g or t)");
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr); 
		String InputStr = br.readLine();
		
		boolean isInputOk = false;
		
		while(!isInputOk){			
		
		if (InputStr.toUpperCase().startsWith("G")){
			isInputOk = true;
			JFrame MainFrame = new CMainFrame();
	    	MainFrame.setVisible(true);
	    	
		}
		else if (InputStr.toUpperCase().startsWith("T"))
			{
			// text based input block
			isInputOk = true;
			//Get the initial qubit string
			System.out.println("Text based version started");
						
			CKet MainKet = new CKet(); // the input ket		
			
			System.out.println("Enter number of steps [1000]:");
			InputStr = br.readLine();
			if (InputStr.length()==0) InputStr = "1000";
			int NumOfSteps = Integer.parseInt(InputStr);
			

			double NoiseRatio = 100;
			while ((NoiseRatio>1)||(NoiseRatio<0)){			
				System.out.println("Enter noise ratio (between 0 and 1) [0.5] :");
				InputStr = br.readLine();
				if (InputStr.length()==0) InputStr = "0.5";
				NoiseRatio = Double.parseDouble(InputStr);
				}
		
			System.out.println("Enter noise power [1] :");
			InputStr = br.readLine();
			if (InputStr.length()==0) InputStr = "1";
			double NoisePower = Math.abs(Double.parseDouble(InputStr));
			
			boolean isDirOk = false;
			String OutputDir = "output";
			
			while (!isDirOk){
				System.out.println("Enter output directory [output]:");
				InputStr = br.readLine();
				OutputDir = InputStr;
				if (InputStr.length()==0) OutputDir = "output";
				File DirCheck = new File(OutputDir);
				if(DirCheck.exists()){
					System.out.println("A directory with the specified name already exists. Please choose a different output directory");	
				}
			else isDirOk = true;
			}
			
						
			CGate GateWrapper = new CGate();// applying the hadamard gate on the qubit chain
			for (int i = 0; i< MainKet.Length;i++)	MainKet.BinaryKet[i] = GateWrapper.GateHadamard(MainKet.BinaryKet[i]);		
			CNoiseSim Simulation = new CNoiseSim(MainKet, NumOfSteps, NoiseRatio, NoisePower, OutputDir);
		
			}	
		else
			{
			System.out.println("GUI or text based version? (Choose g or t)");
			InputStr = br.readLine();
			}
		}	
	}      
}
	


