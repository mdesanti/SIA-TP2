function r = replacement()
    r.r1 = @rmethods1;
    r.r2 = @rmethods2;
end

function x = rmethods1(networks, evaluations)
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

function x = rmethods2(networks, evaluations)
    global genetic;
    selection = [];
    
    N = (length(networks));
    resnextGeneration = []
    toChange = genetic.firstSelectionMethod(evaluations, floor(genetic.method2K / 2) * 2);
    toStay = genetic.firstSelectionMethod(evaluations, N - floor(genetic.method2K / 2) * 2);
    resnextGeneration(1:length(toStay)) = toStay;

    M = length(toStay);

    for j=1:floor(genetic.method2K / 2)
        p1 = networks(toChange(randi([1 genetic.method2K])));
        p2 = networks(toChange(randi([1 genetic.method2K])));
        [c1, c2] = genetic.crossoverMethod(p1,p2);
        resnextGeneration(M + j) = genetic.mutate(c1);
        if (M + j + 1 < N)
            resnextGeneration(M + j + 1) = genetic.mutate(c2);
        end
    end
    
    x = resnextGeneration;
end