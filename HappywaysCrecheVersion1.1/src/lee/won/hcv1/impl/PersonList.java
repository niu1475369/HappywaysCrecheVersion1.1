package lee.won.hcv1.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import lee.won.hcv1.abs.Person;
import lee.won.hcv1.exceptions.IdOverflowException;
import lee.won.hcv1.exceptions.NoPrimaryKeyFoundException;

/**
 * 
 * @author Won Lee
 * @version 1.0 b260720
 * b140955:	This list is specific for the subclasses of Person.
 * 			Since searcher class use binary search for finding person by ID,
 * 			the list has to be put subclasses by order of ID.
 * 			Therefore findBorder method will find index of border between subclasses.
 * 			This can be done because of the ID rule of subclasses of Person.
 * b141633:	findBorder method is optimised for PersonID class.
 * b151346:	Implements Serializable
 * b151553:	findBorder method is now public for CalcTotalFee class.
 * b181655:	findBorder method is modified so that it won't throw IndexOutboundException
 * b220701: bug of findIndex method is fixed now it works fine
 * b220750:	defragId method was implemented and it is ready for IdBackup
 * 			=>Child class needs method to change parentId into new parentId
 * b242015: implement method that convert id number from new id to old id.
 * b260720: restorePrimaryKey method has queue now so that new instances that were added
 * 			after defragging ID gets proper ID after restoring.
 * 
 */
public class PersonList extends ArrayList<Person> implements Serializable{
	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(Person arg0) {
		int id = arg0.getID();
		int index;
		//Get index to insert person into proper position
		//So that this list is on order of Person ID.
		index = findIndex(id, 0, size()-1);
		//System.out.println("\nindex:"+index);
		add(index,arg0);
		return true;
	}
	
	public List<IdBackup> defragId() throws IdOverflowException, NoPrimaryKeyFoundException{
		PersonID.resetId();
		List<IdBackup> idList = new ArrayList<IdBackup>();
		for(Person ps: this){
			int oldId = ps.getID();
			ps.setNewID();
			int newId = ps.getID();
			idList.add(new IdBackup(oldId,newId));
		}
		//ObjectFileHandler ofh = new ObjectFileHandler();
		//ofh.writeIdBackupXML(idList);
		refactorForeignKey(idList);
		return idList;
	}
	
	/**
	 * 
	 * @param map KEY is old ID number and VALUE is new ID number
	 * @throws NoPrimaryKeyFoundException 
	 */
	public void refactorForeignKey(Map<Integer,Integer> map) throws NoPrimaryKeyFoundException{
		int cMin = findBorder(0,0,size()-1)+1;
		int cMax = findBorder(1,cMin,size()-1)+1;
		//System.out.println(cMin+":"+cMax);
		List<Integer> errorIdList = new ArrayList<Integer>();
		boolean nulled = false;
		if(cMin != -1 && cMin != cMax){
			for(int i = cMin; i < cMax; i++){
				Child child = (Child) get(i);
				try{
					int newPid = map.get(child.getParentId());
					child.setParentId(newPid); 
				}catch(NullPointerException e){
					child.setParentId(0);
					errorIdList.add(child.getID());
					nulled = true;
				}
			}
			if(nulled){
				throw new NoPrimaryKeyFoundException(errorIdList);
			}
		}
	}
	
	public void refactorForeignKey(List<IdBackup> list) throws NoPrimaryKeyFoundException{
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for(IdBackup ib: list){
			map.put(ib.getOldId(), ib.getNewId());
		}
		refactorForeignKey(map);
	}
	
	public void restorePrimaryKey(Map<Integer,Integer> map) throws NoPrimaryKeyFoundException, IdOverflowException{
		int pi = 0;
		int ci = 0;
		Queue<Person> queue = new LinkedList<Person>();
		for(Person ps: this){
			try{
				ps.setID(map.get(ps.getID()));
				if(ps instanceof Parent){
					pi = (ps.getID()>pi)? ps.getID(): pi;
					//System.out.println("Parent Max: "+pi);
				}else if(ps instanceof Child){
					ci = (ps.getID()>ci)? ps.getID(): ci;					
					//System.out.println("Child Max: "+ci);
				}
			}catch(NullPointerException e){
				queue.offer(ps);
			}
		}
		if(size()>0){
			/*
			int ip = findBorder(0,0,size()-1);
			int ic = findBorder(1,ip,size()-1);
			Person ps = get(ip);
			if(ps instanceof Parent){
				PersonID.setParentCurrent(ps.getID());
				if((ic-ip)>0){
					PersonID.setChildCurrent(get(ic).getID());
				}
			}else{
				PersonID.setChildCurrent(ps.getID());
			}
			*/
			PersonID.resetId();
			if(pi != 0)
				PersonID.setParentCurrent(pi);
			if(ci != 0)
				PersonID.setChildCurrent(ci);
		}
		for(Person ps:queue){
			//System.out.println(ps.toString());
			int oldId = ps.getID();
			ps.setNewID();
			int newId = ps.getID();
			//System.out.println("Old:"+oldId+"  New:"+newId);
			map.put(oldId, newId);
		}
		refactorForeignKey(map);
	}
	public void restorePrimaryKey(List<IdBackup> list) throws NoPrimaryKeyFoundException, IdOverflowException{
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for(IdBackup ib: list){
			map.put(ib.getNewId(), ib.getOldId());
		}
		restorePrimaryKey(map);
	}
	
	
	/**
	 * 
	 * @param level 
	 * @param min 
	 * @param max 
	 * @return last index number of the chunk of the same type of instances
	 * 
	 */
	public int findBorder(int level, int min, int max){
		//System.out.println("Min:"+min+" Max:"+max);
		if(size() > 0){
			if(min >= max){
				if(min >= size()){
					//This is to avoid IndexOutboundException
					return size()-1;
				}else if(PersonID.getLevel(get(min)) <= level){
					return min;
				}else{
					return min-1;
				}
			}
			int index = 0;
			int mid = min + (max-min)/2;
			//System.out.println("     mid:"+mid);
			int levelAtMid = PersonID.getLevel(get(mid));
			if(levelAtMid == level){
				if(PersonID.getLevel(get(mid+1)) != levelAtMid){
					return mid;
				}else{
					index = findBorder(level, mid+1, max);
				}
			}else if(levelAtMid < level){
				index = findBorder(level, mid+1, max);
			}else if(levelAtMid > level){
				index = findBorder(level, min, mid-1);
			}
			return index;
		}else{
			return 0;
		}
	}

	/**
	 * 
	 * 
	 * @param id
	 * @param min
	 * @param max
	 * @return appropriate index number for id
	 */
	private int findIndex(int id, int min, int max){
		if(size() > 0){
			if(min >= max){
				//System.out.println("min >= max");
				if(get(min).getID() >= id){
					//System.out.println("get(min).getID() == id");
					return min;
				}else{
					//System.out.println("get(min).getID() < id");
					return min+1;
				}
			}
			int index = 0;
			int mid = min + (max-min)/2;
			int idAtMid = get(mid).getID();
			if(idAtMid < id){
				index = findIndex(id, mid+1, max);
			}else if(idAtMid > id){
				index = findIndex(id, min, mid-1);
			}else{
				index = mid;
			}
			return index;
		}else{
			return 0;
		}
	}
}
