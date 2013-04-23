clear
clc
main
network.n = 3;
network.problem = problem.approximation(network.n, functs.tanh);
network.iterLimit = 500;
network.trainPctg = 0.4;
network.momentum = true;
network.adaptive = false;
network.beta = 0.5;
if network.adaptive
	network.startEta = 1;
else
	network.startEta = 0.01;
end
network.neuronsPerLayer = [20 1];
network.train(network.n);
save([logging.pwd,'/','network'],'network');