clear
clc
main;
network.problem = problem.approximation(2, functs.tanh);
network.trainPctg = 0.5;
network.beta = 0.5;
%sinData = sin([-399:400]);
%network.data = sinData;
network.neuronsPerLayer = [10 5 1];
network.train(2)