package circuitInformation;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;


public class RareNode {
	
	private String mName;
	private String mZeroTime;
	private String mOneTime;
	private String mTransitionCount;
	
	public RareNode(String name, String zero, String one, String transit){
		mName = name;
		mZeroTime = zero;
		mOneTime = one;
		mTransitionCount = transit;
		
	}
	
	public RareNode(String name){
		mName = name;
	}
	
	public String getTransitionCount() {
		return mTransitionCount;
	}

	public void setTransitionCount(String mTransitionCount) {
		this.mTransitionCount = mTransitionCount;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getZeroTime() {
		return mZeroTime;
	}

	public void setZeroTime(String mZeroTime) {
		this.mZeroTime = mZeroTime;
	}

	public String getOneTime() {
		return mOneTime;
	}

	public void setOneTime(String mOneTime) {
		this.mOneTime = mOneTime;
	}
	
	
	
	private java.util.ArrayList<RareNode> mItems;

	

	public java.util.ArrayList<RareNode> getmItems() {
		return mItems;
	}

	public void setmItems(java.util.ArrayList<RareNode> mItems) {
		this.mItems = mItems;
	}

	public void sortByTransitionCountAsc() {
	     java.util.Comparator<RareNode> comparator = new java.util.Comparator<RareNode>() {

	      @Override
	      public int compare(RareNode object1, RareNode object2) {
	  		int lfabric = 0;
	  		int rfabric = 0; 
			lfabric = Integer.valueOf(object1.getTransitionCount());
			rfabric = Integer.valueOf(object2.getTransitionCount());
	  		if (lfabric > rfabric) {
				return 1;
			} else if (lfabric < rfabric) {
				return -1;
			} else {
				return 0;
			}      }
	     };
	     Collections.sort(mItems, comparator);
	    }
	    
	    public void sortByZeroTimeAsc() {
	    	java.util.Comparator<RareNode> comparator = new java.util.Comparator<RareNode>() {
	    		
	    		@Override
	    		public int compare(RareNode object1, RareNode object2) {
	    			int lfabric = 0;
	    			int rfabric = 0; 
	    			lfabric = Integer.valueOf(object1.getZeroTime());
	    			rfabric = Integer.valueOf(object2.getZeroTime());
	    			if (lfabric > rfabric) {
	    				return 1;
	    			} else if (lfabric < rfabric) {
	    				return -1;
	    			} else {
	    				return 0;
	    			}      }
	    	};
	    	Collections.sort(mItems, comparator);
	    }
	    
	    public void sortByOneTimeAsc() {
	    	java.util.Comparator<RareNode> comparator = new java.util.Comparator<RareNode>() {
	    		
	    		@Override
	    		public int compare(RareNode object1, RareNode object2) {
	    			int lfabric = 0;
	    			int rfabric = 0; 
	    			lfabric = Integer.valueOf(object1.getOneTime());
	    			rfabric = Integer.valueOf(object2.getOneTime());
	    			if (lfabric > rfabric) {
	    				return 1;
	    			} else if (lfabric < rfabric) {
	    				return -1;
	    			} else {
	    				return 0;
	    			}      }
	    	};
	    	Collections.sort(mItems, comparator);
	    }



}
