function x = step(in)
	if(in < 0)
		x = 0;
	else
		x = 1;
	end
end

function x = sigmoide(in)
	x = tanh(1000*in);
end

function x = and(in)
	x = prod(in);
end