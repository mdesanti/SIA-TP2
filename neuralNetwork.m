function n = neuralNetwork()
    n.eval = @eval;
    n.retrain = @retrain;
    n.train = @train;
end

function x = eval(in, ni)
    global neuron

	global weights
	global neuronWeights 
    

	neuronWeights = weights(ni, :);

	in2 = zeros(1,length(in)+1);
	in2(1,2:length(in2)) = in;
	in2(1) = -1;
	x = neuron.eval(in2);
end

function retrain(n) 
    global neuron
    global util

	global neuronsPerLayer   

	util.networkPrepare(n);

	neuronCount = sum(neuronsPerLayer);

	for i=1:10000
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
	end
end

function train(n)
	global weights
	global neuronsPerLayer

	neuronCount = sum(neuronsPerLayer);
	weights = ((2*rand(neuronCount,n+1)-1)/2);

	retrain(n);
end

