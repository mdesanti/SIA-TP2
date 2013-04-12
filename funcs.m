function func = func()
    func.step = @step;
    func.sigmoide = @sigmoide;
    func.derivsigmoide = @derivsigmoide;
    func.lineal = @lineal;
    func.and = @andF;
    func.or = @orF;
    func.xor = @xorF;
end


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

function x = derivsigmoide(in)
	global beta
	x = 1/2 * beta * sec(beta*in)^2;
end

function x = lineal(in)

end

function x = andF(in)
	x = prod(in);
end

function x = orF(in)
	v = sum(in);
	if v > 0
		x = 1;
	else
		x = 0;
	end
end

function x = xorF(in)
	result = bitxor(in(1), in(2));
	for i=3:length(in)
		result = bitxor(result, in(i));
	end
	x = result;
end