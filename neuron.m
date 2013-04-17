function neuron = neuron()
	neuron.eval = @neuronEval;
	neuron.runInput = @runInput;
	neuron.prepareDeltas = @prepareDeltas;
	neuron.fixWeights = @fixWeights;
end


function x = neuronEval(in)
	global network
	result = sum(network.neuronWeights(1:length(in)) .* in);
	x = network.problem.f(result);
end

% Runs the input and stores the results for each neuron
function runInput(n, ni, inputIndex)
    global network

    network.neuronWeights = network.weights(ni, :);
    
	layer = network.layerForNeuron(ni);
	layerIndex = network.layerIndexForNeuron(ni);
	weightnum = network.weightsPerLayer(layer);
	in = network.inputForLayer(inputIndex,1:weightnum,layer);
	
	% Step 3 on book
	% Pushes the evaluation to the next neurone
	network.inputForLayer(inputIndex, layerIndex + 1, layer + 1) = neuronEval(in);
end

function prepareDeltas(n, ni, inputIndex)
    global funcs
    global util
    global network
    global logging
    
	network.neuronWeights = network.weights(ni, :);

	layer = network.layerForNeuron(ni);
	layerIndex = network.layerIndexForNeuron(ni);
	weightnum = network.weightsPerLayer(layer);
	in = network.inputForLayer(inputIndex,1:weightnum,layer);
	hi = sum(network.neuronWeights(1:length(in)) .* in);
	gprima = network.problem.df(hi); % Check this

    
	if (layer == length(network.neuronsPerLayer))
		% Step 4 on book
        in = network.inputForLayer(inputIndex,1:network.weightsPerLayer(1),1);
		result = network.inputForLayer(inputIndex,layerIndex + 1,layer + 1);
        inWithNoBias = in(2:length(in)); % TODO: Take this, it's unsafe!
		expected = network.problem.learnF(inWithNoBias);
		error = (expected - result); % Check This
        if (abs(error) < 0.0001) 
           error = 0;
        end
        network.err(inputIndex) = error;
    else
        nextLayerNodeCount = network.neuronsPerLayer(layer + 1);
        % gets the indexes of the nodes in the upper layer
        nextLayerFirstNodeIndex = util.getIndexesForLayer(layer+1);
        
		% Step 5 on book
        added = 0;
        for i = nextLayerFirstNodeIndex: nextLayerFirstNodeIndex+nextLayerNodeCount - 1
            %the index is +1 because of the biased input weight
            li = network.layerIndexForNeuron(i); % Index on layer for this neuron
            added = added + network.weights(i,layerIndex + 1) * network.deltas(li, layer + 1);
        end
		error = added; % Check This
        if (abs(error) < 0.0001) 
           error = 0;
        end
    end
    
    
    network.deltas(layerIndex, layer) = (gprima + 0.1) * error;
    

    if (logging.enabled)
        % Store error history
        iSubIndex = mod(inputIndex - 1, 2^n) + 1;
        logging.errors(logging.errorIndexes(iSubIndex, ni),iSubIndex, ni) = (gprima + 0.1) * error;
        logging.errorIndexes(iSubIndex, ni) = logging.errorIndexes(iSubIndex, ni) + 1;
    end
end



function fixWeights(n, ni, inputIndex, cancelMult)
    global network
 
    neuronWeights = network.weights(ni, :);
    layer = network.layerForNeuron(ni);
    niOnLayer = network.layerIndexForNeuron(ni);
 
    % Step 6 on book
    % Check this
    deltaWeight = network.eta .* network.deltas(niOnLayer, layer) .* network.inputForLayer(inputIndex, :, layer);
    
    if (cancelMult == 0)
        deltaWeight = network.lastDeltaWeights(ni,:) * 0.9 + deltaWeight;
    else
        deltaWeight = 0 + deltaWeight;
    end
    
    network.lastDeltaWeights(ni,:) = deltaWeight;
    
    network.weights(ni, :) = neuronWeights + deltaWeight;
end