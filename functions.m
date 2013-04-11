function x = step(in)
	if (in < 0)
		x = 0;
	else
		x = 1;
	end
end

function x = sigmoide(in)
	global beta
	x = (tanh(beta*in) + 1)/2;
end

function x = lineal(in)

end

function x = and(in)
	x = prod(in);
end

function x = or(in)
	v = sum(in);
	if v > 0
		x = 1;
	else
		x = 0;
	end
end