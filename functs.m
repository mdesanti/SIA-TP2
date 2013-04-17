function funct = funct()
    funct.sigmoide.f = @sigmoide;
    funct.sigmoide.df = @derivSigmoide;
    funct.step.f = @step;
    funct.step.df = @derivStep;
    funct.lineal.f = @lineal;
    funct.lineal.df = @derivStep;
end

function x = step(in)
	global network
	if (in < 0)
		x = network.intervals(1);
	else
		x = network.intervals(2);
	end
end

function x = derivStep(in)
	x = 1;
end

function x = sigmoide(in)
	global network
	intervalDiff = abs(network.intervals(1) - network.intervals(2));
	x = tanh(in) * intervalDiff / 2;
end

function x = derivSigmoide(in)
	global beta
	global network
  intervalDiff = abs(network.intervals(1) - network.intervals(2));
	x = sech(in).^2  * intervalDiff / 2; %intervalDiff * (y * (1 - y)) - 0.5;
end

function x = lineal(in)
    % TODO: Implement this
end