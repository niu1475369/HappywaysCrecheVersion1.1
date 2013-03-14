package lee.won.hcv1.listElements;

import java.util.Comparator;

import lee.won.hcv1.abs.Person;

public class ListElementComparatorWithFName implements Comparator<ListElement> {

	public ListElementComparatorWithFName() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(ListElement arg0, ListElement arg1) {
		// TODO Auto-generated method stub
		int stDif = arg0.getFname().compareTo(arg1.getFname());
		return stDif;
	}


}
