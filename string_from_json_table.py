import json


def make_text_unit(units):  # создаёт строку из списка с словарями, аргументом является table_file[x][y]
    if units is None:
        return None
    else:
        string = ''
        for unit in units:
            string += '**' + str(unit['subject']) + '**' + '\n'  # добавление предмета

            if str(type(unit['teacher'])) == "<class 'list'>":  # добавление учителя/учителей
                string += str(', '.join(unit['teacher'])) + '\n'
            elif bool(unit['teacher']):
                string += str(unit['teacher']) + '\n'

            if bool(unit['place']):
                string += 'Каб. ' + str(unit['place']) + '\n'

            if bool(unit['added']):
                string += 'Замена\n'

            # if unit['extra'] is not None: # добавление 1/2 недели, если есть
            #     string += f'{unit["extra"]}\n'
        string += '----------'

        return string


def make_week_string_list(table):
    week = ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота']
    w_list = []
    for h in range(len(table)):
        w_list.append([week[h]])
        k = 0
        try:  # ищет первую пару и выводит её номер
            while table[h][k] is None:
                k += 1
        except IndexError:
            w_list[h].append('День свободен!\n')
        else:
            w_list[h].append('К ' + str(k + 1) + ' паре\n')

        for i in range(len(table[h])):  # создаёт по списку с парами для каждого дня, w_list[n][0] строка с днём недели
            if table[h][i] is not None:
                w_list[h].append(make_text_unit(table[h][i]))
    return w_list


if __name__ == "__main__":
    with open('table.json', 'r', encoding='utf-8') as tbl:
        table_file = json.loads(tbl.read())

    print(make_week_string_list(table_file))
