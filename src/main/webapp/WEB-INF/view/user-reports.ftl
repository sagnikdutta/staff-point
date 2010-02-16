<#-- @ftlvariable name="amount" type="int" -->
<#-- @ftlvariable name="itemsPerPage" type="int" -->
<#-- @ftlvariable name="pageNo" type="int" -->
<#-- @ftlvariable name="reports" type="java.util.List<ru.point.model.Report>" -->
<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="user" type="ru.point.model.User" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>${user.fullName}</@head>
<body>

<div id="highlight">
</div>

<div class="header">
        <span>
            <a href="/">Главная</a> &rarr;
            <@projectRef project=user.mainActivity.project /> &rarr;
            <@userRef user=user />
        </span>
    <@search/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top"><#if session?? && user.id = session.user.id>Я, </#if>${user.fullName}<span
                class="tip red"></span></h1>
        <span class="position">Конструктор отчётов</span>

        <div id="subnav">
            <ul class="nav">
                <li><a href="/user/${user.id}">Инфомация</a></li>
                <li><a class="selected" href="/user/report/${user.id}">Активности</a></li>
                <li><a href="/user/team/${user.id}">Команда</a></li>
                <#if session?? && user.id = session.user.id>
                <li><a href="/user/edit/${user.id}">Редактировать</a></li>
                </#if>
            </ul>
        </div>
    </div>

    <#if session?? && user.id = session.user.id && pageNo == 0 >
    <h1>Добавить новый отчет<span class="tip red"></span></h1>

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
    <a id="newWeek" class="action right" href="#" onclick="return false;">Ещё неделя &darr;</a>
    <a id="clearReport" class="action right" href="#" rel="${user.mainActivity.id}" onclick="return false;">Очистить</a>

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

        <h1 id="past">Прошлые отчёты <span class="tip red"></span></h1>

        <ul id="reportList" class="report">
            <#list reports as report>
            <li <#if session?? && user.id = session.user.id>class="hasHidden"</#if>><@period report=report />
                ${report.text?html?replace("\n", "<br/>")}
                <a href="#" class="hidden pseudo delete right" rel="/report/delete/${report.id}"
                   onclick="return false;">удалить</a></li>
            </#list>
        </ul>

        <h3>&nbsp;</h3>

        <#if (pageNo > 0)>
        <a class="action" href="/user/report/${user.id}/page/${pageNo - 1}">&larr;&nbsp;более свежие&nbsp;</a>
        </#if>

        <#if (amount > (pageNo + 1) * itemsPerPage)>
        <a class="action right" href="/user/report/${user.id}/page/${pageNo + 1}">&nbsp;менее свежие&nbsp;&rarr;</a>
        </#if>
    </div>
    <@foot/>
</body>
</html>
