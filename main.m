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
global delta;
delta = 0.01;
global eta;
eta = 0.015;
global beta;
beta = 100;
global N;
N = 1000;

% Global errors
global err
err = [];
% Weights of all the network, indexed by neuron
global weights;
weights = [];

% Parameters to use when computing
global toCompute
toCompute = funcs.xor;
global func;
global errs;
errs = [];
global errI;
errI = [];
func = funcs.sigmoide;

% Network definition.
global neuronsPerLayer;
neuronsPerLayer = [ 3 1 ];
% Results of all the network, indexed by neuron
global neuronResult;
neuronResult = [];

% Pre-calculated data related to the layers and neurons.
global layerForNeuron;
global layerIndexForNeuron;
global deltas;

layerForNeuron = [];
layerIndexForNeuron = [];
deltas = [];

% Pre-calculated inputs and weights.
global weightsPerLayer;
global inputForLayer;

weightsPerLayer = [];
inputForLayer = [];

% Array used as a pointer to the weights of a single node.
global neuronWeights;

