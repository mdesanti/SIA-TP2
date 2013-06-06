% initPopulation();
% juail not ended
%     evaluateIndividuals();
%     seleccionar, aparear
%     recombinar, mutar
%     reemplazar
function genetic = genetic()
    genetic.run = @run
    genetic.init = @initPopulation
    genetic.k = 24;
end


function x = run()
    global networks
    global network
    global genetic
    global util
    global allErrors
    global stepAmount
    networks = initPopulation(30, [9 6 1]);
    theTestSets = networks(1).data.trainingSet;
    theExpected = networks(1).data.problem.expected;
    ended = 0;
    evaluations = [];
%     total = length(networks(1).data.testSet(:,2:3));
    from = network.trainSize;
    k = 1000000;
    allErrors = [];
    minErrors = [];
    maxErrors = [];
    while(k > 0)
        evaluations = [];
        ids = [];
        for i=1:length(networks)
            result = [];
            util.setNetwork(networks(i).data);
            for j=1:network.trainSize
                aux = network.eval(theTestSets(j,2:3));
                result(j) = theExpected(j) - aux;
            end
            error(i) = (sum(result.^2)/length(result));
            evaluations(i) = sqrt(1/error(i));
        end
        e1 = evaluations;
        allErrors = [allErrors mean(error)];
        minErrors = [minErrors min(error)];
        maxErrors = [maxErrors max(error)];
        networks =  genetic.replacementMethod(networks, evaluations);
        k = k - 1;
        figure(1);
        semilogy(allErrors, 'b');
        hold on;
        semilogy(minErrors, 'g');
        semilogy(maxErrors, 'r');
        hold off;
    end
end
    
function x = initPopulation(size, neuronsPerLayer)
    neuronCount = sum(neuronsPerLayer);
    networks(1:size) = struct('x',[]);
    for k=1:size
        networks(k).data = initNetwork(neuronsPerLayer);
    end
    x = networks;
end


function m = evaluateIndividuals(individuals) 
    
end


function net = initNetwork(neuronsPerLayer)
    global problem;
    global functs;
    global util;
    global initNetwork;
    global genetic;
    global network;
    global ids;
    
    network = initNetwork;
    % Control variables
    network.delta = 0.001; 
    network.startEta = 0.001;
    network.beta = 0.5;
    network.N = 10000;

    network.intervals = [-1 1];
    network.weights = [];

    network.problem = problem.approximation(2, functs.tanh);
    network.neuronsPerLayer = neuronsPerLayer;
    
    network.testSet = [];
    network.trainingSet = [];
    network.trainPctg = 0;

    network.adaptive = true;
    network.momentum = true;

    network.iterLimit = 200;
    network.trainSize = genetic.trainSize;
    network.n = 2;
    network.id = ids + 1;
    ids = ids + 1;
    
    network = util.setNetwork(network);
        
    util.networkPrepare(2, 1);
%     
%     aux = load('weightsBackup');
%     network.weights = aux;
%     
    
    
    net = network;
end
