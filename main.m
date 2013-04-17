% Utility Functions

utilsFile = util;
functsFile = functs;
problemFile = problem;
neuronFile = neuron;
networkFile = neuralNetwork;

% Utility methods
global util;
util = utilsFile;

% Functions used to evaluate inputs or to compute them correctly
global functs
functs = functsFile

% Problems to solve
global problem
problem = problemFile 

% Functions related to a single neuron
global neuron
neuron = neuronFile

% Functions related to the network
global network
network = networkFile

% Control variables
network.delta = 0.001;
network.eta = 0.5;
network.beta = 1;
network.N = 10000;

network.intervals = [-1 1];
network.weights = [];

network.inputGenerator = util.randomInput;
network.problem = problem.xor(5, functs.sigmoide);

global logging

logging.errors = [];
logging.errorIndexes = [];
logging.modes = [1 0];
