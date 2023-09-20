import spacy
import numpy
import os
from numpy import dot
from numpy.linalg import norm

nlp = spacy.load('en_core_web_lg')

def compare_wordlists_by_spacy_vectors(model, wordlist1, wordlist2): 

    vectorlist1 = model(wordlist1)
    vectorlist2 = model(wordlist2) #unicode string
    myData = [ ]
      
    for wordvec1 in vectorlist1:
        for wordvec2 in vectorlist2:

            myData.append([wordvec1, wordvec2, wordvec1.similarity(wordvec2)])
            #print(wordvec1, wordvec2)
            #print(wordvec1.similarity(wordvec2))
            #print("")
    return  myData  

#wordlist1 = (u'illegals refugees immigrants humans') 
#wordlist2 = (u'love hate anger disgust')
#compare_wordlists_by_spacy_vectors(nlp, wordlist1, wordlist2)


def compare_within_wordlist_by_spacy_vectors(model, wordlist1): 

    vectorlist1 = model(wordlist1)
    vectorlist2 = model(wordlist1) #unicode string
    myData = [ ]
    
    for wordvec1 in vectorlist1:
        for wordvec2 in vectorlist2:

            myData.append([wordvec1, wordvec2, wordvec1.similarity(wordvec2)])

            #print(wordvec1, wordvec2)
            #print(wordvec1.similarity(wordvec2))
            #print("")
    return myData

#wordlist1 = (u'illegals refugees immigrants humans') #list of strings = input at present
#wordlist2 = (u'love hate anger disgust')
#compare_within_wordlist_by_spacy_vectors(nlp, wordlist1)
#compare_within_wordlist_by_spacy_vectors(nlp, wordlist2)


def similar_strings(model, word):
    '''
    using spaCy .vocab to find top 20 orthographic similar words to an input word
    '''
    myData = [ ]
    # cosine similarity
    cosine = lambda v1, v2: dot(v1, v2) / (norm(v1) * norm(v2))
    # gather all known words, take only the lowercased versions
    allWords = list({w for w in model.vocab if w.has_vector and w.orth_.islower() and w.lower_ != word })
    # sort by similarity to word
    allWords.sort(key=lambda w: cosine(w.vector, word.vector))
    allWords.reverse()
    print("Top 20 most similar words to" , word.orth_,":")
    for word in allWords[:20]:
        myData.append(word.orth_)
    return myData

#hit = nlp.vocab[u'hit']
#similar_strings(nlp, hit)
