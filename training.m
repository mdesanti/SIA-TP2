source('parte1.m');
source('functions.m');
weights = zeros(1,1);

function x = work(weights, in, func)
	in2 = zeros(1,length(in)+1);
	in2(1,2:length(in2)) = in;
	in2(1) = -1;
	x = stepNeuron(weights, in2, func);
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

function x = train(n, shouldFunct, func)
	DELTA = 0.01;
	weights = (2*rand(1,n+1)-1)/2;
	sets = generateTrainingSets(n)
	top = 2^n;
	finished = zeros(1,top);
	i = 1;
	j = 0;
	eta = 0.4;
	while(prod(finished) < 1 && j < 100)
		i = 1;
		while(i < 1000)
			num = ceil(rand(1,1)*top);
			in = sets(num,:);
			inWithNoBias = in(2:n+1);
			result = stepNeuron(weights, in, func);
			should = shouldFunct(inWithNoBias);
			err = abs(result-should);
			if err > DELTA
				weights = weights .+ fixWeights(in, should, result);
				finished = zeros(1,top);
			else
				finished(num) += 1;
			end
			fflush(stdout);
			i++;
		end
		%weights
		j++;
	end
	x = weights;
end

function out = fixWeights(in, should, was)
	eta = 0.015;
	debug_on_error (1);
	out =in .* eta*(should-was);
end

function out = binary2vector(data,nBits)

	powOf2 = 2.^[0:nBits-1];

	%# do a tiny bit of error-checking
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