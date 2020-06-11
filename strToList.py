import json

with open('table.json', 'r', encoding='utf-8') as tbl:
    table_file = json.loads(tbl.read())


def make_text_unit(unit):
    if unit is None:
        return None
    string = ''

    string += '**' + str(unit['subject']) + '**' + '\n'

    if str(type(unit['teacher'])) == "<class 'list'>":
        string += str(', '.join(unit['teacher'])) + '\n'
    else:
        string += str(unit['teacher']) + '\n'

    if bool(unit['place']):
        string += 'Каб. ' + str(unit['place']) + '\n'

    if bool(unit['added']):
        string += 'Замена\n'

    if unit['week'] == 1:
        string += '1 неделя\n'
    elif unit['week'] == 2:
        string += '2 неделя\n'

    string += '----------'

    return string


def make_week_string_list(table):
    week = ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота']
    w_list = []
    for h in range(len(table)):
        w_list.append([week[h]])
        k = 0
        try:
            while table[h][k] is None:
                k += 1
        except IndexError:
            w_list[h].append('День свободен!\n')
        else:
            w_list[h].append('К ' + str(k + 1) + ' паре\n')

        for i in range(len(table[h])):
            if make_text_unit(table[h][i]) is not None:
                w_list[h].append(make_text_unit(table[h][i]))
    return w_list
