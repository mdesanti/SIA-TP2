function x = stepNeuron(weights, in, func)
	result = weights .* in;
	x = func(result);
		
end