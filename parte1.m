function x = stepNeuron(in)
	global nodeWeights
	global func
	result = sum(nodeWeights .* in);

	debug_on_error(1)
	x = func(result);
end