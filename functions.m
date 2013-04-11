function x = step(result)
	h = sum(result);
	if(h < 0)
		x = 0;
	else
		x = 1;
	end
end

function x = and(in)
	x = prod(in);
end