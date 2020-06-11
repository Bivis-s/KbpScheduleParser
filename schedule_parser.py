import requests
from bs4 import BeautifulSoup
import json


def rvoid(lst):  # перебираем список и делаем новый только с Tag'ами, разбиваем по строкам
    postlist = []
    for i in lst:
        if str(i.__class__) == "<class 'bs4.element.Tag'>":
            postlist.append(i)
    return postlist  # возвращаем список строк


def refresh(url):
    def get_html(url):
        r = requests.get(url, headers={
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                          'Chrome/80.0.3987.149 Safari/537.36 OPR/67.0.3575.115'})  # Получаем метод Response
        r.encoding = 'utf8'  # Кодировка
        return r.text  # Вернем данные объекта text

    page = get_html(url)

    soup = BeautifulSoup(''.join(page), features="lxml")
    sdiv = soup.find('div', id='left_week')
    table = sdiv.find('table')

    lsty = list(table.children)  # создаём список, внутри чередуются пустые NavigableString и Tag

    def separate(lst):  # создаёт вложенный список со строкми и днями [строка][пара]

        strings = rvoid(lst)  # разбиваем на строки
        daystring = []
        k = 0
        for i in strings:  # разбиваем на дни
            daystring.append(rvoid(list(rvoid(strings)[k].children)))
            k += 1
        return daystring  # возвращаем список строк со списком дней

    grid = separate(lsty)

    def is_weeked(pair):
        for i in pair.get_attribute_list('class'):
            if i == 'week1':
                return 1
            elif i == 'week2':
                return 2
        else:
            return 0

    def is_added(pair):  # является ли юнит добавленным
        for i in pair.get_attribute_list('class'):
            if i == 'added':
                return True
        else:
            return False

    def is_removed(pair):  # является ли юнит удалённым
        for i in pair.get_attribute_list('class'):
            if i == 'removed':
                return True
        else:
            return False

    def del_removed(lst):
        k = 0
        while k < len(lst):
            if is_removed(lst[k]):
                del lst[k]
                try:
                    return lst[0]
                except IndexError:
                    return None
            elif is_added(lst[k]):
                return lst[k]
            k += 1
        # БЛЯТЬ, у них в одной ячейке находится 2 пары: одна удалённая, другая действительная и мне в пизду как-то
        try:
            try:
                return lst[1]
            except IndexError:
                return lst[0]
        except IndexError:
            return None

    def normalize_grid(grid):  # убирает удалённые пары, возвращает таблицу
        k = 0
        web = [[]]
        for h in grid:
            web.append([])
            for i in h:
                web[k].append(i)
            k += 1
        del web[0]  # первые две строки не нужны
        del web[0]  # первые две строки не нужны

        k = 0
        betterGrid = []
        # g = 0
        for h in web:
            betterGrid.append([])
            # l = 0
            for i in h:
                sep = rvoid(list(i.children))
                # print(sep, g, l)
                # l += 1
                # print(delRemoved(sep))
                betterGrid[k].append(del_removed(sep))
            # g += 1
            k += 1

        del betterGrid[len(betterGrid) - 1]  # почему-то тут есть лишний список, КоСтЫлЁк, ага

        bestGrid = []
        k = 0
        while k < len(betterGrid[0]):  # переворачивают матрицу на 90 градусов 1
            bestGrid.append([])
            k += 1

        k = 0
        f = 0
        while f < len(betterGrid[0]):  # переворачивают матрицу на 90 градусов 2
            y = 0
            while y < len(betterGrid):
                bestGrid[k].append(betterGrid[y][k])
                y += 1
            k += 1
            f += 1

        del bestGrid[0]
        del bestGrid[len(bestGrid) - 1]

        k = 0

        while k < len(bestGrid):
            f = 0
            while f < len(bestGrid[k]):
                if bestGrid[k][f].get_attribute_list('class')[0] == 'empty-pair':
                    bestGrid[k][f] = None
                f += 1
            k += 1
        return bestGrid

    normGrid = normalize_grid(grid)

    def make_unit(div):  # создаёт словать конкретной пары с 6 ключами
        def to_text(lst):  # функция приводит список бс.тэгов к списку строк
            textlist = [some.text for some in lst]
            return textlist

        keys = ['subject', 'teacher', 'place']
        unit = {'subject': None, 'teacher': None, 'place': None, 'added': False, 'week': None}

        if div is not None:
            divs = rvoid(list(div.children))
            k = 0
            if is_added(div):
                unit['added'] = True
            unit['week'] = is_weeked(div)
            for h in divs:
                if str(div.__class__) == "<class 'bs4.element.Tag'>":  # проверка на тэг
                    for i in unit:
                        arg = to_text(divs[k].findAll('div', f'{i}'))
                        if bool(arg):
                            if len(arg) == 1 or arg[1] == '':
                                unit[f'{i}'] = arg[0].strip()
                            else:
                                unit[f'{i}'] = arg

                k += 1
            if unit['subject'] == 'Пара снята':
                return None
            else:
                return unit
        else:
            return None

    web = []
    k = 0
    while k < len(normGrid):
        web.append([])
        y = 0
        while y < len(normGrid[k]):
            web[k].append(make_unit(normGrid[k][y]))
            y += 1
        k += 1
    print(web)

    with open('table.json', 'w', encoding='utf-8') as f:
        json.dump(web, f, ensure_ascii=False)

# refresh('https://kbp.by/rasp/timetable/view_beta_kbp/?cat=group&id=51')
