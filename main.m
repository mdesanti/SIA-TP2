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
network.delta = 0.01;
network.eta = 0.001;
network.beta = 0.5;
network.N = 10000;

network.intervals = [-1 1];
network.weights = [];

network.problem = problem.approximation(4, functs.tanh);

network.testSet = [];
network.trainPctg = 0.8;

network.adaptive = true;
network.momentum = true;

load data

network.origData = x / 4;
network.data = x / 4;

global logging

logging.errors = [];
logging.errorIndexes = [];
logging.modes = [1 0];
