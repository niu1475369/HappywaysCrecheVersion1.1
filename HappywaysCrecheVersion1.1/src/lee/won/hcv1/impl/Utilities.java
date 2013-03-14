package lee.won.hcv1.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Won Lee
 * @version b3020810
 * b3020810: 
 *
 */
public class Utilities {

	public static List<Integer> convertStringToInts(String st) throws Exception{
		List<Integer> list = new ArrayList<Integer>();
		try{
			if(st.contains(",")&&st.contains("-")){
				list.addAll(convertStringsToInts(st.split(",")));				
			}else if(st.contains(",")&&!st.contains("-")){
				String[] sts = st.split(",");
				list.add(Integer.valueOf(sts[0]));
				list.add(Integer.valueOf(sts[1]));
			}else if(!st.contains(",")&&st.contains("-")){
				String[] sts = st.split("-");
				int s0 = Integer.valueOf(sts[0]);
				int s1 = Integer.valueOf(sts[1]);
				int range = (s0 > s1)? s0 - s1: s1 - s0;
				int start = (s0 > s1)? s1: s0;
				for(int i = 0; i<=range; i++){
					list.add(i+start);
				}				
			}else{
				list.add(Integer.valueOf(st));
			}
			return list;
		}catch (Exception e){
			throw e;
		}
	}
	
	public static List<Integer> convertStringsToInts(String[] sts) throws Exception{
		List<Integer> list = new ArrayList<Integer>();
		for(String st: sts){
			list.addAll(convertStringToInts(st));
		}
		return list;
	}

}
