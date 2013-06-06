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
global stepAmount;


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


global mutationProbability;
global crossOverProbability;
crossOverProbability = 0.2;
mutationProbability = 0.0005;
trainProbability = 0.01;

genetic.trainSize = 10;
genetic.method2K = 14;
genetic.replacementMethod = replacement.method2;
genetic.crossoverMethod = crossover.anularCrossOver;
genetic.firstSelectionMethod = selection.roulette;
genetic.secondSelectionMethod = selection.roulette;
genetic.mutate = crossover.mutate;
genetic.train = crossover.train;
