README

This is an assignment provided by De Montfort University for CTEC2906 Object-Oriented Development
to demonstrate student's ability to design and implement an OO system consisting of a set of Java
classes, using advanced libraries within the Java SDK.

In this assignment I have demonstrate the designed system ability of second year computing students in 
De MontfortUniversity to select their final year module options. There are compulsory modules tht must
be selected based on the course of study, and others that are only associated with certain courses.
Modules either run in term 1 or 2, or all year long (Development Project).

For this I have built an interactive graphical user interface (GUI) that dynamically allows modules
to be selected based on the chosen course of study, computer science and software engineering, and
then stores this information. The application is designed to be user-friendly and contain appropriate
validation to ensure only a legitimate selection of modules is made.

Each modules contains their credit ammount, and whether they are optional or compulsory for Computer
Science and Software Engineering students. In total 120 credits must be selected via any legitimate
combination of modules, but crucially studnets may only select 60 credits per term. The yearlong
compulsory module CTEC3451 contributes towards 15 credits in each term.

As an example, a Computer Science student would have by default 30 credits selected in term 1 and 15
credits in term 2 due to the mandatory nature of IMAT3423 (15 Credits Term 1) and 
CTEC3451 (30 Credits Year Long). This would mean they would need to select a further 30 credits of 
term 1 modules, and a further 45 credits in term 2. A Software Engineering student would be similar,
but would additionally have the mandatory module CTEC3902 in term 2, therefore, the system requires
an additional 30 credits worth of modules to be chosen in both term 1 and 2.

In below table overleaf shows all of the avilable module, their credit amount and whether they are
optional or mandatory for both Computer Science and Software Engineering students.

|Code|Title|Credits|Term|Computer Science|Compulsory|Software Engineering|Compulsory|
|----|-----|-------|----|----------------|----------|--------------------|----------|
|IMAT3423|Systems Building: Methods|15|1|x|x|x|x|
|CTEC3451|Development Project|30|Year Long|x|x|x|x|
|CTEC3902|Rigorous Systems|15|2|x| |x|x|
|CTEC3605|Multi-service Networks 1|15|1|x| |x| |
|CTEC3606|Multi-service Networks 2|15|2|x| |x| |
|CTEC3110|Secure Web Application Development|15|1|x| |x| |
|CTEC3410|Web Application Penetration Testing|15|2|x| |x| |
|CTEC3904|Functional Software Development|15|2|x| |x| |
|CTEC3905|Front-End Web Development|15|2|x| |x| |
|CTEC3906|Interaction Design|15|1|x| |x| |
|CTEC3911|Mobile Application Development|15|1|x| |x| |
|IMAT3104|Database Management and Programming|15|2|x| |x| |
|IMAT3611|Computer Ethics and Privacy|15|1|x| |x| |
|IMAT3406|Fuzzy Logic & Knowledge Based Systems|15|1|x| |x| |
|IMAT3613|Data Mining|15|1|x| |x| |
|IMAT3614|Big Data & Business Models|15|2|x| |x| |
|IMAT3428|Information Technology Services Practice|15|2|x| | | |

