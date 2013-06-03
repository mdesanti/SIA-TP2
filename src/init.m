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
selection = selectionFile;

load data

networkData.origData = x / 4;
networkData.data = x / 4;

global replacement;

replacement = replacementFile;

genetic.replacementMethod = replacement.method1;
genetic.crossoverMethod = crossover.onePointCrossover;
genetic.firstSelectionMethod = selection.roulette;
genetic.secondSelectionMethod = selection.roulette;
genetic.mutate = crossover.mutate;
