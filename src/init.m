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
networkData.loaded = false;

global replacement;
global trainProbability;

global ids;
ids = 0;

replacement = replacementFile;


global mutationProbability;
global crossOverProbability;
crossOverProbability = 0.8;
mutationProbability = 0.003;
trainProbability = 0.005;


global boltzman

boltzman.mean = 0;

% Arquitectura de la red.
genetic.arch = [9 6 1];

% Cantidad de redes
genetic.networkCount = 30;

% Cantidad de elementos a tomar en el metodo 2
genetic.method2K = 20;

% Cantidad de elementos a tomer en el metodo 1.
genetic.k = 10;

% Cantidad de patrones de entrenamiento de backpropagation
genetic.trainSize = 400;

% Cantidad de iteraciones en el backpropagation
genetic.iterations = 100;

% Cantidad de patrones de entrenamiento del AG.
genetic.checkSize = 400;

% Parametros de finalizacion

% Limite de iteraciones con el mismo error minimo
genetic.contentLimit = 100;                  
% Limite de generacinoes global
genetic.generations = 500;
% Error minimo a obtener
genetic.bestTargetError = 0.01;

% Metodos de finalizacion a combinar
genetic.endMethods = {genetic.generationContent, genetic.generationCount};
% Metodo best = combinacion de otros.
genetic.endMethod = genetic.best;

% Metodo de reemplazo
genetic.replacementMethod = replacement.method2;
% Metodo de crossover
genetic.crossoverMethod = crossover.uniformParametrizedCrossOver;
% Metodo de seleccion
genetic.firstSelectionMethod = selection.roulette;
% Segundo metodo de seleccion (para el metodo 2)
genetic.secondSelectionMethod = selection.roulette;


genetic.mutate = crossover.mutate;
genetic.train = crossover.train;
