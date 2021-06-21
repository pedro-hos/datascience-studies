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

#calcular as distancias da matriz utilizando a distancia euclidiana
distancia <- dist(university.padronized, method = "euclidean")

### método hiearquico

#Calcular o Cluster
cluster.hierarquico <- hclust(distancia, method = "single" )

# Dendrograma
plot(cluster.hierarquico, cex = 0.6, hang = -1)

#criando grupos
universidades_hierarquico <- cutree(cluster.hierarquico, k = 5)

#transformando em data frame a saida do cluster
universidades_hierarquico <- data.frame(universidades_hierarquico)

#juntando com a base original
finalCluster <- cbind(university_rank, universidades_hierarquico)

#visualizando em cores os clusters
finalCluster %>% ggplot() +
  geom_point(aes(x = International_Students,
                 y = Percentage_Female,
                 color = as.factor(universidades_hierarquico)),
             size = 3)

### método k-means

#Rodar o modelo
k5 <- kmeans(university.padronized, centers = 5)

#Visualizar os clusters
fviz_cluster(k5, data = university.padronized, main = "Cluster K5", geom = c("point"))

#juntando dados
universityfit <- data.frame(k5$cluster)

#Agrupar cluster e base
finalCluster <-  cbind(finalCluster, universityfit)

### método dbscan

#Calcular o Cluster
dbscan <- fpc::dbscan(university.padronized,eps = 0.56, MinPts = 3)

finalCluster$dbscan <- dbscan$cluster

#visualizando em cores os clusters
finalCluster %>% ggplot() +
  geom_point(aes(x = International_Students,
                 y = Percentage_Female,
                 color = as.factor(dbscan)),
             size = 3)

#Escrevendo csv final com dados
write_csv(finalCluster, file = "../../../datasets/Word_University_Rank_2020_cluster.csv")
