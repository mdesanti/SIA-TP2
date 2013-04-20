clear
clc
main
network.problem = problem.approximation(3, functs.tanh)
network.trainPctg = 0.6
network.beta = 0.5
%sinData = sin([-499:500]);
%network.data = sinData;
network.neuronsPerLayer = [20 1];
network.train(3)
