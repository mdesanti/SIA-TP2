function r = replacement()
    r.r1 = @rmethods1;
    r.r2 = @rmethods2;
    r.r3 = @rmethods3;
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

function x = rmethods3(networks, evaluations)
    global genetic;
    selection = [];
    nextGeneration = networks;
    N = ceil(length(networks)/2);
    for i=1:genetic.k
       selection = genetic.firstSelectionMethod(evaluations, 2)
       winner1 = networks(selection(1));
       winner2 = networks(selection(2));
       [child1 child2] = genetic.crossoverMethod(winner1, winner2);
       nextGeneration(i) = genetic.mutate(child1);
    end
    nexgGenerationEvaluations = [];
    for i=1:length(nextGeneration)
        for i=from+1:1000-2
            result = [];
            aux = network.eval(network.testSet(i,2:3))*3.8;
            result(i-from) = aux - network.problem.expected(i)*3.8;
        end
        nextGenerationEvaluations(i) = 1/sum(result.^2)/length(result);
    end
    len = length(evaluations);
    newLen = length(nextGenerationEvaluations);
    evaluations(len:len+newLen-1) = nextGenerationEvaluations(1:newLen);
    selection = genetic.secondSelectionMethod(evaluations, length(networks));
    
    netLen = length(networks);
    for i=1:length(selection)
        net;
        if (selection(i) <= netLen)
            net = networks(i);
        else
            net = nextGeneration(i);
        end
    end
    
    x;
end