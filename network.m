function x = networkEval(in, ni)
	global weights
	global neuronWeights 

	neuronWeights = weights(ni, :);

	in2 = zeros(1,length(in)+1);
	in2(1,2:length(in2)) = in;
	in2(1) = -1;
	x = neuronEval(in2);
end

function networkRetrain(n) 
	global weights
	global delta
	global neuronsPerLayer
	global weightsPerLayer
	global layerForNeuron
	global inputForLayer

	networkPrepare(n);

	neuronCount = sum(neuronsPerLayer);

	for i=1:10000
		% For every input
		for inputIndex = 1:2^n
			% Eval down-up...
			for ni = 1:neuronCount
				neuronRunInput(n, ni, inputIndex);
			end
			% Prepare up-down...
			for ni = neuronCount:-1:1
				neuronPrepareDeltas(n, ni, inputIndex);
			end
			% Fix weights up-down...
			for ni = neuronCount:-1:1
				neuronFixWeights(n, ni, inputIndex);
			end
		end
	end
end

function networkTrain(n)
	global weights
	global neuronsPerLayer

	neuronCount = sum(neuronsPerLayer);
	weights = ((2*rand(neuronCount,n+1)-1)/2);

	networkRetrain(n);
end

