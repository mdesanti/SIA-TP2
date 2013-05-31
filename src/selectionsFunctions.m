% These functions select n individuals depending on the method
% They return an array of ints that represent which individual was selected
% from the evaluations array.

function selection = rouleteSelection(evaluations, n)
    %suma de todos los elementos de evaluations
    sum = sum(evaluations);
    %para cada valor, ev/sum
    evaluations = evaluations./sum;
    cumSum = 0;
    %en cada lugar de evaluations queda la probabilidad acumulada de cada
    %valor
    for i=1:length(evaluations)
        cumSum = cumSum + evaluations(i);
        evaluations(i) = cumSum;
    end
    %selecciono n elementos
    for j=1:n
        randomNr = rand();
        selected = 0;
        for k=1:length(evaluations)
            selected = k;
            if (randomNr < evaluations(k))
                break;
            end
        end
        selection(j) = selected;
    end
end


function selection = universalEstocasticSelection(evaluations, n)
    %suma de todos los elementos de evaluations
    sum = sum(evaluations);
    %para cada valor, ev/sum
    evaluations = evaluations./sum;
    cumSum = 0;
    %en cada lugar de evaluations queda la probabilidad acumulada de cada
    %valor
    for i=1:length(evaluations)
        cumSum = cumSum + evaluations(i);
        evaluations(i) = cumSum;
    end
    selection = [];
    %selecciono n elementos
    randomNr = rand('unif',0,1/n);
    previous = 1;
    for j=1:n
        selected = 0;
        for k=previous:length(evaluations)
            selected = k;
            if ((randomNr + (k-1)*1/n) < evaluations(k))
                previous = k;
                break;
            end
        end
        selection(j) = selected;
    end
end

%no se como calcular el valor medio temporal

function selection = boltzmanSelection(evaluations, n)
    
end

function selection = tournamentSelection(evaluations, n) 
    for i=1:n
        a = randi([1 n]);
        b = randi([1, n]);
        randomNr = rand();
        if(randomNr < 0.75) {
            %selecciono al mas apto
            if (evaluations(a) > evaluations(b))
                selection(i) = a;
            else
                selection(i) = b;
            end
        else
            %selecciono al menos apto
            if (evaluations(a) > evaluations(b))
                selection(i) = b;
            else
                selection(i) = a;
            end     
        end
    end
end

function selection = rankSelection(evaluations, n)
    
end


function selection = elitSelection(evaluations, n)

end

