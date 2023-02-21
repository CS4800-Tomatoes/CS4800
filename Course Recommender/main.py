import flask as Flask
#from flask_cors import CORS
#import pymongo
import json

app = Flask.Flask(__name__)
# CORS(app)

classes_list = []

#my_client = pymongo.MongoClient("mongodb+srv://msarmiento1621:tXGN4XFKuOcyse19@cluster0.qnobfqx.mongodb.net/test")
#my_db = my_client["Class_Recommender"]
#my_collection = my_db["classes"]

# def init_db():
#     db_file = open("db.csv")
#     for line in db_file.readlines():
#         parts = line.split(",")
#         classes_list.append(
#             {
#             "Course Number" : int(parts[0]),
#             "Course Name" : parts[1],
#             }
#         )
#     print("Database initialized")
    
@app.route('/search/<course_number>')    
def search_by_class_num(course_number):
    course_num = int(course_number)
    res = []
    for course in classes_list:
        if course["Course Number"] == course_num:
            res.append(course)
    return json.dumps(res)

#@app.route('/')
# def search_by_class_num_DB(course_number):
#     res = []
#     for course in my_collection.find():                                        #Timeout error here (?) idk what's going on
#         if course['Course Number'] == int(course_number):
#             course.pop('_id')
#             res.append(course)
#     return json.dumps(res)

@app.route('/')
def home_page():
    return "Temp Home Page"

@app.route('/cs4800')
def hello_world():
    return "Hello from CS4800"

@app.route('/cs4800/conanapi')
def conan_api():
    return "Proof I (Conan Jian) can make an api"

#I turned off db since I don't have it
#init_db()
app.run(host = "0.0.0.0")

#print(classes_list)
#print(search_by_class_num_DB(4200))
