% Utility Functions
source('util.m');      

% Functions used to evaluate inputs or to compute them correctly
source('functions.m'); 

% Functions related to a single neuron
source('neuron.m');

% Functions related to the network
source('network.m');

% Control variables
global delta;
delta = 0.0001;
global eta;
eta = 0.00015
global beta;
beta = 7;

% Weights of all the network, indexed by neuron
global weights;
weights = [];

% Parameters to use when computing
global toCompute
toCompute = @or;
global func;
func = @step;

% Network definition.
global neuronsPerLayer;
neuronsPerLayer = [ 1 ];

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

