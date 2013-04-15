function n = neuralNetwork()
    n.eval = @eval;
    n.retrain = @retrain;
    n.train = @train;
end

function x = eval(in)
    global neuron

	global weights
	global neuronsPerLayer
    global inputForLayer

    
    n = length(weights(1,:));
    
    
    
    neuronCount = sum(neuronsPerLayer);
    inputForLayer = zeros(1, n + 1, length(neuronsPerLayer)+1);
	inputForLayer(1,2:n,1) = in;
    inputForLayer(:,1,:) = 1;
    
    for ni = 1:neuronCount
        neuron.runInput(n, ni, 1);
    end
    
    x = inputForLayer(1,2,length(neuronsPerLayer) + 1);
end

function retrain(n) 
    global neuron
    global util
	global neuronsPerLayer   
    global errs
    global err;
    global delta
    
    global N

	util.networkPrepare(n);

	neuronCount = sum(neuronsPerLayer);
    finished = 0;
    i = 1;
    totalErr = [];
    aux = 0;
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
			for ni = neuronCount:-1:1
				neuron.fixWeights(n, ni, inputIndex);
			end
        end
        if mod(i, 500) == 1
            for inputIndex = 1:2^n
                % Eval down-up...
                for ni = 1:neuronCount
                    neuron.runInput(n, ni, inputIndex);
                end
                % Prepare up-down...
                for ni = neuronCount:-1:1
                    neuron.prepareDeltas(n, ni, inputIndex);
                end
            end
            aux = sum(err.^2)/length(err);
            totalErr = [totalErr;aux];
            plot(totalErr);figure(gcf)
        end
        
        %if mod(i, 500) == 0
         %  plot(errs(:,:,neuronCount));figure(gcf)
        %end
        if aux < delta
            finished = 1;
        end
        i = i +1;
	end
end



function train(n)
	global weights
	weights = [];
	retrain(n);
end

