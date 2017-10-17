import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  // Needed for ActionListener
import java.io.*;

public class CMainFrame extends JFrame {
	
	public JTextField QubitStringTF = new JTextField(30);
	public JTextField QubitStringLengthTF = new JTextField(2);
	public JTextField NoiseRatioTF = new JTextField(3);
	public JTextField NoisePowerTF = new JTextField(3);
	public JTextField NumOfStepsTF = new JTextField(5);
	public JTextField OutputDirTF = new JTextField(10);
	public JTextField AVizNumOfStepsTF = new JTextField(2);
	 
    public CMainFrame() { // constructor builds GUI
        	
    	//Build the content pane.
  	
    	// the first row in the app
    	JPanel QubitStringInputContent = new JPanel();
    	QubitStringInputContent.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
    	JLabel EnterStringLabel = new JLabel("Set the initial qubit string to :");
    	QubitStringTF.setText("11111111111111111111111111111111111111111111"); //

    	QubitStringInputContent.add(EnterStringLabel);
    	QubitStringInputContent.add(QubitStringTF);
    	  	
    	// the second row in the app
    	JPanel QubitStringRandomInputContent = new JPanel();
    	QubitStringRandomInputContent.setLayout(new FlowLayout(FlowLayout.LEFT));
    	
    	JLabel EnterLengthLabel = new JLabel("Or, if you wish to create a random string, specify it`s length here:");
    	QubitStringLengthTF.setText("40");
    	JButton RandomStringButton = new JButton("Random String");
    	RandomStringButton.addActionListener(new RandomButtonListener());
    	
    	QubitStringRandomInputContent.add(EnterLengthLabel);
    	QubitStringRandomInputContent.add(QubitStringLengthTF);
    	QubitStringRandomInputContent.add(RandomStringButton);
    	   	
    	//Combining the first two rows
    	JPanel QubitInputPanel = new JPanel();
    	QubitInputPanel.setLayout(new GridLayout(3,1));
    	
    	JLabel InputLabel = new JLabel("[STEP 1: Specify the initial qubit chain state]");
    	QubitInputPanel.add(InputLabel);
    	QubitInputPanel.add(QubitStringInputContent);
    	QubitInputPanel.add(QubitStringRandomInputContent);
    	
    	QubitInputPanel.setBorder(BorderFactory.createBevelBorder(1));
    	   	
    	// the simulation parameters input in the app------------
    	
    	JLabel SimulationParametersLabel = new JLabel("[STEP 2: Specify the simulation paremeters]");
    	
    	
    	JPanel SimParametersPanel = new JPanel();
    	SimParametersPanel.setLayout(new FlowLayout());
    	
    	JLabel EnterSimParametersLabel = new JLabel("Simulation Parameters:");
    	JLabel NoiseRatioLabel = new JLabel("Noise Ratio [0-1]:");
    	JLabel NoisePowerLabel = new JLabel("Noise Power:");
    	JLabel NumOfStepsLabel = new JLabel("Number Of Steps:");
    	
    	SimParametersPanel.add(EnterSimParametersLabel);
    	
    	SimParametersPanel.add(NoiseRatioLabel);	
    	NoiseRatioTF.setText("0.5");
    	SimParametersPanel.add(NoiseRatioTF);
    	
    	SimParametersPanel.add(NoisePowerLabel);
    	NoisePowerTF.setText("1");
    	SimParametersPanel.add(NoisePowerTF);
    	
    	SimParametersPanel.add(NumOfStepsLabel);
    	NumOfStepsTF.setText("1000");
    	SimParametersPanel.add(NumOfStepsTF);
    	    	
    	JPanel SimParameters = new JPanel();
    	SimParameters.setLayout(new GridLayout(2,1));
    	SimParameters.add(SimulationParametersLabel);
    	SimParameters.add(SimParametersPanel);
    	SimParameters.setBorder(BorderFactory.createBevelBorder(1));
    	
    	//output control panel--------------------
    	
    	JLabel OutputParametersLabel = new JLabel("[STEP 3: Specify the output paremeters]");
    	   	
    	JPanel OutputParametersPanel = new JPanel();
    	OutputParametersPanel.setLayout(new GridLayout(4,2));
    	    	
    	JLabel DirNameLabel = new JLabel("Output directory name (will be created):");
    	   	
    	JPanel OPP1 = new JPanel();
    	OPP1.setLayout(new FlowLayout(FlowLayout.LEFT));
    	OPP1.add(DirNameLabel);
    	OutputDirTF.setText("output");
    	OPP1.add(OutputDirTF);
    	
    	    
    	OutputParametersPanel.add(OutputParametersLabel);   	
    	OutputParametersPanel.add(OPP1);	    	   	  	
    	OutputParametersPanel.setBorder(BorderFactory.createBevelBorder(1));
    	
    	//Running the simulation panel-----------------
    	
    	JPanel RunSimPanel = new JPanel();
    	RunSimPanel.setLayout(new GridLayout(2,1));
    	
    	JLabel RunLabel = new JLabel ("[STEP 4 : Run the simulation]");
    	RunSimPanel.add(RunLabel);   	
    	
    	JButton RunSimButton = new JButton("Run the simulation");
    	RunSimButton.addActionListener(new RunSimButtonListener());
    	RunSimPanel.add(RunSimButton);  	
    	
    	RunSimPanel.setBorder(BorderFactory.createBevelBorder(1));
	
    	// the main content wrapper
    	JPanel Content = new JPanel();
    	Content.setLayout(new GridLayout(4,1));

    	Content.add(QubitInputPanel);
    	Content.add(SimParameters);
    	Content.add(OutputParametersPanel);
    	Content.add(RunSimPanel);
    		        
        // the usual voodoo
        setContentPane(Content);
        setTitle("Computational Physics Project - Quantum Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(655,655);   // does layout of components.
        pack();
        setLocationRelativeTo(null); 
    }//end constructor

    class RandomButtonListener implements ActionListener {         
        public void actionPerformed(ActionEvent e) {        
            QubitStringTF.setText(RandomString(Integer.parseInt(QubitStringLengthTF.getText())));                                        
        }
    }
    
    
    //creates a random binary string of specified length
    public String RandomString(int length){
    	String s = ""; // Start with an empty string
    	for (int i = 0; i< length; i++){
    		double dice = Math.random();
    		if (dice<0.5) s = s+"0";
    		else s = s + "1";
    	} 		
    	return s;
    }
    
    class RunSimButtonListener  implements ActionListener  {         
        public void actionPerformed(ActionEvent e)  {
            
        	
        	CKet MainKet = new CKet(QubitStringTF.getText());
        	CGate GateWrapper = new CGate();// applying the hadamard gate on the qubit chain
			for (int i = 0; i< MainKet.Length;i++)
				MainKet.BinaryKet[i] = GateWrapper.GateHadamard(MainKet.BinaryKet[i]);
			// to be on the safe side...
			if (Double.parseDouble(NoiseRatioTF.getText())>1) NoiseRatioTF.setText("1");
			if (Double.parseDouble(NoiseRatioTF.getText())<0) NoiseRatioTF.setText("0");
			
			try
				{	
					File DirCheck = new File(OutputDirTF.getText()); //check whether output dir already exists
					if(DirCheck.exists()) JOptionPane.showMessageDialog(null,"A directory with the specified name already exists. Please choose a different output directory");
					else {	
							CNoiseSim Simulation = new CNoiseSim(MainKet, Integer.parseInt(NumOfStepsTF.getText()), Double.parseDouble(NoiseRatioTF.getText()), Double.parseDouble(NoisePowerTF.getText()) , OutputDirTF.getText());
							JOptionPane.showMessageDialog(null,"Simulation Complete. Files created in directory "+OutputDirTF.getText());
						}
				}	
			catch (Exception e1)
				{
					System.out.println("Exception in NoiseSim");
					System.exit(0);
				}                               
			}
        }
    }


