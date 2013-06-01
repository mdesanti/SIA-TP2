function r = replacement()
    r.r1 = @rmethods1;
    r.r2 = @rmethods2;
end

function x = rmethod2(items, N)
    iterations = N/2;
end

function x = rmethod1(networks, evaluations)
    global genetic;
    selection = [];
    nextGeneration = networks;
    N = ceil(length(networks)/2);
    for i=1:N
       selection = genetic.firstSelectionMethod(evaluations, 2)
       winner1 = networks(selection(1));
       winner2 = networks(selection(2));
       [child1 child2] = genetic.crossoverMethod(winner1, winner2);
       nextGeneration(i) = genetic.mutate(child1);
       if (i+1 < length(networks))
           nextGeneration(i+1) = genetic.mutate(child1);
       end
    end
    x = nextGeneration;
end