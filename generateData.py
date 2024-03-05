import random
import csv
from faker import Faker

# Initialize Faker for generating random names
fake = Faker()

NUM_FLIGHTS = 100
MIN_PASSENGERS_PER_FLIGHT = 100
MAX_PASSENGERS_PER_FLIGHT = 300
AIRPORT_CODES = ["PEK", "LAX", "JFK", "CDG", "DXB", "HND", "LHR", "SIN", "ICN", "AMS"]
AIRLINE_CODES = ["CA", "AA", "BA", "AF", "EK", "NH", "LH", "SQ", "KE", "KL"]
MAX_BAGGAGE_WEIGHT = 30  # 行李重量上限
MAX_BAGGAGE_VOLUME = 50  # 行李体积上限

# 随机生成航班号
def generate_flight_code():
    airline = random.choice(AIRLINE_CODES)
    flight_number = str(random.randint(100, 999))
    return airline + flight_number

# 生成随机日期
def generate_random_date():
    day = str(random.randint(1, 28)).zfill(2)
    month = str(random.randint(1, 12)).zfill(2)
    year = str(random.randint(22, 24)).zfill(2)
    return day + month + year

# 随机生成行李重量和体积
def generate_baggage_weight_volume():
    weight = random.randint(1, MAX_BAGGAGE_WEIGHT)
    volume = random.randint(1, MAX_BAGGAGE_VOLUME)
    return weight, volume

# 生成乘客数据
passenger_data = []
flight_details_data = []

for _ in range(NUM_FLIGHTS):
    airport_code = random.choice(AIRPORT_CODES)
    flight_code = generate_flight_code()
    date = generate_random_date()
    passengers_in_flight = random.randint(MIN_PASSENGERS_PER_FLIGHT, MAX_PASSENGERS_PER_FLIGHT)

    total_baggage_weight = 0
    total_baggage_volume = 0

    for _ in range(passengers_in_flight):
        unique_code = str(random.randint(1000, 9999))
        name = fake.name()
        checked_in = random.choice(["Yes", "No"])
        baggage_weight, baggage_volume = generate_baggage_weight_volume()
        booking_code = airport_code + flight_code + date + unique_code
        passenger_data.append([booking_code, name, flight_code, date, checked_in, baggage_weight, baggage_volume])

        total_baggage_weight += baggage_weight
        total_baggage_volume += baggage_volume

    # 添加航班信息
    destination_airport = random.choice(AIRPORT_CODES)
    carrier = random.choice(AIRLINE_CODES)
    flight_details_data.append([flight_code, date, destination_airport, carrier, passengers_in_flight, total_baggage_weight, total_baggage_volume])

# 保存乘客数据到CSV
passenger_csv_file = './passenger_data.csv'
with open(passenger_csv_file, mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(["Booking Code", "Name", "Flight Code", "Date", "Checked In", "Baggage Weight (kg)", "Baggage Volume (m^3)"])
    writer.writerows(passenger_data)

# 保存航班详细信息到CSV
flight_details_csv_file = './flight_details_data.csv'
with open(flight_details_csv_file, mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(["Flight Code", "Date", "Destination Airport", "Carrier", "Passengers", "Total Baggage Weight (kg)", "Total Baggage Volume (m^3)"])
    writer.writerows(flight_details_data)
