function n = neuralNetwork()
    n.eval = @eval;
    n.retrain = @retrain;
    n.train = @train;
end

function x = eval(in)
    global neuron
    global network

    
    n = length(network.weights(1,:));
    
    neuronCount = sum(network.neuronsPerLayer);
    network.inputForLayer = zeros(1, n + 1, length(network.neuronsPerLayer)+1);
	network.inputForLayer(1,2:n,1) = in;
    network.inputForLayer(:,1,:) = 1;
    
    for ni = 1:neuronCount
        neuron.runInput(n, ni, 1);
    end
    
    x = network.inputForLayer(1,2,length(network.neuronsPerLayer) + 1);
end

function retrain(n) 
    global neuron
    global util
    global logging
    global network
    
	util.networkPrepare(n);

	neuronCount = sum(network.neuronsPerLayer);
    finished = 0;
    i = 1;
	while(~finished)
		% For every input
		for inputIndex = 1:2^n
			% Eval down-up...
			for ni = 1:neuronCount
				neuron.runInput(n, ni, inputIndex);
			end
			% Prepare up-down...
			for ni = neuronCount:-1:1
				neuron.prepareDeltas(n, ni, inputIndex);
			end
			% Fix weights up-down...
			for ni = 1:neuronCount
				neuron.fixWeights(n, ni, inputIndex);
            end

            
            deltaError = logging.currentError(inputIndex) - logging.lastError(inputIndex);
            if (deltaError > 0) 
               deltaEta = -0.0005 * network.eta;
            else
               deltaEta = 0.0012 * network.eta;
            end
            network.eta = network.eta + deltaEta;

           
        end
        
        if mod(i, 500) == 0
           plot(logging.errors(:,:,neuronCount));figure(gcf)
           network.eta
        end
        
        
        
        
        finished = 1;
        for e=1:length(network.err)
           if(network.err(e) > network.delta)
               finished = 0;
               break;
           end
        end
        i = i + 1;
	end
end



function train(n)
	global network
	network.weights = [];
	retrain(n);
end

