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
    iterWeights = [];
    oldDeltaWeights = [];
    cancelAlpha = 0;
    deltaErrors = [];
    noEtaUpdateTime = 2;

    while(~finished)
        iterWeights = network.weights;
        oldDeltaWeights = network.lastDeltaWeights;
        % For every input
        logging.enabled = false;
        slice = randperm(2^n);
%         for inputIndex = slice
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
				neuron.fixWeights(n, ni, inputIndex, cancelAlpha);
            end
        end
        
        for inputIndex = 1:2^n
        logging.enabled = true;
        % Eval down-up...
        for ni = 1:neuronCount
            neuron.runInput(n, ni, inputIndex);
        end
        
        neuron.prepareDeltas(n, neuronCount, inputIndex);

        
        end
        oldTotalErr = totalErr;
        aux = sum(network.err.^2)/length(network.err);
        totalErr = [totalErr;aux];


        if (cancelAlpha == 0)
            logging.lastError = sum(logging.currentError);
        end
        logging.currentError = totalErr(length(totalErr(:,1)),1);

        
%         if (length(totalErr) > 1)
%            deltaError = logging.currentError - logging.lastError;
%            currE = logging.currentError
%            lastE = logging.lastError
%            if (deltaError > 0)    
%             deltaEta = -0.1 * network.eta;
%             network.eta = network.eta + deltaEta;
%             eta = network.eta;
%             network.weights = iterWeights;
%             network.errorRepeats = 0;
%             logging.errorIndexes = logging.errorIndexes - 1;
%             i = i - 1;
%             totalErr = oldTotalErr;
%             network.lastDeltaWeights = oldDeltaWeights;
%             cancelAlpha = 1;
%             disp('Error correction');
%             disp(i);
%             noEtaUpdateTime = 10;
%            elseif (deltaError < -0.000001 && noEtaUpdateTime > 0)
%             network.errorRepeats = network.errorRepeats + 1;
%             if (network.errorRepeats > 10)
%                 deltaEta = 1.1;
%                 network.eta = network.eta * deltaEta;
%                 eta = network.eta;
%                 network.errorRepeats = 0;
%                 noEtaUpdateTime = 10;
%             end
%            end
%            cancelAlpha = 0;
%            deltaErrors = [deltaErrors deltaError];
%         else
%            noEtaUpdateTime = noEtaUpdateTime - 1;
%         end
        
        

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

