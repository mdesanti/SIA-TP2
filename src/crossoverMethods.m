%Paso de una matriz de pesos a un array.
%Deberíamos recibir la cantidad de bits de entrada.
function weightsArray = getWeightsArray(network, n)
    neuronsPerLayer = network.neuronsPerLayer;
    weights = network.weights;
    weightsArray = [];
    length = 1;
    for i=1:length(network.weights)
        layer = network.layerIndexForNeuron(i);
        %va hasta + 1 por el peso del bias
        if (layer > 1)
            weightQty = neuronsPerLayer(layer-1)+1;
        else
            weigthQty = n + 1;
        end
        weightsArray(length:weightQty) = weights(i,1:weightQty);
        length = length + weigthQty;
    end
end

function weightsMatrix = getWeightsMatrix(network, weightsArray, n)
    neuronsPerLayer = network.neuronsPerLayer;
    weightsMatrix = [];
    length = 1;
    for i=1:length(network.weights)
        layer = network.layerIndexForNeuron(i);
        %va hasta + 1 por el peso del bias
        if (layer > 1)
            weightQty = neuronsPerLayer(layer-1)+1;
        else
            weigthQty = n + 1;
        end
        network.weights(i,1:weigthQty) = weightsArray(length:length + weightQty);
        length = length + weigthQty;
    end
    weightsMatrix = network.weights;
end

function children = onePointCrossover(network1, network2)
    weights1 = getWeightsArray(network1, n);
    weights2 = getWeightsArray(network2, n);
    
    swapPoint = randi([1 length(weights1)]);
    
    for i=swapPoint:length(weights1)
        aux = weights1(i);
        weights1(i) = weights2(i);
        weights2(i) = aux;
    end
    network1.weights = weights1;
    network2.weights = weights2;
    children(1) = network1;
    children(2) = network2;
end

function children = twoPointCrossover(network1, network2)
    weights1 = getWeightsArray(network1, n);
    weights2 = getWeightsArray(network2, n);
    
    swapPoint = randi([1 length(weights1)]);
    endSwapPoint = rand([1 length(weights1)]);
    while (endSwapPoint < swapPoint)
        endSwapPoint = rand([1 length(weights1)]);
    end
    
    for i=swapPoint:endSwapPoint
        aux = weights1(i);
        weights1(i) = weights2(i);
        weights2(i) = aux;
    end
    network1.weights = weights1;
    network2.weights = weights2;
    children(1) = network1;
    children(2) = network2;
end

function children = anularCrossOver(network1, network2)
    weights1 = getWeightsArray(network1, n);
    weights2 = getWeightsArray(network2, n);
    
    swapPoint = randi([1 length(weights1)]);
    endSwapPoint = rand([1 length(weights1)]);
    finish = endSwapPoint;
    if (endSwapPoint < swapPoint) 
        finish = length(weights1);
    end
    
    for i=swapPoint:finish
        aux = weights1(i);
        weights1(i) = weights2(i);
        weights2(i) = aux;
    end
    
    if (endSwapPoint < swapPoint)
        for i=1:endSwapPoint
        aux = weights1(i);
        weights1(i) = weights2(i);
        weights2(i) = aux;
    end
    end
    
    network1.weights = weights1;
    network2.weights = weights2;
    children(1) = network1;
    children(2) = network2;
end

function children = uniformParametrizedCrossOver(network1, network2)
    global crossOverProbability;
    weights1 = getWeightsArray(network1, n);
    weights2 = getWeightsArray(network2, n);
    
    for i=1:length(weights1)
        randomNr = rand();
        if(rand < crossOverProbability) 
            aux = weights1(i);
            weights1(i) = weights2;
            weights2(i) = aux;
        end
    end
    
    getWeightsMatrix(network1, weights1, n);
    getWeightsMatrix(network2, weights2, n);
    children(1) = network1;
    children(2) = network2;
end


function child = mutate(network) 
    global mutationProbability;
    weights = getWeightsArray(network, n);
    max = max(weights);
    min = min(weights);
    for i=1:length(weights)
        randomNr = rand();
        if(randomNr < mutationProbability)
            weights(i) = rand() * ( abs(max) + abs(min) ) - abs(min);
        end
    end
    getWeightsMatrix(network, weights, n);
    child = network;
end


