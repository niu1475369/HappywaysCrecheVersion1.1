package lee.won.hcv1.listElements;

import java.util.Comparator;


public class ListElementComparatorWithId implements Comparator<ListElement> {

	public ListElementComparatorWithId() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(ListElement arg0, ListElement arg1) {
		// TODO Auto-generated method stub
		return arg0.getId() - arg1.getId();
	}

}
