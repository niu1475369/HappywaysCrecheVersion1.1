package lee.won.hcv1.listElements;

import java.util.Comparator;

import lee.won.hcv1.abs.Person;

public class ListElementComparatorWithSName implements Comparator<ListElement> {

	public ListElementComparatorWithSName() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(ListElement arg0, ListElement arg1) {
		// TODO Auto-generated method stub
		int stDif = arg0.getSname().compareTo(arg1.getSname());
		return stDif;
	}


}
