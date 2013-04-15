function neuron = neuron()
	neuron.eval = @neuronEval;
	neuron.runInput = @runInput;
	neuron.prepareDeltas = @prepareDeltas;
	neuron.fixWeights = @fixWeights;
end


function x = neuronEval(in)
	global neuronWeights
	global func
	result = sum(neuronWeights(1:length(in)) .* in);
	x = func(result);
end

% Runs the input and stores the results for each neuron
function runInput(n, ni, inputIndex)
	global weights
	global neuronWeights
	global inputForLayer
	global layerForNeuron
	global layerIndexForNeuron
	global weightsPerLayer

	layer = layerForNeuron(ni);
	layerIndex = layerIndexForNeuron(ni);
	neuronWeights = weights(ni, :);
	weightnum = weightsPerLayer(layer);
	in = inputForLayer(inputIndex,1:weightnum,layer);
	

	% Step 3 on book
	% Pushes the evaluation to the next neurone
	inputForLayer(inputIndex, layerIndex + 1, layer + 1) = neuronEval(in);

	% should = toCompute(inWithNoBias);
	% err = abs(result-should);
	% if err > delta
	% 	valid = 0;
	% else
	% 	valid = 1;
	% end
end

function prepareDeltas(n, ni, inputIndex)
    global funcs
    global util

	global weights
	global neuronWeights
	global inputForLayer
	global toCompute
	global layerIndexForNeuron
	global layerForNeuron
	global neuronsPerLayer
	global deltas
    global delta
	global weightsPerLayer
    
    global errs;
    global errI;
    global err;
    

	neuronWeights = weights(ni, :);

	layer = layerForNeuron(ni);
	layerIndex = layerIndexForNeuron(ni);
	weightnum = weightsPerLayer(layer);
	in = inputForLayer(inputIndex,1:weightnum,layer);
	hi = sum(neuronWeights(1:length(in)) .* in);
	gprima = funcs.derivsigmoide(hi); % Check this

    
	if (layer == length(neuronsPerLayer))
		% Step 4 on book
        in = inputForLayer(inputIndex,1:weightsPerLayer(1),1);
		result = inputForLayer(inputIndex,layerIndex + 1,layer + 1);
        inWithNoBias = in(2:length(in)); % TODO: Take this, it's unsafe!
		expected = toCompute(inWithNoBias);
		error = (expected - result); % Check This
        err(inputIndex) = error;
    else
        nextLayerNodeCount = neuronsPerLayer(layer + 1);
        % gets the indexes of the nodes in the upper layer
        nextLayerFirstNodeIndex = util.getIndexesForLayer(layer+1);
        
		% Step 5 on book
        added = 0;
        for i = nextLayerFirstNodeIndex: nextLayerFirstNodeIndex+nextLayerNodeCount - 1
            %the index is +1 because of the biased input weight
            li = layerIndexForNeuron(i); % Index on layer for this neuron
            added = added + weights(i,layerIndex + 1) * deltas(li, layer + 1);
        end
		error = added; % Check This
    end
    
    
%     
    if (abs(error) < delta)
        error = 0;
    end
    deltas(layerIndex, layer) = gprima * error;
    
    % Store error history
    iSubIndex = mod(inputIndex - 1, 2^n) + 1;
    errs(errI(iSubIndex, ni),:,iSubIndex, ni) = neuronWeights;
    errI(iSubIndex,ni) = errI(iSubIndex,ni) + 1;
end

function fixWeights(n, ni, inputIndex)
	global eta
	global weights
	global neuronWeights
	global inputForLayer
	global deltas
	global layerIndexForNeuron
	global layerForNeuron
	global neuronsPerLayer 
	global weightsPerLayer

	neuronWeights = weights(ni, :);
	layer = layerForNeuron(ni);
	niOnLayer = layerIndexForNeuron(ni);

	% Step 6 on book
	% Check this
	weightnum = weightsPerLayer(layer);
	deltaWeight = eta .* deltas(niOnLayer, layer) .* inputForLayer(inputIndex, :, layer);
	weights(ni, :) = neuronWeights + deltaWeight;
end