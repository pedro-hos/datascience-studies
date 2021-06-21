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

# Hierarquico

mortes_maio <- read.csv("../../../datasets/sjc-deaths/obitos_maio.csv")
rownames(mortes_maio) <- mortes_maio[,1]
mortes_maio <- mortes_maio[,2]

mortes_maio.padronizado <- scale(mortes_maio)

distancia_matriz <- dist(mortes_maio.padronizado, method = "euclidean")
cluster.hierarquico <- hclust(distancia_matriz, method = "single" )

# Dendrograma
plot(cluster.hierarquico, cex = 0.6, hang = -1)

#Criar o grafico e destacar os grupos
rect.hclust(cluster.hierarquico, k = 4)

#Elbow
fviz_nbclust(mortes_maio.padronizado, FUN = hcut, method = "wss")

grupo_idade <- cutree(cluster.hierarquico, k = 5)
table(grupo_idade)
grupos <- data.frame(grupo_idade)
base_final <- cbind(mortes_maio, grupos)

# entendendo os clusters
#FAZENDO ANALISE DESCRITIVA
#MEDIAS das variaveis por grupo
media <- base_final %>% 
  group_by(grupo_idade) %>% 
  summarise(quantidade = n(),
            Media_obitos = mean(mortes_maio))

media
