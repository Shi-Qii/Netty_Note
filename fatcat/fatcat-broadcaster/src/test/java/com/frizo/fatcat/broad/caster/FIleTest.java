package com.frizo.fatcat.broad.caster;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FIleTest {
    @Test
    public void testFile() throws FileNotFoundException {
        File f = new File("D:\\buffer\\log.txt");
        BufferedReader reader = new BufferedReader(new FileReader(f));
        reader.lines().forEach(System.out::println);
    }
}
