% initPopulation();
% juail not ended
%     evaluateIndividuals();
%     seleccionar, aparear
%     recombinar, mutar
%     reemplazar
function genetic = genetic()
    genetic.run = @run
    genetic.init = @initPopulation
end


function x = run()
    global networks
    global network
    global genetic
    global util
    global allErrors
    networks = initPopulation(50, [3 2 1]);
    theTestSets = networks(1).data.testSet;
    theExpected = networks(1).data.problem.expected;
    ended = 0;
    evaluations = [];
%     total = length(networks(1).data.testSet(:,2:3));
    from = network.trainSize;
    k = 100;
    allErrors = [];
    while(k > 0)
        for i=1:length(networks)
            result = [];
            for j=from+1:600-2
                util.setNetwork(networks(i).data);
                aux = network.eval(theTestSets(j,2:3))*3.8;
                result(j-from) = aux - theExpected(j)*3.8;
            end
            error(i) = (sum(result.^2)/length(result));
            evaluations(i) = 1/(sum(result.^2)/length(result));
        end
        e1 = evaluations;
        allErrors = [allErrors mean(error)];
        mean(evaluations)
        networks =  genetic.replacementMethod(networks, evaluations);
        k = k - 1;
        figure(1);
        plot(allErrors);
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
    global network;
    global mutationProbability;
    
    mutationProbability = 0.2;
    
    network = initNetwork;
    % Control variables
    network.delta = 0.001;
    network.startEta = 1;
    network.beta = 1;
    network.N = 10000;

    network.intervals = [-1 1];
    network.weights = [];

    network.problem = problem.approximation(2, functs.tanh);
    network.neuronsPerLayer = neuronsPerLayer;
    
    network.testSet = [];
    network.trainingSet = [];
    network.trainPctg = 0.5;

    network.adaptive = true;
    network.momentum = true;

    network.iterLimit = 200;
    network.trainSize = 400;
    network.n = 2;
    
    network = util.setNetwork(network);
        
    util.networkPrepare(2);
    
    
    
    net = network;
end
