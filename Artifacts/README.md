#California Department of Technology - OnCore Consulting ADPQ Vendor Pool Refresh Prototype - 
###[Prototype URL](Prototype URL)

##Technical Approach _(2)_
Oncore’s principle focus during development of CalOrders is summarized best by Jeff Sutherland.
 
“[Value] people over processes; products that actually work over documenting what that product is supposed to do; collaborating with customers over negotiating with them; and responding to change over following a plan.” (Scrum: The Art of Doing Twice the Work in Half the Time)

Oncore focused on delivering what the end user needed. This was done with a combination of user research, rapid prototyping, and constant interaction with end users through interviews and focus groups. These tight cycles between ideas and testing allowed us to respond quickly to change. Focusing on solutions not features and adhering to the [U.S. Digital Services](https://playbook.cio.gov/) Playbook promoted user satisfaction and adoption through continuous interaction/feedback. We [followed](https://github.com/OncoreLLC/CalOrders/wiki/Addressing-U.S.-Digital-Services-Playbook-Plays) all of the applicable Playbook plays.  

##Project Requirements
Reqirement | Reference(s)
--- | ---
a. Assigned one (1) leader and gave that person authority and responsibility and held that person accountable for the quality of the prototype submitted; |
b. Assembled a multidisciplinary and collaborative team that includes, at a minimum, five (5)of the labor categories as identified in Attachment B: PQVP DS-AD Labor Category Descriptions; |
c. Understood what people needed1, by including people in the prototype development and design process; |
d. Used at least a minimum of three (3) “user-centric design” techniques and/or tools; |
e. Used GitHub to document code commits; |
f. Used Swagger to document the RESTful API, and provided a link to the Swagger API; |
g. Complied with Section 508 of the Americans with Disabilities Act and WCAG 2.0; |
h. Created or used a design style guide and/or a pattern library; |
i. Performed usability tests with people; |
j. Used an iterative approach, where feedback informed subsequent work or versions of the prototype; |
k. Created a prototype that works on multiple devices, and presents a responsive design; |
l. Used at least five (5) modern2 and open-source technologies, regardless of architectural layer (frontend, backend, etc.); |
m. Deployed the prototype on an Infrastructure as a Service (IaaS) or Platform as Service(PaaS) provider, and indicated which provider they used; |
n. Developed automated unit tests for their code;  |

##CalOrder Development Team
Oncore selected Mike Tsay as the Team Leader/Project Manager, who had authority over and was responsible and accountable for the quality of the prototype submitted _(2a)_.

**We selected a multidisciplinary and collaborative team that fulfilled the following 8 labor categories _(2b)_:**  

Role | Team Member  
--- | ---  
Product Manager	| [Mike Tsay](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Team-Bios)
Agile Coach/Technical Architect | [Royce Owens, CSM](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Team-Bios)  
Technical Architect/DevOps Engineer | [Kyle Poland](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Team-Bios) 
Interaction Designer/User Researcher/Usability Tester | [Mike Earl, CSM](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Team-Bios)
Front End Web Developer | [Kevin Babcock](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Team-Bios)
Front End Web Developer | [Ryan Sinor](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Team-Bios)  
Backend Web Developer | [Janice Wiley](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Team-Bios)  
Backend Web Developer | [Won Lee](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Team-Bios)  
DevOps Engineer | [Suganya Ravikumar](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Team-Bios)  

##User Centric Design - Understanding what people need  
The first step of a User-Centered-Design (UCD) process is to formulate a plan.  We developed a [statement of work](https://github.com/OncoreLLC/CalOrders/wiki/Statement-of-Work) to identify the project objectives, requirements, user community, constraints, schedule, and deliverables.

Our plan combines our UCD and Agile processes into a cohesive and flexible workflow.

The team utilized User-Centered-Design (UCD) and Agile development techniques to formulate the product backlog.  All team members participated in the initial meetings with the users which was a natural hook into our Agile methodolgy and gave everyone the opportunity to understand the scope of the project and understand how each of them would contribute to the project's success.  

The table below summarizes how we incorporated the UCD practices in the development of CalOrders.  Each section includes a link to another page that provides detail on the subject. _(2d)_  

Plan | Analyze| Design | Implement | Test & Measure  
--- | --- | --- | --- | ----
[Kickoff Meeting](https://github.com/OncoreLLC/CalOrders/wiki/Hold-Kick-Off-Meeting) | [Research Domain](https://github.com/OncoreLLC/CalOrders/wiki/Domain-Research)    |[Scenarios](https://github.com/OncoreLLC/CalOrders/wiki/Scenarios) | [Style Guide](http://www.oracle.com/webfolder/technetwork/jet/jetCookbook.html)    |[Usability Test](https://github.com/OncoreLLC/CalOrders/wiki/Usability-Testing)
[Statement of Work](https://github.com/OncoreLLC/CalOrders/wiki/Statement-of-Work)   |[Focus Groups](https://github.com/OncoreLLC/CalOrders/wiki/Focus-Group)|[Story Boarding](https://github.com/OncoreLLC/CalOrders/wiki/Story-Boards)     |[Wireframe Walkthrough](https://github.com/OncoreLLC/CalOrders/wiki/Wireframe-Walkthrough) | Refine Application            
[Develop a Plan](https://github.com/OncoreLLC/CalOrders/wiki/Develop-a-Plan)   | [Personas](https://github.com/OncoreLLC/CalOrders/wiki/Develop-Personas)||                       | Retest
||[Interviews](https://github.com/OncoreLLC/CalOrders/wiki/Interviews) ||

##Agile Prototype Development   

[Oncore's Agile methodology](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Methodolgy) provided the team an iterative approach, where feedback informed subsequent work or versions of the prototype_(2j)_.   

###Principles

There are many different specific project development methodologies within agile. but they all center around a core set of principles:

1. Define a measurable goal.
2. Everyone owns the problem.
3. Small steps with visible impact.
4. Validate with your target audience.
5. Measure success.
6. Reflect, adjust, iterate.

###Process   

Our [Agile](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Methodolgy) process allowed us to rapidly deliver a working product to maintain the momentum from the User Centered Design Sessions, allowing us to show the end user the system we were building. Combined with sprint cycles we were able to add new stories; existing stories in the backlog and icebox were continually evolving and reprioritized with continued meetings outside of the development cycle. These meeting took place between the product owner, business analysts and end users. Focusing on keeping things simple and evolving fostered the environment of rapid delivery and allowed for face to face interactions with the end users on how the system was evolving.  More importanly, it allowed for failure, and with the quick adjustments yieled a system that worked for the end users.

###Practices
The list below provides links to additional artifacts and other important information about each agile practice that was utilized for this project.<br>
* [User Stories (Pivotal Tracker)](https://www.pivotaltracker.com/n/projects/1968721) 
* [User Story Estimation](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Story-Estimation) 
* [User Story Prioritization](https://github.com/OncoreLLC/CalOrders/wiki/Agile-Story-Prioritization) 
* [Sprint planning](https://github.com/OncoreLLC/CalOrders/wiki/Sprint-Planning) 
* [Sprints](https://github.com/OncoreLLC/CalOrders/wiki/Sprints)
* [Sprint Reviews](https://github.com/OncoreLLC/CalOrders/wiki/Sprint-Reviews)
* [Retrospectives](https://github.com/OncoreLLC/CalOrders/wiki/Sprint-Retrospectives) 
* [Daily Standups](https://github.com/OncoreLLC/CalOrders/wiki/Sprint-Daily-Stand-ups)

###Architecture & [DevOps](https://github.com/OncoreLLC/CalOrders/wiki/Environments)  
  The team's DevOps methodology.  

###Modern Open-Source Technologies _(2l)_  
Technology | Requirement  
--- | ---  
Linux	| Operating System used for both the development workstation and the deployment environment 
NetBeans | Development IDE  
Oracle JET | Open Source JavaScript UI Component Library  
Node JS | Frontend Javscript Engine  
Payara | JEE Application Server  
MySQL | Database  
Jenkins | Continuous Integration  
JUnit | [automated unit test cases are here in the repository]  (https://github.com/OncoreLLC/CalOrders/tree/master/CalOrdersRest/test/com/oncore/calorders/rest/service/extension)
Selenium | [automated regression test cases are here in the repository](https://github.com/OncoreLLC/CalOrders/tree/master/CalOrdersSelenium/test/test/oncore/calorders/selenium)  
Knockout Js | Front-end Javascript library  
Require Js | Front-end Javascript library  
jQuery | Front-end Javascript library  
Swagger | Automated Documentation of RESTful services   
NVDA | Screen Reader
WAVE | ADA Browser testing plugin


###Deployment Environment  
  Azure Cloud _(2m)_  
    [Architecture doc](Architecture doc)  

###Automated Unit Test _(2n)_  
  Junit4  
  Selenium  

###Continuous Monitoring and Integration  
  Configured Jenkins within a Docker container on an Azure cloud environment _(2s)_.   

  As we completed functioning code, we were able to continually show the end users how we were building checkin activity on GitHub generated builds through Jenkins resulting in automated testing and automatic Docker deployments to the Azure cloud. _(2m, 2p)_  

###Build and Deploy
  Jenkins  
  Docker  

###Configuration Management  
  GitHub _(2e, 2q)_  
    [PRODUCT NAME GitHub](PRODUCT NAME GitHub)  
  
###Setup and Deployment Documentation _(2t)_  
  [Deployment Documentation](Deployment Documentation)
