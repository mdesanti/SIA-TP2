function x = step(in)
	if(in < 0)
		x = 0;
	else
		x = 1;
	end
end

function x = sigmoide(in)
	x = (tanh(7*in) + 1)/2;
end

function x = lineal(in)

end

function x = and(in)
	x = prod(in);
end