% Utility Functions
source('util.m');      

% Functions used to evaluate inputs or to compute them correctly
source('functions.m'); 

% Functions related to a single neuron
source('neuron.m');

% Functions related to the network
source('network.m');

% Control variables
global delta = 0.0001;
global eta = 0.00015;
global beta = 7;

% Weights of all the network, indexed by neuron
global weights = [];

% Parameters to use when computing
global toCompute = @or;
global func = @step;

% Network definition.
global neuronsPerLayer = [ 1 ];

% Results of all the network, indexed by neuron
global neuronResult = [];

% Expected results of all the network, indexed by neuron
global neuronExpectedResult = []; % Im still thinking if this is useful

% Pre-calculated data related to the layers and neurons.
global layerForNeuron = [];
global layerIndexForNeuron = [];
global deltas = [];

% Pre-calculated inputs and weights.
global weightsPerLayer = [];
global inputForLayer = [];

% Array used as a pointer to the weights of a single node.
global neuronWeights;

