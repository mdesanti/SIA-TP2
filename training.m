source('parte1.m');
source('functions.m');
source('util.m');
global delta = 0.001;
global weights = [];
global eta = 0.0015;
global beta = 7;

function x = work(in, func)
	global weights
	in2 = zeros(1,length(in)+1);
	in2(1,2:length(in2)) = in;
	in2(1) = -1;
	x = stepNeuron(in2, func);
end

function y = generateTrainingSets(n)
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

function x = retrain(n, shouldFunct, func) 
	global weights
	global delta
	if (length(weights) == 0) 
		weights = (2*rand(1,n+1)-1)/2;
	end
	sets = generateTrainingSets(n)
	top = 2^n;
	finished = zeros(1,top);
	i = 1;
	j = 0;
	while(prod(finished) < 1 && j < 100)
		i = 1;
		while(i < 1000)
			num = ceil(rand(1,1)*top);
			in = sets(num,:);
			inWithNoBias = in(2:n+1);
			result = stepNeuron(in, func);
			should = shouldFunct(inWithNoBias);
			err = abs(result-should);
			if err > delta
				weights = weights .+ fixWeights(in, should, result);
				finished = zeros(1,top);
			else
				finished(num) += 1;
			end
			fflush(stdout);
			i++;
		end
		j++;
	end
end

function x = train(n, shouldFunct, func)
	global weights
	weights = (2*rand(1,n+1)-1)/2;
	retrain(n, shouldFunct, func)
end

function out = fixWeights(in, should, was)
	global eta
	out =in .* eta*(should-was);
end