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
    totalErr = [];
    aux = 0;
    firstIteration = false;
    network.errorRepeats = 0;
    network.tendencyDown = 0;
    network.oldWeights = zeros(length(network.weights(:,1)), length(network.weights(1,:)),1);
    oldEta = [];
    errorFixes = 1;
    weightsBeforeIteration = [];
    oldDeltaWeights = [];
    cancelAlpha = 0;
    deltaErrors = [];
    noEtaUpdateTime = 2;
    oldLastError = 0;

    while(~finished)
        weightsBeforeIteration = network.weights;
        oldDeltaWeights = network.lastDeltaWeights;
        % For every input
        
        slice = randperm(2^n);

        slice = slice(1:max(2^n,1));

        for inputIndex = slice
            logging.enabled = false;
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
				neuron.fixWeights(n, ni, inputIndex, cancelAlpha);
            end
         end
        
         
            logging.enabled = true;
            for subInputIndex = 1:2^n
                % Eval down-up...
                for ni = 1:neuronCount
                    neuron.runInput(n, ni, subInputIndex);
                end
                neuron.prepareDeltas(n, neuronCount, subInputIndex);
            end
        
            oldTotalErr = totalErr;
            aux = sum(network.err.^2)./length(network.err);
            totalErr = [totalErr;aux];


            oldLastError = logging.lastError;
            logging.lastError = sum(logging.currentError);
            logging.currentError = totalErr(length(totalErr));

            
            if (length(totalErr) > 1)
               deltaError = logging.currentError - logging.lastError;
               currE = logging.currentError;
               lastE = logging.lastError;
               eta = network.eta;
               if (deltaError > 0)    
                deltaEta = -0.5 * network.eta;
                network.eta = network.eta + deltaEta;
                eta = network.eta;
                network.weights = weightsBeforeIteration;
                network.errorRepeats = 0;
                logging.errorIndexes = logging.errorIndexes - 1;
                logging.currentError = logging.lastError;
                logging.lastError = oldLastError;
                i = i - 1;
                totalErr = oldTotalErr;
                network.lastDeltaWeights = oldDeltaWeights;
                cancelAlpha = 1;
               else
                network.errorRepeats = network.errorRepeats + 1;
                if (network.errorRepeats > 10)
                    deltaEta = 5;
                    network.eta = network.eta * deltaEta;
                    eta = network.eta;
                    network.errorRepeats = 0;
                    % noEtaUpdateTime = 10;
                end
                cancelAlpha = 0;
               end
               
               deltaErrors = [deltaErrors deltaError];
            end
            noEtaUpdateTime = noEtaUpdateTime - 1;
        
        oldEta = [oldEta network.eta];

        if mod(i, 25) == 0
            figure(1); 
            plot(logging.errors(:,:,neuronCount));
        end
            
        if mod(i, 25) == 0
            figure(2);
            plot(totalErr);
        end
        
        if mod(i, 25) == 0
            figure(3);
            plot(oldEta);
        end

        if mod(i, 25) == 0
            figure(4);
            permuted = permute(network.oldWeights, [2 3 1]);
            plot(permuted(:,:,neuronCount)');
        end

        
        if (length(deltaErrors) > 100) 
            figure(5);
            plot(deltaErrors(50:length(deltaErrors)));
        end
        
        if aux < network.delta
            finished = 1;
        end
        if (i > 1)
            network.oldWeights(:,:,i) = network.weights;
        end
        % end
        i = i + 1;
    end
end



function train(n)
	global network
	network.weights = [];
	retrain(n);
end
