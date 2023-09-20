import pandas as pd
import numpy as np
from numpy.random import randn, seed
from numpy import mean, add, var, std
from math import sqrt
from statistics import stdev

	
def diff_of_means(wordlist1, wordlist2, wordlist3, wordlist4):
    df1 = pd.DataFrame(wordlist1)
    df2 = pd.DataFrame(wordlist2)
    df3 = pd.DataFrame(wordlist3)
    df4 = pd.DataFrame(wordlist4)
    a1 = df1.loc[:,2].mean()
    a2 = df2.loc[:,2].mean()
    b1 = df3.loc[:,2].mean()
    b2 = df4.loc[:,2].mean()
    d1= (np.add(a1, a2))/2
    d2= (np.add(b1, b2))/2
    d3= np.subtract(d1,d2)
    print("Group 1 mean similarity scores:",a1, a2, d1)
    print("Group 2 mean similarity scores:",b1, b2, d2)
    print("The global mean similarity for Group 1 is", d1)
    print("The global mean similarity for Group 2 is", d2)
    print("The difference of the means is", d3)  


def cohens_d(wordlist1, wordlist2, wordlist3, wordlist4):
    df1 = pd.DataFrame(wordlist1)
    df2 = pd.DataFrame(wordlist2)
    df3 = pd.DataFrame(wordlist3)
    df4 = pd.DataFrame(wordlist4)
    a1 = df1.loc[:,2].mean()
    a2 = df2.loc[:,2].mean()
    a3 = pd.concat([df1,df2])
    b1 = df3.loc[:,2].mean()
    b2 = df4.loc[:,2].mean()
    b3 = pd.concat([df3,df4])
    # calculate the mean similarity of the samples
    #d1= (np.add(a1,a2))/2
    #d2= (np.add(b1,b2))/2
    d4 = a3.loc[:,2].mean()
    d5 = b3.loc[:,2].mean()
    # calculate the difference of the means
    d3= np.subtract(d4,d5)
    # calculate the pooled standard deviation
    s = sqrt((np.std(a3.loc[:,2]) ** 2 + np.std(b3.loc[:,2]) ** 2) / 2)
    #calculate cohen's d
    cohen_d = d3 / s
    #return cohen_d
    print("The average similarity scores for Group 1 are", (a1, a2))
    print("The average similarity scores for Group 2 are", (b1, b2))
    print("The global mean similarity score for Group 1 is", d4)
    print("The global mean similarity score for Group 2 is", d5)
    print("The difference of the means is", d3) 
    #print(b3)
    print("Cohen's D is", cohen_d)
