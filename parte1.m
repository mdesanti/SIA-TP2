function x = stepNeuron(weights, in, func)
	result = sum(weights .* in);
	x = func(result);
end