import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

public class Algorithm {
	String fName = null;
	HashMap<String, Integer> atr1_Map,atr2_Map,atr3_Map,atr4_Map;
	HashMap<String, String> accepted;
	BufferedReader br;
	
	String splitBy;
	String suffix_no;
	String suffix_yes;
	
	int countTotal;
	int count_no;
	int count_yes;
	
	//Instances in File
	List<String[]> instances = null;

	Algorithm(String fileName) {
		this.fName = fileName;
		instances = new ArrayList<String[]>();
		try {
			String line = null;
			br = new BufferedReader(new FileReader(fName));
			br.readLine();
			while ((line = br.readLine()) != null) {
				instances.add(line.split(","));
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void initializeStructures(){
		atr1_Map = new HashMap<String, Integer>();
		atr2_Map = new HashMap<String, Integer>();
		atr3_Map = new HashMap<String, Integer>();
		atr4_Map = new HashMap<String, Integer>();

		splitBy = ",";
		suffix_no = "no";
		suffix_yes = "yes";

		accepted = new HashMap<String, String>();
		accepted.put("no", suffix_no);
		accepted.put("yes", suffix_yes);

		countTotal = 0;
		count_no = 0;
		count_yes = 0;
	}

	void Train(List<String[]> training) {
		
		initializeStructures();
		String reSuffix = null;
		
		for (int i = 0; i < training.size(); i++) {
			String[] values = training.get(i);
			//increment counts
			countTotal++;
			reSuffix = accepted.get(values[4]);
			
			if (reSuffix.equals(suffix_no)) {
				count_no++;
			} else if (reSuffix.equals(suffix_yes)) {
				count_yes++;
			}
			//populate hash tables

			if (!atr1_Map.containsKey(values[0] + reSuffix)) {
				atr1_Map.put(values[0] + reSuffix, 0);
			} else {
				atr1_Map.put(values[0] + reSuffix,
						(atr1_Map.get(values[0] + reSuffix)) + 1);
			}

			if (!atr2_Map.containsKey(values[1] + reSuffix)) {
				atr2_Map.put(values[1] + reSuffix, 0);
			} else {
				atr2_Map.put(values[1] + reSuffix,
						(atr2_Map.get(values[1] + reSuffix)) + 1);
			}

			if (!atr3_Map.containsKey(values[2] + reSuffix)) {
				atr3_Map.put(values[2] + reSuffix, 0);
			} else {
				atr3_Map.put(values[2] + reSuffix,
						(atr3_Map.get(values[2] + reSuffix)) + 1);
			}
			if (!atr4_Map.containsKey(values[3] + reSuffix)) {
				atr4_Map.put(values[3] + reSuffix, 0);
			} else {
				atr4_Map.put(values[3] + reSuffix,
						(atr4_Map.get(values[3] + reSuffix)) + 1);
			}
		
		}
	}
	
	double classify(List<String[]> training, List<String[]> test) {
		Train(training);
		float prob_no=(float) count_no/countTotal;
		float prob_yes=(float) count_yes/countTotal;
		float res1,res2=0.0f;
		
		double num_matching = 0;
		float num_missing = 0;
		float atr1=0, atr2=0, atr3=0, atr4=0;
		
		for(int i = 0; i < test.size(); i++){
			
			String[] vals = test.get(i);
			TreeMap<Float, String> results=new TreeMap<Float,String>();

			// calculate probability of the input belonging to each of the 2 classes
			//class 1
			num_missing = 0;
			atr1=0;atr2=0;atr3=0;atr4=0;
			
			if(atr1_Map.containsKey(vals[0])){
				atr1 = (float)atr1_Map.get(vals[0]);
			}else{	atr1 = 1;num_missing += 1;	}
			
			if(atr2_Map.containsKey(vals[1])){
				atr2 = (float)atr2_Map.get(vals[1]);
			}else{ 	atr2 = 1;num_missing += 1; }
			
			if(atr3_Map.containsKey(vals[2])){
				atr3 = (float)atr3_Map.get(vals[2]);
			}else{	atr3 = 1;num_missing += 1;	}
			
			if(atr4_Map.containsKey(vals[3])){
				atr4 = (float)atr4_Map.get(vals[3]);
			}else{	atr4 = 1;num_missing += 1;	}
		
		
			res1=prob_no*(atr1/(count_no+num_missing))*
						 (atr2/(count_no+num_missing))*
						 (atr3/(count_no+num_missing))*
						 (atr4/(count_no+num_missing));
			
			//class 2
			num_missing = 0;
			atr1=0;atr2=0;atr3=0;atr4=0;
						
			if(atr1_Map.containsKey(vals[0])){
				atr1 = (float)atr1_Map.get(vals[0]);
			}else{	atr1 = 1;num_missing += 1;	}
			
			if(atr2_Map.containsKey(vals[1])){
				atr2 = (float)atr2_Map.get(vals[1]);
			}else{ 	atr2 = 1;num_missing += 1; }
			
			if(atr3_Map.containsKey(vals[2])){
				atr3 = (float)atr3_Map.get(vals[2]);
			}else{	atr3 = 1;num_missing += 1;	}
			
			if(atr4_Map.containsKey(vals[3])){
				atr4 = (float)atr4_Map.get(vals[3]);
			}else{	atr4 = 1;num_missing += 1;	}
			
		
			res2=prob_yes*(atr1/(count_yes+num_missing))*
						 (atr2/(count_yes+num_missing))*
						 (atr3/(count_yes+num_missing))*
						 (atr4/(count_yes+num_missing));
			
			results.put(res1, "no");
			results.put(res2, "yes");
			
			System.out.println(results.get(results.lastKey()));
			System.out.println(vals[4]);
			if(results.get(results.lastKey()).equals(vals[4])){
				num_matching++;
			}
			
		}
		System.out.println(num_matching + " " + test.size());
		return num_matching/test.size(); 
	}//end func
	
	
	//Cross Validation function
	void crossValidation(){
		double accuracy = 0;
		double total_accuracy = 0;
		int num_rows = instances.size();
		List<String[]> testing = null;
		List<String[]> training = null;
		int subset_size = instances.size()/10;
		
		System.out.println("----------------10 Fold cross validation------------------");
		
		for (int i = 0; i < 10; i++){
			testing = new ArrayList<String[]>(instances.subList(0, (subset_size)));
			training = new ArrayList<String[]>(instances.subList((i*subset_size), ((i+1)*subset_size) - 1));
			
			if(i < 9) {
				if(training != null)
					training.addAll(new ArrayList<String[]>(instances.subList((i + 1)*num_rows/10, instances.size() - 1)));
				else
					training = new ArrayList<String[]>(instances.subList((i + 1)*num_rows/10, instances.size() - 1));
			}	
			accuracy = classify(training, testing);
			System.out.println("Classification Accuracy "+ (i+1) + ": "+(double)Math.round(accuracy * 100 * 100)/100 + "%");			
			total_accuracy += accuracy;
		}	
		
		
		System.out.println("Average Accuracy of Naive Bayes: "+(double)Math.round(total_accuracy * 100)/10 + "%");
	}
}
