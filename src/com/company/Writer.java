package com.company;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

class Writer
{
    public String filename;

    public List<String> lines;

    public void Write()
    {
        try {
            Path file = Paths.get(filename);
            Files.write(file, lines, StandardOpenOption.CREATE);
        }
        catch(Exception e)    {
            System.out.println(e.fillInStackTrace());
        }
    }

    public Writer(String filename)
    {
        lines  = new ArrayList<String>();
        this.filename =  filename;
    }
}
