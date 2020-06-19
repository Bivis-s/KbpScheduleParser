import json
from discord.ext import commands
import discord
import time

import group_parser
import string_from_json_table
import schedule_parser_better


# возвращает сообщение без команды
def getClear(message):
    if message.find(' ') != -1:
        return message[message.find(' ') + 1:]
    else:
        return empty_content


second_mark = 0
unstop = True


def anti_spam():
    global second_mark
    global unstop

    first_mark = time.time()
    if unstop or first_mark - second_mark >= 3:
        second_mark = time.time()
        unstop = False
        return True
    else:
        return False


client = commands.Bot(command_prefix='-')
empty_content = 'Давай попробуем ещё раз'
no_group = 'Укажите группу'
group_num = no_group
group_id = 'Т-791'
GROUPS_URL = 'https://kbp.by/rasp/timetable/view_beta_kbp/'
SCHEDULE_URL = 'https://kbp.by/rasp/timetable/view_beta_kbp/?page=stable&cat=group&'


@client.event
async def on_ready():
    print('Бот готов')


@client.command(pass_conetext=True)
async def t(ctx):
    commands_list = getClear(ctx.message.content)
    print(commands_list, type(commands_list))
    await ctx.send(commands_list)


@client.command(pass_conetext=True)
async def g(ctx):  # устанавливает валидную группу в глобальную переменную
    global group_num
    global group_id
    commands_list = getClear(ctx.message.content)
    commands_list_splited = commands_list.split()  # разбиваем команду на слова, например [set, group]

    if commands_list_splited[0] == 'set' and len(commands_list_splited) >= 2:  # если команда -g set [group]
        if anti_spam():  # антиспам
            group_parser.refresh(GROUPS_URL)  # обновляет файл groups.json
            with open('groups.json', 'r', encoding='utf-8') as tbl:
                group_file = json.loads(tbl.read())

            for i in range(len(group_file)):  # валидатор группы
                val = list(group_file[i].keys())
                this_group = commands_list_splited[1].upper()
                if this_group in val:  # проверяем валидность введенной группы и записываем её id в group_id
                    group_num = commands_list_splited[1].upper()
                    group_id = group_file[i][this_group]
                    break
                else:
                    group_num = no_group

            if group_num == no_group:  # ответ в зависимости от валидации
                await ctx.send('Странная группа, ' + str(commands_list_splited[1]))
            else:
                await ctx.send(f'Группа {group_num} установлена ' + str(group_id))
        else:  # антиспам
            await ctx.send('Воу, воу, не так быстро')

    elif commands_list == empty_content:  # если команда просто -g
        if anti_spam():  # антиспам
            group_parser.refresh(GROUPS_URL)  # обновляет файл groups.json
            with open('groups.json', 'r', encoding='utf-8') as tbl:
                group_file = json.loads(tbl.read())
                answer = discord.Embed(title='Доступные группы', description='Введите команду -g set [номер группы]',
                                       url=GROUPS_URL)
                k = 0
                for i in group_file:  # выводит колонки с группами
                    val = group_file[k].keys()
                    val_column = '\n'.join(list(val)[1:])
                    answer.add_field(name=i['head'], value=str(val_column))
                    k += 1
            await ctx.send(embed=answer)  # выводит ембед-таблицу с группами
        else:  # антиспам
            await ctx.send('Воу, воу, не так быстро')
    else:  # если команда невалидна
        await ctx.send('Чтобы указать группу введите команду -g set [номер группы], чтобы установить группу или -g, '
                       'чтобы посмотреть список доступных групп ')


@client.command(pass_conetext=True)
async def s(ctx):  # выводит форматированное расписание в виде embed-столбцов
    if group_num == no_group:
        await ctx.send('Чтобы указать группу введите команду -g set [номер группы], чтобы установить группу или -g, '
                       'чтобы посмотреть список доступных групп')
    else:
        if anti_spam():  # антиспам задержка 3 секунды
            schedule_parser_better.refresh(SCHEDULE_URL + group_id)
            with open('table.json', 'r', encoding='utf-8') as tbl:
                table_file = json.loads(tbl.read())
            week_str_list = string_from_json_table.make_week_string_list(table_file)

            answer = discord.Embed(title='Расписание', description=f'{group_num}', url=(SCHEDULE_URL + group_id))
            for i in range(len(week_str_list)):
                pairs = len(week_str_list[i]) - 2
                ending = ''
                if pairs in [4, 3, 2]:  # проверка на окончание
                    ending = 'ы'
                elif pairs == 1:
                    ending = 'а'

                answer.add_field(name=week_str_list[i][0],
                                 value=("\n".join(week_str_list[i][1:]) + '\nВсего ' + str(pairs) + f' пар{ending}'))

            await ctx.send(embed=answer)
        else:
            await ctx.send('Воу, воу, не так быстро')


client.run('your_token')
