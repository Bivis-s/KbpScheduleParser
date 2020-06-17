import requests
from bs4 import BeautifulSoup
import json
import sys

url = 'https://kbp.by/rasp/timetable/view_beta_kbp/?page=stable&cat=group&id=16'


def remove_void(lst):  # перебираем список и делаем новый только с bs4 Tag'ами
    post_lst = []
    for i in lst:
        if str(i.__class__) == "<class 'bs4.element.Tag'>":
            post_lst.append(i)
    return post_lst


def get_html(link):  # Возвращает bs4 объект страницы
    r = requests.get(link, headers={
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                      'Chrome/80.0.3987.149 Safari/537.36 OPR/67.0.3575.115'})
    r.encoding = 'utf8'  # Кодировка
    bs4_page = BeautifulSoup(r.text, features="lxml")  # создаёт bs4 объект из строки
    bs4_page.prettify()
    return bs4_page


page = get_html(url)


def get_left_week(bs4_page):
    bs4_page = bs4_page.find('div', id='left_week')
    try:
        bs4_page = bs4_page.find('table')
    except AttributeError:
        print('ERROR there is no left week. ///', sys._getframe())
        return None
    return bs4_page


left_week = get_left_week(page)


def make_children_list(bs4_page):
    try:
        children_list = list(bs4_page.children)  # создаёт список потомков bs4_page
        children_list = remove_void(children_list)  # удаляет символы новой строки в bs4 NavigableString из списка
        return children_list  # возвращает только bs4 Tag'и
    except AttributeError:
        print("It's look like you tried to get children from non-bs4 obj", sys._getframe())
        return None


td_lines = make_children_list(left_week)[2:]  # отрезается строка с днями недели и чекбоксами замен


def make_sells(lst):
    table = []
    for i in range(len(lst)):  # создаёт таблицу, стостоящую из строк и пар в них
        sells = make_children_list(lst[i])[1:-1]  # разбивает строку на ячейки, удаляет первый и последний элемент
        # (это номера строк)
        table.append(sells)
    for h in range(len(table)):
        for i in range(len(table[h])):
            table[h][i] = make_children_list(table[h][i])
    for h in range(len(table)):  # удаляет пустые и удалённые пары из ячейки
        for i in range(len(table[h])):
            for u in range(len(table[h][i])):
                classes = table[h][i][u].get_attribute_list('class')
                if 'removed' in classes or 'empty-pair' in classes:
                    table[h][i][u] = None

    return table


# make_sells(td_lines)[3][2]
print(make_sells(td_lines)[3][2])
# print(make_sells(td_lines)[3])
