package simulation;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SubsetIterator<E> {
    private final List<E> set;
    private final int max;
    private int index;
    
    private int subsetSize;
    
    public SubsetIterator(List<E> originalList, int subsetSize) {
        set = originalList;
        max = (1 << set.size());
        index = 0;
        this.subsetSize = subsetSize;
    }
    
    public boolean hasNext() {
        return index < max;
    }
    public Set<E> next() {
//    	System.out.println("\n----");
        Set<E> newSet = new LinkedHashSet<E>();
        int flag = 1;      
        for (E element : set) {
//        	System.out.println("\nelement: "+element);
//        	System.out.println(Integer.toBinaryString(index)+" index\n"+Integer.toBinaryString(flag)+" flag\n"+Integer.toBinaryString(index&flag)+" &");
            if ((index & flag) != 0 && Integer.bitCount(index) == subsetSize) {
//            	System.out.println("added");
                newSet.add(element);
            }
            flag <<= 1;
        }
        ++index;
        return newSet;
    }
    
    // Test.
    public static void main(String[] args) {
        List<String> set = Arrays.asList("A", "B", "C", "D");
        SubsetIterator<String> it = 
                new SubsetIterator<String>(set, 5);
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
