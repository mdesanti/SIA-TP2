function x = stepNeuron(in, func)
	global weights
	result = sum(weights .* in);

	debug_on_error(1)
	x = func(result);
end