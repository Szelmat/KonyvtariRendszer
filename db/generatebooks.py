import sqlite3 as sqlite
import csv
import random

# Majd ebből választunk egy random kiadót 
publishers = (
    'Allison & Busby',
    'Austin Macauley Publishers',
    'Bison Books',
    'Blake Publishing',
    'Brill Publishers',
    'Broadview Press',
    'City Lights Publishers',
    'Crocker & Brewster',
    'Delacorte Press',
    'Dover Publications',
    'David & Charles',
    'DNA Publications',
    'Folio Society',
    'Hemus',
    'The Jarrold Group'
)

conn = sqlite.connect('library.db') # Csatlakozunk az adatbázishoz
curs = conn.cursor() # A kurzorral tudunk parancsokat lefuttatni

duplicate = 10001

with open('books.csv', encoding="utf8") as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            line_count += 1
        elif line_count in (22, 23, 24): # az Egri csillagok id-jének átugrása
            line_count += 1
        else:
            id_ = row[0]
            author = row[7]
            title = row[10]
            publisher = publishers[random.randrange(0, 15)]
            year = 0
            if row[8] != '':
                year = int(float(row[8]))

            edition = random.randrange(0, 6)
            isbn = row[5]
            borrow = 1

            curs.execute("INSERT INTO books(id, author, title, publisher, year, edition, isbn, borrow) VALUES (?, ?, ?, ?, ?, ?, ?, ?);", (id_, author, title, publisher, year, edition, isbn, borrow))

            # 10%-os eséllyel mégegyszer bekerül a könyv
            chance = random.randrange(0, 101)
            while(chance <= 10):
                id_ = duplicate
                duplicate += 1
                curs.execute("INSERT INTO books(id, author, title, publisher, year, edition, isbn, borrow) VALUES (?, ?, ?, ?, ?, ?, ?, ?);", (id_, author, title, publisher, year, edition, isbn, borrow))
                chance = random.randrange(0, 101)
            
            # print(f'id: {id_} | author: {author} | title: {title} | publisher: {publisher} | year: {year} | edition: {edition} | isbn: {isbn} | borrow: {borrow}')
            line_count += 1
    conn.commit()
    curs.close()
    conn.close()
    print(f'Processed {line_count} lines.')
    
