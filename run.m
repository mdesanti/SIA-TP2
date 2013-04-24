clear
clc
main
network.problem = problem.approximation(1, functs.tanh)
network.trainPctg = 0.8
network.beta = 0.5
%sinData = sin([-499:500]);
%network.data = sinData;
%network.momentum = 0;
network.neuronsPerLayer = [1];
network.train(1)