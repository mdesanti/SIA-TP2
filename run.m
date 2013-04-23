clear
clc
main
network.n = 3;
network.problem = problem.approximation(network.n, functs.tanh);
network.startEta = 0.005;
network.iterLimit = 200;
network.trainPctg = 0.4;
network.momentum = true;
network.adaptive = true;
network.beta = 0.5;
network.neuronsPerLayer = [20 1];
network.train(network.n);
save([logging.pwd,'/','network'],'network');