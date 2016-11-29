
# install required packages: devtools and plumber
if(! ("devtools" %in% installed.packages()[,"Package"])){
        install.packages("devtools")
}
if(! ("plumber" %in% installed.packages()[,"Package"])){
        devtools::install_github("trestletech/plumber")
}

library(plumber)
r <- plumb("functions.R")  # 
r$run(port=8080)