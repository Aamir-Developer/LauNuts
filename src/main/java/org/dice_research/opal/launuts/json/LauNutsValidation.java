package org.dice_research.opal.launuts.json;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LauNutsValidation
{
    public static void main(String[] args)
    {
        String filename = "LAUs_Polygon.json";
        LauNutsValidation launutsobj = new LauNutsValidation();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename))
        {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray lauRead = (JSONArray) obj;
            for (int i = 0; i < lauRead.size(); i++)
            {
                String lauString = launutsobj.parseLauObject((JSONObject) lauRead.get(i), i);
                System.out.println("lauString   " + lauString);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
    private String parseLauObject(JSONObject type, int j)
    {
        String lautype = (String) type.get("Initial_geometry_type");
        System.out.println("check lautype   " + lautype);
        JSONArray coordinates = (JSONArray) type.get("Coordinates");
        if ((lautype.equalsIgnoreCase("Polygon") || lautype.equalsIgnoreCase("MultiPolygon"))
                && coordinates.size() > 3)
        {
            // this polygon checks first and last coordinates are identical
            if (coordinates.get(0).equals(coordinates.get(coordinates.size() - 1)))
            {
                return "Polygon is valid";
            }
            else
            {
                if (lautype.equalsIgnoreCase("Polygon"))
                {
                    return "this is not a polygon value but showing polygon line no:  " + (3 + j * 7);
                }
                // if record is multipolygon
                for (int i = 1; i < coordinates.size(); i++)
                {
                    if (coordinates.get(0).equals(coordinates.get(i))
                            && coordinates.get(i + 1).equals(coordinates.get(coordinates.size() - 1)))
                    {
                    }
                }
                return "Multipolygon is validated";
            }
        }
        else if (lautype.equalsIgnoreCase("Point") && (coordinates.size() == 1))
        {
            return "it is point";
        }
        else
        {
            return "invalid type";
        }
    }
}

