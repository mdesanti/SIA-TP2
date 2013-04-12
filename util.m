
% Initializes Weights
% Initializes Inputs
% Models the network
function networkPrepare(n)
	global weights
	global delta
	global deltas
	global neuronsPerLayer
	global weightsPerLayer
	global layerForNeuron
	global inputForLayer
	global layerIndexForNeuron

	neuronCount = sum(neuronsPerLayer);

	% Initializes the weight only if its the first time.
	% !!! Weights are stored based on the index of each node.
	if (length(weights) == 0) 
		weights = ((2*rand(neuronCount,n+1)-1)/2);
	end

	neuronResult = zeros(neuronCount);
	neuronExpectedResult = zeros(neuronCount);

	% Makes matrix containing the inputs for each layer.
	% !!! Inputs are stored based on the level of each layer.
	inputForLayer = zeros(2^n, n+1, length(neuronsPerLayer) + 1); % TODO: n+1 is not a wire parameter, it should be the maximum size of inputs for all layers
	inputForLayer(:,:,1) = generateRandomInputs(n);
	for i = 2:length(neuronsPerLayer) + 1
		inputForLayer(:,1,i) = -1;
	end

	% Prepares the deltas matrix
	deltas = zeros(max(neuronsPerLayer) + 1, length(neuronsPerLayer));
	
	% Generates an index to know how many weights has each layer
	weightsPerLayer = zeros(neuronCount);
	for layer = 1:length(neuronsPerLayer)
		if (layer == 1)
			weightsPerLayer(layer) = 1 + n;
		else
			weightsPerLayer(layer) = 1 + neuronsPerLayer(layer - 1);
		end
	end

	% Helps to know for a given node index to which layer it belongs.
	i = 1;
	for layer = 1:length(neuronsPerLayer)
		for k = 1:neuronsPerLayer(layer)
			layerIndexForNeuron(i) = k;
			layerForNeuron(i++) = layer;
		end
	end
end

% Converts a number to its size in bits
function out = binary2vector(data,nBits)
	powOf2 = 2.^[0:nBits-1];
	
	if data > sum(powOf2)
	   error('not enough bits to represent the data')
	end

	out = false(1,nBits);

	ct = nBits;

	while data>0
		if data >= powOf2(ct)
			data = data-powOf2(ct);
			out(ct) = true;
		end
		ct = ct - 1;
	end
	out = fliplr(out);
end

% Generates inputs for the network
function y = generateRandomInputs(n)
	max = 2^n;
	k = 1;
	x = zeros(max,n+1);
	for i=0:max-1
		out = (zeros(1,n));
		out = binary2vector(i,n);
		x(k,1) = -1;
		x(k,2:length(out)+1) = out;
		k++;
	end
	y = x;
end