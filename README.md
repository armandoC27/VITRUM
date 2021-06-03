**VITRuM**

VITRuM is a java framework that allows you to carry out the analysis of test cases in order to identify test smell.

In order to improve flexibility, VITRuM is composed of several modules which each of them has specific responsibilities.

To use VITRuM follow these steps:
-Clone this repo: git clone https://github.com/armandoC27/VITRUM
-Download libTest libraries (in module plugin/lib/libTest) and paste in folder that you prefer (only for dynamic analysis)
-Run jar task from gradle or in alternative run it with IntelliJ specifing a configuration (with MainClass CLI.cliLogic.MainVitrum) 
-You can use Vitrum in two ways (in both case you have to use the command: java -jar CLI-1.0.jar and then the parameters):
   1)Run VITRuM on single project by passing the following parameters structure:
     FirstParameter: path of directory's save
     SecondParameter: path of project cloned on your machine
     ThirdParameter: path of libraries that you have already downloaded (only for dynamic analysis) 
     Options for dynamic analysis:
     -mutation: requires a parameter (to run with mutation coverage)
     -flaky: requires a parameter (to run with flaky test option)
     -lc: no requires a parameter (to run with line coverage and branch coverage)
  2)Run VITRuM on a list of projects on every version:
    FirstParameter: path of directory's save
    SecondParameter: path of a .txt file witch contains all Github link projects to mine
    Option:
    -mining: to run with mining option
In the first case you will have a file "ResultTest.csv" which contains the result of the analysis of the project that you specify.
In the second case you will have a folder for each project that you specified in the .txt file and in these tou will find a file "ResultTest.csv" for every project with added the corrispondent hash commit and version in every row of the file.

