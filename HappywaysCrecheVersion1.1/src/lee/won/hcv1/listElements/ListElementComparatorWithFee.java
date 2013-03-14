package lee.won.hcv1.listElements;

import java.util.Comparator;

public class ListElementComparatorWithFee implements Comparator<ListElement> {

	public ListElementComparatorWithFee() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(ListElement o1, ListElement o2) {
		// TODO Auto-generated method stub
		if(o1.getTxf_Fee() == null && o2.getTxf_Fee() == null){
			return 0;
		}else if(o1.getTxf_Fee() == null && o2.getTxf_Fee() != null){
			return 1;
		}else if(o1.getTxf_Fee() != null && o2.getTxf_Fee() == null){
			return -1;
		}
		return Double.compare(o1.getFee(), o2.getFee());
	}

}
