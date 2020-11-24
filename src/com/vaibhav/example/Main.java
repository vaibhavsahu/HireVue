package com.vaibhav.example;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(Vote.testInputMatchesOutput("sample-input-1.txt", "sample-output-1.txt"));
        System.out.println(Vote.testInputMatchesOutput("sample-input-2.txt", "sample-output-2.txt"));
    }
}
