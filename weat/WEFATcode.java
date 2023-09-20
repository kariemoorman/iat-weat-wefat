import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/*
 * @author Aylin Caliskan (aylinc@princeton.edu)
 */

public class WordEmbeddingFactualAssociationTestProfessions {
	public static void main(String[] args) throws Exception{
		
        if (args.length < 4) {
            System.out.println("Usage:");
            System.out.print("\tWordEmbeddingFactualAssociationTestProfessions [outputFilename] [BLSProfessionsFilename] ");
            System.out.println("[semanticModelFilename] [professionsToTestFilename]");
            System.out.println("([professionsToTestFilename] is a newline-separated list of professions to perform WEFAT.)");
            return;
        }
        
        // Filename to output results
        String outputFile = args[0];
        outputFile ="bla";
        // Filename of BLS professions statistics CSV
        String professionsFile = args[1];

        // Filename of semantic model to use
        String semanticModel = args[2];

        // Filename of list of words to test (separated by newlines)
        String professionsToTestFilename = args[3]; 

    	int wordDimension = 300;
    	String delimiter = " ";	//dimension delimiter in the word embeddings
    	boolean caseSensitive = true;
    	boolean checkWordPresence = false;
    	boolean getCentroid = true;
 	

    	// professions from 2015 U.S. Bureau of Labor Statistics - http://www.bls.gov/cps/cpsaat11.htm
		File professionsToTestFile = new File(professionsToTestFilename);
        List<String> professionsList = Utils.readFileUTF8(professionsToTestFile, true);
        String[] professions = {"technician" , "accountant" , "supervisor" , "engineer" , "worker" , "educator" , "clerk" , "counselor" , "inspector" , "mechanic" , "manager" , "therapist" , "administrator" , "salesperson" , "receptionist" , "librarian" , "advisor" , "pharmacist" , "janitor" , "psychologist" , "physician" , "carpenter" , "nurse" , "investigator" , "bartender" , "specialist" , "electrician" , "officer" , "pathologist" , "teacher" , "lawyer" , "planner" , "practitioner" , "plumber" , "instructor" , "surgeon" , "veterinarian" , "paramedic" , "examiner" , "chemist" , "machinist" , "appraiser" , "nutritionist" , "architect" , "hairdresser" , "baker" , "programmer" , "paralegal" , "hygienist" , "scientist"};
        
        /*This list of 50 words was derived using the following procedure:
         
         For each occupational category in the 2015 Bureau of Labor Statistics data (http://www.bls.gov/cps/cpsaat11.htm), label it with the most representative one-word occupation name (usually the last word of the category title). For example, “chemical engineer” becomes “engineer”. If multiple representative occupation words are listed in a single category, we assign multiple labels to the category. The data thus derived is in the file professionsBLS2015.csv in the data/ folder.
         
         If the same label results from multiple categories, we sum the number of workers in those categories to derive the total number of workers for that label. Similarly, we compute the percentage of female workers for the label as the weighted mean of the corresponding percentages for the constituent categories.
         
         Next, manually remove a small number of unsuitable categories: “aide”, “technologist”, and “assistant”, because they are generic and/or do not prime any specific BLS occupational category, and “waiter” and “waitress” because they are gendered terms.
         
         Finally, pick the 50 most “occupation-like” words as follows. Compute the centroid of the word vectors of the remaining words, sort the words by (ascending) distance of the corresponding vector from this centroid, and pick the first 50.
*/
        //professionsList.toArray(new String[professionsList.size()]);

    	// Association attributes - http://projectimplicit.net/nosek/papers/harvesting.GroupDynamics.pdf
    	// Nosek, 2002 - Harvesting Implicit Group Attitudes and Beliefs From a Demonstration Web Site
    	// female
    	String[] attributesFirstSet={"sister" , "female" , "woman" , "girl" , "daughter" , "she" , "hers" , "her"};
    	// male
    	String[] attributesSecondSet={"brother" , "male" , "man" , "boy" , "son" , "he" , "his" , "him"};    
        //input ends
                

    	if(checkWordPresence == true){
			//remove words from categories if they do not exist
			professions = Utils.removeCategoryWordsIfNotInDictionary(professions, semanticModel, wordDimension, delimiter, caseSensitive);
			attributesFirstSet = Utils.removeCategoryWordsIfNotInDictionary(attributesFirstSet, semanticModel, wordDimension, delimiter, caseSensitive);
			attributesSecondSet = Utils.removeCategoryWordsIfNotInDictionary(attributesSecondSet, semanticModel, wordDimension, delimiter, caseSensitive);
		}
		
		double[] centroid = new double[wordDimension];
		if(getCentroid == true){
			//professions centroid
			int counter=0;
			
			for(int i=0; i< professions.length; i++){			
				double[] concept1Embedding = new double[wordDimension];
				concept1Embedding = Utils.getWordEmbedding(semanticModel, wordDimension, delimiter, professions[i], caseSensitive);
				
				if(concept1Embedding[1]!=999){
					counter++;
				}
				
				for(int column=0; column < wordDimension;column++){
					centroid[column]=centroid[column]+concept1Embedding[column];
				}
			}
		
			for(int column=0; column < wordDimension;column++){
			centroid[column]=centroid[column]/counter;
	    	}		
		}
		
    	
		Integer[] toShuffle = new Integer[attributesFirstSet.length + attributesSecondSet.length];
    	for(int i=0; i < (attributesFirstSet.length + attributesSecondSet.length); i++ ){
    		toShuffle[i]=i;
    	}
		
    	Collections.shuffle(Arrays.asList(toShuffle));				
		Collections.shuffle(Arrays.asList(attributesFirstSet));
		System.out.println(Arrays.toString(attributesFirstSet));
		Collections.shuffle(Arrays.asList(attributesSecondSet));
		System.out.println(Arrays.toString(attributesSecondSet));
		
		String[] bothStereotypes = (String[])ArrayUtils.addAll(attributesFirstSet, attributesSecondSet);
		Collections.shuffle(Arrays.asList(bothStereotypes));

		double[] meanConcept1Stereotype1=new double[professions.length];
		double[] meanConcept1Stereotype2=new double[professions.length];

		//profession to attributesFirstSet
		for(int i=0; i< professions.length; i++){
			double[] concept1Embedding = new double[wordDimension];
			concept1Embedding = Utils.getWordEmbedding(semanticModel, wordDimension, delimiter, professions[i], caseSensitive);
		
			for(int j=0; j< attributesFirstSet.length; j++){
				double[] stereotype1Embedding = new double[wordDimension];
				stereotype1Embedding = Utils.getWordEmbedding(semanticModel, wordDimension, delimiter, attributesFirstSet[j], caseSensitive);
				double similarityCompatible = Utils.cosineSimilarity(concept1Embedding, stereotype1Embedding);
				meanConcept1Stereotype1[i] = meanConcept1Stereotype1[i] + similarityCompatible;
			}	
			meanConcept1Stereotype1[i] = meanConcept1Stereotype1[i]/(attributesFirstSet.length );	
		}
		
		//profession to attributesSecondSet
		for(int i=0; i< professions.length; i++){
			double[] concept1Embedding = new double[wordDimension];
			concept1Embedding = Utils.getWordEmbedding(semanticModel, wordDimension, delimiter, professions[i], caseSensitive);
		
			for(int j=0; j< attributesSecondSet.length; j++){
				double[] stereotype2Embedding = new double[wordDimension];
				stereotype2Embedding = Utils.getWordEmbedding(semanticModel, wordDimension, delimiter, attributesSecondSet[j], caseSensitive);
				double similarityCompatible = Utils.cosineSimilarity(concept1Embedding, stereotype2Embedding);
				meanConcept1Stereotype2[i] = meanConcept1Stereotype2[i] + similarityCompatible;
			}		
			meanConcept1Stereotype2[i] = meanConcept1Stereotype2[i]/(attributesSecondSet.length );
		}
		
	
		//profession to null distribution 	
  		double [][] concept1NullMatrix= new double[professions.length][bothStereotypes.length];
	
		for(int i=0; i< professions.length; i++){
			double[] concept1Embedding = new double[wordDimension];					
			concept1Embedding = Utils.getWordEmbedding(semanticModel, wordDimension, delimiter, professions[i], caseSensitive);
									
			for(int j=0; j<bothStereotypes.length;j++){					
				double similarityCompatible; 							
				double[] nullEmbedding = new double[wordDimension];				
				nullEmbedding = Utils.getWordEmbedding(semanticModel, wordDimension, delimiter, bothStereotypes[j], caseSensitive);
				similarityCompatible = Utils.cosineSimilarity(concept1Embedding, nullEmbedding);
				concept1NullMatrix[i][j]=similarityCompatible;						
				}		
		}
	    
		for(int i=0; i< professions.length; i++){
			double percentage = Utils.calculateWomenPercentage(professionsFile,professions[i]);
			double[] nullDistributionConcept1 = new double[bothStereotypes.length];
			for(int iter=0; iter<bothStereotypes.length;iter++){
				nullDistributionConcept1[iter] = concept1NullMatrix[i][toShuffle[iter]];		
			}		
			
			double[] concept1Embedding = Utils.getWordEmbedding(semanticModel, wordDimension, delimiter, professions[i], caseSensitive);		
			int frequency = Utils.getWordFrequencyOrder(semanticModel, wordDimension, delimiter, professions[i], caseSensitive);		
			
			System.out.println(professions[i]+", " + percentage +", "+Utils.effectSize(nullDistributionConcept1,meanConcept1Stereotype1[i] - meanConcept1Stereotype2[i] ) 
			+", "+Utils.cosineSimilarity(concept1Embedding, centroid)+", " + frequency);	
					
			Utils.writeFile(professions[i]+", " + percentage+", "+ Utils.effectSize(nullDistributionConcept1,meanConcept1Stereotype1[i] - meanConcept1Stereotype2[i] )
			+", "+Utils.cosineSimilarity(concept1Embedding, centroid)+", " + frequency +"\n", outputFile, true);																			
		}		
	}
}
