import requests
from bs4 import BeautifulSoup
import json
# noinspection PyUnresolvedReferences
from schedule_parser import rvoid


def refresh(url):
    def get_html(url):
        r = requests.get(url, headers={
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                          'Chrome/80.0.3987.149 Safari/537.36 OPR/67.0.3575.115'})  # Получаем метод Response
        r.encoding = 'utf8'  # Кодировка
        return r.text  # Вернем данные объекта text

    page = get_html(url)

    soup = BeautifulSoup(''.join(page), features="lxml")
    divs = soup.findAll('div')

    find_block = None
    for i in divs:
        if i.get_attribute_list('class')[0] == 'find_block':
            find_block = i

    columns = rvoid(list(find_block.children))
    table = []
    for h in range(len(columns)):
        table.append({})
        table[h].update({'head': columns[h].div.text})
        hrefs = columns[h].findAll('a')
        for i in hrefs:
            text = i.get_attribute_list('href')[0]
            out = text.split('&')
            table[h][i.text] = out[-1]

    with open('groups.json', 'w', encoding='utf-8') as f:
        json.dump(table, f, ensure_ascii=False)

# refresh('https://kbp.by/rasp/timetable/view_beta_kbp/')
