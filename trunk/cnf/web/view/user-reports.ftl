<#-- @ftlvariable name="reports" type="java.util.List<ru.point.model.Report>" -->
<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="user" type="ru.point.model.User" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head></@head>
<body>
<div class="header">
        <span>
            <a href="/">Главная</a> &rarr;
            <@projectRef project=user.mainActivity.project /> &rarr;
            <@userRef user=user />
        </span>
    <@search/>
    <@loginForm session=session/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top"><#if session?? && user.id = session.user.id>Я, </#if>${user.fullName}<span
                class="tip red"></span></h1>
        <span class="position">Конструктор отчётов</span>

        <div id="subnav" class="nav">
            <ul>
                <li><a href="/user/${user.id}">Инфомация</a></li>
                <li><a class="selected" href="/user/report/${user.id}">Активности</a></li>
                <li><a href="/user/team/${user.id}">Команда</a></li>
                <#if session?? && user.id = session.user.id>
                <li><a href="/user/edit/${user.id}">Редактировать</a></li>
                </#if>
            </ul>
        </div>
    </div>

    <#if session?? && user.id = session.user.id>
    <h1><a href="#datepick" class="toggle" rel="datepick">Добавить новый отчет<span class="tip red"></span></a></h1>

    <div id="datepick" class="datepick">
        <div class="row days">
            <div class="column">Период отчёта</div>
            <div class="column">Понедельник</div>
            <div class="column">Вторник</div>
            <div class="column">Среда</div>
            <div class="column">Четверг</div>
            <div class="column">Пятница</div>
        </div>
    </div>
    <a id="newWeek" class="action right" href="#">Ещё неделя &darr;</a>

    <div class="reportText">
        <span class="position">Текст отчёта</span>
        <textarea id="reportText" cols="60" rows="10"></textarea>
        <select>
            <option value="${user.mainActivity.id}">${user.mainActivity.role.name}
                в ${user.mainActivity.project.name}</option>
            <#if (user.activities?size > 1) >
            <optgroup label="Все активности">
                <#list user.activities as activity>
                <option value="${activity.id}">${activity.role.name} в ${activity.project.name}</option>
                </#list>
            </optgroup>
            </#if>
        </select>
        <a id="reportSubmit" class="action right" href="#" rel="${user.mainActivity.id}">Отправить</a>
    </div>

    </#if>

    <div>

        <h1 id="past">Прошлые отчёты<span class="tip red"></span></h1>

        <ul id="reportList">
            <#list reports as report>
            <li>${report.start.time?date} ~ ${report.end.time?date}:<br/>
                ${report.text?html?replace("\n", "<br/>")} </li>
            </#list>
        </ul>
    </div>
    <@foot/>
</body>
</html>
