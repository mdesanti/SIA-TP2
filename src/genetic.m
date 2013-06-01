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
    networks = initPopulation(10, [3 2 1], 10);
    ended = 0;
    evaluations = [];
    total = length(network.testSet(:,2:3));
    from = 1000-total-2;
    while(~ended)
        %evaluateIndividuals()
        for i=1:length(networks)
            for i=from+1:1000-2
                result = [];
                aux = network.eval(network.testSet(i,2:3))*3.8;
                result(i-from) = aux - network.problem.expected(i)*3.8;
            end
            %revisar esto!
            evaluations(i) = 36-sum(result.^2)/length(result);
        end
        %select
        selection = select(evaluations);        
    end
end
    
function x = initPopulation(size, neuronsPerLayer, n)
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

    network = initNetwork;
    % Control variables
    network.delta = 0.001;
    network.startEta = 1;
    network.beta = 1;
    network.N = 10000;

    network.intervals = [-1 1];
    network.weights = [];

    network.problem = problem.approximation(neuronsPerLayer(1), functs.tanh);
    network.neuronsPerLayer = neuronsPerLayer;
    
    network.testSet = [];
    network.trainingSet = [];
    network.trainPctg = 0.5;

    network.adaptive = true;
    network.momentum = true;

    network.iterLimit = 200;
    network.n = 2;
    
    network = util.setNetwork(2, network);
        
    util.networkPrepare(2);
    
    net = network;
end