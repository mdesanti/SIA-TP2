function r = replacement()
    r.method1 = @rmethods1;
    r.method2 = @rmethods2;
    r.method3 = @rmethods3;
end

function x = rmethods1(networks, evaluations)
    global genetic;
    global crossOverProbability;
    selection = [];
    nextGeneration = networks;
    N = ceil(length(networks)/2);
    for i=1:N
       selection = genetic.firstSelectionMethod(evaluations, 2);
       winner1 = networks(selection(1)).data;
       winner2 = networks(selection(2)).data;
       randomNr = rand;
       if(randomNr < crossOverProbability)
           [child1 child2] = genetic.crossoverMethod(winner1, winner2);
       else
           child1 = winner1;
           child2 = winner2;
       end
       nextGeneration(2 * i - 1).data = genetic.train(genetic.mutate(child1));
       if (2 * i <= length(networks))
           nextGeneration(2 * i).data = genetic.train(genetic.mutate(child2));
       end
    end
    x = nextGeneration;
end

function x = rmethods2(networks, evaluations)
    global genetic;
    selection = [];
    
    N = (length(networks));
    toChange = genetic.firstSelectionMethod(evaluations, floor(genetic.method2K / 2) * 2);
    toStay = genetic.firstSelectionMethod(evaluations, N - floor(genetic.method2K / 2) * 2);
    resnextGeneration(1:length(toChange) + length(toStay)) = struct('x',[]);
    for i=1:length(toStay)
        resnextGeneration(i).data = networks(toStay(i)).data;
    end

    M = length(toStay);

    for j=1:length(toChange)
        p1 = networks(toChange(ceil(rand() * genetic.method2K))).data;
        p2 = networks(toChange(ceil(rand() * genetic.method2K))).data;
        [c1, c2] = genetic.crossoverMethod(p1,p2);
        resnextGeneration(M + j).data = genetic.train(genetic.mutate(c1));
        if (M + j + 1 <= N)
            resnextGeneration(M + j + 1).data = genetic.train(genetic.mutate(c2));
        end
    end
    
    x = resnextGeneration;
end

function x = rmethods3(networks, evaluations)
    global genetic;
    global network;
    global util;
    selection = [];
    nextGeneration = struct('x',[]);
    N = ceil(length(networks)/2);
    for i=1:genetic.k
       selection = genetic.firstSelectionMethod(evaluations, 2);
       winner1 = networks(selection(1)).data;
       winner2 = networks(selection(2)).data;
       [child1 child2] = genetic.crossoverMethod(winner1, winner2);
       nextGeneration(i).data = genetic.train(genetic.mutate(child1));
    end
    nexgGenerationEvaluations = [];
    from = networks(1).data.trainSize;
    for i=1:length(nextGeneration)
        result = [];
        for j=from+1:800
            util.setNetwork(nextGeneration(i).data);
            aux = network.eval(network.testSet(j,2:3))*3.8;
            result(j-from) = aux - network.problem.expected(j)*3.8;
        end
        nextGenerationEvaluations(i) = 1/sum(result.^2)/length(result);
    end
    len = length(evaluations);
    newLen = length(nextGenerationEvaluations);
    evaluations(len:len+newLen-1) = nextGenerationEvaluations(1:newLen);
    selection = genetic.secondSelectionMethod(evaluations, length(networks));
    
    netLen = length(networks);
    for i=1:length(selection)
        if (selection(i) <= netLen)
            x(i) = networks(i);
        else
            x(i) = nextGeneration(i);
        end
    end
    
    x;
end