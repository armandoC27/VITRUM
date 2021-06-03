**VITRuM**  
  
VITRuM is a java framework that allows you to carry out the analysis of test cases in order to identify test smell.  
  
In order to improve flexibility, VITRuM is composed of several modules which each of them has specific responsibilities.  

To use VITRuM follow these steps:

 - Clone this repo: git clone https://github.com/armandoC27/VITRUM  
 - Download libTest libraries (in module plugin/lib/libTest) and paste in folder that you prefer (only for dynamic analysis)
 - Run jar task from gradle or in alternative run it with Intelli specifing a configuration (with MainClass CLI.cliLogic.MainVitrum)

Now, you can use Vitrum in two ways (in both case you have to use the command: java -jar CLI-1.0.jar and then the parameters):  

 1. Run VITRuM on single project by passing the following parameters structure:  
         ***FirstParameter***: path to the save folder of result files.
         ***SecondParameter***: path of project cloned on your machine  
         ***ThirdParameter***: path of libraries that you have already downloaded (only for dynamic analysis)   
         *Options* for dynamic analysis:  
         ***-mutation***: requires a argument (to run with mutation coverage)  
         ***-flaky***: requires a argument, number of time to run flaky (to run with flaky test option)  
         ***-lc***: not requires a argument (to run with line coverage and branch coverage)
 2. Run VITRuM on a list of projects on every version:  
        ***FirstParameter***: path to the save folder of result files.  
        *Option*:  
        ***-mining***: with argument, path of a .txt file witch contains all Github link projects to mine

In the first case you will have a file "ResultTest.csv" which contains the result of the analysis of the project that you specify which saved at path of *First Parameter*
In the second case you will have a folder for each project that you specified in the .txt file and in these you will find a file "ResultTest.csv" for every project with added the corrispondent hash commit and version in every row of the file.