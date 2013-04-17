function problem = problem()
    problem.and = @buildAnd;
    problem.or = @buildOr;
    problem.xor = @buildXor;
    problem.simmetry = @buildSim;
end

% Build the functions structs

function x = buildAnd(n, act)
	x.learnF = @andF;
	x.f = act.f;
	x.df = act.df;

	global network
	network.neuronsPerLayer = [ n 1 ];
end

function x = buildOr(n, act)
	x.learnF = @orF;
	x.f = act.f;
	x.df = act.df;

	global network
	network.neuronsPerLayer = [ n 1 ];
end

function x = buildXor(n, act)
	x.learnF = @xorF;
	x.f = act.f;
	x.df = act.df;

	global network
	network.neuronsPerLayer = [ n 1 ];
end

function x = buildSim(n, act)
	x.learnF = @simmetry;
	x.f = act.f;
	x.df = act.df;

	global network
	network.neuronsPerLayer = [ n 1 ];
end

function x = andF(in)
	global network
	x = prod(in - network.intervals(1));
	if x == 0 
		x = network.intervals(1);
	else
		x = network.intervals(2);
	end
end

function x = orF(in)
	global network
	v = sum(in - network.intervals(1));
	if v > 0
		x = network.intervals(1);
	else
		x = network.intervals(2);
	end
end

function x = xorF(in)
	global network
    
    in = (in - network.intervals(1)) / network.intervals(2);
    
	result = bitxor(in(1), in(2));
	for i=3:length(in)
		result = bitxor(result, in(i));
	end
	if result == 0 
		x = network.intervals(1);
	else
		x = network.intervals(2);
	end
end

function x = simmetry(in)
	global network
  other = in(length(in):-1:1);
  x = 1;
  for i=1:length(in)
      if other(i) ~= in(i)
          x = 0;
      end
  end
  if x == 0 
		x = network.intervals(1);
	else
		x = network.intervals(2);
	end
end