package com.vaibhav.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Vote {
    private String preference;
    private String option;
    private int count;

    public static final String FILE_PATH = "<User File Path>";

    public Vote() {
    }

    public Vote(String preference, String option, int count) {
        this.preference = preference;
        this.option = option;
        this.count = count;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return count == vote.count &&
                preference.equals(vote.preference) &&
                option.equals(vote.option);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preference, option, count);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "preference='" + preference + '\'' +
                ", option='" + option + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    public static boolean testInputMatchesOutput(String inputFile, String outputFile) throws FileNotFoundException {


        List<Vote> votes = readFromInputFile(inputFile);
        List<String> actualOutput = processVotes(votes);
        List<String> expectedOutput = readFromOutputFile(outputFile);

        return Objects.equals(expectedOutput, actualOutput);
    }

    private static List<String> readFromOutputFile(String outputFile) {
        List<String> result = new ArrayList<>();
        try {
            File file;
            BufferedReader bufferedReader = null;
            bufferedReader = new BufferedReader(new FileReader(FILE_PATH + "sample-output-2.txt"));
            String line = bufferedReader.readLine();

            while (line != null) {
                result.add(line);
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static List<Vote> readFromInputFile(String inputFile){
        List<Vote> votes = new ArrayList<>();
        try {
            File file;
            BufferedReader bufferedReader = null;
            bufferedReader = new BufferedReader(new FileReader(FILE_PATH+"sample-input-2.txt"));
            String line = bufferedReader.readLine();

            while (line != null) {

                line = line.replace("|", ",");
                String[] arr = line.split(",");

                for (int i = 0; i < arr.length; i += 3) {
                    Vote vote = new Vote();
                    vote.setPreference(arr[i]);
                    vote.setOption(arr[i + 1]);
                    vote.setCount(Integer.valueOf(arr[i + 2]));
                    votes.add(vote);
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return votes;
    }

    private static List<String> processVotes(List<Vote> votes){
        List<String> results = new ArrayList<>();
        //filter the votes based on same preference
        Map<String, List<Vote>> map = votes.stream().collect(Collectors.groupingBy(Vote::getPreference));

        //sort the map by keys
        Map<String, List<Vote>> sortedMap = map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        for (Map.Entry<String, List<Vote>> entry: sortedMap.entrySet()){
            List<Vote> voteList = entry.getValue();
            Set<String> set = new HashSet<>();
            voteList.removeIf( vote -> !set.add(vote.getOption()));

            Collections.sort(voteList, Comparator.comparing(Vote::getCount).reversed());


            int count = 1;
            for(Vote v: voteList){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(v.getPreference());
                stringBuilder.append(",");
                stringBuilder.append(count++);
                stringBuilder.append(",");
                stringBuilder.append(v.getOption());
                results.add(stringBuilder.toString());
            }
        }
        return results;
    }
}
