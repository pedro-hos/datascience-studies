library(tidyverse) #pacote para manipulacao de dados
library(cluster) #algoritmo de cluster
library(dendextend) #compara dendogramas
library(factoextra) #algoritmo de cluster e visualizacao
library(fpc) #algoritmo de cluster e visualizacao
library(gridExtra) #para a funcao grid arrange
library(readxl)

# Carregando Base
university_rank <- na.omit(read.csv("../../../datasets/Word_University_Rank_2020.csv"))
university_data <- university_rank %>% 
  select(University, International_Students, Percentage_Female)

rownames(university_data) <- university_data[,1]
university_data <- university_data[,-1]

#padronizar dados
university.padronized <- (scale(university_data))

#VERIFICANDO ELBOW 
fviz_nbclust(university.padronized, kmeans, method = "wss")

#Rodar o modelo
k5 <- kmeans(university.padronized, centers = 5)

#Visualizar os clusters
fviz_cluster(k5, data = university.padronized, main = "Cluster K5", geom = c("point"))

#juntando dados
universityfit <- data.frame(k5$cluster)

#Agrupar cluster e base
finalCluster <-  cbind(university_rank, universityfit)

write_csv(finalCluster, file = "../../../datasets/Word_University_Rank_2020_cluster.csv")
