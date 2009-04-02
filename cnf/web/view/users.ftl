<#-- @ftlvariable name="users" type="java.util.List<ru.point.model.User>" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>Поиск, ${users?size} найдено</@head>
<body>
<div class="header">
        <span>
            <a href="/">Главная</a> &rarr; Поиск
        </span>
    <@search/>
    <@loginForm session=session/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top">Поиск Людей<span class="tip red"></span></h1>
        <span class="position">По вашему запросу найдено ${users?size} людей</span>

        <div id="subnav" class="nav">
            <ul>
                <li><a href="#">По алфавиту</a></li>
                <li><a href="#">По роли</a></li>
                <li><a href="#">По проектам</a></li>
            </ul>
        </div>
    </div>

    <h1>Результаты<span class="tip red"></span></h1>

    <table id="people-list">
        <#list users as person>
        <tr>
            <td style="width: 15em; "><@userRef user=person/></td>
            <td><#list person.getActivities() as pos>${pos.getRole().getName()}<br/></#list></td>
        </tr>
        </#list>
    </table>
</div>
<@foot />
</body>
</html>
