# HerokuApp – Rest Assured API Automation Framework

## Project Overview
This project is an API automation testing framework built using **Rest Assured** and **TestNG** to validate REST APIs of the HerokuApp application.  
It covers request and response validation, status code verification, positive and negative test scenarios, and generates detailed execution reports using Extent Reports.

This framework demonstrates real-world API automation practices including clean project structure, reusable utilities, and reporting.

# API Test Case Documentation

API test cases are also documented in Excel format.  
Please download the file to view complete test cases.

📁 Location:
API test case document/Project3_Telecom_API.xlsx

##  Tech Stack
- Language: Java  
- API Automation: Rest Assured  
- Test Framework: TestNG  
- Build Tool: Maven  
- Reporting: Extent Reports  
- Version Control: Git & GitHub  
- API Tool (for reference): Postman  

---

## Project Structure

├── src/main/java │   ├── utility # Common utilities (Extent Report, helpers) │
├── src/test/java │   ├── tests   │   └── listeners      # TestNG listeners │ └── pojoClasses    # POJO classes for request/response │
├── docs │   ├── postman-screenshots 
│── extent-report-screenshots │-- extent report 
├── pom.xml 


---

## Test Coverage
- Validate HTTP status codes
- Validate response body fields
- Dynamic request data handling
- Token-based authentication (where applicable)

---

##  Postman Request Screenshots
Postman was used to manually verify APIs before automation.

📁 Location: docs/postman

Includes:
- Request method (GET / POST / PUT / DELETE)
- Request headers and body
- Response body and status codes

---

## Extent Report Screenshots
After execution, detailed Extent Reports are generated showing:
- Test execution summary
- Passed / Failed test cases
- Error details for failed tests_

📁 Location: Reports/ ExtentReport_screenshots
                     /TelecomPorjectAPI (it has extent report.html)

                     


