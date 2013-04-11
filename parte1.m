function x = stepNeuron(weights, in)
	result = weights .* in;
	h = sum(result);
	if(h < 0)
		x = 0;
	else
		x = 1;
	end
		
end