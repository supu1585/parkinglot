##Parking Lot

## Problem Statement

1. I own a parking lot that can hold up to 'n' cars at any given point in time. Each slot is given a number starting at 1 increasing with increasing distance from the entry point in steps of one. I want to create an automated ticketing system that allows my customers to use my parking lot without human intervention.

2. When a car enters my parking lot, I want to have a ticket issued to the driver. The ticket issuing process includes us documenting the registration number (number plate) and the colour of the car and allocating an available parking slot to the car before actually handing over a ticket to the driver (we assume that our customers are nice enough to always park in the slots allocated to them). 

3. The customer should be allocated a parking slot which is nearest to the entry. At the exit the customer returns the ticket with the time the car was parked in the lot, which then marks the slot they were using as being available. Total parking charge should be calculated as per the parking time. Charge applicable is $10 for first 2 hours and $10 for every additional hour.

4. We interact with the system via a simple set of commands which produce a specific output. Please take a look at the example below, which includes all the commands. The system should accept a filename as a parameter at the command prompt and read the commands from that file.

## Getting Started

Here are the instructions for you to set the project up and run in your local machine for testing purposes.

### System Requirements

You will probably need to run these scripts on a Linux box and require:

			
	- Java
	- Maven
	- Git (for version control)



### Installing

Run the below command to install the build the project (Java, Maven assumed to be pre-installed on the system)

			
		mvn  install


### Running from command prompt

java -jar target/parkinglot-0.0.1-SNAPSHOT.jar <filename> (Will read the commands from the file)


## Built With

- [Maven](https://maven.apache.org/) - Build/Dependency Management

To run the code so it accepts input from a file:
parkinglot/src/main/resources/file_inputs.txt

#Commands
• Createparkinglotofsizen:create_parking_lot {capacity}

• Parkacar:park{car_number}

• Remove(Unpark)carfrom:leave{car_number}{hours}

• Printstatusofparkingslot:status

##Input (contents of file):

create_parking_lot 6

park KA-01-HH-1234

park KA-01-HH-9999

park KA-01-BB-0001

park KA-01-HH-7777

park KA-01-HH-2701

park KA-01-HH-3141

leave KA-01-HH-3141 4

status

park KA-01-P-333

park DL-12-AA-9999

leave KA-01-HH-1234 4

leave KA-01-BB-0001 6

leave DL-12-AA-9999 2

park KA-09-HH-0987

park CA-09-IO-1111

park KA-09-HH-0123

status

## Output (to STDOUT):

Created parking lot with 6 slots

Allocated slot number: 1
Allocated slot number: 2
Allocated slot number: 3
Allocated slot number: 4
Allocated slot number: 5
Allocated slot number: 6

Registration number KA-01-HH-3141 with Slot Number 6 is free with Charge 30

Slot No.  Registration No.
1   KA-01-HH-1234
2   KA-01-HH-9999
3   KA-01-BB-0001
4   KA-01-HH-7777
5   KA-01-HH-2701

Allocated slot number: 6

Sorry, parking lot is full

Registration number KA-01-HH-1234 with Slot Number 1 is free with Charge 30

Registration number KA-01-BB-0001 with Slot Number 3 is free with Charge 50

Registration number DL-12-AA-9999 not found

Allocated slot number: 1

Allocated slot number: 3

Sorry, parking lot is full

Slot No.  Registration No.
1   KA-09-HH-0987
2   KA-01-HH-9999
3   CA-09-IO-1111
4   KA-01-HH-7777
5   KA-01-HH-2701
6   KA-01-P-333


