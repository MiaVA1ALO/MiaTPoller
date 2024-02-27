import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MiaTPoller
{
    private String url;
    private final int pollID;
    private final int choiceIndex;

    private int counter = 0;

    private HttpURLConnection connection;
    public static void main(String[] args)
    {
        // "MiaTPoller" in ASCII art
        System.out.print("#     #          ####### ######                                     \n##   ## #   ##      #    #     #  ####  #      #      ###### #####  \n# # # # #  #  #     #    #     # #    # #      #      #      #    # \n#  #  # # #    #    #    ######  #    # #      #      #####  #    # \n#     # # ######    #    #       #    # #      #      #      #####  \n#     # # #    #    #    #       #    # #      #      #      #   #  \n");
        System.out.print("#     # # #    #    #    #        ####  ###### ###### ###### #    # \n");
        if(args.length == 4)
        {
            new MiaTPoller(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }
        else
        {
            System.err.println("Usage: java MiaTPoller [url] [pollID] [choiceIndex] [numTimes]\nRefer to readme.txt for detailed documentation.");
        }
    }

    public MiaTPoller(String url, int pollID, int choiceIndex, int numTimes)
    {
        this.url = url;
        this.pollID = pollID;
        this.choiceIndex = choiceIndex;

        fixURL();
        // this is buggy and experimental, i don't recommend using a non-terminating while loop.
        // it will work for a long time before overflowing though (which it does)
        if(numTimes == -1)
        {
            while(true)
            {
                vote();
            }
        }
        else
        {
            for(int i = 0; i < numTimes; i++)
            {
                vote();
            }
            connection.disconnect();
            System.out.println("Done after " + counter + " polls generated!");
        }
    }

    // The main function for voting in TotalPoller polls.
    private void vote()
    {
        try {
            counter++;
            URL url = new URL(this.url);

            // Connects to the domain
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            // Build the POST query...
            String payload = "totalpoll[id]=" + pollID + "&totalpoll[page]=1&totalpoll[view]=vote&totalpoll[choices][]=" + choiceIndex + "&totalpoll[action]=vote&action=tp_action";

            // Send the payload to domain
            try (OutputStream os = connection.getOutputStream())
            {
                OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                osw.write(payload);
                osw.flush();
                osw.close();
            }

            // Get the HTTP response from domain
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                System.out.println(counter);
            }
            else
            {
                System.out.println("POST request failed with code: " + connection.getResponseCode());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    // a bunch of logic to change the url depending on user input
    private void fixURL()
    {
        // remove www.
        if(url.contains("www."))
        {
            url = url.replace("www.", "");
        }
        // add https://
        if(!url.startsWith("https://"))
        {
            url = "https://" + url;
        }
        // remove /
        if(url.endsWith("/"))
        {
            url = url.substring(0, url.length() - 1);
        }
        // add /wp-admin/admin-ajax.php
        if(!url.endsWith("/wp-admin/admin-ajax.php"))
        {
            url += "/wp-admin/admin-ajax.php";
        }
        // removes whitespace
        url = url.trim();
        System.out.println("The domain will be as follows... make sure it looks right!: " + url);
    }

}
