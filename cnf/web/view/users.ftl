<#-- @ftlvariable name="users" type="java.util.List<ru.point.model.User>" -->
<#-- @ftlvariable name="users" type="java.util.List<ru.point.model.User>" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>Поиск, найдено?</@head>
<body>
<div class="header">
        <span>
            <a href="/">Главная</a> &rarr; Поиск
        </span>

    <@search/>
    <@login/>
</div>
<div class="box one">
    <h1 class="top">
        Найдено:
    </h1>
    Вид:
    <ul class="control">
        <li><a href="#">По алфавиту</a></li>
        <li><a href="#">По позиции</a></li>
    </ul>
    <table id="people-list">
        <#list users as person>
        <tr>
            <th><@userRef user=person/></th>
            <td><#list person.activities as pos>${pos.getName()}<br/></#list></td>
        </tr>
        </#list>
    </table>
</div>
<@foot />
</body>
</html>
