########################################
#
#   CHAMANDO BIBLIOTECAS IMPORTANTES
#
########################################

library(tidyverse) #pacote para manipulacao de dados
library(cluster) #algoritmo de cluster
library(dendextend) #compara dendogramas
library(factoextra) #algoritmo de cluster e visualizacao
library(fpc) #algoritmo de cluster e visualizacao
library(gridExtra) #para a funcao grid arrange
library(readxl)

dados <- read.csv("alldata.csv")
rownames(dados) <- dados[,2]
dados <- dados[,3]

d <- dist(dados, method = "euclidean")
d

hc1 <- hclust(d, method = "single" )
plot(hc1, cex = 0.6, hang = -1)
rect.hclust(hc1, k = 3)
