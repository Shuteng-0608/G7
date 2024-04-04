1. System overview
This system is an application that simulates the airport boarding process and aims to improve the operational efficiency of the airport by simulating the check-in and boarding process of passengers at the airport, as well as the management of flights. The system allows multiple check-in counters to process passengers in parallel and organise the passenger flow through one or more queues. At the same time, the system displays real-time queue status, check-in progress and flight information through a graphical user interface (GUI), as well as providing log files to record system operation status.

2. Architecture design
The system adopts the MVC (Model-View-Controller) architectural pattern to support the design principles of high cohesion and low coupling, and to facilitate the separation of the user interface from the business logic. The following are the main components of the system:
Model: Contains all business logic and data structures, such as passengers, flights, check-in counters, etc.
View: Responsible for displaying the data (model) and any data changes to the user. Mainly GUI components, such as queue display, check-in counter status, flight information, etc.
Controller: Accepts user input and calls the model and view to fulfil user requests. For example, control the start, pause and termination of the simulation.


3. Core functionality implementation
Multi-threaded check-in counters: each check-in counter is controlled by a separate thread to simulate the ability to process passengers in parallel. Implemented using Java's Runnable interface.
Passenger Queuing Mechanism: Passengers arrive at the airport and join a shared queue, waiting to be processed by an idle check-in counter. The queue is managed using a producer-consumer model, ensuring that threads safely add and remove passengers from the queue.
Graphical User Interface (GUI): Implemented using the Java Swing library, the GUI displays the current state of the system, including the passenger queue, check-in counter status and flight information.
Logging: Logger, implemented using the singleton pattern, records key system events such as passenger queuing, check-in and boarding.

4. UML
![tmp666E](https://github.com/Shuteng-0608/G7/assets/72130686/00650679-2458-4dfa-85c6-db4f24477496)
