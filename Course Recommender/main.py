from flask import Flask
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

class_list_db=[]

def search_by_budget_db(Course_Num):
  res = []
  for Course in class_list_db:    
    if Course['Course ID'] == Course_Num:
      res.append(Course)
  return res

def init_db():
    db_file = open("db.csv")
    for line in db_file.readlines():  
        parts = line.split(",")
        class_list_db.append(
            {
                "Course ID" : int(parts[0]),
                "Course Name" : parts[1]
            }
        )  
    print(class_list_db)

@app.route("/search/<budget>")
def search_by_budget(budget):
  budget = float(budget)
  return search_by_budget_db(budget)

def hello_world():
    return "Welcome to the Class Recommender"



#print(hello_world())
init_db()
#print(search_by_budget_db(4800))
app.run(host = "0.0.0.0")
