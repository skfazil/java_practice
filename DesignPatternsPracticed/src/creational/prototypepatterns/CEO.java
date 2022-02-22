package creational.prototypepatterns;

import java.util.ArrayList;
import java.util.List;

public class CEO implements Cloneable{
	
	private List<String> empList;

	public List<String> getEmpList() {
		return empList;
	}

	public void setEmpList(List<String> empList) {
		this.empList = empList;
	}

	/*
	 * @Override public String toString() { return "CEO [empList=" + empList + "]";
	 * }
	 */
	
	//We overridden clone, to do a deep copy(which creates new memory, and changes in other copied objects will not reflect in others)
	@Override
	public Object clone() throws CloneNotSupportedException{
		CEO ceo = null;
		List<String> newEmpList = null;
		if(empList!=null&&empList.size()>0) {
			ceo = new CEO();
			newEmpList = new ArrayList<String>();
			for(String s : empList) {
				newEmpList.add(s);
			}
			ceo.setEmpList(newEmpList);
		}
		
		return ceo;
	}
	
	/*
	 * //Shallow copy example
	 * 
	 * @Override public Object clone() throws CloneNotSupportedException{ return
	 * this; }
	 */

}
