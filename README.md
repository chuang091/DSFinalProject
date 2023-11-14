"# DSFinalProject" 

I uploaded three programs, two of which work together.
**IMPORTANT** Both programs needs jsoup jars to be added to modulepath and classpath
Please download the jSoup_Jars.zip file and unzip it
--To add to modulepath, right click the project after importing, select buildpath, then Configure buildpath
Then select Modulepath, then click Add External JARs on the right, then select the three jars from the file you just unzip
--To add to classpath, select Classpath, then click add Library, then User Library at the end of the list
Then User Library again but this time on the right, then new, Enter a name you like (Doesn't matter as long as you can know what that is)
Then select the Library you just created, and select Add External JARs, then select the same three jars you unzipped, then click apply until you exit the buildpath configuration window

First is the Parallel_Test.zip
This program is the one that works on it's own

Three functions are built in this program
1. Calculate the square sum of 1~1000000000 twenty times using single-thread
2. Calculate the square sum of 1~1000000000 twenty times using multi-thread (All of the threads in your PC)
   **Note: This would put a lot of stress on your cpu for a short amount of time (depending on how fast your cpu is)
3. Fetch link found online from the keyword "computer", feel free to change the word (But you need to change the code since it's just for test)

Note 1. You can change the keyword by changing this code [FetchContent fetcher = new FetchContent("computer", 10);] found in TestMain.java under [case ("3")]
The String in the constructor is the keyword parameter, feel free the change it!
Note 2. You can change the number of URLs by changing the second parameter of the same code; I set it to 10 as default.
Note 3. The links may not work due to encoding of HTMLs, we can work on fixing it later.
Note 4. For the parallel testing, if you find it longer than single-thread (should be at least 2x faster), please adjust this variable
[if (end - start <= 100000) {}], you can start by removing or adding some 0s, it should make a significant difference.
The code is found in the Calculation class under the compute() method
Note 5. At the end of TestMain are the runtime I tested on my PC, you could use it as a benchmark, but it may vary from cpu to cpu

Second & Third are FetchContent(With Server).zip and the ClientTest.zip
These two programs work together; respecfully as a server and a client
** Note: I recommand testing on 2+ PCs for a better experience

The function of this program
- Connect two machines via LAN (WAN would be too difficult due to firewalls)
**Note: Please change the ip on the client side, as I didn't use a scanner to scan for user input
  See Note 3. for more details
- Enter a keyword from the client side
- The server side would take in the keyword and fetch URLs just like the one in the Parallel_Test.zip
- After acquiring URLs, the server sends the found URLs to the client
- The client recieves the URLs.

Note 1. All of the drawbacks mentioned previously are also the cases of this program.
Note 2. I didn't programmed this program so the client could keep entering keywords and find contents; the program automatically terminates after doing one search
In the actual project this could be easily changed
Note 3. To change the clients ip (so it matches your server ip), please change this code [Socket socket = new Socket("192.168.168.124", 800);]
which could be found in ClientMain.java under the main method
Note 4. The int value 800 in the same line is the "key", you can change it to whatever port you like, but make sure that both server and client sides are modified.
