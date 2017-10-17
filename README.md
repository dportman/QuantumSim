### QuantumSim
Simulation of a noisy quantum information channel in Java

# What is Quantum Information? 

Quantum Information is physical information (usually represented by zeros and ones in our computerized world), stored in the state of a quantum system. This means that a physical property is used to store the information: the most popular quantum information unit is the Qubit (quantum bit) - a two-state quantum system, such as a polarized photon or an electron with two possible values of spin in the Z direction. For example, we can code the qubit to hold the information "0" if we set its spin Z to be +1/2, or to hold "1" if the spin Z is -1/2. 

# What is Quantum Information good for? 

Quantum Information offers significant impovements over classical information: for example, when a qubit is transmitted from A to B, the receiving party B can tell whether the qubit had been read by a malicious eavesdropper E , because any measurement of the qubit collapses its state and thus affects the information it carries. 
Another big plus of quantum information it the ability to store information in a qubit that is a superposition of its basis states: in this way, if a classical bit can only take "0" and "1" values, a qubit can be represented as α |0> + β|1>, where both α and β are complex coefficients. Moreover, quantum information uses the entanglement property of certain quantum systems to speed-up information transmission (super-dense coding), and even allow teleportation of information. 

# Noise in Quantum Information channels 

However, not all is well : like everything in the real world, a quantum information channel cannot be perfect. The same physical phenomena that allow quantum information can greatly interfere its successful implementation in the real world. Amongst these, to be noted are the depolarizing channel (when qubits are in the form of polarized photons, and the medium through which the photons are transmitted ruins their polarization), Amplitude damping (due to energy dissipation), and Phase damping - loss of quantum information without loss of energy. 


# Phase Damping 

The simulation explores a noise process which is unique to quantum information : the phase damping. Taking an example from our physical world, phase damping describes what happens when a photon scatters randomly as it travels through a waveguide, or how electronic states in an atom are perturbed upon interacting with distant electrical charges. The energy eigenstates of a quantum system do not change as a function of time, but do accumulate a phase proportional to the eigenvalue. When such a system evolves for an amount of time (noted as steps in the simulation), partial information about this quantum phase - the relative phase between the energy eigenstates - is lost. 

# Implementation 

What actually happens in the simulation is as follows: 
We start with a chain of qubits in various states of the form α |0> + β|1>, α and β determined by user input of the initial qubit chains. A phase gate of the form is applied randomly (with frequency based on the NoiseRatio property in the simulation) on one qubit in the chain, where the parameter θ	has a gaussian distribution, with mean 0 and variance propotional to the NoisePower value in the simulation. 
Each successfull activation of the phase gate simulates a phase damping. After a specific amount of time (which can be derived from the simulation), the qubit no longer holds any information - it is entirely ruined by the noise. One of the goals of my simulation is to explore the dependency of the time it takes for a qubit chain to lose its information on the noise parameters NoiseRatio, NoisePower and the length of the qubit chain.
A measure of the amount of information that is not lost is the Fidelity. 

This material is based on "M.A. Nielsen and I.L. Chuang, Quantum Computation and Quantum Information (Cambridge University Press, Cambridge, 2000)", p.383-385
