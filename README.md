**Vitrum Rev**

Vitrum é un framework java che permette di efettuare l'analisi dei casi di test al fine di individuare test smells.

Al fine di migliorare la flessibilità, Vitrum è composto da diversi moduli i quali ognuno di essi ha delle specifiche responsabilità.

Per poter utilizzare questo framework segui i seguenti step:
-Clone this repo: git clone https://github.com/armandoC27/VITRUM
-Download libTest libraries and paste in folder that you prefer (only for dynamic analysis)
-Run jar task from gradle
-Now your jar is ready and you can use the Vitrum in two ways:
   1)Run vitrum on single project by passing the following parameters structure:
     FirstParameter: path of directory's save
     SecondParameter: path of project cloned on your machine
     ThirdParameter: path of libraries that you have already downloaded (only for dynamic analysis) 
     Options for dynamic analysis:
     -mutation: requires a parameter
     -flaky: requires a parameter
     -lc: no requires a parameter
       
