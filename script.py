import pandas as pd
from getpass import getpass
from mysql.connector import connect, Error


def addData(column, insert_reviewers_query):
    data = pd.read_excel(r'W:\Diplom\db\botanic_info.xlsx')
    df = pd.DataFrame(data, columns=[column])
    reviewers_records = []
    for col in df:
        for x in df[col].unique():
            if pd.isna(x) == 0:
                reviewers_records.append((0, x))
    try:
        with connect(
            host="localhost",
            user="root",
            password="root",
            database="xyloteka_db",
        ) as connection:

            with connection.cursor() as cursor:
                cursor.executemany(insert_reviewers_query,
                                   reviewers_records)
                connection.commit()
                print(f"{column} insert into db")
    except Error as e:
        print(e)


def addTwoColumnData(column1, column2, insert_reviewers_query):
    data = pd.read_excel(r'W:\Diplom\db\botanic_info.xlsx')
    df = pd.DataFrame(data, columns=[column1, column2])
    reviewers_records = []
    for rows in df.values:
        if pd.isna(rows[0]) == 0 & pd.isna(rows[1]) == 0:
            reviewers_records.append((0, rows[0], rows[1]))
    try:
        with connect(
            host="localhost",
            user="root",
            password="root",
            database="xyloteka_db",
        ) as connection:
            with connection.cursor() as cursor:
                cursor.executemany(insert_reviewers_query,
                                   reviewers_records)
                connection.commit()
            print(f"{column1}, {column2} insert into db")
    except Error as e:
        print(e)


addData("Отдел", """INSERT INTO division (id, name) VALUES ( %s, %s ) """)
addData("Класс", """INSERT INTO classis (id, name) VALUES ( %s, %s ) """)
addData("Семейство", """INSERT INTO family (id, name) VALUES ( %s, %s ) """)
addData("Порядок", """INSERT INTO ordo (id, name) VALUES ( %s, %s ) """)
addData("Род", """INSERT INTO genus (id, name) VALUES ( %s, %s ) """)
addData("Отдел", """INSERT INTO division (id, name) VALUES ( %s, %s ) """)
addData("Торговое и другие названия",
        """INSERT INTO names (id, name_trade) VALUES ( %s, %s ) """)
addTwoColumnData("Вид (латынь)", "Вид (русское)",
                 """INSERT INTO species (id, name_lat, name_rus) VALUES ( %s, %s, %s ) """)
print('Success')
# print(f'insert into {table}')
