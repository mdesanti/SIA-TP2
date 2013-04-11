source('parte1.m');
weights = zeros(1,1);

function x = work(weights, in)
	in2 = zeros(1,length(in)+1);
	in2(1,2:length(in2)) = in;
	in2(1) = -1;
	x = stepNeuron(weights, in2);
end

function y = generateTrainingSets(n)
	max = 2^n;
	k = 1;
	x = zeros(max,n+1);
	for i=0:max-1
		out = (zeros(1,n));
		out = binary2vector(i,n);
%		out = out .- 0.5;
%		out = out .* 2;
		x(k,1) = -1;
		x(k,2:length(out)+1) = out;
		k++;
	end
	y = x;
end

function x = train(n)
	weights = (2*rand(1,n+1)-1)/2;
	sets = generateTrainingSets(n)
	finished = zeros(1,2^n);
	i = 1;
	while(sum(finished) <= 8 && i < 100*(2^n))
		num = mod(i,2^n) + 1;
		result = stepNeuron(weights, sets(num,:));
		should = prod(sets(num, 2:n));
		if(result != should)
			weights = weights .+ fixWeights(sets(num,:), should, result);
%			disp(fixWeights(sets(num,:), should, result));
			finished = zeros(1,2^n);
		else
			finished(num) += 1;
		end
		fflush(stdout);
		i++;
	end
	finished

	for	i=1:2^n
		work(weights, sets(i,2:2^n -1))
	end
	x = weights;
end

function out = fixWeights(in, should, was)
	eta = 0.15;
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