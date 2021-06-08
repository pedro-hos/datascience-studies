years <- c(2016, 2017, 2018, 2019, 2020, 2021)
ranges <- c("Natimorto", "Jovens", "Adultos", "Idosos")

n_years <- c()
n_ranges <- c()
values <- c()

for (y in years) {
   for(r in ranges) {
     n_years <- c(n_years, y)
     n_ranges <- c(n_ranges, r)
     values <- c(values, nrow(subset(dados, (YEAR == y & RANGE_AGE == r))))
   }
}

data1 <- data.frame(n_years, n_ranges, values)

write.csv(data1, file="mortes_range.csv")

graph <- ggplot(data = data1, aes(fill = n_ranges, x = n_years, y=values)) + 
  geom_bar(position="dodge", stat="identity") + 
  labs(title = "Óbitos por range idade segundo site da URBAM", 
       x = "Anos", y = "Óbitos", caption = "Período de 2016 à Junho de 2021") +
  theme_light()

ggplotly(graph)
