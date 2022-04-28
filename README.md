# Toptal Screening Assignment

This framework supports the API and UI automation through Selenium and Rest Assured library. Written in Java programming language and supports Extent Reporting. It also supports running the test on BrowserStack cloud. 

### Built With

1. Selenium - Browser automation framework
2. Rest Assured - API automation Library
3. Java - Programming language
4. Maven - Dependency management
5. TestNG - Testing framework
6. Extent Report - Reporting framework

### Prerequisite 

1. Selenium - Version 3.141.59 or higher
2. Rest Assured - Version 4.4.0 or higher
3. Java - Version 1.8 or higher
4. TestNG Plugin - Latest
5. Maven
6. Jenkins
7. Chrome/Edge browser installed - Any Version
8. JAVA_HOME and MAVEN_HOME Environment variable configured
9. BrowserStack User ID and Access Key - Optional (If needs to run on BrowserStack)

### Installation and Framework Set-Up

1. Download the zip or clone the Git repository.
2. Unzip the zip file (if you downloaded one).
3. Open the Eclipse and Import the Maven project
4. Navigate to the src\main\resources folder and open the Configuration.properties file, change the details like BrowserStack credentials, Environment name, Build no etc.
5. Keep the "platFormName" as "local" if you want to test on a local machine and change it to "BrowserStack" if you want to run it on the BrowserStack platform.
6. Open the TestData.properties file under the src\test\resources folder and check the application details like URL, application credentials (Username, Password) and application test data.
7. In the same TestData.properties file, check the API details like Base URI, credentials like User ID, Token etc and change accordingly.
8. Open the Jenkins UI and configure the project (if needs to be executed from Jenkins)

### Running Example

#### A. Using TestNG
Right-click on the testNG.xml file and Run as TestNG Suite

#### B. Using CI/CD
Right-click on the Project name and Run as Maven Test

#### C. Using Maven
Access the Jenkins project and click on Build Now

#### D. Using Browser Stack
Change the platFormName to BrowserStack in the Configuration.properties file (See the point no. 5 in the Installation and Framework Set Up above) and then run the code using any of the above-mentioned ways

### Execution Report
Extent report HTML file 'ExtentReportResults.html' can be accessed from the test-output folder.
