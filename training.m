source('parte1.m');
source('functions.m');
source('util.m');

global delta = 0.001;
global weights = [];
global flushtrains = true;
global eta = 0.0015;
global beta = 7;

global toCompute = @or;
global func = @sigmoide;

global layers = 2;
global nodesPerLayer = [ 2 1 ];
global weightsPerLayer = [ 6 3 ];

global inputForNode = [];

global nodeWeights;

function x = work(in, ni)
	global weights
	global nodeWeights 

	nodeWeights = weights(ni, :);

	in2 = zeros(1,length(in)+1);
	in2(1,2:length(in2)) = in;
	in2(1) = -1;
	x = stepNeuron(in2);
end

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

function retrainNode(n, ni)
	global weights
	global nodeWeights
	global toCompute
	global delta
	global inputForNode

	nodeWeights = weights(ni, :);

	if (length(nodeWeights) == 0) 
		nodeWeights = (2*rand(1,n+1)-1)/2;
	end

	inData = inputForNode(:,:,ni);

	top = 2^n;
	finished = zeros(1,top);
	i = 1;
	j = 0;
	while(prod(finished) < 1 && j < 100)
		i = 1;
		while(i < 1000)
			num = ceil(rand(1,1)*top);
			in = inData(num,:);
			inWithNoBias = in(2:n+1);
			result = stepNeuron(in);
			should = toCompute(inWithNoBias);
			err = abs(result-should);
			if err > delta
				nodeWeights = nodeWeights .+ fixWeights(in, should, result);
				finished = zeros(1,top);
			else
				finished(num) += 1;
			end
			fflush(stdout);
			i++;
		end
		j++;
	end
	weights(ni, :) = nodeWeights;
end

function x = retrain(n) 
	global weights
	global delta
	global nodesPerLayer
	global inputForNode

	nodesCount = sum(nodesPerLayer)
	if (length(weights) == 0) 
		weights = ((2*rand(nodesCount,n+1)-1)/2);
	end

	inputForNode = zeros(2^n, n+1, nodesCount);
	for ni = 1:nodesPerLayer(1)
		inputForNode(:,:,ni) = generateRandomInputs(n)
	end

	for ni = 1:nodesCount
		retrainNode(n, ni)
	end
end

function x = train(n)
	global weights
	global nodesPerLayer
	nodesCount = sum(nodesPerLayer);
	weights = ((2*rand(nodesCount,n+1)-1)/2);

	retrain(n);
end

function out = fixWeights(in, should, was)
	global eta
	out =in .* eta*(should-was);
end