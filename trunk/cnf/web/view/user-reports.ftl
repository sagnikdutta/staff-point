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
            <a href="/">�������</a> &rarr;
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
        <h1 class="top">����� &mdash; ������<span class="tip red"></span></h1>
        <span class="position">����������� �������</span>

        <div id="subnav">
            <ul>
                <li><a href="/user/${user.id}">���������</a></li>
                <li><a class="selected" href="/user/report/${user.id}">����������</a></li>
                <li><a href="/user/team/${user.id}">�������</a></li>
                <#if session?? && user.id = session.user.id>
                <li><a href="/user/edit/${user.id}">�������������</a></li>
                </#if>
            </ul>
        </div>
    </div>

    <#if session?? && user.id = session.user.id>
    <h1>����� �������<span class="tip red"></span></h1>

    <div class="datepick">
        <div class="row days">
            <div class="column">Week</div>
            <div class="column">Monday</div>
            <div class="column">Tuesday</div>
            <div class="column">Wednesday</div>
            <div class="column">Thursday</div>
            <div class="column">Friday</div>
        </div>
    </div>
    <a id="newWeek" class="action right" href="#">��� ������ &darr;</a>

    <div class="reportText">
        <textarea id="reportText" cols="60" rows="10"></textarea>
        <select>
            <option value="${user.mainActivity.id}">${user.mainActivity.role.name} � ${user.mainActivity.project.name}</option>
            <#if (user.activities?size > 1) >
            <optgroup label="��� ����������">
                <#list user.activities as activity>
                <option value="${activity.id}">${activity.role.name} � ${activity.project.name}</option>
                </#list>
            </optgroup>
            </#if>
        </select>
        <a id="reportSubmit" class="action right" href="#past" rel="${user.mainActivity.id}">���������</a>
    </div>

    </#if>

    <div>

        <h1 id="past">������� ������<span class="tip red"></span></h1>

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
