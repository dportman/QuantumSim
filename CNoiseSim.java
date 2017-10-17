import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.*;
import java.util.Random;

public class CNoiseSim {

	
	
	public CNoiseSim(CKet tempSystemKet, int tempNumOfSteps, double tempNoiseRatio,double tempNoisePower, String tempOutputDir) throws IOException{
	
		SystemKet = tempSystemKet;
		NumOfSteps = tempNumOfSteps;
		NoiseRatio = tempNoiseRatio;
		NoisePower = tempNoisePower;
		OutputDir = tempOutputDir;
		GateWrapper = new CGate();
		RunSimulation();
		
	}
	
	public void RunSimulation() throws IOException{
		
		System.out.println("Starting Simulation");		
		CKet InitialKet = SystemKet;
		
		new File(OutputDir).mkdir(); //making a new dir
		
		//Recording the fidelity drop
		File FidelityFile = new File(OutputDir,"!Fidelity.txt");
		File ParametersFile = new File(OutputDir,"!Parameters.txt");
		File FilelistFile = new File(OutputDir,"filelist.dat");
		BufferedWriter FilelistWriter = new BufferedWriter(new FileWriter(FilelistFile));
		BufferedWriter ParametersWriter = new BufferedWriter(new FileWriter(ParametersFile));
		
			ParametersWriter.write("Initial Ket:");
			ParametersWriter.newLine();
			for (int i = 0; i<InitialKet.Length;i++)
			{
				ParametersWriter.write("Qubit #"+(i+1)+" : ReAlpha = " + InitialKet.BinaryKet[i].ReAlpha + " ImAlpha = "+InitialKet.BinaryKet[i].ImAlpha+ " ReBeta = "+InitialKet.BinaryKet[i].ReBeta+ " ImBeta = "+InitialKet.BinaryKet[i].ImBeta);
				ParametersWriter.newLine();
			}
			ParametersWriter.write("Steps = " + NumOfSteps);
			ParametersWriter.newLine();
			ParametersWriter.write("Noise Ratio = " + NoiseRatio);
			ParametersWriter.newLine();
			ParametersWriter.write("Noise Power = " + NoisePower);
			ParametersWriter.newLine();
		ParametersWriter.close();
		
		BufferedWriter FidelityWriter = new BufferedWriter(new FileWriter(FidelityFile));
		FidelityWriter.write("Step Fidelity");
		FidelityWriter.newLine();	
		
		Random r = new Random();
		for (int step=0;step<NumOfSteps;step++)
		{
			
			if (Math.random()<NoiseRatio)
			{									
				double RandomPhase = r.nextGaussian()*NoisePower; 	// the phase is gaussian with mean 0 and variance NoisePower/2
				int NoiseLocation = (int)Math.round((SystemKet.Length-1)*Math.random());
				SystemKet.BinaryKet[NoiseLocation]=GateWrapper.GatePhase(SystemKet.BinaryKet[NoiseLocation], RandomPhase);				
			}	
			FidelityWriter.write(step+" "+SystemKet.FidelityWith(InitialKet));
			FidelityWriter.newLine();	
			//if (step % AVizNumOfSteps == 0) 
			SystemKet.Ket2AViz(OutputDir,"OUT"+(step+1));
			FilelistWriter.write("OUT"+(step+1)+".xyz");
			FilelistWriter.newLine();
			
		}
		FilelistWriter.close();
		FidelityWriter.close();	
		System.out.println("Complete. Files created in directory "+ OutputDir);
	}
		
	//Properties
	CKet SystemKet;
	int NumOfSteps;
	double NoiseRatio;
	double NoisePower;
	String OutputDir;
	CGate GateWrapper;
	
}
