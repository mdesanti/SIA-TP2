clear
clc
main
network.n = 2
network.problem = problem.approximation(network.n, functs.tanh)
network.trainPctg = 0.8
network.beta = 0.5
network.neuronsPerLayer = [9 6 1];
network.train(network.n)