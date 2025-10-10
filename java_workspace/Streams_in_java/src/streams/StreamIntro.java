package streams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamIntro {
    public static void main(String[] args) {
        List<String> names = new ArrayList<String>();
        names.add("sinchana");
        names.add("swathi");
        names.add("nitya");
        List<String> sNames = names.stream()
        		.filter(name-> name.startsWith("s"))
        		.collect(Collectors.toList());
        System.out.println(sNames);
    }
}
