import json
import psycopg2
import os

username = 'kpi_bd1_234_user1'
password = 'asdfhjkl123'
database = 'stackoverflow'
host = 'kpi-bd1-234-postgres1.c6giv6zpxbc1.eu-central-1.rds.amazonaws.com'
port = '5432'

conn = psycopg2.connect(user=username, password=password, dbname=database, host=host, port=port)

data = {}
with conn:

    cur = conn.cursor()

    for table in ('users', 'posts', 'comments', 'votes', 'vote_type', 'votes_posts', 'votes_comments'):
        cur.execute('SELECT * FROM ' + table)
        rows = []
        fields = [x[0] for x in cur.description]

        for row in cur:
            rows.append(dict(zip(fields, row)))

        data[table] = rows

out_path = os.environ['HOME'] + '/export/'
with open(out_path + 'stackoverflow.json', 'w') as outf:
    json.dump(data, outf, default = str) 