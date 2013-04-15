% XOR OUTPUT FOR N = 3 [0 1 1 0 1 0 0 1];

% Utility Functions

utilsFile = util;
funcsFile = funcs;
neuronFile = neuron;

global util
util = utilsFile

% Functions used to evaluate inputs or to compute them correctly
global funcs
funcs = funcsFile

% Functions related to a single neuron
global neuron
neuron = neuronFile

% Functions related to the network
global network

network = neuralNetwork

% Control variables
network.delta = 0.01;
network.eta = 0.5;
network.beta = 1;
network.N = 10000;

network.weights = [];
network.neuronsPerLayer = [3 1];

network.toCompute = funcs.xor;
network.func = funcs.sigmoide;

global logging

logging.errors = [];
logging.errorIndexes = [];
logging.modes = [1 0];
