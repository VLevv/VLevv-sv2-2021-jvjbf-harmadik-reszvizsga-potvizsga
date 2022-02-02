package examinformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ExamService {
    private int theoryMax;
    private int practiceMax;
    private Map<String,ExamResult> results=new TreeMap<>();

    public int getTheoryMax() {
        return theoryMax;
    }

    public int getPracticeMax() {
        return practiceMax;
    }

    public Map<String, ExamResult> getResults() {
        return results;
    }

    public void readFromFIle(Path path){
        try(BufferedReader reader = Files.newBufferedReader(path)){
            String tmp=reader.readLine();
            theoryMax =Integer.parseInt(tmp.substring(0,2));
            practiceMax =Integer.parseInt(tmp.substring(3));
            while (reader.ready()){
                tmp=reader.readLine();
                results.put(tmp.substring(0,tmp.indexOf(";")),new ExamResult(Integer.parseInt(tmp.substring(tmp.indexOf(";")+1,tmp.indexOf(";")+3)),Integer.parseInt(tmp.substring(tmp.indexOf(";")+4))));
            }
        }catch (IOException e){
            throw new IllegalArgumentException("Cannot read file: src\\main\\java\\data.txt",e);
        }
    }

    public List<String> findPeopleFailed(){
        return results.entrySet().stream()
                .filter(entry->entry.getValue().getTheory()<(theoryMax/2)||entry.getValue().getPractice()<(practiceMax/2))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public String findBestPerson(){
        List<String> passed=results.entrySet().stream().filter(entry->!findPeopleFailed().contains(entry.getKey())).map(Map.Entry::getKey).collect(Collectors.toList());
        int maxPoint=results.entrySet().stream().filter(entry->!findPeopleFailed().contains(entry.getKey())).map(Map.Entry::getValue).map(value->(value.getPractice()+value.getTheory())).max(Integer::compareTo).get();
        List<String> bests=new ArrayList<>();
        for (Map.Entry<String,ExamResult> entry : results.entrySet()) {
            if(passed.contains(entry.getKey())&&maxPoint==(entry.getValue().getPractice()+entry.getValue().getTheory())){
                bests.add(entry.getKey());
            }
        }
        return bests.stream().min(String::compareTo).get();
    }
}
