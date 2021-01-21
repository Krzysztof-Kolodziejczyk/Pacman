package classes;

import java.io.File;
import java.util.Scanner;

public class Maze {

    public int[][] mazeMap;
    public int width = 0;
    public int height = 0;

    public Maze()
    {
        setMazeMap();
    }

    private void setMazeMap()
    {
        String mapString = "";

        try{

            Scanner scanner = new Scanner(new File("resources/map/map.txt"));
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine;
            while (scanner.hasNextLine())
            {
                currentLine = scanner.nextLine();
                stringBuilder.append(currentLine).append('\n');
                height ++;
            }

            mapString = stringBuilder.toString();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


        for(char cellCode: mapString.toCharArray())
        {
            if(cellCode == ' ')
            {
                width ++;
            }
            if(cellCode == '\n')
            {
                width++;
                break;
            }
        }

        // fill "mazeMap" 2d array with codes from file
        int i=0;
        int j=0;
        mazeMap = new int[height][width];
        char[] charArray = mapString.toCharArray();
        for(int k=0; k < charArray.length; k++)
        {
            if(charArray[k] == '\n')
            {
                charParser(i, j, charArray, k);
                j = 0;
                i ++;
            }
            else if(charArray[k] == ' ' && charArray[k-1] != ' ')
            {
                charParser(i, j, charArray, k);
                j++;
            }
        }
    }


    private void charParser(int i, int j, char[] charArray, int k) {
        if(charArray[k-2] != ' ')
        {
            if(charArray[k-2] != '-')
            {
                int tensDigit = Integer.parseInt(String.valueOf(charArray[k-2])) * 10;
                int unityDigit = Integer.parseInt(String.valueOf(charArray[k-1]));
                this.mazeMap[i][j] = tensDigit + unityDigit;
            }
            else
            {
                int unityDigit = Integer.parseInt(String.valueOf(charArray[k-1]));
                this.mazeMap[i][j] = -1 * unityDigit;
            }
        }
        else
        {
            int unityDigit = Integer.parseInt(String.valueOf(charArray[k-1]));
            this.mazeMap[i][j] = unityDigit;
        }

    }

}
