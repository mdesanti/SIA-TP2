utilsFile = util;
functsFile = functs;
problemFile = problem;
neuronFile = neuron;
geneticFile = genetic;
crossoverFile = crossover;
networkFile = neuralNetwork;
selectionFile = selection;
replacementFile = replacement;

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
global initNetwork
initNetwork = networkFile

global genetic
genetic = geneticFile

global crossover;
crossover = crossoverFile

global networkData;
global selection;
global network;
global iterationsN;

iterationsN = -1;
network = []; % dummy
selection = selectionFile;

load data

networkData.origData = x / 3.8;
networkData.data = x / 3.8;

global replacement;
global trainProbability;

global ids;
ids = 0;

replacement = replacementFile;

trainProbability = 0.001;

genetic.method2K = 10;
genetic.replacementMethod = replacement.method1;
genetic.crossoverMethod = crossover.onePointCrossover;
genetic.firstSelectionMethod = selection.rank;
genetic.secondSelectionMethod = selection.rank;
genetic.mutate = crossover.mutate;
genetic.train = crossover.train;
