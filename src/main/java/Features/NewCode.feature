@Login_to_application_and_do_Functions

Feature: Test the application flow


 Scenario: Log into the application and follow steps
  
Given user launch the application and enter the application URL
When user logs in with valid cred
And verify user landed to next page
Then user starts to enter details