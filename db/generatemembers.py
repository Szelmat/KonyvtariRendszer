import sqlite3 as sqlite
import csv
import random

# id, name, address, type, contact
types = (
    "Egyetemi hallgató",
    "Egyetemi oktató",
    "Másik egyetem polgára",
    "Egyéb"
)

counter = 10 # innen kezdődnek az id-k

conn = sqlite.connect('library.db') # Csatlakozunk az adatbázishoz
curs = conn.cursor() # A kurzorral tudunk parancsokat lefuttatni

with open('people.csv', encoding="utf8") as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            line_count += 1
        else:
            id_ = counter
            counter += 1
            name = row[0] + " " + row[1]
            adress = f"{row[4]}, {row[3]}"
            type_ = types[random.randrange(0, 4)]
            contact = row[10]
            line_count += 1

            print(f"{id_}, {name}, {adress}, {type_}, {contact}")

            curs.execute("INSERT INTO members(id, name, address, type, contact) VALUES (?, ?, ?, ?, ?);", (id_, name, adress, type_, contact))

    conn.commit()
    curs.close()
    conn.close()
    print(f'Processed {line_count} lines.')