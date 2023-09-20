# IAT-WEAT-WEFAT

This project (2018) examines cognitive bias, comparing unconscious or implicit bias with conscious bias across numerous sociocultural groups, topics, and events (e.g., immigration, ethnicity, elections). Using Qualtrics, we conducted survey-based experiments comprised of a series of implicit association tests (IAT), bias inventory metrics, political ideology metrics, and free response questions allowing participants to self-assess their own bias. We targeted adult participants in the United States. We compared results with those found in large-scale language models using WEAT/WEFAT ([Caliskan, Bryson, & Narayanan, 2017](https://doi.org/10.1126/science.aal4230)) trained on various corpora (e.g., Twitter, Reddit, Wikipedia, News, CommonCrawl) across multiple languages (e.g., English, Spanish, Chinese).

Preliminary findings: [Comparing IAT with WEAT/WEFAT Applied to Immigration (2018)](https://github.com/kariemoorman/iat-weat-wefat/blob/main/bias_and_cognition_presentation_2018_moorman_karie.pdf)

<hr>

### IAT 

The IAT is a computer-based measure involving the rapid classification of stimuli into two key blocks. The order of these blocks is manipulated as a within-subjects factor. In each block, subjects will complete 40 trials. Each trial involves the sorting of an individual stimulus item. In the first block, subjects will rapidly sort individual stimuli using a theoretically mismatched pair of combinations, where `"Category 1 (positive)"` and `"Category 2 (negative)"` appear together, and `"Concept 1"` and `"Concept 2"` appear together. Stimuli are comprised of 5 words synonymous with `"Concept 1"`, 5 words synonymous with `"Concept 2"`, 5 words carrying negatively valenced meaning, and 5 words carrying positively valenced meaning. In the second block, subjects performed a second sorting task using a theoretically matched pair of combinations, where `"Concept 2"` and `"Category 2"` go together, and ‘`"Concept 1"` and `"Category 1"` go together. Once again, stimuli included `"Concept 1"` and `"Concept 2"` words, positively valenced words, and negatively valenced words.

Evidence of implicit bias toward `"Concept 2"` relative to `"Concept 1"` is initially provided by a statistically significant difference in average response times between both blocks. If a latency in sorting times is not produced, it suggests that subjects do not display an implicit bias towards `"Concept 2"`, as stimuli would have been rapidly classified at comparable rates across both blocks. To the extent a latency in response rates exists, however, the equation in (1) is used to subtract the average response latencies from each other, yielding a relative IAT effect whereby positive scores indicate an automatic negative attitude toward `"Concept 2"` relative to `"Concept 1"`: 

```math
\left(Average Response Latency_{mismatched} \right) - \left(Average Response Latency_{matched} \right) = IAT effect         (1)
```
The resultant IAT score, or D, is calculated using a scoring algorithm, in (2), that involves computing the difference in average response latency, in milliseconds, between the two sorting conditions (M1 and M2) and dividing by the standard deviation of all latencies for both sorting tasks (see Greenwald et al., 2003):

```math
(M_1 - M_2)/SD = D                                                                    (2) 
```

The difference between the more conventional Cohen’s d (1988) and the IAT D is that the standard deviation in the denominator of d is a pooled within-treatment standard deviation. The IAT D computes the standard deviation with the scores in both conditions, ignoring the condition membership of each score (Nosek et al., 2005). IAT scores range from -2 to +2. Conventional small, medium, and large values of D are 0.2, 0.5, and 0.8, respectively.


### WEAT

To quantify bias in large scale corpora of text, scientists use permutation tests on sets of word embeddings derived via neural network models trained on natural language text corpora. Word embeddings are representations of words that are mapped to vectors of real numbers, such that each word represents a point in a continuous vector space (e.g., see word2vec, GloVe). Semantically similar words are embedded nearby each other, such that words mapped to nearby points are thought to share semantic features. Vector spaces generally range from 50 dimensions to 300 dimensions. The larger the dimensionality, the better able the vector model is at capturing most aspects of similarity between all or most words in a large corpus of text.

The Word Embedding Association Test (WEAT) (Caliskan et al., 2017) is a permutation test that measures the unlikelihood that there is no difference between the two sets of target word embeddings (e.g., “citizen” and “non-citizen” words) in terms of their relative similarity to the two sets of attribute word embeddings (e.g., “good” and “bad” word embeddings ). The null hypothesis is that there is no difference between target words sets and attribute words sets. To measure this unlikelihood of the null hypothesis, the WEAT computes the probability that a random permutation of the attribute words would produce the observed (or greater) difference in sample means. 

As described in (Caliskan et al., 2017), suppose we let X and Y be two sets of target words of equal size, and A,B the two sets of attribute words, and cos(a,b) denote the cosine of the angle between the vectors a and b, in (3) and (4):  

The permutation test statistic is 
```math
                  s(X,Y,A,B) =\sum_{x∈X}s(x,A,B) − \sum_{y∈Y}s(y,A,B),                                       (3)
```
where 
```math
                  s(w, A, B) = mean_{a∈A}cos(w,a) − mean_{b∈B}cos(w,b).                              (4)
```
In (4), `s(w,A,B)` measures the association of the word w with the attribute, and `s(X,Y,A,B)` measures the differential association of the two sets of target words with the attribute. The p-value is calculated using the equation in (5), and the effect size is calculated using the equation in (6): 

The one-sided p-value of the permutation test is 

```math
Pr_i [s(Xi ,Yi ,A,B) > s(X,Y,A,B)]                                                              (5)
```

The effect size is 
```math
 \left(mean_{x∈X} s(x,A,B) − mean_{y∈Y} s(y,A,B)\right) 	/ 	 \left(std-dev_{w∈X∪Y} s(w,A,B)\right) (6)

   
```
The WEAT, as described above, is a normalized measure of how separate the two distributions (of associations between the target word sets and each attribute word) are from each other. 
Since the WEAT uses words as test subjects, not humans, the resultant p-values and effect sizes have a different interpretation from those of the IAT. This is to say, the IAT measures the differential association between a single pair of target concepts and an attribute, while the WEAT only measures the differential association between two sets of target concepts and an attribute.

<hr>

### Resources

Caliskan, A., Bryson, J. J., & Narayanan, A. (2017). Semantics derived automatically from language corpora contain human-like biases. Science, 356(6334), 183-186.

Carpenter, T. (2018). iatgen: IATs for Qualtrics. R package version 1.2.0.

Carpenter, T., Pogacar, R., Pullig, C., Kouril, M., LaBouff, J., Aguilar, S., Isenberg, N., & Chakroff, A. (2018, April 3). Conducting IAT Research within Online Surveys: A Procedure, Validation, and Open Source Tool. http://doi.org/10.17605/OSF.IO/6XDYJ. https://iatgen.wordpress.com.

Cohen, J. (1988). Statistical power analysis for the behavioral sciences. 2nd.

Eclipse, I. D. E. (2007). Eclipse Foundation. http://eclipse.org. 

Greenwald, A. G., Nosek, B. A., & Banaji, M. R. (2003). Understanding and using the Implicit Association Test: I. An improved scoring algorithm. Journal of Personality and Social Psychology, 85(2), 197-216.

Hedrick, J., & Ksiazkiewicz, A. (2012). Implicit Attitudes toward Highly Skilled and Low-skilled Immigration.

Keating, L. (2017). A Cross Cultural Analysis of Implicit and Explicit Xenophobia (Doctoral dissertation, Yale University).

Nosek, B. A., Greenwald, A. G., & Banaji, M. R. (2005). Understanding and using the Implicit Association Test: II. Method variables and construct validity. Personality and Social Psychology Bulletin, 31(2), 166-180.

Pérez, E. O. (2010). Explicit evidence on the import of implicit attitudes: The IAT and immigration policy judgments. Political Behavior, 32(4), 517-545.

Qualtrics, I. (2013). Qualtrics. Provo, UT, USA. https://www.qualtrics.com.
