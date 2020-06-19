import requests
from bs4 import BeautifulSoup
import json
import sys


def remove_void(lst):  # перебираем список и делаем новый только с bs4 Tag'ами
    post_lst = []
    for i in lst:
        if str(i.__class__) == "<class 'bs4.element.Tag'>":
            post_lst.append(i)
    return post_lst


def remove_None(lst):  # удаляет пустые пары из ячеек
    k = True
    while k:
        k = False
        for i in lst:
            if i is None:
                lst.remove(i)
                k = True


def rotate_table_90(old_table):  # Меняет строки и столбцы местами
    new_table = []
    for i in range(len(old_table[0])):  # создаёт строки из столбцов
        new_table.append([])
    k = 0
    while k < len(old_table[0]):  # заполняет строки столбцами
        y = 0
        while y < len(old_table):
            new_table[k].append(old_table[y][k])
            y += 1
        k += 1
    return new_table


def get_html(link):  # Возвращает bs4 объект страницы
    r = requests.get(link, headers={
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/80.0.3987.149 Safari/537.36 OPR/67.0.3575.115'})
    r.encoding = 'utf8'  # Кодировка
    bs4_page = BeautifulSoup(r.text, features="lxml")  # создаёт bs4 объект из строки
    bs4_page.prettify()
    return bs4_page


def get_left_week(bs4_page):  # возвращает bs4-элемент с id "left_week"
    bs4_page = bs4_page.find('div', id='left_week')
    try:
        bs4_page = bs4_page.find('table')
    except AttributeError:
        print('ERROR there is no left week. ///', sys._getframe())
        return None
    return bs4_page


def make_children_list(bs4_page):  # возвращает список потомков bs4-элемента (без пустых строк, всё кошерно)
    try:
        children_list = list(bs4_page.children)  # создаёт список потомков bs4_page
        children_list = remove_void(children_list)  # удаляет символы новой строки в bs4 NavigableString из списка
        return children_list  # возвращает только bs4 Tag'и
    except AttributeError:
        print("It's looks like you tried to get children from non-bs4 obj", sys._getframe())
        return None


def make_sells(lst):  # возвращает таблицу по дням с парами в каждой ячейке
    table = []
    for i in range(len(lst)):  # создаёт таблицу, стостоящую из строк и пар в них
        sells = make_children_list(lst[i])[1:-1]  # разбивает строку на ячейки, удаляет первый и последний элемент
        # (это номера строк в расписании)
        table.append(sells)
    for h in range(len(table)):
        for i in range(len(table[h])):
            table[h][i] = make_children_list(table[h][i])
    for h in range(len(table)):  # удаляет пустые и удалённые пары из ячейки
        for i in range(len(table[h])):
            for u in range(len(table[h][i])):
                classes = table[h][i][u].get_attribute_list('class')
                if 'removed' in classes or 'empty-pair' in classes:  # заменяет пустые и удалённые пары в ячейке на None
                    table[h][i][u] = None
                if len(table[h][i]) == 1 and table[h][i][0] is None:  # заменяет пустые одиночные ячейки на None
                    table[h][i] = None

    for h in table:  # удаляет пустые и удалённые пары из ячейки
        for i in h:
            if isinstance(i, list):
                remove_None(i)

    return rotate_table_90(table)


def make_unit(sell):  # создаёт словарь с параметрами пары. Значениями могут быть строки, списки(учителя), None, bool
    if sell is not None:
        pairs = []
        for pair in sell:
            unit = {'subject': None, 'teacher': None, 'place': None, 'added': False, 'extra': None, 'group': None}
            if 'added' in pair.get_attribute_list('class'):  # ищет added в html классе пары
                unit['added'] = True

            divs = pair.findAll('div')  # получаем список всех дивов в паре
            for i in unit:  # перебираем дивы и присваиваем совпадения ключам словаря
                attributes = []
                for h in divs:
                    if i in h.get_attribute_list('class'):
                        if h.text != '':
                            attributes.append(h.text)
                if len(attributes) > 1:
                    unit[i] = attributes
                elif attributes:
                    unit[i] = attributes[0]
            pairs.append(unit)
            if unit['subject'] == 'Пара снята':
                return None
        return pairs
    else:
        return None


def make_unit_sells(sells):
    table = []
    for i in range(len(sells)):
        table.append([])
        for h in sells[i]:
            table[i].append(make_unit(h))
    return table


def refresh(url):
    page = get_html(url)
    left_week = get_left_week(page)
    td_lines = make_children_list(left_week)[2:]  # отрезается строка с днями недели и чекбоксами замен
    pair_sells = make_sells(td_lines)
    unit_sells = make_unit_sells(pair_sells)
    with open('table.json', 'w', encoding='utf-8') as f:
        json.dump(unit_sells, f, ensure_ascii=False)
