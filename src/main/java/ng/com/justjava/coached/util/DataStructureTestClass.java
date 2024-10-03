package ng.com.justjava.coached.util;

import org.apache.commons.lang3.StringUtils;
import scala.Int;

import java.util.*;

 public class DataStructureTestClass {

    public static void main(String[] args){
        System.out.println(" The class here is.....................");
        Stack<String> colors=new Stack<>();
        LinkedList<String> linkedList = new LinkedList<>();



        HashMap<String,String> maps= new LinkedHashMap<>();
        maps.put(null,"Blue");
        maps.put("Indigo","Indigo");
        maps.put("Black","Black");
        maps.put("Green","Green");
        maps.put("Yellow","Yellow");



        System.out.println("1 Maps=="+maps+"\n \n \n");

        maps.put("White","White");
        System.out.println("2 Maps=="+maps+"\n \n \n");


        TreeMap<String,String> treeMaps= new TreeMap<>();
        treeMaps.put("Blue","Blue");
        treeMaps.put("Black","Black");
        treeMaps.put("Green","Green");
        treeMaps.put("Yellow","Yellow");
        treeMaps.put("Indigo","Indigo");

        System.out.println(" treeMaps=="+treeMaps+"\n \n \n");

        SortedMap<String,String> sortedMaps= new TreeMap<>();
        sortedMaps.put("Blue","Blue");
        sortedMaps.put("Black","Black");
        sortedMaps.put("Green","Green");
        sortedMaps.put("Yellow","Yellow");
        sortedMaps.put("Indigo","Indigo");

        System.out.println("1");
        System.out.println();
        System.out.println("2");
        System.out.println(" sortedMaps=="+sortedMaps+"\n \n \n");

        System.out.println(" subMap Blue to Indigo sortedMaps=="+sortedMaps.subMap("Blue","Indigo"));
        System.out.println(" tailMap sortedMaps=="+sortedMaps.tailMap("Green")+"\n \n \n");


        HashSet<String> sets=new HashSet<>();
        TreeSet<String> treeSet = new TreeSet<>();

        treeSet.add("Green");

        treeSet.add("Yellow");
        treeSet.add("Green");
        treeSet.add("Blue");
        treeSet.add("Black");
        System.out.println(" Treeset==="+treeSet);

        //sets.add("Yellow");
        //sets.add("Green");
        //sets.add("Green");
        sets.add("Yellow");
        sets.add("Green");
        sets.add("Blue");


        sets.add("Black");
        //sets.add("Yellow");
// [Yellow, Blue, Black, Green]
        System.out.println("Set: "+sets);

        linkedList.add(0,"Indigo");


        colors.push("Green");
        colors.push("Green");
        colors.push("Green");
        colors.push("Blue");
        colors.push("Yellow");
        //colors.add(2,"Black");
        colors.add("White");
        colors.push(linkedList.remove(0));
        System.out.println("Stack: 1 The whole color===="+colors);
        System.out.println(" Popping..........."+colors.pop());
        System.out.println("Stack: 2 The whole color===="+colors);

        PriorityQueue queue = new PriorityQueue(6);


        //queue: 1 The whole color====[Green1, Green2, Green3, Blue, Yellow, White]
        //queue: 2 The whole color====[Green3, Blue, Yellow, White, Indigo]
        queue.add("Green1");
        queue.add("Green2");
        queue.add("Green3");
        queue.add("Blue");
        queue.add("Yellow");

        //colors.add(2,"Black");
        queue.add("White");
        //queue.add(linkedList.remove(0));
        System.out.println("queue: 1 The whole color===="+queue);
        System.out.println(" removing..........."+queue.remove());
        System.out.println("queue: peek===="+queue.peek());
        System.out.println("queue: element===="+queue.element());
        System.out.println("queue: poll===="+queue.poll());
        System.out.println("queue: offer===="+queue.offer("Indigo"));
        System.out.println("queue: 2 The whole color===="+queue);

        StringBuilder builder = new StringBuilder("Testing");
        builder.append(" String builder");
        System.out.println("builder.charAt(3)=="+builder);
        System.out.println("builder.charAt(3)=="+builder.charAt(3));

        Vector<String> vector = new Vector<>();
/*        float f = 100f/0.00f;
        System.out.println(" F here=="+f);*/
        //int i = 100/0;

        String series="";
        series=series+","+fibonacci(4);
        for (int i = 0; i <= 8; i++) {
            System.out.print(fibonacci(i) + " ");
        }

        int b=0;
        int c=1;
        b = b++ + ++c;
        System.out.println(" The b now===="+b);
    }

    public static Integer fibonacci(Integer n){
        //System.out.println(" Numbers are =="+n);

        if(n<= 1) {
            return n;
        }else {
            Integer fibo = fibonacci(n - 1) + fibonacci(n - 2);
            return fibo;
        }
    }
}
