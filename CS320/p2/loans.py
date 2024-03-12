import json
import csv
from zipfile import ZipFile
from io import TextIOWrapper

race_lookup = {
    "1": "American Indian or Alaska Native",
    "2": "Asian",
    "3": "Black or African American",
    "4": "Native Hawaiian or Other Pacific Islander",
    "5": "White",
    "21": "Asian Indian",
    "22": "Chinese",
    "23": "Filipino",
    "24": "Japanese",
    "25": "Korean",
    "26": "Vietnamese",
    "27": "Other Asian",
    "41": "Native Hawaiian",
    "42": "Guamanian or Chamorro",
    "43": "Samoan",
    "44": "Other Pacific Islander"
}


class Applicant:
    def __init__(self, age, race):
        self.age = age
        self.race = set()
        for r in race:
            if r in race_lookup:
                (self.race).add(race_lookup[r])
    
    def __repr__(self):
        return f"Applicant('{self.age}', {list(self.race)})"
    
    def lower_age(self):
        low_age = (self.age).split("-")[0]
        if low_age.isdigit() == False:
            low_age = low_age.replace('>', '')
            low_age = low_age.replace(' ', '')
            low_age = low_age.replace('<', '')
        return int(low_age)

    def __lt__(self, other):
        return Applicant.lower_age(self) < Applicant.lower_age(other)
    
    
class Loan:
    def __init__(self, values):
        try:
            self.loan_amount = float(values["loan_amount"])
        except ValueError:
            self.loan_amount = -1
        try:
            self.property_value = float(values["property_value"])
        except ValueError:
            self.property_value = -1
        try:
            self.interest_rate = float(values["interest_rate"])
        except ValueError:
            self.interest_rate = -1
        self.applicants = []
        
        app_race = []
        for race_num in range(1, 6):
            if values[f"applicant_race-{race_num}"] != "":
                app_race.append(values[f"applicant_race-{race_num}"])
        (self.applicants).append(Applicant(values["applicant_age"], app_race))
        
        co_app_race = []
        if values["co-applicant_age"] != "9999":
            for co_race_num in range(1, 6):
                if values[f"co-applicant_race-{co_race_num}"] != "":
                    co_app_race.append(values[f"co-applicant_race-{co_race_num}"])
            (self.applicants).append(Applicant(values["co-applicant_age"], co_app_race))
    
    def __str__(self):
        return f"<Loan: {self.interest_rate}% on ${self.property_value} with {len(self.applicants)} applicant(s)>"
    
    def __repr__(self):
        return str(self)
    
    def yearly_amounts(self, yearly_payment):
        assert self.interest_rate > 0
        assert self.loan_amount > 0
        amt = self.loan_amount

        while amt > 0:
            yield amt
            amt = amt + (self.interest_rate/100.0) * amt
            amt = amt - yearly_payment
            
            
class Bank:
    def __init__(self, name):
        with open("banks.json") as f:
            bank_data = json.load(f)
            for bank in bank_data:
                if bank["name"] == name:
                    self.lei = bank["lei"]
        self.loans = []
            
        with ZipFile('wi.zip') as zf:
            with zf.open("wi.csv", "r") as f:
                wi_contents = TextIOWrapper(f)
                wi_df = csv.DictReader(wi_contents)
                for wi_loan in wi_df:
                    if wi_loan["lei"] == self.lei:
                        self.loans.append(Loan(wi_loan))
                        
    def __len__(self):
        return len(self.loans)
    
    def __getitem__(self, arg):
        return self.loans[arg]
                
            