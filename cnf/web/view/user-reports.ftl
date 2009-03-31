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
        <h1 class="top">Время &mdash; Деньги<span class="tip red"></span></h1>
        <span class="position">Конструктор отчётов</span>

        <div id="subnav">
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
    <h1>Выбор периода<span class="tip red"></span></h1>

    <div class="datepick">
        <div class="row days">
            <div class="column">Week</div>
            <div class="column">Monday</div>
            <div class="column">Tuesday</div>
            <div class="column">Wednesday</div>
            <div class="column">Thursday</div>
            <div class="column">Friday</div>
        </div>
        <div class="row">
            <div class="column week">W29/2009</div>
            <div id="d20090302" class="column day">2 марта</div>
            <div id="d20090303" class="column day">3 марта</div>
            <div id="d20090304" class="column day">4 марта</div>
            <div id="d20090305" class="column day">5 марта</div>
            <div id="d20090306" class="column day">6 марта</div>
        </div>
        <div class="row">
            <div class="column week">W28/2009</div>
            <div id="d20090309" class="column day">9 марта</div>
            <div id="d20090310" class="column day">10 марта</div>
            <div id="d20090311" class="column day">11 марта</div>
            <div id="d20090312" class="column day">12 марта</div>
            <div id="d20090313" class="column day">13 марта</div>
        </div>
        <div class="clear"></div>
        <textarea id="reportText" cols="60" rows="10" class="cleanOnFocus">текст нового отчета</textarea>
        <a id="reportSubmit" rel="${user.mainActivity.id}" href="#past">Добавить</a>
    </div>

    </#if>

    <h1 id="past">Прошлые отчёты<span class="tip red"></span></h1>

    <ul>
        <#list reports as report>
        <li>${report.text}</li>
        </#list>
    </ul>
</div>
<@foot/>
</body>
</html>
