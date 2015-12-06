# Description - 
Web application displaying analysis of 108246 android apps. It displays following results - 
  1. Third party packages
  2. Permissions used by each application, categorized into permissions used by the third party packages and by native 
code and the destination and source classes involved in the use of permission.
  3. Http links embedded in the applications, categorized into links embedded in the third party packages and in the native
code. It also displays the classes where these links are used with the code.

# Over all Project setup & Description
This repository only showcases the display component of the complete project. The above mentioned data is collected via python scripts present in the repository named "python_static_analyzer". Both web-application & the analyzer scripts together application database was copied on Amaon-EC2 instance. Then using the scripts mentioned under repository "staticAnalysis_Writeup_utilityScripts" the analysis was started remotely & data collection was monitored.

# Technologies & languages used : 
1. Java
2. JSTL
3. mysql
4. Python scripts
5. Shell scripts
6. Androguard to reverse engineer dex files and collect above data.
