import json

with open('groups.json', 'r', encoding='utf-8') as tbl:
    group_file = json.loads(tbl.read())

val = group_file[0].keys()
val_column = '\n'.join(list(val)[1:])
print(val)